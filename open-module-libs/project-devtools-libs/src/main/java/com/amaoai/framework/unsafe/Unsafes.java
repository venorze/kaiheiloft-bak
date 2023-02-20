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
///* Creates on 2022/8/16. */
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
///**
// * @author Vincent Luo
// */
//@Deprecated
//public final class Unsafes {
//    /**
//     * @return 获取Unsafe类，可以直接操作内存。
//     */
//    public static Unsafe getUnsafe() {
//        Unsafe unsafe;
//
//        try {
//            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
//            unsafeField.setAccessible(true);
//            unsafe = (Unsafe) unsafeField.get(null);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        return unsafe;
//    }
//}
