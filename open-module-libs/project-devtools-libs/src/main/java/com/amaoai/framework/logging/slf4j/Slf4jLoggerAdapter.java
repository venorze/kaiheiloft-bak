package com.amaoai.framework.logging.slf4j;

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

import com.amaoai.framework.exception.NotImplementsException;
import com.amaoai.framework.logging.Logger;
import com.amaoai.framework.logging.LoggerAdapter;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class Slf4jLoggerAdapter implements LoggerAdapter {
    @Override
    public Logger getLogger(String key) {
        org.slf4j.Logger logger
                = checkNopLogger(LoggerFactory.getLogger(key));

        return new Slf4jLogger(logger);
    }

    @Override
    public Logger getLogger(Class<?> key) {
        org.slf4j.Logger logger
                = checkNopLogger(LoggerFactory.getLogger(key));

        return new Slf4jLogger(logger);
    }

    /**
     * 检查获取到的日志对象实现是不是NOPLogger。
     *
     * @see NOPLogger
     */
    private org.slf4j.Logger checkNopLogger(org.slf4j.Logger logger) {
        if (logger instanceof NOPLogger)
            throw new NotImplementsException();

        return logger;
    }
}
