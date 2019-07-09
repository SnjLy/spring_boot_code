package com.yehao.boot.mybatis.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yehao.boot.mybatis.plus.dao.MyBatisUserMapper;
import com.yehao.boot.mybatis.plus.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMybatisPlusApp.class)
@Transactional
public class SpringBootMybatisPlusAppTests {

	@Autowired
	private MyBatisUserMapper myBatisUserMapper;

	@Test
	public void contextLoads() {
	}


	@Rollback(value = true)
	@Test
	public void testMybatisPlusInsert(){
		int N = 10;
		List<User> users = new ArrayList<>(N);
		for (int i = 0; i <N; i++) {
			User u = new User();
			u.setName("name" + i);
			u.setAge(20+i);
			u.setSex(i%2);
			u.setPassword(UUID.randomUUID().toString());
			u.setUserName(UUID.randomUUID().toString());
			u.setAddress("addressusDf$$5sersdhfis" + i);
			u.setMajor("major" + i);
			int insert = myBatisUserMapper.insert(u);
			System.out.println(u.toString());
		}

		System.out.println(("----- selectAll method test ------"));
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.ge(true, "id", 10);
		List<User> userList = myBatisUserMapper.selectList(queryWrapper);
		Assert.assertNotNull(userList);
		Assert.assertTrue(userList.size() > N);
		userList.forEach(System.out::println);

	}





}
