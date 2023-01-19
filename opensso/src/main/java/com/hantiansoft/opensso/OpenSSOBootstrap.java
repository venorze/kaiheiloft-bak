package com.hantiansoft.opensso;

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

/* Creates on 2023/1/17. */

import com.hantiansoft.framework.security.AuthenticationTokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Vincent Luo
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OpenSSOBootstrap {

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Value("${jwt.token.expire}")
    private Long tokenExpire;

    public static void main(String[] args) {
        SpringApplication.run(OpenSSOBootstrap.class, args);
    }

    @Bean
    public AuthenticationTokenGenerator authenticationTokenGenerator() {
        return new AuthenticationTokenGenerator(tokenSecret, tokenExpire);
    }

}
