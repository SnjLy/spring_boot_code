package com.spring.demo.elasticsearch;

import com.spring.demo.elasticsearch.client.ElasticRestClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Package: com.spring.demo.elasticsearch
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 11/03/2019.
 */
public class IndexDocumentDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(IndexDocumentDemo.class);


    public static void main(String[] args) {
        IndexDocumentDemo indexDocument = new IndexDocumentDemo();
        indexDocument.indexDocument();
    }

    public Object indexDocument() {
        //1. 获取client
        RestHighLevelClient client = ElasticRestClient.getClient();

        //创建索引请求
        IndexRequest indexRequest = new IndexRequest(
                "item", "_doc", "2");

        // 2、准备文档数据
        // 方式一：直接给JSON串
        /*String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"createDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);*/

        // 方式二：以map对象来表示文档
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimi");
        jsonMap.put("createDate", new Date());
        jsonMap.put("name", "item_test");
        jsonMap.put("productId", UUID.randomUUID().toString());
        jsonMap.put("price", 180.6);
        jsonMap.put("city", "110100");
        jsonMap.put("message", "trying out Elasticsearch");
        indexRequest.source(jsonMap);

        // 方式三：用XContentBuilder来构建文档

       /* XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.field("createDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        indexRequest.source(builder);*/

        // 方式四：直接用key-value对给出
/*
        indexRequest.source("user", "kimchy",
                "createDate", new Date(),
                "message", "trying out Elasticsearch");*/


        //3、其他的一些可选设置
        /*indexRequest.routing("routing");  //设置routing值
        indexRequest.timeout(TimeValue.timeValueSeconds(1));  //设置主分片等待时长
        indexRequest.setRefreshPolicy("wait_for");  //设置重刷新策略
        indexRequest.version(2);  //设置版本号
        indexRequest.opType(DocWriteRequest.OpType.CREATE);  //操作类别
        */

        //4、发送请求
        IndexResponse indexResponse = null;

        try {
            //同步方式
            indexResponse = client.index(indexRequest);
        } catch (ElasticsearchException e) {
            // 捕获，并处理异常
            //判断是否版本冲突、create但文档已存在冲突
            if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("冲突了，请在此写冲突处理逻辑！\n" + e.getDetailedMessage());
            }
            LOGGER.error("索引异常", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //5、处理响应
        if (indexResponse != null) {
            String index = indexResponse.getIndex();
            String type = indexResponse.getType();
            String id = indexResponse.getId();
            long version = indexResponse.getVersion();
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                LOGGER.info("新增文档成功，处理逻辑代码写到这里。");
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("修改文档成功，处理逻辑代码写到这里。");
            }
            // 分片处理信息
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

            }
            // 如果有分片副本失败，可以获得失败原因信息
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("副本失败原因：" + reason);
                }
            }
        }

        //异步方式发送索引请求
        /*ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        client.indexAsync(indexRequest, listener);*/

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
