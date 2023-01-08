package com.hantiansoft.framework.cache;

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

/* Creates on 2022/8/5. */

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Jvm本地缓存
 *
 * @author Vincent Luo
 */
public interface JvmCache {

    /**
     * @return 缓存中存放的数据个数
     */
    int size();

    /**
     * @return 是否为空
     */
    boolean is_empty();

    /**
     * 遍历缓存中的所有数据
     */
    void forEach(BiConsumer<? super String, ? super Object> action);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 存入数据到缓存，如果存在会覆盖。根据 @expireTime 参数设置过期时间。
     * 如果 @expireTime 为 null，则永不过期。
     */
    <T> void push_back(String k, T v, Date expireTime);

    /**
     * 存入数据到缓存，如果存在会覆盖。自定义过期时间以及单位。不能设置永久缓存。
     */
    <T> void push_back(String k, T v, int expire, TimeUnit timeUnit);

    /**
     * 将Map存入到缓存中
     */
    <T> void push_back_all(Map<String, T> all, int expire, TimeUnit timeUnit);

    /**
     * 将序列化的数据同步到缓存中
     */
    <T> void import_buffer(byte[] bytes);

    /**
     * @return 导出数据用于同步
     */
    byte[] export_buffer();

    /**
     * 从缓存中获取数据，如果不存在返回 null。这个接口只能通过最简单的key获取数据。
     * 不能进行复杂的匹配。
     *
     * @return 返回值根据泛型自动推断类型
     */
    <T> T a_value_of(String k);

    /**
     * p_values_of 可以通过复杂的匹配条件获取数据。
     * <p>
     * 代码示例：
     * p_values_of("user*"); // 获取前缀为 user 的所有数据
     * p_values_of("*user"); // 获取后缀为 user 的所有数据
     * p_values_of("*");     // 获取所有数据
     *
     * @return 返回值根据泛型自动推断类型，如果泛型不匹配。list中的元素可能会造成
     * 异常，所以使用前请自行判断在缓存中存放的数据类型。
     */
    <T> List<T> p_value_of(String k);

    /**
     * 删除缓存。
     */
    void remove(String k);

    /**
     * 刷新缓存时间, 如果expireTime小于小于过期时间，则不刷新。
     * expireTime == null 则永不过期。
     */
    void flush(String k, Date expireTime);

    /**
     * 刷新缓存时间, 如果expire小于小于过期时间，则不刷新。
     * expire <= 0 删除缓存
     */
    void flush(String k, int expire, TimeUnit timeUnit);

}
