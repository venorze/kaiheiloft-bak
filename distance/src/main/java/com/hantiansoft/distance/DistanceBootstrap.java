package com.hantiansoft.distance;

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

import com.hantiansoft.adapter.SourcePolicy;
import com.hantiansoft.adapter.StoreAdapter;
import com.hantiansoft.qiniu.QiniuSourcePolicy;
import com.hantiansoft.spring.framework.annotation.EnableBootModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author Vincent Luo
 */
@EnableBootModule
@EnableDiscoveryClient
@SpringBootApplication
public class DistanceBootstrap {

    @Value("${sdk.qiniu.access}")
    private String qiniuAccess;

    @Value("${sdk.qiniu.secret}")
    private String qiniuSecret;

    @Value("${sdk.qiniu.bucket}")
    private String qiniuBucket;

    /**
     * 入口函数
     */
    public static void main(String[] args) {
        SpringApplication.run(DistanceBootstrap.class, args);
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

}
