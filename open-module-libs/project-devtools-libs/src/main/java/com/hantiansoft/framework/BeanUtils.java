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

/* Creates on 2022/8/8. */

import com.hantiansoft.framework.collections.Lists;
import com.hantiansoft.framework.refection.ClassUtils;
import org.joda.time.field.FieldUtils;

import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Vincent Luo
 */
public class BeanUtils {

    public static <T> List<T> copyProperties(List<?> listSource, Class<T> targetClass) {
        return copyProperties(listSource, targetClass, new String[]{});
    }

    /**
     * @return 拷贝属性到新的对象集合
     */
    public static <T> List<T> copyProperties(List<?> listSource, Class<T> targetClass, String... ignoreFields) {
        List<T> listTarget = Lists.newArrayList(listSource.size());
        listSource.forEach(src -> listTarget.add(copyProperties(src, targetClass, ignoreFields)));
        return listTarget;
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        return copyProperties(source, targetClass, new String[]{});
    }

    /**
     * @return 创建新的对象，并将源对象的属性拷贝到新的对象
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass, String... ignoreProperties) {
        try {
            T __t = ClassUtils.newInstance(targetClass);
            copyProperties(source, __t, ignoreProperties);
            return __t;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, new String[]{});
    }

    /**
     * 拷贝属性列表到目标对象
     */
     @SuppressWarnings("ConstantConditions")
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        Asserts.throwIfNull(source, "源对象不能为空");
        Asserts.throwIfNull(source, "目标对象不能为空");

        /* source class */
        Class<?> sourceClass = source.getClass();

        /* source class */
        Class<?> targetClass = target.getClass();
        Field[] targetDeclaredFields = targetClass.getDeclaredFields();

        /* 忽略成员 */
        List<String> ignorePropertiesName = Lists.newArrayList(ignoreProperties);
        int ignorePropertiesNamesSize = ignorePropertiesName.size();

        for (Field targetDeclaredField : targetDeclaredFields) {
            var targetFieldName = targetDeclaredField.getName();
            if (ignorePropertiesNamesSize > 0 &&
                    ignorePropertiesName.contains(targetFieldName))
                continue;

            try {
                Field sourceDeclaredField = ClassUtils.getDeclaredField(sourceClass, targetFieldName);

                // 如果当前类不存在目标成员，则从父类去寻找
                if (sourceDeclaredField == null)
                    sourceDeclaredField = findDeclaredFieldInSuperClass(sourceClass, targetFieldName);

                // 设置成员内容
                sourceDeclaredField.setAccessible(true);
                targetDeclaredField.setAccessible(true);
                targetDeclaredField.set(target, sourceDeclaredField.get(source));
            } catch (Throwable e) {
                // ignore
            }
        }
    }

    /**
     * 查找父类是否有目标成员
     */
    private static Field findDeclaredFieldInSuperClass(Class<?> clazz, String targetFieldName) {
        Field rfield;
        var superclass = clazz.getSuperclass();

        // 如果没有父类直接跳出该方法
        if (superclass == null)
            return null;

        rfield = ClassUtils.getDeclaredField(superclass, targetFieldName);
        if (rfield == null)
            rfield = findDeclaredFieldInSuperClass(superclass, targetFieldName);

        return rfield;
    }

}
