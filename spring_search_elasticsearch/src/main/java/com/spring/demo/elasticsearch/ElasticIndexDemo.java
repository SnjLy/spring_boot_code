package com.spring.demo.elasticsearch;

import com.spring.demo.elasticsearch.client.ElasticRestClient;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Package: com.helijia.elasticsearch.client
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 08/03/2019.
 */
public class ElasticIndexDemo {


    public static void main(String[] args) {
        ElasticIndexDemo indexDemo = new ElasticIndexDemo();
        indexDemo.createIndex();
    }


    public void createIndex(){

        RestHighLevelClient client = ElasticRestClient.getClient();
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest("item");

        //2. 设置索引的settings
        request.settings(Settings.builder()
                //分片数
                .put("index.number_of_shards", 3)
                // 副本数
                .put("index.number_of_replicas", 2)
                // 默认分词器
                .put("analysis.analyzer.default.tokenizer", "ik_smart")
        );

        // 3、设置索引的mappings
        request.mapping("_doc",
                "  {\n" +
                        "    \"_doc\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"message\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }",
                XContentType.JSON);

        // 4、 设置索引的别名
        request.alias(new Alias("product"));

        // 5、 发送请求
        // 5.1 同步方式发送请求
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        // 5.1 异步方式发送请求
        /*ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(CreateIndexResponse createIndexResponse) {
                    // 6、处理响应
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse
                            .isShardsAcknowledged();
                    System.out.println("acknowledged = " + acknowledged);
                    System.out.println(
                            "shardsAcknowledged = " + shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("创建索引异常：" + e.getMessage());
                }
            };

            client.indices().createAsync(request, listener);
            */


        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
