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

import com.amaoai.framework.logging.slf4j.Slf4jLoggerAdapter;
import com.amaoai.framework.logging.std.StdLogger;
import com.amaoai.framework.logging.std.StdLoggerAdapter;

import java.lang.reflect.Constructor;

/**
 * 日志对象创建工厂，初始化的时候会自动去查找可用的日志接口。
 * 如果没有可用的日志接口将会使用默认的标准输出日志接口。
 * <p>
 * 日志框架的加载顺序 slf4j > std
 *
 * @author Vincent Luo
 * @see StdLogger
 */
public class LoggerFactory {
    private static LoggerAdapter loggerAdapterInstance;

    static {
        tryFindAvailableAdapterImplements(LoggerFactory::slf4j);
        tryFindAvailableAdapterImplements(LoggerFactory::std);
    }

    public static Logger getLogger(Class<?> key) {
        return loggerAdapterInstance.getLogger(key);
    }

    public static Logger getLogger(String key) {
        return loggerAdapterInstance.getLogger(key);
    }

    /**
     * 尝试查找可用日志适配器的实现
     */
    private static void tryFindAvailableAdapterImplements(Runnable runnable) {
        try {
            if (loggerAdapterInstance == null)
                runnable.run();
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 获取slf4j的日志适配器
     */
    private static void slf4j() {
        try {
            Constructor<? extends LoggerAdapter> loggerAdapterConstructor
                    = Slf4jLoggerAdapter.class.getConstructor();

            LoggerAdapter loggerAdapter = loggerAdapterConstructor.newInstance();
            loggerAdapter.getLogger(LoggerFactory.class).info("log adapter - [slf4j]");
            loggerAdapterInstance = loggerAdapter;
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 获取标准输出的日志适配器
     */
    private static void std() {
        try {
            Constructor<? extends LoggerAdapter> loggerAdapterConstructor
                    = StdLoggerAdapter.class.getConstructor();
            LoggerAdapter loggerAdapter = loggerAdapterConstructor.newInstance();
            loggerAdapter.getLogger(LoggerFactory.class).info("log adapter - [std]");
            loggerAdapterInstance = loggerAdapter;
        } catch (Exception e) {
            // ignore
        }
    }
}
