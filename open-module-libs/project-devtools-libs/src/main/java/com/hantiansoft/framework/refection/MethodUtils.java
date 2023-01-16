package com.hantiansoft.framework.refection;

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

/* Creates on 2022/8/16. */

import com.hantiansoft.framework.exception.FmtRuntimeException;

import java.lang.reflect.Method;

/**
 * 方法工具类
 *
 * @author Vincent Luo
 */
public final class MethodUtils {

    /* 成员方法调用 */
    public static Object doInvokeNonStatic(Object o, String name, Object... parameters) {
        return invoke0(o.getClass(), o, name, parameters);
    }

    /* 静态方法调用 */
    public static Object doInvokeStatic(Class<?> aClass, String name, Object... parameters) {
        return invoke0(aClass, null, name, parameters);
    }

    /**
     * 通过反射机制调用方法, 并且提供参数和返回值。
     *
     * @param aClass     类对象
     * @param name       方法名
     * @param o          实例化对象，如果调用静态方法就传null
     * @param parameters 方法参数
     * @return 返回值
     */
    static Object invoke0(Class<?> aClass, Object o, String name, Object... parameters) {
        Class<?>[] parametersClassArray = ClassUtils.toClassArray(parameters);
        try {
            Method declaredMethod = aClass.getDeclaredMethod(name, parametersClassArray);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(o, parameters);
        } catch (Throwable e) {
            throw new FmtRuntimeException(e);
        }
    }

}
