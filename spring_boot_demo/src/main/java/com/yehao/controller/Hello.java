package com.yehao.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package: com.yehao.controller
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/7/26.
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/hello")
public class Hello {
    public static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

    @RequestMapping("/world")
    public String home() {
        LOGGER.info("hello " );
        return "Hello World!";
    }
}
