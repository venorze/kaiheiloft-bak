package com.amaoai.kaiheiloft;

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

/* Creates on 2023/1/8. */

import com.amaoai.adapter.SourcePolicy;
import com.amaoai.adapter.StoreAdapter;
import com.amaoai.export.opensso.OpenSSOFeignPackage;
import com.amaoai.framework.generators.SnowflakeGenerator;
import com.amaoai.mybatisplus.configuration.EnableMybatisKit;
import com.amaoai.qiniu.QiniuSourcePolicy;
import com.amaoai.spring.framework.annotation.EnableGlobalProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author Vincent Luo
 */
@EnableGlobalProcess
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        OpenSSOFeignPackage.PACKAGE
})
@EnableMybatisKit
@SpringBootApplication
public class KaiheiloftBootstrap {

    @Value("${sdk.qiniu.access}")
    private String qiniuAccess;

    @Value("${sdk.qiniu.secret}")
    private String qiniuSecret;

    @Value("${sdk.qiniu.bucket}")
    private String qiniuBucket;

    /**
     * 全局上下文
     */
    public static ConfigurableApplicationContext ApplicationContext = null;

    /**
     * 入口函数
     */
    public static void main(String[] args) {
        ApplicationContext = SpringApplication.run(KaiheiloftBootstrap.class, args);
        ConfigurableListableBeanFactory beanFactory = ApplicationContext.getBeanFactory();
    }

    /**
     * 创建对象储存SDK对象
     */
    @Bean
    public SourcePolicy sourcePolicy() {
        Properties props = new Properties();
        props.put("access", qiniuAccess);
        props.put("secret", qiniuSecret);
        props.put("bucket", qiniuBucket);
        return StoreAdapter.createSourcePolicy(QiniuSourcePolicy.class, props);
    }

    /**
     * 创建雪花算法ID生成器
     */
    @Bean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator(0, 1);
    }

}
