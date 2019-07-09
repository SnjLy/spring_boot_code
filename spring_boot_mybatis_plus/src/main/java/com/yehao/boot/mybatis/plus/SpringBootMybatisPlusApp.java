package com.yehao.boot.mybatis.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author snjly
 * @date 2019-07-09
 */
@MapperScan("com.yehao.boot.mybatis.plus.dao")
@SpringBootApplication
public class SpringBootMybatisPlusApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisPlusApp.class, args);
	}

}
