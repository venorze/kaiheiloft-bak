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

import com.hantiansoft.framework.exception.ObjectNotFoundRuntimeException;
import com.hantiansoft.framework.unsafe.Unsafes;
import sun.misc.Unsafe;

/**
 * 类加载器工具类
 *
 * @author Vincent Luo
 */
public final class ClassLoaders {

    /* Unsafe类 */
    private static final Unsafe theUnsafe = Unsafes.getUnsafe();

    /**
     * 获取当前类加载器，获取顺序是：
     * 当前线程的上下文类加载器 -> 当前类加载器 -> 系统类加载器
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (cl == null)
            if ((cl = ClassUtils.class.getClassLoader()) == null)
                cl = ClassLoader.getSystemClassLoader();

        return cl;
    }

    /**
     * 将外部的字节码加载到当前的类加载器中，加载到当前的类加载器中
     * 外面的字节码就可以共享类库，使用当前项目的类。
     */
    public static Class<?> loadClass(String fullname, byte[] a, int off, int len) {
        return loadClass(fullname, a, off, len, ClassLoaders.getDefaultClassLoader());
    }

    /**
     * 将外部的字节码加载到当前的类加载器中，从而达到运行时动态加载的目的。需要注意的是如果
     * 你的需求需要让每个类的类库相互隔离，那么#loadClass()就不能满足你的要求，
     * 这时候需要自己编写一个类加载器来实现相互隔离的需求。
     *
     * @param fullname    全类名
     * @param a           字节码文件读取出的数据
     * @param off         偏移量
     * @param len         长度
     * @param classLoader 类加载器，如果使用默认传空
     */
    public static Class<?> loadClass(String fullname, byte[] a, int off, int len,
                                     ClassLoader classLoader) {
        try {
            return theUnsafe.defineClass(fullname, a, off, len, classLoader, null);
        } catch (Throwable e) {
            if (e instanceof NoClassDefFoundError)
                throw new ObjectNotFoundRuntimeException("Class not found: " + fullname, e);

            throw new IllegalArgumentException(e);
        }
    }

}
