package com.amaoai

import devtools.framework.io.IOUtils
import stdlibkt.*

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
object KotlinMain {
        @JvmStatic
        fun main(args: Array<String>) {
            // 打开
            val fd = fopen("D:\\Temp\\value.txt", "w+")
            // 写入
            val writeBytes = "Hello Fucker!!!\n".toByteArray()
            fwrite(writeBytes, writeBytes.size, 1, fd)
            // 写入字符流
            fputs("size_t fread(void *ptr, size_t size, size_t nmemb, FILE *stream)", fd)
            fputs("int fclose(FILE *fd)\n", fd)
            // putc
            fputc('M'.code, fd)
            fputc('Y'.code, fd)
            fputc('\n'.code, fd)
            // 读取
            fseek(fd, 0, SEEK_SET)
            val a = ByteArray(50)
            fread(a, a.size, 1, fd)
            println(IOUtils.toCharArray(a))
            // fgets
            fseek(fd, 0, SEEK_SET)
            val getBytes = CharArray(10)
            println(fgets(getBytes, getBytes.size, fd))

            // 设置指针位置
            fseek(fd, 0, SEEK_SET)
            println("================================================")
            var c: Char
            while (true) {
                c = fgetc(fd)
                if (feof(fd)) break
                print(c)
            }
            // 关闭
            fclose(fd)
        }

}