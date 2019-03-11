package com.spring.demo.elasticsearch.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Package: com.spring.demo.elasticsearch.client
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 08/03/2019.
 */
public class ElasticRestClient {

    private static String url = "es-url";
    private static int port = 9200;
    private static String schema = "http";

    public static final String USERNAME = "es-username";
    public static final String PASSWORD = "es-password";


    private static  RestHighLevelClient client;

    public static RestHighLevelClient getClient(){
        if (null ==client){
            initClient();
        }
        return client;
    }


    public static  void initClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

        RestHighLevelClient  client1 = new RestHighLevelClient(RestClient.builder(new HttpHost(url, port, schema))
        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }));
        client = client1;
    }

}

