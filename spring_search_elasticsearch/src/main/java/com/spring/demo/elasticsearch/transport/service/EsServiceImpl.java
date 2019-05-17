package com.spring.demo.elasticsearch.transport.service;

import com.spring.demo.elasticsearch.transport.client.EsClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.spring.demo.elasticsearch.transport.service
 *
 * @author: liuyong
 * @date: 2019-05-17
 */
@Service("esService")
@Slf4j
public class EsServiceImpl implements EsService {


    /**
     * 创建索引
     */
    @Override
    public void createIndex() {
        TransportClient client = EsClient.getClient();

        CreateIndexRequestBuilder builder = client.admin().indices().prepareCreate("yehao_test");
        try {
            XContentBuilder mapping = XContentFactory.jsonBuilder();
            mapping.startObject()
                    .startObject("properties")
                    .startObject("name").field("type","text").field("analyzed","standard").endObject() //设置分析器
                    .startObject("age").field("type","long").endObject()
                    .startObject("class_name").field("type","keyword").endObject()
                    .startObject("birth").field("type","date").field("format","yyyy-MM-dd").endObject()//设置Date的格式
                    .endObject()
                    .endObject();
            builder.addMapping("test", mapping);
            CreateIndexResponse createIndexResponse = builder.get();
            System.out.println(createIndexResponse.toString());
        } catch (IOException e) {
            log.error(e.getMessage() + e);
        }


    }

    /**
     * 插入索引文档
     *
     * @param object
     */
    @Override
    public void insert(Object object) {
        IndexRequestBuilder indexRequestBuilder = EsClient.getClient().prepareIndex("test", "test");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 18);
        map.put("class_name", "English");
        map.put("birth", new Date());
        map.put("address", "南京市香港路");
        IndexResponse indexResponse = indexRequestBuilder.setSource(map).get();
        RestStatus status = indexResponse.status();
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(indexResponse.toString());

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("name", "yehao")
                    .field("age", 15)
                    .field("class_name", "Elasticsearch")
                    .field("birth", new Date("2018-09-12"))
                    .field("address", "北京市长春路")
                    .endObject();

            IndexResponse response = indexRequestBuilder.setSource(builder).get();
            System.out.println(response.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 批量插入文档
     *
     * @param object
     */
    @Override
    public void bulkInsert(Object object) {
        BulkRequestBuilder bulkRequestBuilder = EsClient.getClient().prepareBulk();
        bulkRequestBuilder.add(EsClient.getClient().prepareIndex());
    }

    /**
     * 删除文档数据和索引
     */
    @Override
    public void delete() {

    }

    /**
     * 根据查询条件删除文档数据和索引
     */
    @Override
    public void deleteByQuery() {
        TransportClient client = EsClient.getClient();
        TermQueryBuilder builder = QueryBuilders.termQuery("name", "张三");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("test").setSearchType("test")
                .setQuery(builder);
        SearchResponse response = executeQuery(searchRequestBuilder);

        /*DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete();
        DeleteResponse deleteResponse = deleteRequestBuilder.get();*/

    }

    /**
     * es查询文档
     * 搜索全部
     * QueryBuilder matchAll = QueryBuilders.matchAllQuery();
     *
     * 字段值包含搜索
     * QueryBuilder match = QueryBuilders.matchQuery("field","text");
     *
     * 字段值精确值匹配
     * QueryBuilder term = QueryBuilders.termQuery("field","text");
     * QueryBuilder terms = QueryBuilders.termsQuery("field","text","text2","text3");
     *
     * 2
     * 前缀搜索
     * QueryBuilder prefix= QueryBuilders.prefixQuery("field","text");
     *
     * 模糊值搜索
     * FuzzyQueryBuilder fuzzy= QueryBuilders.fuzzyQuery(field, value);
     *
     * 通配符搜索
     * QueryBuilder wildcard= QueryBuilders.wildcardQuery(field,patten);
     *
     * 搜索语句搜索
     * QueryBuilder queryString= QueryBuilders.queryStringQuery("queryString");
     */
    @Override
    public void query() {
        TransportClient client = EsClient.getClient();
        //构建一个query 即查询条件
        QueryBuilder match = QueryBuilders.matchQuery("name","yehao");
        //根据查询条件构建一个查询问句
        SearchRequestBuilder search = client.prepareSearch("test")
                .setQuery(match)
                .setTypes("test")  //指定类型 可选
                .setFrom(0).setSize(10) //分页 可选
                .addSort("age", SortOrder.DESC);//排序 可选
        //搜索返回搜索结果
        SearchResponse response = executeQuery(search);
    }

    private SearchResponse executeQuery(SearchRequestBuilder searchRequestBuilder){
        SearchResponse response = searchRequestBuilder.get();
        //命中的文档
        SearchHits hits = response.getHits();
        //命中总数
        Long total = hits.getTotalHits();
        //循环查看命中值
        for(SearchHit hit:hits.getHits()){
            //文档元数据
            String index = hit.getIndex();
            //文档的_source的值
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            System.out.println(hit.getSourceAsString());
        }
        return response;
    }
}
