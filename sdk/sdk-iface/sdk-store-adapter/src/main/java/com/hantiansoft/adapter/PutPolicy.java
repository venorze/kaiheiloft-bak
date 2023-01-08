package com.hantiansoft.adapter;

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

import com.hantiansoft.framework.StringUtils;
import com.hantiansoft.framework.io.ByteBuf;

import java.io.InputStream;

/**
 * 文件上传策略
 *
 * @author Vincent Luo
 */
public interface PutPolicy {

    /**
     * 直接通过 File 对象上传文件。
     *
     * @param path 文件路径
     * @param fmt  上传保存的文件名
     * @param args 字符串格式化参数（如果有，例如：avatar/{}）
     */
    void putFile(String path, String fmt, Object... args);

    /**
     * 通过字节流上传文件
     *
     * @param byteBuf 字节数组缓冲区
     * @param fmt  上传保存的文件名
     * @param args 字符串格式化参数（如果有，例如：avatar/{}）
     */
    void putBytes(ByteBuf byteBuf, String fmt, Object... args);

    /**
     * 通过数据流上传文件
     *
     * @param inputStream 输入流
     * @param fmt  上传保存的文件名
     * @param args 字符串格式化参数（如果有，例如：avatar/{}）
     */
    void putInputStream(InputStream inputStream, String fmt, Object... args);

}
