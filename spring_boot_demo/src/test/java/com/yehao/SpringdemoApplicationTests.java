package com.yehao;

import com.yehao.entity.User;
import com.yehao.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringdemoApplicationTests {

	@Autowired
	private UserService userService;

	@MockBean
	private User user;

	@Before
	public void init(){

	}

	@After
	public void close(){

	}

	@Rollback
	@Test
	public void addUser() {
		if (null == user){
			user = new User();
			user.setUserName("junit");
			user.setAge(1212);
			user.setPassword(UUID.randomUUID().toString());
		}
		userService.insert(user);
		Assert.assertNotNull(user.getId());

		User u = userService.selectByPrimaryKey(user.getId());
		Assert.assertNotNull(u);

	}



}
