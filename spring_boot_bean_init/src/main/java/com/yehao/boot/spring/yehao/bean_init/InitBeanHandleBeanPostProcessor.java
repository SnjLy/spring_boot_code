package com.yehao.boot.spring.yehao.bean_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * com.yehao.boot.spring.yehao.bean_init
 * 声明后置处理器BeanPostProcessor
 * @author: SNJly
 * @date: 2019-06-03
 */
public class InitBeanHandleBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("beanInitOrder".equals(beanName)) {
            System.out.println("执行BeanPostProcessor的postProcessBeforeInitialization方法");
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("beanInitOrder".equals(beanName)) {
            System.out.println("执行BeanPostProcessor的postProcessAfterInitialization方法");
        }
        return bean;
    }
}
