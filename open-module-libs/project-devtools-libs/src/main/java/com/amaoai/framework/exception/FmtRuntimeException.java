package com.amaoai.framework.exception;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
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

/* Creates on 2022/8/15. */

import com.amaoai.framework.StringUtils;

/**
 * @author Vincent Luo
 */
public class FmtRuntimeException extends RuntimeException {
    public FmtRuntimeException() {
    }

    public FmtRuntimeException(String message, Object... args) {
        super(StringUtils.vfmt(message, args));
    }

    public FmtRuntimeException(String message, Throwable cause, Object... args) {
        super(StringUtils.vfmt(message, args), cause);
    }

    public FmtRuntimeException(Throwable cause) {
        super(cause);
    }
}
