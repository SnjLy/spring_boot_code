package com.yehao.boot.redis;

import com.yehao.boot.message.redis.framework.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author : LiuYong
 * Created by bosszhipin on 2019-08-10.
 * @Package: com.yehao.boot.redis
 * @Description:
 * @function:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RedisServiceTests {

    @Autowired
    public RedisService redisService;


    @Test
    public void testRedis(){
        String key = "snjly";
        Object value = "snjly&&";
        redisService.set(key, value);
        Object o = redisService.get(key);
        Assert.assertEquals(value, o);

        String age = "age";
        Object valueAge = 1000;
        redisService.set(age, valueAge);
        Object age2 = redisService.get(age);
        Assert.assertEquals(valueAge, age2);
    }
}
