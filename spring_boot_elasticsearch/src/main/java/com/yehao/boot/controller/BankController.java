package com.yehao.boot.controller;

import com.yehao.boot.model.BankModel;
import com.yehao.boot.service.BankRepository;
import org.apache.lucene.queryparser.xml.builders.DisjunctionMaxQueryBuilder;
import org.apache.lucene.search.DisjunctionMaxQuery;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;

/**
 * @Package: com.yehao.boot.controller
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/11/2.
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankRepository bankRepository;

    @RequestMapping("/getById")
    public BankModel getById(@RequestParam("id") Long id) {
        return bankRepository.findById(id).get();
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public BankModel saveBank(BankModel bank) {
        return bankRepository.save(bank);
    }

    @RequestMapping("/searchByQuery")
    public List<BankModel> searchByQuery(@RequestParam("query") String query) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(query);
        Iterable<BankModel> searchResult = bankRepository.search(builder);
        Iterator<BankModel> iterator = searchResult.iterator();
        ArrayList<BankModel> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }


    public List<BankModel> search(@RequestParam("query") String query,
                                  @RequestParam(value = "size", defaultValue = "20") Integer size,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page){

        PageRequest pageable = PageRequest.of(page, size);
        //分数，自动按分数排序

       QueryBuilder queryBuilder = QueryBuilders.boolQuery();
       SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
       bankRepository.search(queryBuilder);

       return null;
    }


    protected static QueryBuilder disMaxQuery() {
        return QueryBuilders.disMaxQuery()
                .add(QueryBuilders.termQuery("name", "kimchy"))          // Your queries
                .add(QueryBuilders.termQuery("name", "elasticsearch"))   // Your queries
                .boost(1.2f)
                .tieBreaker(0.7f);
    }

}
