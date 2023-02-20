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

import com.amaoai.framework.StringUtils;
import com.amaoai.framework.logging.Logger;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class Slf4jLogger implements Logger {
    private final org.slf4j.Logger slf4jLogger;

    public Slf4jLogger(org.slf4j.Logger slf4jLogger) {
        this.slf4jLogger = slf4jLogger;
    }

    @Override
    public boolean isDebugEnabled() {
        return slf4jLogger.isDebugEnabled();
    }

    @Override
    public void info(String fmt, Object... args) {
        slf4jLogger.info(fmt, args);
    }

    @Override
    public void debug(String fmt, Object... args) {
        slf4jLogger.debug(fmt, args);
    }

    @Override
    public void warn(String fmt, Object... args) {
        slf4jLogger.warn(fmt, args);
    }

    @Override
    public void error(String fmt, Object... args) {
        slf4jLogger.error(fmt, args);
    }

    @Override
    public void error(String fmt, Throwable e, Object... args) {
        slf4jLogger.error(StringUtils.vfmt(fmt, args), e);
    }
}
