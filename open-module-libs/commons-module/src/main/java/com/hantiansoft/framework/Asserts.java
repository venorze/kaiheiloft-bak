package com.hantiansoft.framework;

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

/* Creates on 2020/3/11. */

import io.jsonwebtoken.lang.Collections;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * @author Vincent Luo
 */
public class Asserts {

    public interface CallbackFunctionHasReturnValue<R> {
        R apply() throws Throwable;
    }

    public interface CallbackFunctionNoReturnValue {
        void apply() throws Throwable;
    }

    /**
     * 断言一个对象，如果对象是空则抛出异常
     */
    public static void throwIfNull(Object object) {
        if (object == null)
            throw new NullPointerException();
    }


    /**
     * 断言一个对象，如果对象是空则抛出异常
     */
    public static void throwIfNull(Object object, String message, Object... args) {
        if (object == null)
            throw new NullPointerException(StringUtils.vfmt(message, args));
    }

    /**
     * 断言一个条件，如果bool变量为true则抛出异常
     */
    public static void throwIfBool(boolean bool, String message, Object... args) {
        if (!bool)
            throw new IllegalArgumentException(StringUtils.vfmt(message, args));
    }

    /**
     * 断言函数的执行（有返回值），如果函数执行出错，则抛出异常(默认异常消息)
     */
    public static <R> R throwIfError(CallbackFunctionHasReturnValue<R> call) {
        return throwIfError(call, null);
    }

    /**
     * 断言函数的执行（有返回值），如果函数执行出错，则抛出异常(自定义异常消息)
     */
    public static <R> R throwIfError(CallbackFunctionHasReturnValue<R> call, String message, Object... args) {
        try {
            return call.apply();
        } catch (Throwable e) {
            if (StringUtils.isNotEmpty(message))
                throw new IllegalArgumentException(StringUtils.vfmt(message, args), e);
            else
                throw new IllegalArgumentException(e);
        }
    }

    /**
     * 断言函数的执行（无返回值），如果函数执行出错，则抛出异常(默认异常消息)
     */
    public static void throwIfError(CallbackFunctionNoReturnValue call) {
        throwIfError(call, null);
    }

    /**
     * 断言函数的执行（无返回值），如果函数执行出错，则抛出异常(可以自定义异常消息)
     */
    public static void throwIfError(CallbackFunctionNoReturnValue call, String message, Object... args) {
        try {
            call.apply();
        } catch (Throwable e) {
            if (StringUtils.isNotEmpty(message))
                throw new IllegalArgumentException(StringUtils.vfmt(message, args), e);
            else
                throw new IllegalArgumentException(e);
        }
    }


    /**
     * 断言一个字符串，如果是空字符串或者是null则抛出异常
     */
    public static void throwIfEmpty(String input, String message) {
        if (StringUtils.isEmpty(input))
            throw new IllegalArgumentException(message);
    }

    public static void throwIfEmpty(String input) {
        throwIfEmpty(input, "string is empty or null!");
    }

    /**
     * 断言一个集合对象，如果是空字符串或者是null则抛出异常
     */
    public static <T> void throwIfEmpty(Collection<T> input, String message) {
        if (Collections.isEmpty(input))
            throw new IllegalArgumentException(message);
    }

    public static <T> void throwIfEmpty(Collection<T> input) {
        throwIfEmpty(input, "collection is empty or null!");
    }

    /**
     * 断言一个Map对象，如果是空字符串或者是null则抛出异常
     */
    public static <K, V> void throwIfEmpty(Map<K, V> input, String message) {
        if (Collections.isEmpty(input))
            throw new IllegalArgumentException(message);
    }

    public static <K, V> void throwIfEmpty(Map<K, V> input) {
        throwIfEmpty(input, "map is empty or null!");
    }

}
