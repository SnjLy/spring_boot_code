package com.yehao.boot.search.service;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author : LiuYong at 2019-11-22
 * @package: com.yehao.boot.search.service
 */
public class ClientService {

    public static final Logger log = LoggerFactory.getLogger(ClientService.class);


    public static TransportClient getClient() {
        TransportClient client = null;
        try {
            client = new PreBuiltXPackTransportClient(Settings.builder()
                    .put("cluster.name", "snjly-es-cluster")
                    .put("client.transport.sniff", true)
                    .put("xpack.security.transport.ssl.enabled", "true")
//                    .put("xpack.security.transport.ssl.verification_mode", "certificate")
//                    .put("xpack.ssl.key", "/Users/bosszhipin/Software/client/client.key")
//                    .put("xpack.ssl.certificate", "/Users/bosszhipin/Software/client/client.crt")
//                    .put("xpack.ssl.certificate_authorities", "/Users/bosszhipin/Software/ca/ca.crt")
                    .put("xpack.security.user", "elastic:elasticsearch")
                    .build())
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("106.13.44.20"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }


    public static void main(String[] args) {
        TransportClient client = getClient();
        String index = "bank";
        ClusterHealthResponse health = client.admin().cluster().prepareHealth().get();
        log.info("--> connected to [{}] cluster which is running [{}] node(s).", health.getClusterName(), health.getNumberOfNodes());

        SearchRequestBuilder requestBuilder = client.prepareSearch(index).setTypes("account");
        requestBuilder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("age", 32)));
        requestBuilder.setFrom(0).setSize(10);
        SearchResponse response = requestBuilder.get();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info("hit:{}", sourceAsMap);
        }
    }
}
