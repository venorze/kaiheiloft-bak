package com.amaoai.msocksrv;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/* Creates on 2023/2/8. */

import com.amaoai.export.opensso.ImportOpenSSOExportModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.metrics.ApplicationStartup;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 消息服务器
 *
 * @author Vincent Luo
 */
@EnableFeignClients
@EnableDiscoveryClient
@ImportOpenSSOExportModule
@SpringBootApplication
public class MsocksrvMain {

    public static void main(String[] args) {
        // 启动Spring服务
        // var configurableApplicationContext = SpringApplication.run(MsocksrvMain.class, args);
        // 启动长连接服务
        SocksrvServerSocketApplication.run(new ConfigurableApplicationContext() {
            @Override
            public void setId(String id) {

            }

            @Override
            public void setParent(ApplicationContext parent) {

            }

            @Override
            public void setEnvironment(ConfigurableEnvironment environment) {

            }

            @Override
            public ConfigurableEnvironment getEnvironment() {
                return null;
            }

            @Override
            public void setApplicationStartup(ApplicationStartup applicationStartup) {

            }

            @Override
            public ApplicationStartup getApplicationStartup() {
                return null;
            }

            @Override
            public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {

            }

            @Override
            public void addApplicationListener(ApplicationListener<?> listener) {

            }

            @Override
            public void removeApplicationListener(ApplicationListener<?> listener) {

            }

            @Override
            public void setClassLoader(ClassLoader classLoader) {

            }

            @Override
            public void addProtocolResolver(ProtocolResolver resolver) {

            }

            @Override
            public void refresh() throws BeansException, IllegalStateException {

            }

            @Override
            public void registerShutdownHook() {

            }

            @Override
            public void close() {

            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getApplicationName() {
                return null;
            }

            @Override
            public String getDisplayName() {
                return null;
            }

            @Override
            public long getStartupDate() {
                return 0;
            }

            @Override
            public ApplicationContext getParent() {
                return null;
            }

            @Override
            public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
                return null;
            }

            @Override
            public BeanFactory getParentBeanFactory() {
                return null;
            }

            @Override
            public boolean containsLocalBean(String name) {
                return false;
            }

            @Override
            public boolean containsBeanDefinition(String beanName) {
                return false;
            }

            @Override
            public int getBeanDefinitionCount() {
                return 0;
            }

            @Override
            public String[] getBeanDefinitionNames() {
                return new String[0];
            }

            @Override
            public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType, boolean allowEagerInit) {
                return null;
            }

            @Override
            public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType, boolean allowEagerInit) {
                return null;
            }

            @Override
            public String[] getBeanNamesForType(ResolvableType type) {
                return new String[0];
            }

            @Override
            public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
                return new String[0];
            }

            @Override
            public String[] getBeanNamesForType(Class<?> type) {
                return new String[0];
            }

            @Override
            public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
                return new String[0];
            }

            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
                return null;
            }

            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
                return null;
            }

            @Override
            public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
                return new String[0];
            }

            @Override
            public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
                return null;
            }

            @Override
            public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
                return null;
            }

            @Override
            public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
                return null;
            }

            @Override
            public <A extends Annotation> Set<A> findAllAnnotationsOnBean(String beanName, Class<A> annotationType, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
                return null;
            }

            @Override
            public Object getBean(String name) throws BeansException {
                return null;
            }

            @Override
            public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
                return null;
            }

            @Override
            public Object getBean(String name, Object... args) throws BeansException {
                return null;
            }

            @Override
            public <T> T getBean(Class<T> requiredType) throws BeansException {
                return null;
            }

            @Override
            public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
                return null;
            }

            @Override
            public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
                return null;
            }

            @Override
            public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
                return null;
            }

            @Override
            public boolean containsBean(String name) {
                return false;
            }

            @Override
            public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
                return false;
            }

            @Override
            public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
                return false;
            }

            @Override
            public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
                return false;
            }

            @Override
            public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
                return false;
            }

            @Override
            public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
                return null;
            }

            @Override
            public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
                return null;
            }

            @Override
            public String[] getAliases(String name) {
                return new String[0];
            }

            @Override
            public void publishEvent(Object event) {

            }

            @Override
            public void start() {

            }

            @Override
            public void stop() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }

            @Override
            public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
                return null;
            }

            @Override
            public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
                return null;
            }

            @Override
            public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
                return null;
            }

            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                return new Resource[0];
            }

            @Override
            public Resource getResource(String location) {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }
        }, args);
    }

}
