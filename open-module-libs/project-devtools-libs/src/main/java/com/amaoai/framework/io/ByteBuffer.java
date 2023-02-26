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
public abstract class ByteBuffer {

    /* 设置偏移位置 */
    public static final int SEEK_SET =
            IOUtils.SEEK_SET;

    /* 从当前位置往后偏移 */
    public static final int SEEK_CUR =
            IOUtils.SEEK_CUR;

    /* 从最后位置往前偏移 */
    public static final int SEEK_END =
            IOUtils.SEEK_END;

    /* 指针当前偏移量 */
    protected int position;

    /* 缓冲区有效内存大小 */
    protected int capacity;

    /**
     * @return 创建缓冲区，分配在JVM内存中。初始大小默认4kb。缓冲区最大值取决于
     *         JVM的堆大小。
     */
    public static ByteBuffer alloc() {
        return alloc(IOUtils.DEFAULT_BYTE_BUFFER_SIZE);
    }

    /**
     * @return 创建缓冲区，分配在JVM内存中。根据参数分配缓冲区初始大小。
     */
    public static ByteBuffer alloc(int size) {
        return new HeapByteBuffer(size);
    }

    /**
     * @return 使用对象包装后的ByteBuf
     */
    public static ByteBuffer wrap(Object object) {
        byte[] bytes = ObjectSerializationUtils.serializationQuietly(object);
        return wrap(bytes, 0, bytes.length);
    }

    /**
     * 使用ByteBuf包装字节数组
     */
    public static ByteBuffer wrap(byte[] a) {
        return wrap(a, 0, a.length);
    }

    /**
     * 使用ByteBuf包装字节数组
     *
     * @param a   数组对象
     * @param off 开始位置
     * @param len 拷贝大小
     */
    public static ByteBuffer wrap(byte[] a, int off, int len) {
        return new HeapByteBuffer(a, off, len);
    }

    /**
     * @return 复制当前缓冲区
     */
    public abstract ByteBuffer duplicate();

    /**
     * @return 返回缓冲区真实大小
     */
    public int size() {
        return capacity;
    }

    /**
     * @return 返回缓冲区未读大小
     */
    public int remsize() {
        return capacity - position;
    }

    /**
     * 重置缓冲区偏移量
     */
    public void flip() {
        seek(0);
    }

    /**
     * @return 判断是不是读取到缓冲区末尾了
     */
    public boolean eof() {
        return position == capacity;
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
            case SEEK_SET -> this.position = position;
            case SEEK_CUR -> this.position += position;
            case SEEK_END -> this.position = capacity - position;
        }

        Assert.throwIfBool(!(this.position < 0 || this.position > capacity),
                "偏移量超出范围：{}， 数组大小：{}", this.position, capacity);
    }

    /**
     * 清空缓冲区
     */
    public abstract byte[] clear();

    /**
     * 指定偏移量和长度清空缓冲区，调用 clear 后会重置
     * 读写指针。
     */
    public abstract void clear(int off, int len);

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
     * @return 当前函数返回一个 int 类型。从当前读写指针位置，往后读 4 个字节。
     *         转换为 int 类型数字返回出去。读写指针向后移动 4 位。
     */
    public int readInt() {
        var arr = IOUtils.getByteArray(IOUtils.SIZE_OF_INT);
        read(arr, 0, arr.length);
        return IOUtils.toInt(arr);
    }

    /**
     * @return 当前函数返回一个 long 类型.从当前读写指针位置，往后读 8 个字节。
     *         转换为 long 类型数字返回出去。读写指针向后移动 8 位。
     */
    public long readLong() {
        var arr = IOUtils.getByteArray(IOUtils.SIZE_OF_LONG);
        read(arr, 0, arr.length);
        return IOUtils.toLong(arr);
    }

    /**
     * @return 返回可读字节数
     */
    public int readable() {
        return capacity - position;
    }

    /**
     * @return 根据当前读写指针位置读取一个字节
     */
    public abstract byte getByte();

    /**
     * @return 获取缓冲区字节
     */
    public abstract byte[] getBytes();

    /**
     * @return 获取缓冲区剩余为读写的字节
     */
    public byte[] getRemBytes() {
        var arr = IOUtils.getByteArray(remsize());
        read(arr, 0, arr.length);
        return arr;
    }

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
