package com.amaoai.opensso.service;

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

/* Creates on 2023/1/20. */

import java.util.Map;

/**
 * JWT认证服务.
 *
 * @author Vincent Luo
 */
public interface AuthenticationService {

    /**
     * 创建token
     */
    String createToken(Map<String, Object> payload);

    /**
     * 获取荷载内容
     */
    Map<String, Object> getClaims(String token);

    /**
     * 验证token
     */
    boolean verifier(String token);

}
