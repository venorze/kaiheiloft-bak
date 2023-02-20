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

/* Creates on 2022/8/13. */

import com.amaoai.framework.Assert;

/**
 * 支持随机访问的ByteBuf抽象类
 *
 * @author Vincent Luo
 */
public abstract class ByteBuf {
    /* int类型占用字节大小 */
    public static final long SIZE_OF_INT = 4L;

    /* short类型占用字节大小 */
    public static final long SIZE_OF_SHORT = 2L;

    /* long类型占用字节大小 */
    public static final long SIZE_OF_LONG = 8L;

    /* double类型占用字节大小 */
    public static final long SIZE_OF_DOUBLE = 8L;

    /* float类型占用字节大小 */
    public static final long SIZE_OF_FLOAT = 4L;

    /* char类型占用字节大小, 编码使用 UTF-8 所以char类型占用2个字节 */
    public static final long SIZE_OF_CHAR = 2L;

    /* boolean类型占用字节大小 */
    public static final long SIZE_OF_BOOLEAN = 1L;

    /* 设置偏移位置 */
    public static final int SEEK_SET = 0x1000;

    /* 从当前位置往后偏移 */
    public static final int SEEK_CUR = 0x1001;

    /* 从最后位置往前偏移 */
    public static final int SEEK_END = 0x1010;

    /* 指针当前偏移量 */
    protected int position;

    /* 缓冲区有效内存大小 */
    protected int capacity;

    /**
     * @return 创建缓冲区，分配在JVM内存中。初始大小默认4kb。缓冲区最大值取决于
     *         JVM的堆大小。
     */
    public static ByteBuf alloc() {
        return alloc(IOUtils.DEFAULT_BUFFER_SIZE);
    }

    /**
     * @return 创建缓冲区，分配在JVM内存中。根据参数分配缓冲区初始大小。
     */
    public static ByteBuf alloc(int size) {
        return new HeapByteBuf(size);
    }

    /**
     * @return 使用对象包装后的ByteBuf
     */
    public static ByteBuf wrap(Object object) {
        byte[] bytes = ObjectSerializationUtils.serializationQuietly(object);
        return wrap(bytes, 0, bytes.length);
    }

    /**
     * 使用ByteBuf包装字节数组
     *
     * @param a   数组对象
     * @param off 开始位置
     * @param len 拷贝大小
     */
    public static ByteBuf wrap(byte[] a, int off, int len) {
        return new HeapByteBuf(a, off, len);
    }

    /**
     * @return 复制当前缓冲区
     */
    public abstract ByteBuf duplicate();

    /**
     * @return 返回缓冲区真实大小
     */
    public int size() {
        return capacity;
    }

    /**
     * 重置缓冲区偏移量
     */
    public void flip() {
        seek(0);
    }

    /**
     * 设置当权缓冲区偏移量，默认模式为SEEK_SET
     */
    public void seek(int position) {
        seek(position, SEEK_SET);
    }

    /**
     * 设置当权缓冲区偏移量
     */
    public void seek(int position, int mode) {
        switch (mode) {
            case SEEK_SET:
                this.position = position;
                break;
            case SEEK_CUR:
                this.position += position;
                break;
            case SEEK_END:
                this.position = capacity - position;
                break;
        }

        Assert.throwIfBool(!(this.position < 0 || this.position > capacity),
                "偏移量超出范围：{}， 数组大小：{}", this.position, capacity);
    }

    /**
     * 从缓冲区读取字节数组，读取数据大小为数组大小。
     */
    public void read(byte[] a) {
        read(a, 0, a.length);
    }

    /**
     * 读取缓冲区中的数据。
     */
    public abstract void read(byte[] a, int off, int len);

    /**
     * @return 根据当前读写指针位置读取一个字节
     */
    public abstract byte getByte();

    /**
     * @return 获取缓冲区字节
     */
    public abstract byte[] getBufferArray();

    /**
     * 写入int类型数据到缓冲区
     */
    public void write(int i) {
        write(IOUtils.toByteArray(i));
    }

    /**
     * 写入float类型数据到缓冲区
     */
    public void write(float f) {
        write(IOUtils.toByteArray(f));
    }

    /**
     * 写入char类型数据到缓冲区
     */
    public void write(char ch) {
        write(IOUtils.toByteArray(ch));
    }

    /**
     * 写入long类型数据到缓冲区
     */
    public void write(long l) {
        write(IOUtils.toByteArray(l));
    }

    /**
     * 写入单个字节到缓冲区中，使用这种方式对于较小的数据量来说性能较高。如果数据量较大建议还是
     * 是用{@link #write0(byte[], int, int)}方法写入较大的数据。
     *
     * @param a 写入的字节
     */
    public abstract void write(byte a);

    /**
     * 写入单个字节到缓冲区中，使用这种方式对于较小的数据量来说性能较高。如果数据量较大建议还是
     * 是用{@link #write0(byte[], int, int)}方法写入较大的数据。
     *
     * @param a 写入的字节数组
     */
    public void write(byte[] a) {
        write(a, 0, a.length);
    }

    /**
     * 根据参数长度写入字节数据到缓冲区
     */
    public void write(byte[] a, int off, int len) {
        Assert.throwIfNull(a);

        if (off < 0 || len < 0 || off + len > a.length)
            throw new IndexOutOfBoundsException();

        write0(a, off, len);
    }

    /**
     * write0函数是ByteBuf中最底层的写入函数，所有的write函数的变种都是基于write0写入数据。
     * 而write0是不会对数据进行校验和检查的。
     * <p>
     * 所有的检查都是在其他的write函数中。子类只需要关心写入就行了。
     *
     * @param a   写入数据的字节数组
     * @param off 写入数据的偏移量
     * @param len 写入数据的长度
     */
    abstract void write0(byte[] a, int off, int len);

}
