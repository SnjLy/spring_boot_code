package com.yehao.demo.spring_cloud_eureka_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.yehao.demo.spring_cloud_eureka_client.controller
 *
 * @author: liuyong
 * @date: 2019-05-16
 */
@RestController
public class DiscoveryController {


        @Value("${server.port}")
        private String port;

        @GetMapping("/client/{id}")
        public String client(@PathVariable("id") String id) {
            String services = "port :"+port + " id:" + id;
            System.out.println(services);
            return services;
    }
}
