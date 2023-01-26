package com.hantiansoft.kaiheiloft.controller;

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

/* Creates on 2023/1/27. */

import com.hantiansoft.kaiheiloft.system.KaiheiloftApplicationContext;
import com.hantiansoft.spring.framework.WebRequests;

/**
 * 所有Controller的父类
 *
 * @author Vincent Luo
 */
public class SuperController {

    /**
     * @return 获取当前请求用户ID
     */
    protected Long getUserId() {
        return Long.valueOf(WebRequests.getString(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USER_ID));
    }

    /**
     * @return 获取当前请求用户名称
     */
    protected String getUsername() {
        return WebRequests.getString(KaiheiloftApplicationContext.WEB_REQUEST_ATTRIBUTE_USERNAME);
    }

}
