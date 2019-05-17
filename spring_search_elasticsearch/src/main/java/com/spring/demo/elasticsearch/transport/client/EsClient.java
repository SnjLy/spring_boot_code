package com.spring.demo.elasticsearch.transport.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

/**
 * com.spring.demo.elasticsearch.transport.client
 * es.version  6.5.4
 *  单例模式
 *
 * @author: liuyong
 * @date: 2019-05-17
 */
public class EsClient {

    private static TransportClient client ;

    public static void initClient(){
        Settings settings = Settings.builder()
                .put("cluster.name", "snjly-es-cluster").put("node.name", "node-1")
                .put("client.transport.sniff", true)
                .build();
        client = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(new InetSocketAddress("106.13.44.20", 9300)));
        System.out.println("es client init success");
    }

    public static TransportClient getClient(){
        if (client == null){
            initClient();
        }
        return client;
    }

}
