//package com.hantiansoft.framework.unsafe;
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
///* Creates on 2022/8/15. */
//
//import com.hantiansoft.framework.exception.IllegalAccessRuntimeException;
//
///**
// * 方便管理指针的对象
// *
// * @author Vincent Luo
// */
//@Deprecated
//public class MemoryPointer {
//    /* 内存地址指针 */
//    long mptr;
//
//    /* 分配的内存大小 */
//    long size;
//
//    /* 指针是否已经被释放掉 */
//    boolean free;
//
//    public MemoryPointer(long p, long sz) {
//        this.mptr = p;
//        this.size = sz;
//    }
//
//    /* 检查指针是否已被释放掉 */
//    void checkAvailable() {
//        if (free)
//            throw new IllegalAccessRuntimeException("指针已被释放");
//    }
//
//    /**
//     * 重新分配内存大小
//     */
//    void realloc(long l, long size) {
//        checkAvailable();
//        this.mptr = l;
//        this.size = size;
//    }
//}
