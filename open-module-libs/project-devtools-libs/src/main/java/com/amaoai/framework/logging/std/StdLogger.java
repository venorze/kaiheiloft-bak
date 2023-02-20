package com.amaoai.framework.logging.std;

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
import com.amaoai.framework.io.IOUtils;
import com.amaoai.framework.logging.Logger;
import com.amaoai.framework.time.DateUtils;

import java.io.OutputStream;

import static com.amaoai.framework.io.IOUtils.stderr;
import static com.amaoai.framework.io.IOUtils.stdout;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class StdLogger implements Logger {
    private final String name;

    public StdLogger(String name) {
        this.name = name;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void info(String fmt, Object... args) {
        trace(stdout, "INFO", fmt, args);
    }

    @Override
    public void debug(String fmt, Object... args) {
        trace(stdout, "DEBUG", fmt, args);
    }

    @Override
    public void warn(String fmt, Object... args) {
        trace(stdout, "WARN", fmt, args);
    }

    @Override
    public void error(String fmt, Object... args) {
        trace(stderr, "ERROR", fmt, args);
    }

    @Override
    public void error(String fmt, Throwable e, Object... args) {
        trace(stderr, "ERROR", StringUtils.vfmt("{}\n{}", fmt, StringUtils.getStackTrace(e)),
                args);
    }

    void trace(OutputStream output, String level, String fmt, Object... args) {
        int traceIdx = Thread.currentThread().getStackTrace().length - 1;
        StackTraceElement stackTrace =
                Thread.currentThread().getStackTrace()[traceIdx];

        IOUtils.vfprintf(output, "{} {}.{}({}:{}) [{}] - {}\n",
                DateUtils.vfmt(),
                this.name,
                stackTrace.getMethodName(),
                stackTrace.getFileName(),
                stackTrace.getLineNumber(),
                level,
                StringUtils.vfmt(fmt, args));
    }

}
