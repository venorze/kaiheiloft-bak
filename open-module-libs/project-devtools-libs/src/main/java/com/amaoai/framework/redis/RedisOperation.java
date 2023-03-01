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
import com.amaoai.framework.io.IOUtils;
import redis.clients.jedis.Jedis;

import java.io.Closeable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Amaoai
 */
@SuppressWarnings("ALL")
public class RedisOperation implements Closeable {

    /**
     * Jedis线程池
     */
    private final Jedis jedis;

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
        this.jedis = jedis;
    }

    /**
     * 放入一个String Value作为值
     */
    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    /**
     * 放入一个String Value作为值
     */
    public String setex(String key, long second, String value) {
        return jedis.setex(key, second, value);
    }

    /**
     * 放入一个Json Value作为值
     */
    public String set(String key, Object obj) {
        return jedis.set(key, JSON.toJSONString(obj));
    }

    /**
     * 放入一个Json Value作为值，并设置过期时间
     */
    public String setex(String key, long second, Object obj) {
        return jedis.setex(key, second, JSON.toJSONString(obj));
    }

    /**
     * setbit
     */
    public boolean setbit(String key, long offset, boolean value) {
        return jedis.setbit(key, offset, value);
    }

    /**
     * setbit 并设置过期时间
     */
    public boolean setbitex(String key, long second, long offset, boolean value) {
        setbit(key, offset, value);
        return expire(key, second);
    }

    /**
     * 使用字节作为key
     */
    public String setByte(String key, String value) {
        return jedis.set(IOUtils.toByteArray(key), IOUtils.toByteArray(value));
    }

    /**
     * 使用字节作为key
     */
    public String setByte(String key, Object value) {
        return setByte(key, JSON.toJSONString(value));
    }

    /**
     * 使用字节作为key
     */
    public String setByte(Object key, Object value) {
        return setByte(String.valueOf(key), value);
    }

    /**
     * 使用字节作为key，并设置过期时间
     */
    public String setByteEx(byte[] key, long second, String value) {
        return jedis.setex(key, second, IOUtils.toByteArray(value));
    }

    /**
     * 使用字节作为key，并设置过期时间
     */
    public String setByteEx(byte[] key, long second, Object value) {
        return setByteEx(key, second, JSON.toJSONString(value));
    }

    /**
     * 使用字节作为key，并设置过期时间
     */
    public String setByteEx(String key, long second, Object value) {
        return setByteEx(IOUtils.toByteArray(key), second, value);
    }

    /**
     * 使用字节作为key，并设置过期时间
     */
    public String setByteEx(Object key, long second, Object value) {
        return setByteEx(String.valueOf(key), second, value);
    }

    /**
     * 获取字符串
     */
    public String get(String key) {
        return jedis.get(key);
    }

    /**
     * 获取Json对象
     */
    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    /**
     * 获取String对象，key会被转换成字节数组
     */
    public String getByte(String key) {
        return new String(jedis.get(IOUtils.toByteArray(key)), StandardCharsets.UTF_8);
    }


    /**
     * 获取String对象，key会被转换成字节数组
     */
    public <T> T getByte(String key, Class<T> clazz) {
        return JSON.parseObject(
              new String(jedis.get(IOUtils.toByteArray(key)), StandardCharsets.UTF_8), clazz);
    }


    /**
     * getbit
     */
    public boolean getbit(String key, long offset) {
        return jedis.getbit(key, offset);
    }

    /**
     * 在指定列表的尾部（右边）添加一个或多个元素
     */
    public long rpush(String key, String... values) {
        return jedis.rpush(key, values);
    }

    /**
     * 在指定列表的头部（左边）添加一个或多个元素
     */
    public long lpush(String key, String... values) {
        return jedis.lpush(key, values);
    }

    /**
     * 移除并获取指定列表的最后一个元素(最右边)
     */
    public String rpop(String key) {
        return jedis.rpop(key);
    }


    /**
     * 移除并获取指定列表的第一个元素(最左边)
     */
    public String lpop(String key) {
        return jedis.lpop(key);
    }

    /**
     * 替换指定索引位置的值
     */
    public String lset(String key, long index, String value) {
        return jedis.lset(key, index, value);
    }

    /**
     * 获取列表元素数量
     */
    public long llen(String key) {
        return jedis.llen(key);
    }

    /**
     * 获取列表 start 和 end 之间 的元素
     */
    public List<String> lrange(String key, long start, long end) {
        return jedis.lrange(key, start, end);
    }

    /**
     * 设置指定哈希表中指定字段的值
     */
    public long hset(String key, Map<String, String> value) {
        return jedis.hset(key, value);
    }

    /**
     * 只有指定字段不存在时设置指定字段的值
     */
    public long hsetnx(String key, String field, String value) {
        return jedis.hsetnx(key, field, value);
    }

    /**
     * 同时将一个或多个 field-value (域-值)对设置到指定哈希表中
     */
    public String hmset(String key, Map<String, String> value) {
        return jedis.hmset(key, value);
    }

    /**
     * 获取指定哈希表中指定字段的值
     */
    public String hget(String key, String field) {
        return jedis.hget(key, field);
    }

    /**
     * 获取指定哈希表中一个或者多个指定字段的值
     */
    public List<String> hmset(String key, String... fields) {
        return jedis.hmget(key, fields);
    }

    /**
     * 获取指定哈希表中一个或者多个指定字段的值
     */
    public Map<String, String> hgetAll(String key) {
        return jedis.hgetAll(key);
    }

    /**
     * 查看指定哈希表中指定的字段是否存在
     */
    public boolean hexists(String key, String field) {
        return jedis.hexists(key, field);
    }

    /**
     * 删除一个或多个哈希表字段
     */
    public long hdel(String key, String... fields) {
        return jedis.hdel(key, fields);
    }

    /**
     * 获取指定哈希表中字段的数量
     */
    public long hlen(String key) {
        return jedis.hlen(key);
    }

    /**
     * 对指定哈希中的指定字段做运算操作（正数为加，负数为减）
     */
    public long hincrby(String key, String field, long value) {
        return jedis.hincrBy(key, field, value);
    }

    /**
     * key是否存在
     */
    public boolean exists(String key) {
        return jedis.exists(key);
    }

    /**
     * 设置key过期时间
     */
    public boolean expire(String key, long second) {
        return jedis.expire(key, second) == 1L;
    }

    /**
     * 选择DB
     */
    public void select(int dbnum) {
        jedis.select(dbnum);
    }

    /**
     * 删除KEY
     */
    public void del(String key) {
        jedis.del(key);
    }

    /**
     * 删除KEY
     */
    public void delByte(String key) {
        jedis.del(IOUtils.toByteArray(key));
    }

    /**
     * 删除KEY
     */
    public void delByte(Object key) {
        delByte(String.valueOf(key));
    }

    /**
     * 关闭连接或返回连接池
     */
    @Override
    public void close() {
        jedis.close();
    }

}
