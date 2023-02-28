/* SuppressWarnings */
@file:Suppress("NOTHING_TO_INLINE")

package stdlibkt

import com.amaoai.framework.io.IOUtils
import java.nio.charset.Charset

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
 * 将 [String] 解析为 [Int] 数字并返回结果。
 */
inline fun toInt(str: String): Int = str.toInt()

/**
 * 将 [ [Byte] ] 类型转换为 [ [Int] ] 类型
 */
inline fun toInt(bit: Byte): Int = bit.toInt()

/**
 * 将 [ [Short] ] 类型转换为 [ [Int] ] 类型
 */
inline fun toInt(short: Short): Int = short.toInt()

/**
 * 将 [Long] 类型转换为 [ [Int] ] 类型型
 */
inline fun toInt(l: Long): Int = l.toInt()

/**
 * 将 [Float] 类型转换为 [ [Int] ] 类型型
 */
inline fun toInt(f: Float): Int = f.toInt()

/**
 * 将 [Double] 类型转换为 [ [Int] ] 类型型
 */
inline fun toInt(double: Double): Int = double.toInt()

/**
 * 将 [ByteArray] 类型转换为 [ [Int] ] 类型型
 */
inline fun toInt(arr: ByteArray): Int = IOUtils.toInt(arr)

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

/**
 * 将 [ByteArray] 类型转换为 [Long] 类型
 */
inline fun toLong(arr: ByteArray): Long = IOUtils.toLong(arr)

/**
 * 将 [String] 类型转换为 [ByteArray] 类型
 */
inline fun toByteArray(str: String): ByteArray = IOUtils.toByteArray(str)

/**
 * 将 [Char] 类型转换为 [ByteArray] 类型
 */
inline fun toByteArray(chr: Char, chrset: Charset): ByteArray = IOUtils.toByteArray(chr, chrset)

/**
 * 将 [Int] 类型转换为 [ByteArray] 类型
 */
inline fun toByteArray(i: Int): ByteArray = IOUtils.toByteArray(i)

/**
 * 将 [Long] 类型转换为 [ByteArray] 类型
 */
inline fun toByteArray(l: Long): ByteArray = IOUtils.toByteArray(l)

/**
 * 将 [Float] 类型转换为 [ByteArray] 类型
 */
inline fun toByteArray(f: Float): ByteArray = IOUtils.toByteArray(f)

/**
 * 将任意类型转换为 [String] 类型
 */
inline fun toString(a: Any): String = "$a"