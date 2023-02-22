package devtools.framework.refection;

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

import devtools.framework.io.InputStreamTransferStation;

import java.io.InputStream;

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

}
