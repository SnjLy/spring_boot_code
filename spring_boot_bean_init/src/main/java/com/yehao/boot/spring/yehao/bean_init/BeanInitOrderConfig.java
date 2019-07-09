package com.yehao.boot.spring.yehao.bean_init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.yehao.boot.spring.yehao.bean_init
 * 配置类，相当于xml中配置bean
 * @author: SNJly
 * @date: 2019-06-03
 */
@Configuration
public class BeanInitOrderConfig {


    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod", name = "beanInitOrder")
    public BeanInitOrder getBeanInitOrder(){
        return new BeanInitOrder();
    }

    @Bean
    public InitBeanHandleBeanPostProcessor getInitBeanHandle(){
        return new InitBeanHandleBeanPostProcessor();
    }

    @Bean
    public InitBeanHandleBeanFactoryPostProcessor getInitBeanHandleBeanFactoryPostProcess(){
        return new InitBeanHandleBeanFactoryPostProcessor();
    }

}
