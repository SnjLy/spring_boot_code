package com.datasorce.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @date 2018-08-28
 * @author yehao
 */
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
public class RecommendCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendCommonApplication.class, args);
	}
}
