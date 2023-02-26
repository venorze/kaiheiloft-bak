package com.amaoai.msrv;

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

import com.amaoai.export.opensso.OpenSSOFeignPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 消息服务器
 *
 * @author Vincent Luo
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        OpenSSOFeignPackage.PACKAGE
})
@SpringBootApplication
public class MsrvBootstrap {

    public static void main(String[] args) {
        // 启动Spring服务
        var configurableApplicationContext = SpringApplication.run(MsrvBootstrap.class, args);
        // 启动长连接服务
        SocketBootstrap.run(configurableApplicationContext, args);
    }

}
