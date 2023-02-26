package com.amaoai.framework;

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

/* Creates on 2022/4/15. */

import com.alibaba.fastjson2.JSON;
import com.amaoai.framework.collections.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent Luo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {

    private String code;
    private String message;
    private Object data;

    /**
     * HTTP状态
     */
    public interface Status {
        String S200 = "200";
        String S500 = "500";
        String S401 = "401";
    }

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(Object record) {
        return new R<T>(Status.S200, "success", record);
    }

    /**
     * 自定义填充数据填充
     */
    public static <T> R<T> ok(String k1, Object v1) {
        return ok(Maps.of(k1, v1));
    }

    public static <T> R<T> ok(String k1, Object v1, String k2, Object v2) {
        return ok(Maps.of(k1, v1, k2, v2));
    }

    public static <T> R<T> ok(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return ok(Maps.of(k1, v1, k2, v2, k3, v3));
    }

    public static <T> R<T> ok(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return ok(Maps.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }

    public static <T> R<T> ok(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        return ok(Maps.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }

    public static <T> R<T> ok(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6, Object v6) {
        return ok(Maps.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6));
    }

    public static <T> R<T> fail(String code, String message) {
        return new R<T>(code, message, null);
    }

    public static <T> R<T> fail(String message) {
        return fail(Status.S500, message);
    }

    /**
     * 结果是否成功
     */
    public boolean isSuccess() {
        return StringUtils.equals(code, Status.S200);
    }

    /**
     * 获取数据并转换成对应的对象
     */
    public T to(Class<T> clazz) {
        return JSON.to(clazz, this.data);
    }

}
