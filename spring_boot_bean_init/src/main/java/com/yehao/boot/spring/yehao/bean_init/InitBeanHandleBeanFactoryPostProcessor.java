package com.yehao.boot.spring.yehao.bean_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * com.yehao.boot.spring.yehao.bean_init
 * 属性处理器实现BeanFactoryPostProcessor
 * @author: SNJly
 * @date: 2019-06-03
 */
public class InitBeanHandleBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition getBeanInitOrder = beanFactory.getBeanDefinition("beanInitOrder");
        MutablePropertyValues propertyValues = getBeanInitOrder.getPropertyValues();
        propertyValues.addPropertyValue("name", "properties");
        System.out.println("执行InitBeanHandleBeanFactoryPostProcessor的postProcessBeanFactory方法");
    }
}
