package devtools.framework.io;

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
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 字节缓冲工具类
 *
 * @author Vincent Luo
 */
public class ObjectSerializationUtils {

    /**
     * @return 将对象序列化成字节数组
     */
    public static byte[] serializationQuietly(Object obj) {
        ByteArrayOutputStream bytebuf = new ByteArrayOutputStream();

        try (ObjectOutputStream output = new ObjectOutputStream(bytebuf)) {
            output.writeObject(obj);
            return bytebuf.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 字节数组反序列化后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T unserializationQuietly(byte[] a) {
        if (a.length < 6)
            return (T) new String(a);

        String protocol = Integer.toHexString(a[0] & 0x000000ff) + Integer.toHexString(a[1] & 0x000000ff);

        // 如果是jdk序列化后的
        if ("ACED".equalsIgnoreCase(protocol)) {
            ByteArrayInputStream byteInputStream = null;
            ObjectInputStream inputStream = null;

            try {
                byteInputStream = new ByteArrayInputStream(a);
                inputStream = new ObjectInputStream(byteInputStream);
                return (T) inputStream.readObject();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(byteInputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }

        return null;
    }

}
