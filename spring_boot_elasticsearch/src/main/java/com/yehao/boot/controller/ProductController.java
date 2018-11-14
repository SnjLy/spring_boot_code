package com.yehao.boot.controller;

import com.yehao.boot.model.ProductModel;
import com.yehao.boot.service.ProductRepository;
import com.yehao.boot.utils.IdUtil;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Package: com.yehao.boot.controller
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/10/12.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //每页数量
    private static final Integer PAGESIZE = 10;

    @GetMapping("save")
    public String save() {
        ProductModel goodsInfo = new ProductModel(IdUtil.generateUUID(), "productName");
        goodsInfo.setDescription("第一个尝试作品");
        productRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/delete?id=1525415333329
    @GetMapping("delete")
    public String delete(long id) {
        productRepository.deleteById(id);
        return "success";
    }

    //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id, String name, String description) {
        ProductModel goodsInfo = productRepository.findById(id).get();
        goodsInfo.setName(name);
        goodsInfo.setDescription(description);
        goodsInfo.setUpdateTime(new Date());
        productRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/product/getOne?id=1539343664536
    @GetMapping("getOne")
    public ProductModel getOne(long id) {
        Optional<ProductModel> goodsInfo = productRepository.findById(id);

        return goodsInfo.get();
    }


    //http://localhost:8888/getGoodsList?query=商品
    //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
    //根据关键字"商品"去查询列表，name或者description包含的都查询
    @GetMapping("getGoodsList")
    public List<ProductModel> getList(Integer pageNumber, String query) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        //es搜索默认第一页页码是0


        SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        Page<ProductModel> goodsPage = productRepository.search(searchQuery);
        return goodsPage.getContent();
    }


    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String query) {
            /*FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                    .add(QueryBuilders.matchPhraseQuery("name", query),
                            ScoreFunctionBuilders.weightFactorFunction(100))
                    .add(QueryBuilders.matchPhraseQuery("description", query),
                            ScoreFunctionBuilders.weightFactorFunction(100))
                    //设置权重分 求和模式
                    .scoreMode("sum")
                    //设置权重分最低分
                    .setMinScore(10);*/


        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("name", query));
        ScoreFunctionBuilder<?> scoreFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("id")
                .modifier(FieldValueFactorFunction.Modifier.LN1P)
                .factor(0.1f);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder, scoreFunctionBuilder)
                .boostMode(CombineFunction.SUM).setMinScore(10);

        // 设置分页
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }



}
