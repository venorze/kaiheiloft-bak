package com.amaoai.adapter;

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

/* Creates on 2023/1/9. */

import devtools.framework.refection.ClassUtils;

import java.util.Properties;

/**
 * 对象存储SDK适配器
 *
 * @author Vincent Luo
 */
public class StoreAdapter {

    /**
     * 获取储存策略对象
     *
     * @param clazz PutPolicy接口实现对象
     * @param props 初始化配置信息
     */
    public static SourcePolicy createSourcePolicy(Class<? extends SourcePolicy> clazz, Properties props) {
        return ClassUtils.newInstance(clazz, props);
    }

}
