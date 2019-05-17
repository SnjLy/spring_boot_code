package com.spring.demo.elasticsearch;

import com.spring.demo.elasticsearch.transport.service.EsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * com.spring.demo.elasticsearch
 *
 * @author: liuyong
 * @date: 2019-05-17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransportSearchTest {

    @Autowired
    private EsService esService;


    @Test
    public void testSearch(){
        esService.createIndex();
        esService.insert(null);
        esService.query();
        esService.deleteByQuery();
    }
}
