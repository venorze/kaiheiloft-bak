package com.amaoai.framework

import com.amaoai.framework.io.ReadOptions
import com.amaoai.framework.refection.ClassLoaders
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.Properties

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

/* Creates on 2023/2/22. */

/**
 * properties文件内容读取
 *
 * @author Amaoai
 */
object PropertiesSourceLoaders {

    /**
     * 通过InputStream加载Properties
     */
    @JvmStatic
    fun loadProperties(input: InputStream): Properties {
        val prop = Properties()
        prop.load(input)
        return prop
    }

    /**
     * 加载classpath下的properties文件
     */
    @JvmStatic
    fun loadProperties(filename: String): Properties? {
        return try {
            val station = ClassLoaders.getResourceStreamStation(filename)
            station.read(ReadOptions.PROPERTIES) as Properties
        } catch (e: FileNotFoundException) {
            null
        }
    }

}