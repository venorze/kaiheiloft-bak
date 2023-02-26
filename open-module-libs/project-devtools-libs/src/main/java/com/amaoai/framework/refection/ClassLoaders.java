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

/* Creates on 2022/8/16. */

import com.amaoai.framework.collections.Lists;
import com.amaoai.framework.exception.ObjectNotFoundRuntimeException;
import com.amaoai.framework.io.FileUtils;
import com.amaoai.framework.io.InputStreamTransferStation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * 类加载器工具类
 *
 * @author Vincent Luo
 */
public final class ClassLoaders {

    /**
     * @return 当前类加载器
     */
    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

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
     * 获取Classpath目录下的资源文件
     *
     * @param filename 文件名
     * @return 输入流
     */
    public static InputStreamTransferStation getResourceStreamStation(String filename) {
        InputStream resourceStream = currentClassLoader().getResourceAsStream(filename);
        if (resourceStream != null)
            return new InputStreamTransferStation(resourceStream);

        return null;
    }

    /**
     * 获取某个包下的所有类
     */
    public static List<Class<?>> scanPackages(String basePackage) {
        List<Class<?>> classList = Lists.newArrayList();

        try {
            // 处理basePackages路径
            String clearPackage = basePackage.replaceAll("\\.", "/");
            URL resource = currentClassLoader().getResource(clearPackage);
            if (resource == null)
                throw new ObjectNotFoundRuntimeException("路径不存在");

            File file = new File(resource.getPath());
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                String name = listFile.getName();
                if (FileUtils.extensionEquals(name, ".class")) {
                    classList.add(
                            Class.forName(basePackage + "." + name.substring(0, name.indexOf(".")))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

}
