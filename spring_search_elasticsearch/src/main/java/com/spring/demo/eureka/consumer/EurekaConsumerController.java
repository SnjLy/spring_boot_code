package com.spring.demo.eureka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.spring.demo.eureka.consumer
 *
 * @author: liuyong
 * @date: 2019-05-16
 */
@RequestMapping("/consumer")
@RestController
public class EurekaConsumerController {


    @Autowired
    private RestTemplate restTemplate;


    @Value("${eureka.instance.hostname}")
    private String instanceName;


    @GetMapping("/router/{param}")
    public String router(@PathVariable("param") String param) {
        //到注册中心找服务并调用服务
        String json = restTemplate.getForObject("http://"+ instanceName+"/client/2", String.class);
        System.out.println("远程调用返回的结果:" + json);
        return json + param;
    }

}
