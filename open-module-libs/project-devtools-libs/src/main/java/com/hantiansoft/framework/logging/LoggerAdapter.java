package com.hantiansoft.framework.logging;

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

/* Creates on 2022/8/18. */

/**
 * 日志适配器，用于多个日志的实现。子类实现getLogger()函数即可。
 * 获取到日志对象就可以使用日志了。
 *
 * @author Vincent Luo
 */
public interface LoggerAdapter {
    /**
     * 根据类名或者其他自定义的名称获取日志对象。
     */
    Logger getLogger(String key);

    /**
     * 根据类对象获取日志对象。
     */
    Logger getLogger(Class<?> key);
}
