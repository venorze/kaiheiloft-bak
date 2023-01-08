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

/* Creates on 2022/8/8. */

import com.hantiansoft.framework.collections.Lists;
import com.hantiansoft.framework.collections.Maps;
import com.hantiansoft.framework.io.ObjectSerializationUtils;
import com.hantiansoft.framework.time.TimeUnits;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Vincent Luo
 */
class JvmCacheImplements implements JvmCache {

    /**
     * 存放缓存的Map对象
     */
    private final Map<String, JvmCacheDelayValue> internalCacheHashMap =
            Maps.newConcurrentHashMap();

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            new ScheduledThreadPoolExecutor(1);

    public JvmCacheImplements() {
        startScheduledExecutorTaskClear();
    }

    /**
     * 定时清理
     */
    void startScheduledExecutorTaskClear() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            final List<String> expiredKeys = Lists.newArrayList();

            if (!is_empty()) {
                for (Map.Entry<String, JvmCacheDelayValue> entry : internalCacheHashMap.entrySet()) {
                    JvmCacheDelayValue odv = entry.getValue();
                    if (odv.expired())
                        expiredKeys.add(entry.getKey());
                }

                expiredKeys.forEach(internalCacheHashMap::remove);
            }

        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

    @Override
    public int size() {
        return internalCacheHashMap.size();
    }

    @Override
    public boolean is_empty() {
        return internalCacheHashMap.isEmpty();
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        internalCacheHashMap.forEach((k, v) -> action.accept(k, v.getData()));
    }

    @Override
    public void clear() {
        internalCacheHashMap.clear();
    }

    @Override
    public <T> void push_back(String k, T v, Date expireTime) {
        internalCacheHashMap.put(k, new JvmCacheDelayValue(k, v, expireTime));
    }

    @Override
    public <T> void push_back(String k, T v, int expire, TimeUnit timeUnit) {
        push_back(k, v, TimeUnits.valueOf(timeUnit.name()).plus(expire));
    }

    @Override
    public <T> void push_back_all(Map<String, T> all, int expire, TimeUnit timeUnit) {
        if (all != null && !all.isEmpty())
            all.forEach((k, v) -> push_back(k, v, expire, timeUnit));
    }

    @Override
    public <T> void import_buffer(byte[] bytes) {
        Map<String, JvmCacheDelayValue> importData = ObjectSerializationUtils.unserializationQuietly(bytes);
        if (importData != null && !importData.isEmpty())
            internalCacheHashMap.putAll(importData);
    }

    @Override
    public byte[] export_buffer() {
        return ObjectSerializationUtils.serializationQuietly(internalCacheHashMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T a_value_of(String k) {
        JvmCacheDelayValue odv = internalCacheHashMap.get(k);

        if (odv == null)
            return null;

        if (odv.expired()) {
            internalCacheHashMap.remove(k);
            return null;
        }

        return (T) odv.getData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> p_value_of(String k) {
        List<JvmCacheDelayValue> values = Lists.newArrayList();

        int flag = 0;
        final int START_WITH = 1;
        final int END_WITH = 2;

        String findk = "";
        char[] charArray = k.toCharArray();

        /* 匹配结尾 */
        if (charArray[0] == '*') {
            flag = END_WITH;
            findk = k.substring(1);
        }

        /* 匹配开始 */
        if (charArray[charArray.length - 1] == '*') {
            flag = START_WITH;
            findk = k.substring(0, k.length() - 1);
        }

        if (flag > 0) {
            int finalFlag = flag;
            String finalFindk = findk;
            internalCacheHashMap.forEach((key, value) -> {
                if (finalFlag == START_WITH) {
                    if (key.startsWith(finalFindk))
                        values.add(value);
                } else {
                    if (key.endsWith(finalFindk))
                        values.add(value);
                }
            });
        } else {
            values.add(internalCacheHashMap.get(k));
        }

        /* 过期key */
        final List<JvmCacheDelayValue> expiredJvmCacheDelayValue = Lists.newArrayList();

        values.forEach(value -> {
            if (value.expired()) {
                expiredJvmCacheDelayValue.add(value);
                internalCacheHashMap.remove(value.getKey());
            }
        });

        values.removeAll(expiredJvmCacheDelayValue);

        return values.stream().map(value -> (T) value.getData()).collect(Collectors.toList());
    }

    @Override
    public void remove(String k) {
        internalCacheHashMap.remove(k);
    }

    @Override
    public void flush(String k, Date expireTime) {
        JvmCacheDelayValue odv = internalCacheHashMap.get(k);
        if (odv == null)
            return;

        odv.setExpireTime(expireTime);
    }

    @Override
    public void flush(String k, int expire, TimeUnit timeUnit) {
        flush(k, TimeUnits.valueOf(timeUnit.name()).plus(expire));
    }

}
