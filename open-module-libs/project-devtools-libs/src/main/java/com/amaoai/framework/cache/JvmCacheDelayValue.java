package com.amaoai.framework.cache;

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

/* Creates on 2022/8/8. */

import com.amaoai.framework.time.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vincent Luo
 */
@Data
class JvmCacheDelayValue implements Serializable {
    private final String key;
    private final Object data;
    private Date expireTime;

    public JvmCacheDelayValue(String key, Object data, Date expireTime) {
        this.key = key;
        this.data = data;
        this.expireTime = expireTime;
    }

    public boolean expired() {
        return DateUtils.gteq(new Date(), expireTime);
    }
}
