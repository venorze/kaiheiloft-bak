package com.amaoai.framework.refection;

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

/* Creates on 2022/8/11. */

import com.amaoai.framework.collections.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Vincent Luo
 */
public final class ClassUtils {

    /**
     * 实例化一个类对象，根据类的构造器传入参数数据。
     *
     * @param _class     类对象
     * @param parameters 构造器参数，如果使用空构造器就不传
     * @return 类对象实例
     */
    public static <T> T newInstance(Class<T> _class, Object... parameters) {
        try {
            Class<?>[] parametersClassArray = toClassArray(parameters);
            Constructor<T> constructor = _class.getConstructor(parametersClassArray);
            return constructor.newInstance(parameters);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将参数数组转换成类型数组
     */
    public static Class<?>[] toClassArray(Object... parameters) {
        List<Class<?>> parametersClassList = Lists.newArrayList();
        for (Object parameter : parameters)
            parametersClassList.add(parameter.getClass());

        Class<?>[] parametersClassArray = new Class<?>[parametersClassList.size()];
        parametersClassList.toArray(parametersClassArray);

        return parametersClassArray;
    }

    /**
     * 获取成员（包括父类）对象但不抛出异常
     */
    public static Field getDeclaredFieldIncludeSuperclass(Class<?> sourceClass, String targetFieldName) {
        try {
            return sourceClass.getDeclaredField(targetFieldName);
        } catch (Exception ignore) {
            return findFieldInSuperclass(sourceClass, targetFieldName);
        }
    }

    /**
     * 递归查找
     */
    private static Field findFieldInSuperclass(Class<?> clazz, String targetFieldName) {
        Field rfield;
        var superclass = clazz.getSuperclass();

        // 如果没有父类直接跳出该方法
        if (superclass == null)
            return null;

        try {
            rfield = superclass.getDeclaredField(targetFieldName);
        } catch (Exception ignore){
            rfield = null;
        }

        if (rfield == null)
            rfield = findFieldInSuperclass(superclass, targetFieldName);

        return rfield;
    }

    /**
     * 获取所有成员（包括父类）对象但不抛出异常
     */
    public static List<Field> getDeclaredFieldsIncludeSuperclass(Class<?> sourceClass) {
        return getDeclaredFieldsIncludeSuperclass(sourceClass, Lists.newArrayList());
    }

    /**
     * 递归查找
     */
    private static List<Field> getDeclaredFieldsIncludeSuperclass(Class<?> sourceClass, List<Field> fields) {
        // 获取所有成员
        Field[] declaredFields = sourceClass.getDeclaredFields();
        fields.addAll(Lists.of(declaredFields));

        Class<?> superclass = sourceClass.getSuperclass();
        if (superclass != Object.class)
            getDeclaredFieldsIncludeSuperclass(superclass, fields);

        return fields;
    }
}
