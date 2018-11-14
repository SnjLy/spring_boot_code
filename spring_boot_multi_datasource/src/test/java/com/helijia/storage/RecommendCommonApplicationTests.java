package com.helijia.storage;

import com.alibaba.fastjson.JSON;
import com.helijia.storage.dao.hlj.entity.UserCollect;
import com.helijia.storage.dao.hlj.mapper.UserCollectMapper;
import com.helijia.storage.dao.search.entity.RecommendWord;
import com.helijia.storage.service.RecommendService;
import com.helijia.storage.service.UserService;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendCommonApplicationTests {

	@Autowired
	private UserCollectMapper userCollectMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private RecommendService recommendService;
	@Test
	public void contextLoads() {

	}

	@Test
	public void testCollect(){
		String userId = "112a7a982d2946efa2254cac93104cca";
		Date date = new Date();
		List<UserCollect> collectList = userCollectMapper.queryUserCollect(userId, DateUtil.yesterday());
		System.out.println(JSON.toJSONString(collectList));
	}


	@Test
	public void testUserService(){
		String userId = "112a7a982d2946efa2254cac93104cca";
		Date date = new Date();
		List<UserCollect> collectList = userService.queryUserCollect(userId, DateUtil.yesterday());
		System.out.println(JSON.toJSONString(collectList));
	}

	@Test
	public void testRecommendSelect(){
		List<RecommendWord> words = recommendService.getRecommendWords(2);
		System.out.println(JSON.toJSONString(words));
	}
}
