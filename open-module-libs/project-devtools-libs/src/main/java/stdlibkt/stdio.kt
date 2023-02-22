/* SuppressWarnings */
@file:Suppress("HasPlatformType", "NOTHING_TO_INLINE")

package stdlibkt

import devtools.framework.io.IOUtils

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
 *
 * 文件描述符
 */
typealias FileDescriptor = devtools.framework.io.FileDescriptor

/**
 * @see [IOUtils.SEEK_CUR]
 */
const val SEEK_CUR = IOUtils.SEEK_CUR

/**
 * @see [IOUtils.SEEK_END]
 */
const val SEEK_END = IOUtils.SEEK_END

/**
 * @see [IOUtils.SEEK_SET]
 */
const val SEEK_SET = IOUtils.SEEK_SET

/**
 * @see [IOUtils.EOF]
 */
const val EOF = IOUtils.EOF

/**
 * @see [IOUtils.fopen]
 */
inline fun fopen(filename: String, mode: String): FileDescriptor = IOUtils.fopen(filename, mode)

/**
 * @see [IOUtils.fread]
 */
inline fun fread(ptr: ByteArray, size: Int, nmemb: Int, fd: FileDescriptor): Int = IOUtils.fread(ptr, size, nmemb, fd)

/**
 * @see [IOUtils.fwrite]
 */
inline fun fwrite(buf: ByteArray, size: Int, nmemb: Int, fd: FileDescriptor): Int = IOUtils.fwrite(buf, size, nmemb, fd)

/**
 * @see [IOUtils.fputs]
 */
inline fun fputs(str: String, fd: FileDescriptor): Int = IOUtils.fputs(str, fd)

/**
 * @see [IOUtils.fgets]
 */
inline fun fgets(a: CharArray, n: Int, fd: FileDescriptor): String = IOUtils.fgets(a, n, fd)

/**
 * @see [IOUtils.fputc]
 */
inline fun fputc(chr: Int, fd: FileDescriptor): Int = IOUtils.fputc(chr, fd)

/**
 * @see [IOUtils.fgetc]
 */
inline fun fgetc(fd: FileDescriptor): Char = IOUtils.fgetc(fd)

/**
 * @see [IOUtils.ferror]
 */
inline fun ferror(fd: FileDescriptor): Boolean = IOUtils.ferror(fd)

/**
 * @see [IOUtils.feof]
 */
inline fun feof(fd: FileDescriptor): Boolean = IOUtils.feof(fd)

/**
 * @see [IOUtils.fflush]
 */
inline fun fflush(fd: FileDescriptor): Int = IOUtils.fflush(fd)

/**
 * @see [IOUtils.fseek]
 */
inline fun fseek(fd: FileDescriptor, offset: Long, origin: Int): Int = IOUtils.fseek(fd, offset, origin)

/**
 * @see [IOUtils.fclose]
 */
inline fun fclose(fd: FileDescriptor): Int = IOUtils.fclose(fd)