package com.yehao.boot.spring.yehao.bean_init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBeanInitApp {

    @Autowired
    private BeanInitOrder beanInitOrder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBeanInitApp.class, args);
    }

    @PostConstruct
    public void test(){
        System.out.println("执行启动类的PostConstruct， 获取bean:[beanInitOrder]的属性值=" + beanInitOrder.getName());
    }

}
