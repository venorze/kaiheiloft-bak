/* SuppressWarnings */
@file:Suppress("NOTHING_TO_INLINE")

package stdlibkt

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

/* Creates on 2023/2/22. */

/**
 * @author Amaoai
 */

/**
 * 将 [String] 类型转换为 [Long] 类型
 */
inline fun toLong(str: String): Long = str.toLong()

/**
 * 将 [Byte] 类型转换为 [Long] 类型
 */
inline fun toLong(bit: Byte): Long = bit.toLong()

/**
 * 将 [Short] 类型转换为 [Long] 类型
 */
inline fun toLong(short: Short): Long = short.toLong()

/**
 * 将 [Int] 类型转换为 [Long] 类型
 */
inline fun toLong(i: Int): Long = i.toLong()

/**
 * 将 [Float] 类型转换为 [Long] 类型
 */
inline fun toLong(f: Float): Long = f.toLong()

/**
 * 将 [Double] 类型转换为 [Long] 类型
 */
inline fun toLong(double: Double): Long = double.toLong()