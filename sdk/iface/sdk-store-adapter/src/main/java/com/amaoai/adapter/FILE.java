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

import java.util.Date;

/**
 * 文件信息
 *
 * @author Vincent Luo
 */
public interface FILE {

    /**
     * @return 文件名
     */
    String getName();

    /**
     * @return 文件的hash值
     */
    String getHash();

    /**
     * @return 文件的md5值
     */
    String getMd5();

    /**
     * @return 文件大小
     */
    long getSize();

    /**
     * @return 文件类型
     */
    String getType();

    /**
     * @return 获取上传时间
     */
    Date getPutTime();

}
