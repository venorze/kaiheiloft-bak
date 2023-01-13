//package com.hantiansoft.framework.io;
//
///* ************************************************************************
// *
// * Copyright (C) 2020 Vincent Luo All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not useEnv this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// * ************************************************************************/
//
///* Creates on 2022/8/14. */
//
//import com.hantiansoft.framework.unsafe.DirectUtils;
//import com.hantiansoft.framework.unsafe.MemoryPointer;
//
///**
// * 直接分配在机器内存中的缓冲区，可以直接访问。同时优点在于可以读取数据量较大的文件
// * 或消息。
// *
// * @author Vincent Luo
// */
//@Deprecated
//class DirectByteBuf extends ByteBuf {
//    /* 内存地址指针 */
//    private final MemoryPointer ptr;
//
//    /**
//     * 默认构造函数，初始化缓冲区大小
//     */
//    public DirectByteBuf(long size) {
//        // 在JVM堆外申请一个size大小的内存空间
//        this.ptr = DirectUtils.malloc(size);
//    }
//
//    /**
//     * ByteBuf构造函数,传入字节数组以及缓冲区大小，
//     * 初始化缓冲区。
//     *
//     * @see #duplicate()
//     */
//    public DirectByteBuf(byte[] a, int off, int len) {
//        this(len);
//        DirectUtils.memcpy(a, off, this.ptr, 0, len);
//    }
//
//    @Override
//    public ByteBuf duplicate() {
//        byte[] a = IOUtils.getByteArray(capacity);
//        int pos = position;
//        flip();
//        read(a);
//        seek(pos);
//
//        return new DirectByteBuf(a, 0, a.length);
//    }
//
//    @Override
//    public void read(byte[] a, int off, int len) {
//        for (int i = 0; i < len; i++)
//            a[off + i] = DirectUtils.getByte(ptr, position + i);
//
//        position += len;
//    }
//
//    @Override
//    public byte getByte() {
//        byte b = DirectUtils.getByte(ptr, position);
//        position++;
//        return b;
//    }
//
//    @Override
//    public byte[] getBufferArray() {
//        byte[] a = IOUtils.getByteArray(capacity);
//        int pos = position;
//
//        flip();
//        read(a);
//        seek(pos);
//
//        return a;
//    }
//
//    /* 确保缓冲区内部容量足够 */
//    private void ensureCapacity(long size) {
//        if ((capacity + size) > DirectUtils.sizeof(ptr))
//            DirectUtils.realloc(ptr, (DirectUtils.sizeof(ptr) + size) * 2);
//    }
//
//    @Override
//    public void write(byte a) {
//        ensureCapacity(1);
//        DirectUtils.putByte(ptr, position, a);
//        position += 1;
//        capacity += 1;
//    }
//
//    @Override
//    void write0(byte[] a, int off, int len) {
//        ensureCapacity(len);
//        DirectUtils.memcpy(a, off, ptr, position, len);
//        position += len;
//        capacity += len;
//    }
//
//    /**
//     * 当对象被GC回收时释放内存
//     */
//    @Override
//    protected void finalize() {
//        DirectUtils.free(ptr);
//    }
//}
