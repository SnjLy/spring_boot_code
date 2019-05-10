package com.spring.demo.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * com.spring.demo.elasticsearch
 * 使用一个类测试es的常用api记录下来
 *
 * @author : liuyong
 * @date : 2019-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EsQueryTest {

    private TransportClient client;

    /**
     * 初始化client
     */
    @Before
    public void testBefore() {
        Settings settings = Settings.builder()
                .put("cluster.name", "snjly-es-cluster").put("node.name", "node-1")
                .put("client.transport.sniff", true)
                .build();
        client = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(new InetSocketAddress("106.13.44.20", 9300)));
        System.out.println("client init success");

    }


    /**
     * 使用Get查询
     */
    @Test
    public void testGet() {
        GetRequestBuilder requestBuilder = client.prepareGet();
        requestBuilder.setIndex("user_gps").setType("user_gps").setId("fRGqqWkBktcKsfzwrSzx");

        //builder.get底层也是调用builder.execute()
        GetResponse response = requestBuilder.get();
        Map<String, Object> source = response.getSource();
        System.out.println(JSON.toJSONString(source));

        //本质上同builder.get()
        ActionFuture<GetResponse> execute = requestBuilder.execute();
        GetResponse response1 = execute.actionGet(1, TimeUnit.SECONDS);
        System.out.println(response1.getSourceAsString());
    }


    /**
     * 获取多个
     * get a list of documents based on their index and id:
     */
    @Test
    public void testMultiGet() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                //by single id
                .add("user_gps", "user_gps", "fRGqqWkBktcKsfzwrSzx")
                //指定多个ID
                .add("user_gps", "user_gps", "fxGqqWkBktcKsfzwrSzx", "hBGqqWkBktcKsfzwrSzx", "iRGqqWkBktcKsfzwrSzx")
                //指定其他索引的ID查询
                .add("list_geek_v2", "geek", "100769")
                .get();


        /*MultiGetRequestBuilder requestBuilder = client.prepareMultiGet()
                .add("user_gps", "user_gps", "fRGqqWkBktcKsfzwrSzx")
                //指定多个ID
                .add("user_gps", "user_gps", "fxGqqWkBktcKsfzwrSzx", "hBGqqWkBktcKsfzwrSzx", "iRGqqWkBktcKsfzwrSzx")
                //指定其他索引的ID查询
                .add("list_geek_v2", "geek", "100769");
        MultiGetResponse responses = requestBuilder.get();*/

        for (MultiGetItemResponse mGIR : multiGetItemResponses) {
            GetResponse response = mGIR.getResponse();
            boolean exists = response.isExists();
            if (exists) {
                Map<String, Object> source = response.getSource();
                System.out.println(response.getIndex() + "----" + response.getType() + "----" + response.getSourceAsString());
            }

        }


    }


    /**
     * 测试批量操作数据
     */
    @Ignore
    @Test
    public void testBulk() {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk()
                //todo 构建具体的bulk中的request
                .add(new IndexRequest("twitter", "_doc", "1").source("/* your doc here */"))
                .add(new UpdateRequest())
                .add(new DeleteRequest());
        //.add(new IndexRequestBuilder()).add(new UpdateRequestBuilder());
        BulkResponse bulkResponse = bulkRequestBuilder.get();
        boolean hasFailures = bulkResponse.hasFailures();
        String s = bulkResponse.buildFailureMessage();
        if (hasFailures) {
            System.out.println(s);
        }


        //https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.3/java-docs-bulk-processor.html
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        })
                //每1000个请求执行一次
                .setBulkActions(1000)
                //We want to flush the bulk every 5mb
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                //We want to flush the bulk every 5 seconds whatever the number of requests
                .setFlushInterval(TimeValue.timeValueMinutes(5))
                //Set the number of concurrent requests. A value of 0 means that only a single request will be allowed to be executed.
                // A value of 1 means 1 concurrent request is allowed to be executed while accumulating new bulk requests.
                .setConcurrentRequests(1)
                //Set a custom backoff policy which will initially wait for 100ms, increase exponentially and retries up to three times.
                // A retry is attempted whenever one or more bulk item requests have failed with an EsRejectedExecutionException which
                // indicates that there were too little compute resources available for processing the request. To disable backoff,
                // pass BackoffPolicy.noBackoff().
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        //增加自己的request
        bulkProcessor.add(new IndexRequest());

        // Flush any remaining requests
        bulkProcessor.flush();

        // Or close the bulkProcessor if you don't need it anymore
        bulkProcessor.close();

        // Refresh your indices
        client.admin().indices().prepareRefresh().get();

        // Now you can start searching!
        client.prepareSearch().get();
    }


    /**
     * 测试updateByQuery
     * 可以指定脚本
     */
    @Test
    public void testUpdateByQuery() {
        UpdateByQueryRequestBuilder updateByQueryRequestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQueryRequestBuilder.source("source_index")
                .filter(QueryBuilders.termQuery("level", "7")) //查询条件
                .size(100)
                .script(new Script("your script here"));

        BulkByScrollResponse bulkByScrollResponse = updateByQueryRequestBuilder.get();
        int batches = bulkByScrollResponse.getBatches();

    }


    /**
     * 指定IDs查询
     */
    @Test
    public void testIdsQuery() {
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery("user_gps");
        queryBuilder.addIds("fRGqqWkBktcKsfzwrSzx");
        this.searchFunction(queryBuilder);
    }


    @Test
    public void testSearch() {
        SearchResponse response = client.prepareSearch("index", "type")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("fieldName", "fieldValue"))
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(19)).setFrom(0).setSize(20)
                .setExplain(true)
                .get();

        Suggest suggest = response.getSuggest();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(hit.getSourceAsString());
        }

        /*
         * 测试使用scrolls in java
         * 处理大批量数据请求类似传统数据库的光标滚动
         */
        QueryBuilder qb = QueryBuilders.termQuery("multi", "test");
        SearchResponse scrollResp = client.prepareSearch("index")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get(); //max of 100 hits will be returned for each scroll

        //Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
                System.out.println(hit.getSourceAsString());
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(60000))
                    .get();
        } while (scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.

    }


    @Test
    public void testMultiSearch() {
        MultiSearchRequestBuilder msrb = client.prepareMultiSearch();
        //add new SearchRequestBuilder()
        msrb.add(
                client.prepareSearch("index", "type")
                        .setQuery(QueryBuilders.termQuery("name", "first"))
                        .setSize(1));

        SearchRequestBuilder builder = SearchAction.INSTANCE.newRequestBuilder(client).
                setQuery(QueryBuilders.termQuery("gender", "man")).setSize(2).setExplain(true);
        msrb.add(builder);

        msrb.add(new SearchRequest());

        MultiSearchResponse response = msrb.get();
        for (MultiSearchResponse.Item item : response.getResponses()) {
            SearchResponse response1 = item.getResponse();
            if (item.isFailure()) {
                String failureMessage = item.getFailureMessage();
                System.out.println(failureMessage);
            }
            for (SearchHit hit : response1.getHits().getHits()) {
                String source = hit.getSourceAsString();
                System.out.println(source);
                String index = hit.getIndex();
                String id = hit.getId();
                String[] matchedQueries = hit.getMatchedQueries();
                float score = hit.getScore();
                System.out.println(id + "----" + score);
            }
        }

    }


    /**
     * 聚合查询测试
     * 测试前将查询字段条件改为index中的合法字段
     */
    @Test
    public void testAggregations() {
        SearchResponse response = client.prepareSearch("user_gps", "bank")
                .setQuery(QueryBuilders.matchAllQuery())
                //添加一个聚合
                .addAggregation(
                        AggregationBuilders.terms("agg1").field("field1")
                                .subAggregation(AggregationBuilders.count("genderAgg").field("gender"))
                )
                .addAggregation(
                        AggregationBuilders.avg("priceAgg").field("price")
                )
                .addAggregation(
                        AggregationBuilders.dateHistogram("dateAgg").field("birth")
                                .dateHistogramInterval(DateHistogramInterval.YEAR)
                ).get();

        Aggregations aggregations = response.getAggregations();
        Aggregation agg1 = aggregations.get("agg1");
        System.out.println(agg1.getType() + "===" + agg1.getName() + "-=======-" + agg1.getMetaData());

        Histogram histogram = aggregations.get("dateAgg");
        Map<String, Object> dateAgg = histogram.getMetaData();
        Terms genderAgg1 = aggregations.get("genderAgg");
        Map<String, Object> genderAgg = genderAgg1.getMetaData();


    }


    /**
     * 查询遍历抽取
     *
     * @param queryBuilder  queryBuilder
     */
    private void searchFunction(QueryBuilder queryBuilder) {
        SearchResponse response = client.prepareSearch("user_gps")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000))
                .setQuery(queryBuilder)
                .setSize(100).get();

        response = client.prepareSearchScroll(response.getScrollId())
                .setScroll(new TimeValue(60000)).get();
        long totalHits = response.getHits().getTotalHits();
        for (SearchHit hit : response.getHits().getHits()) {
            String id = hit.getId();
            Assert.assertNotNull(id);
            for (Map.Entry<String, Object> next : hit.getSourceAsMap().entrySet()) {
                System.out.println(next.getKey() + ": " + next.getValue());
            }
        }
    }

}
