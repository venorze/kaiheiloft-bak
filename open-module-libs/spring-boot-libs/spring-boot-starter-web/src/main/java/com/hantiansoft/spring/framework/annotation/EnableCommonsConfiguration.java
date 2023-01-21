package com.hantiansoft.spring.framework.annotation;

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

/* Creates on 2022/12/23. */

import com.hantiansoft.spring.framework.configuration.GlobalExceptionHandler;
import com.hantiansoft.spring.framework.configuration.ValidationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 整合通用配置的启动注解，包含以下配置：
 *
 *      1. 全局异常处理配置
 *      2. 请求参数校验配置
 *
 * @author Vincent Luo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({
        GlobalExceptionHandler.class,
        ValidationConfiguration.class
})
@Documented
public @interface EnableCommonsConfiguration {
}
