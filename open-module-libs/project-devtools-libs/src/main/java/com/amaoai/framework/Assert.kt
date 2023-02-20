package com.amaoai.framework

import com.amaoai.framework.StringUtils.vfmt

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

/* Creates on 2023/2/20. */

/**
 * 断言工具类
 *
 * @author Vincent Luo
 */
object Assert {

    interface CallbackFunctionHasReturnValue<R> {
        @Throws(Throwable::class)
        fun apply(): R
    }

    interface CallbackFunctionNoReturnValue {
        @Throws(Throwable::class)
        fun apply()
    }

    // 如果没有自定义异常信息，那么就打印原本的异常信息
    private fun hasFmt(e: Throwable, fmt: String?, vararg args: Any): Throwable {
        return if (StringUtils.isNotEmpty(fmt)) {
            IllegalArgumentException(vfmt(fmt, *args), e)
        } else {
            IllegalArgumentException(e)
        }
    }

    /**
     * 断言一个对象，如果对象是空则抛出异常（默认异常信息）
     */
    @JvmStatic
    fun throwIfNull(obj: Any?) = throwIfNull(obj, "null")

    /**
     * 断言一个对象，如果对象是空则抛出异常（自定义异常信息）
     */
    @JvmStatic
    fun throwIfNull(obj: Any?, fmt: String, vararg args: Any) {
        if (obj == null)
            throw NullPointerException(vfmt(fmt, *args))
    }

    /**
     * 断言一个对象，如果对象不是空则抛出异常（默认异常信息）
     */
    @JvmStatic
    fun throwIfNotNull(obj: Any?) = throwIfNotNull(obj, "object must be null.")

    /**
     * 断言一个对象，如果对象是空则抛出异常（自定义异常信息）
     */
    @JvmStatic
    fun throwIfNotNull(obj: Any?, fmt: String, vararg args: Any) {
        if (obj != null)
            throw IllegalArgumentException(vfmt(fmt, *args))
    }

    /**
     * 断言一个布尔类型的结果，如果结果是False则抛出异常（自定义异常信息）
     */
    @JvmStatic
    fun throwIfBool(bool: Boolean) = throwIfBool(bool, "result is false!")

    /**
     * 断言一个布尔类型的结果，如果结果是False则抛出异常（自定义异常信息）
     */
    @JvmStatic
    fun throwIfBool(bool: Boolean, fmt: String, vararg args: Any) {
        if (!bool)
            throw IllegalArgumentException(vfmt(fmt, *args))
    }

    /**
     * 断言函数的执行（有返回值），如果函数执行出错，则抛出异常(打印默认的异常信息)
     */
    @JvmStatic
    fun <R> throwIfError(call: CallbackFunctionHasReturnValue<R>): R = throwIfError(call, null)

    /**
     * 断言函数的执行（有返回值），如果函数执行出错，则抛出异常(打印自定义异常消息)
     */
    @JvmStatic
    fun <R> throwIfError(call: CallbackFunctionHasReturnValue<R>, fmt: String?, vararg args: Any): R {
        return try {
            call.apply()
        } catch (e: Throwable) {
            throw hasFmt(e, fmt, *args)
        }
    }

    /**
     * 断言函数的执行（无返回值），如果函数执行出错，则抛出异常(打印默认的异常信息)
     */
    @JvmStatic
    fun throwIfError(call: CallbackFunctionNoReturnValue) = throwIfError(call, null)

    /**
     * 断言函数的执行（无返回值），如果函数执行出错，则抛出异常(打印自定义异常消息)
     */
    @JvmStatic
    fun throwIfError(call: CallbackFunctionNoReturnValue, fmt: String?, vararg args: Any) {
        try {
            call.apply()
        } catch (e: Throwable) {
            throw hasFmt(e, fmt, *args)
        }
    }

    /**
     * 断言一个字符串，如果字符串为空则抛出异常（默认异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: String?) = throwIfEmpty(input, "string is null or empty.")

    /**
     * 断言一个字符串，如果字符串为空则抛出异常（自定义异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: String?, fmt: String, vararg args: Any) {
        if (input.isNullOrEmpty())
            throw IllegalArgumentException(vfmt(fmt, *args))
    }

    /**
     * 断言一个集合，如果集合为空则抛出异常（默认异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: Collection<*>?) = throwIfEmpty(input, "list is null or empty.")

    /**
     * 断言一个集合，如果集合为空则抛出异常（自定义异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: Collection<*>?, fmt: String, vararg args: Any) {
        if (input.isNullOrEmpty())
            throw IllegalArgumentException(vfmt(fmt, *args))
    }


    /**
     * 断言一个Map，如果Map为空则抛出异常（默认异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: Map<*, *>?) = throwIfEmpty(input, "map is null or empty.")

    /**
     * 断言一个Map，如果Map为空则抛出异常（自定义异常消息）
     */
    @JvmStatic
    fun throwIfEmpty(input: Map<*, *>?, fmt: String, vararg args: Any) {
        if (input.isNullOrEmpty())
            throw IllegalArgumentException(vfmt(fmt, *args))
    }

}