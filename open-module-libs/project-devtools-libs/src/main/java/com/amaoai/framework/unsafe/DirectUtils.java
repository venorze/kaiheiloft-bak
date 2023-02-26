//package com.amaoai.framework.unsafe;
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
//import sun.misc.Unsafe;
//
///**
// * 直接内存访问工具类，可以像C一样直接内存访问。
// *
// * @author Vincent Luo
// */
//@Deprecated
//public final class DirectUtils {
//
//    /* Unsafe类 */
//    private static final Unsafe theUnsafe = Unsafes.getUnsafe();
//
//    /**
//     * 在机器内存中直接分配内存，而不是在JVM堆上分配内存。这个就相当于在c或c艹中的malloc函数。
//     * 使用这个函数必须调用{@link #free}函数来释放内存。
//     *
//     * @param size 分配内存大小
//     * @return 内存地址
//     */
//    public static MemoryPointer malloc(long size) {
//        return new MemoryPointer(theUnsafe.allocateMemory(size), size);
//    }
//
//    /**
//     * 重新在直接内存中分配内存大小，扩容地址的原有内存大小。这里的ptr必须保证是有效的。
//     * 否则可能会出现意想不到的问题。
//     *
//     * @param ptr  使用 malloc() 函数分配的内存地址指针。
//     * @param size 新的内存大小
//     */
//    public static void realloc(MemoryPointer ptr, long size) {
//        ptr.realloc(
//                theUnsafe.reallocateMemory(ptr.mptr, size),
//                size);
//    }
//
//    /**
//     * 内存拷贝，从指针中拷贝数据到目标字节数组中。在使用这些函数前，必须确保你的内存地址是可用的。
//     * 而且字节数组满足你所写的偏移量否则可能会遇到意想不到的问题。
//     *
//     * @param srcObject  源内存地址指针
//     * @param srcOffset  内存地址偏移量
//     * @param destObject 目标字节数组
//     * @param destOffset 目标字节数组偏移量
//     * @param len        拷贝长度
//     */
//    public static void memcpy(MemoryPointer srcObject, long srcOffset, byte[] destObject, long destOffset, long len) {
//        theUnsafe.copyMemory(null, srcObject.mptr + srcOffset,
//                destObject, Unsafe.ARRAY_BYTE_BASE_OFFSET + destOffset,
//                len);
//    }
//
//    /**
//     * 内存拷贝，从字节数组中拷贝数据到目标内存中。在使用这些函数前，必须确保你的内存地址是可用的。
//     * 而且字节数组满足你所写的偏移量否则可能会遇到意想不到的问题。
//     *
//     * @param srcObject  源字节数组对象
//     * @param srcOffset  字节数组偏移量
//     * @param destObject 目标内存地址指针
//     * @param destOffset 内存地址偏移量
//     * @param len        拷贝长度
//     */
//    public static void memcpy(byte[] srcObject, long srcOffset,
//                              MemoryPointer destObject, long destOffset,
//                              long len) {
//        theUnsafe.copyMemory(srcObject, Unsafe.ARRAY_BYTE_BASE_OFFSET + srcOffset,
//                null, destObject.mptr + destOffset,
//                len);
//    }
//
//    /**
//     * 添加一个Byte数据到内存中
//     *
//     * @param ptr   内存指针
//     * @param off   偏移量
//     * @param value byte value
//     */
//    public static void putByte(MemoryPointer ptr, long off, byte value) {
//        theUnsafe.putByte(ptr.mptr + off, value);
//    }
//
//    /**
//     * 从内存地址中读取一个字节数据
//     *
//     * @param ptr 内存地址指针
//     * @param off 偏移量
//     * @return 字节数据
//     */
//    public static byte getByte(MemoryPointer ptr, long off) {
//        return theUnsafe.getByte(ptr.mptr + off);
//    }
//
//    /**
//     * 初始化内存数据。为什么需要这个函数？因为使用 malloc 分配出来的数据是上一个使用这个内存地址的
//     * 程序留下来的数据，而不是0。所以如果需要初始化就可以使用memset函数。
//     *
//     * @param ptr   内存地址指针
//     * @param off   内存地址偏移量
//     * @param value 初始化值
//     * @param size  初始化大小
//     */
//    public static void memset(MemoryPointer ptr, long off, byte value, long size) {
//        theUnsafe.setMemory(ptr.mptr + off, size, value);
//    }
//
//    /**
//     * 传递内存地址释放内存
//     * <p>
//     * param ptr
//     * 使用 malloc() 函数分配的内存地址指针。
//     */
//    public static void free(MemoryPointer ptr) {
//        theUnsafe.freeMemory(ptr.mptr);
//    }
//
//    /**
//     * 获取指针内存大小
//     *
//     * @param ptr 指针对象
//     * @return 分配的内存大小
//     */
//    public static long sizeof(MemoryPointer ptr) {
//        ptr.checkAvailable();
//        return ptr.size;
//    }
//}
