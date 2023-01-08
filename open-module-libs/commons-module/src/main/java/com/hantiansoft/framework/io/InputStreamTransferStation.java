package com.hantiansoft.framework.io;

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

/* Creates on 2022/8/9. */

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 可以通过InputStreamTransferStation.transfer方法将InputStream转换成任意
 * 你想转换的格式或类型。
 * <p>
 * 需要关闭流！
 *
 * @author Vincent Luo
 */
public class InputStreamTransferStation implements AutoCloseable {
    private final InputStream internalInputStream;

    public InputStreamTransferStation(InputStream inputStream) {
        if (inputStream == null)
            throw new RuntimeException("No InputStream.");

        this.internalInputStream = inputStream;
    }

    public InputStreamTransferStation(byte[] a) {
        if (a == null)
            throw new RuntimeException("No Byte Array.");

        this.internalInputStream = new ByteArrayInputStream(a);
    }

    /**
     * 默认转为字符串
     */
    public byte[] strread() {
        return (byte[]) strread(IOStreamReadEnumerate.TEXT);
    }

    /**
     * 默认以UTF-8编码形式读取数据
     */
    public Object strread(IOStreamReadEnumerate type) {
        return read(type, "UTF-8");
    }

    /**
     *   读取输入流中的数据，将数据转换成其他类型。读取完数据以后流将会被关闭，
     * 不能进行二次读取。
     */
    public Object read(IOStreamReadEnumerate type, String encoding) {
        try {
            if (type == null)
                throw new RuntimeException("输入流读取类型不能为空");

            switch (type) {
                case TEXT:
                    return IOUtils.strread(internalInputStream, encoding);
                case INT:
                    return Integer.parseInt(IOUtils.strread(internalInputStream, encoding));
                case LONG:
                    return Long.parseLong(IOUtils.strread(internalInputStream, encoding));
                case OBJECT:
                    return ObjectSerializationUtils.unserializationQuietly(IOUtils.read(internalInputStream));
                case BYTE_BUF:
                    return IOUtils.read(internalInputStream);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(internalInputStream);
    }
}
