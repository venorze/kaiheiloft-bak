package com.amaoai.framework.collections;

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

/* Creates on 2019/12/13 */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 静态的Map工具类，注意如果在创建Map的时候要初始化
 * Map的大小，那么你设置的Map大小必须是2的幂。
 *
 * @author Vincent Luo
 */
public final class Maps {

    private Maps() {
    }

    /**
     * 拷贝一个Map
     */
    public static <K, V> Map<K, V> copyOf(Map<K, V> copyMap) {
        return copyOf(copyMap, null);
    }

    /**
     * 拷贝一个Map
     *
     * @param addMap 拷贝map并添加一个map
     */
    public static <K, V> Map<K, V> copyOf(Map<K, V> copyMap, Map<K, V> addMap) {
        HashMap<K, V> map = new HashMap<>(copyMap);
        if (addMap != null)
            map.putAll(addMap);
        return map;
    }

    /**
     * 创建一个新的{@code HashMap}实例
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * 创建一个新的{@code HashMap}实例并设置{@code HashMap}的初始化大小，
     * 减少Map扩容带来的性能损耗。
     */
    public static <K, V> HashMap<K, V> newHashMap(int capacity) {
        return new HashMap<>(capacity);
    }

    /**
     * 拷贝{@code Map}中的数据到新创建的{@code HashMap}实例中
     */
    public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> HashMap<K, V> ofMap(K k, V v) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k, v);
        return map;
    }

    public static <K, V> HashMap<K, V> ofMap(K k1, V v1, K k2, V v2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> HashMap<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> HashMap<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> HashMap<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static <K, V> HashMap<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return map;
    }

    /**
     * 创建一个新的{@code LinkedHashMap}实例
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * 创建一个新的{@code LinkedHashMap}实例并设置{@code LinkedHashMap}的初始化大小，
     * 减少Map扩容带来的性能损耗。
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int capacity) {
        return new LinkedHashMap<>(capacity);
    }

    /**
     * 拷贝{@code Map}中的数据到新创建的{@code LinkedHashMap}中
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<K, V> map) {
        return new LinkedHashMap<>(map);
    }

    /**
     * 创建一个新的{@code ConcurrentHashMap}实例
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(32);
    }

    /**
     * 创建一个新的{@code ConcurrentHashMap}实例并设置{@code ConcurrentHashMap}的初始化大小，
     * 减少Map扩容带来的性能损耗。
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int capacity) {
        return new ConcurrentHashMap<>(capacity);
    }

    /**
     * 拷贝{@code Map}中的数据到新的{@code ConcurrentHashMap}实例中
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<K, V> map) {
        return new ConcurrentHashMap<>(map);
    }

}
