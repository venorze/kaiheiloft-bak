package com.amaoai.framework.redis;

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

/* Creates on 2023/2/28. */

import com.alibaba.fastjson.JSON;
import com.amaoai.framework.time.DateUtils;
import com.amaoai.framework.time.TimeUnits;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;

/**
 * @author Amaoai
 */
public class RedisOperation implements Closeable {

    /**
     * Jedis线程池
     */
    private final Jedis jedisOps;

    // redis库索引列表
    public static final int REDIS_DATABASE_IDX_0 = 0;
    public static final int REDIS_DATABASE_IDX_1 = 1;
    public static final int REDIS_DATABASE_IDX_2 = 2;
    public static final int REDIS_DATABASE_IDX_3 = 3;
    public static final int REDIS_DATABASE_IDX_4 = 4;
    public static final int REDIS_DATABASE_IDX_5 = 5;
    public static final int REDIS_DATABASE_IDX_6 = 6;
    public static final int REDIS_DATABASE_IDX_7 = 7;
    public static final int REDIS_DATABASE_IDX_8 = 8;
    public static final int REDIS_DATABASE_IDX_9 = 9;
    public static final int REDIS_DATABASE_IDX_10 = 10;
    public static final int REDIS_DATABASE_IDX_11 = 11;
    public static final int REDIS_DATABASE_IDX_12 = 12;
    public static final int REDIS_DATABASE_IDX_13 = 13;
    public static final int REDIS_DATABASE_IDX_14 = 14;
    public static final int REDIS_DATABASE_IDX_15 = 15;

    // redis过期时间预设
    public static final long EXPIRE_TIMEUNIT_SECONDS = 1;
    public static final long EXPIRE_TIMEUNIT_MINUTES = EXPIRE_TIMEUNIT_SECONDS * 60;
    public static final long EXPIRE_TIMEUNIT_HOURS = EXPIRE_TIMEUNIT_MINUTES * 60;
    public static final long EXPIRE_TIMEUNIT_DAYS = EXPIRE_TIMEUNIT_HOURS * 24;

    RedisOperation(Jedis jedis) {
        this.jedisOps = jedis;
    }

    /**
     * 放入一个String Value作为值
     */
    public String put(String key, String value) {
        return jedisOps.set(key, value);
    }

    /**
     * 放入一个String Value作为值
     */
    public String putex(String key, long second, String value) {
        return jedisOps.setex(key, second, value);
    }

    /**
     * 放入一个Json Value作为值
     */
    public String put(String key, Object obj) {
        return jedisOps.set(key, JSON.toJSONString(obj));
    }

    /**
     * 放入一个Json Value作为值，并设置过期时间
     */
    public String putex(String key, long second, Object obj) {
        return jedisOps.setex(key, second, JSON.toJSONString(obj));
    }

    /**
     * 获取字符串
     */
    public String get(String key) {
        return jedisOps.get(key);
    }

    /**
     * 获取Json对象
     */
    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    /**
     * key是否存在
     */
    public boolean exists(String key) {
        return jedisOps.exists(key);
    }

    /**
     * 设置key过期时间
     */
    public boolean expire(String key, long second) {
        return jedisOps.expire(key, second) == 1L;
    }

    /**
     * 选择DB
     */
    public void select(int dbnum) {
        jedisOps.select(dbnum);
    }

    /**
     * 删除KEY
     */
    public void delete(String key) {
        jedisOps.del(key);
    }

    /**
     * 关闭连接或返回连接池
     */
    @Override
    public void close() {
        jedisOps.close();
    }

}
