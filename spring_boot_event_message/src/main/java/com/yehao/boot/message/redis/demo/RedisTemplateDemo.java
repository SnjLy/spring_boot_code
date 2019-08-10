package com.yehao.boot.message.redis.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : LiuYong
 * @Package: com.yehao.boot.message.redis.demo
 * @Description:
 * @function:
 */
@Service
@Slf4j
public class RedisTemplateDemo {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void setValue(String key, String value){
        stringRedisTemplate.boundValueOps(key).set(value);
    }

    public String getValue(String key, String value){
        return stringRedisTemplate.boundValueOps(key).get();
    }

}
