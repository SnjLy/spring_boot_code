package com.yehao.boot.spring.yehao.bean_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * com.yehao.boot.spring.yehao.bean_init
 * Bean定义
 * @author: SNJly
 * @date: 2019-06-03
 */
public class BeanInitOrder implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    private String name;

    public BeanInitOrder() {
        System.out.println("执行构造方法 BeanInitOrder()");
    }

    public void initMethod(){
        System.out.println("执行bean定义的initMethod方法");
    }

    public void destroyMethod(){
        System.out.println("执行bean定义的destroyMethod方法");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("执行PostConstruct方法");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("执行PreDestroy方法");
    }

    /**
     * 实现InitializingBean接口，实现方法
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        System.out.println("实现InitializingBean的afterPropertiesSet方法");
    }


    /**
     * 实现DisposableBean接口，实现其方法
     * @throws Exception
     */
    public void destroy() throws Exception {
        System.out.println("实现DisposableBean接口,执行DisposableBean的destroy方法");
    }

    /**
     * 实现BeanNameAware, 实现其方法
     * @param s
     */
    public void setBeanName(String s) {
        System.out.println("实现BeanNameAware,注入BeanName");
    }


    /**
     * 实现BeanFactoryAware，实现其接口方法
     * @param beanFactory
     * @throws BeansException
     */
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("实现BeanFactoryAware,注入BeanFactory");
    }


    /**
     * 实现ApplicationContextAware,实现其方法
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("实现ApplicationContextAware,注入ApplicationContext");
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
