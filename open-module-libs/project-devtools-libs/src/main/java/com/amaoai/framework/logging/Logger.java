package com.amaoai.framework.logging;

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
 * 通用日志接口
 *
 * @author Vincent Luo
 */
public interface Logger {
    /**
     * @return 配置日志是否开启debug打印
     */
    boolean isDebugEnabled();

    /**
     * 打印标准级别的日志输出
     */
    void info(String fmt, Object... args);

    /**
     * 打印调试级别的日志输出
     */
    void debug(String fmt, Object... args);

    /**
     * 打印警告级别的日志输出
     */
    void warn(String fmt, Object... args);

    /**
     * 打印错误级别的日志输出
     */
    void error(String fmt, Object... args);

    /**
     * 打印错误级别的日志输出，并指定异常对象一同输出
     */
    void error(String fmt, Throwable e, Object... args);
}
