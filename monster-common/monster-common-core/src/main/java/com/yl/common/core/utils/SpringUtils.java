package com.yl.common.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @Author: YL
 * @Date: 2024-05-21
 * @Project monster
 */
public class SpringUtils implements BeanFactoryPostProcessor {
    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 获取类型为requiredType的对象。
     * 这个方法通过传入的Class对象来检索相应类型的Bean。如果找到了匹配的Bean，则返回该Bean的实例；
     * 否则，将抛出BeansException异常。
     *
     * @param clz 指定要获取的Bean的类型。这是一个Class对象，代表了所需Bean的类型。
     * @return 返回类型为T的Bean实例。如果找不到对应的Bean，则抛出BeansException。
     * @throws BeansException 如果无法找到指定类型的Bean，或者发生其他错误时抛出。
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        // 通过beanFactory获取指定类型的Bean
        return (T) beanFactory.getBean(clz);
    }


}
