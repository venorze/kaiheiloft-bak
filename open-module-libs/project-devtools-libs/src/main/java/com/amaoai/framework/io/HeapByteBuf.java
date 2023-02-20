package com.amaoai.framework.io;

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

/* Creates on 2022/8/12. */

import com.amaoai.framework.Asserts;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 数据缓冲区, 支持随机访问和写入。
 * 大部分API参考c语言中的 seek() fread() fwrite() 设计
 * <p>
 * ByteBuf不考虑在内存中直接分配，而是让数据分配JVM在堆上。这样避免了内存溢出的问题。
 * <p>
 * ByteBuf可分配的内存大小取决于JVM的可用内存。
 *
 * @author Vincent Luo
 */
class HeapByteBuf extends ByteBuf implements Serializable {
    // 缓冲区
    private byte[] buf;

    /**
     * 默认构造函数，初始化缓冲区大小
     */
    public HeapByteBuf(int size) {
        this.buf = IOUtils.getByteArray(size);
    }

    /**
     * ByteBuf构造函数,传入字节数组以及缓冲区大小，
     * 初始化缓冲区。
     *
     * @see #duplicate()
     */
    public HeapByteBuf(byte[] a, int off, int len) {
        Asserts.throwIfNull(a);

        if (off == 0 && len == a.length) {
            this.buf = a;
        } else {
            this.buf = IOUtils.arrcpy(a, off, len);
        }

        this.capacity = this.buf.length;
    }

    @Override
    public ByteBuf duplicate() {
        return new HeapByteBuf(getBufferArray(), 0, capacity);
    }

    @Override
    public void read(byte[] a, int off, int len) {
        Asserts.throwIfBool(!(len > buf.length), "读取的数据超出缓冲区大小，读取大小：{}, 数组大小：{}",
                len, a.length);

        System.arraycopy(this.buf, this.position, a, off, len);
        this.position += len;
    }

    @Override
    public byte getByte() {
        byte b = buf[position];
        position++;
        return b;
    }

    @Override
    public byte[] getBufferArray() {
        byte[] retval = new byte[capacity];
        int pos = this.position;
        seek(0);
        read(retval);
        seek(pos);
        return retval;
    }

    /* 确保缓冲区内部容量足够 */
    private void ensureCapacity(int size) {
        if (buf.length < (capacity + size)) {
            byte[] nbuf = IOUtils.getByteArray((buf.length + size) * 2);
            System.arraycopy(buf, 0, nbuf, 0, buf.length);
            buf = nbuf;
        }
    }

    @Override
    public void write(byte a) {
        ensureCapacity(1);
        buf[position++] = a;
        capacity++;
    }

    @Override
    void write0(byte[] a, int off, int len) {
        ensureCapacity(len);
        System.arraycopy(a, off, buf, position, len);
        position += len;
        capacity += len;
    }

    @Override
    public String toString() {
        int pos = position;
        flip();
        byte[] a = IOUtils.getByteArray(capacity);
        read(a);
        seek(pos);
        return "ByteBuf{" +
                "buf=" + Arrays.toString(a) +
                '}';
    }

}
