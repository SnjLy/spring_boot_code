package com.spring.demo.elasticsearch;

import com.spring.demo.elasticsearch.client.ElasticRestClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.spring.demo.elasticsearch
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 11/03/2019.
 */
public class ElasticSearchDemo {

    public static Logger LOGGER = LoggerFactory.getLogger(ElasticSearchDemo.class);


    public static void main(String[] args) {
        ElasticSearchDemo searchDemo = new ElasticSearchDemo();
        searchDemo.search();
    }

    public Object search() {
        RestHighLevelClient client = ElasticRestClient.getClient();

        //1. 创建searchRequest请求
        String[] indexs = new String[]{"item"};
        SearchRequest request = new SearchRequest(indexs);
        request.types("_docs");

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构造QueryBuilder
            /*QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10);
            sourceBuilder.query(matchQueryBuilder);*/

        sourceBuilder.query(QueryBuilders.termQuery("age", 18));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        //设置超时时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //设置是否返回_source字段
        //sourceBuilder.fetchSource(false);

        //设置返回哪些字段
        String[] includes = new String[]{"productId", "user", "name", "city", "createDate", "message"};
        String[] excludes = new String[]{"_types"};
        sourceBuilder.fetchSource(includes, excludes);

        //指定排序
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //sourceBuilder.sort(new FieldSortBuilder("_score").order(SortOrder.ASC));

        //设置返回profile, default: profile=false
        //sourceBuilder.profile(true);


        // 可选的设置
        //searchRequest.routing("routing");

        // 高亮设置
        /*HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("name");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
        highlightBuilder.field(highlightUser);
        sourceBuilder.highlighter(highlightBuilder);*/

        //加入聚合
       /* TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_name")
                .field("name.keyword");
        aggregation.subAggregation(AggregationBuilders.avg("av_price"))
                .field("price");
        sourceBuilder.aggregation(aggregation);*/


        //做查询建议
            /*SuggestionBuilder termSuggestionBuilder =
                    SuggestBuilders.termSuggestion("user").text("kmichy");
                SuggestBuilder suggestBuilder = new SuggestBuilder();
                suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
            sourceBuilder.suggest(suggestBuilder);*/


        request.source(sourceBuilder);
        // 3、 发送请求
        try {
            SearchResponse response = client.search(request);
            //4、处理响应
            //搜索结果状态信息
            RestStatus status = response.status();
            TimeValue took = response.getTook();
            Boolean terminatedEarly = response.isTerminatedEarly();
            boolean timeOut = response.isTimedOut();

            //分片搜索情况
            int totalShards = response.getTotalShards();
            int successfulShards = response.getSuccessfulShards();
            int failedShards = response.getFailedShards();
            for (ShardSearchFailure failure : response.getShardFailures()) {
                //handle the failure shard
            }
            // 处理命中文档搜索结果
            SearchHits hits = response.getHits();
            long totalHits = hits.getTotalHits();
            float maxScore = hits.getMaxScore();

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit searchHit : searchHits) {
                // do something with the searchHit

                String index = searchHit.getIndex();
                String type = searchHit.getType();
                String id = searchHit.getId();
                float score = searchHit.getScore();

                //取_source
                //取成字符串
                String sourceAsString = searchHit.getSourceAsString();
                //取成map
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

                //从map中取字段值
                /*
                String documentTitle = (String) sourceAsMap.get("title");
                List<Object> users = (List<Object>) sourceAsMap.get("user");
                Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
                */
                LOGGER.info("index:" + index + "  type:" + type + "  id:" + id);
                LOGGER.info(sourceAsString);

                //获取高亮结果
               /* Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                HighlightField highlightField = highlightFields.get("name");
                Text[] fragments = highlightField.fragments();
                String fragmentString = fragments[0].toString();
                LOGGER.info("highlight filed:" + fragmentString);*/
            }

            //获取聚合结果
           /* Aggregations aggregations = response.getAggregations();
            Terms byName = aggregations.get("by_name");
            Terms.Bucket elasticBucket = byName.getBucketByKey("Elastic");
            Avg avgPrice= elasticBucket.getAggregations().get("avg_price");
            double value = avgPrice.getValue();
            LOGGER.info("aggregations value = " + value);*/


            // 获取建议结果
            /*Suggest suggest = response.getSuggest();
            TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
            for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                for (TermSuggestion.Entry.Option option : entry) {
                    String suggestText = option.getText().string();
                }
            }*/

            //异步方式发送获查询请求
/*
            ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse getResponse) {
                    //结果获取
                }

                @Override
                public void onFailure(Exception e) {
                    //失败处理
                }
            };
            client.searchAsync(request, listener);*/

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
