package devtools.jna;

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

import com.sun.jna.*;
import devtools.framework.io.IOUtils;

/**
 * c语言stdio标准库
 *
 * @author Amaoai
 */
@SuppressWarnings("UnusedReturnValue")
public interface StdioCLib extends Library {

    /**
     * JNA实例对象
     */
    StdioCLib CLibInstance = (StdioCLib)
            Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                    StdioCLib.class);

    /**
     * 文件指针的当前位置
     */
    int SEEK_CUR = 1;

    /**
     * 文件的末尾
     */
    int SEEK_END = 2;

    /**
     * 文件的开头
     */
    int SEEK_SET = 0;

    /**
     * C 库函数 FILE *fopen(const char *filename, const char *mode)
     * 使用给定的模式 mode 打开 filename 所指向的文件。
     *
     * 文件打开模式有以下模式：
     *
     *      - "r"    打开一个用于读取的文件。该文件必须存在。
     *      - "w"    创建一个用于写入的空文件。如果文件名称与已存在的文件相同，则会删除已有文件的内容，文件被视为一个新的空文件。
     *      - "a"    追加到一个文件。写操作向文件末尾追加数据。如果文件不存在，则创建文件。
     *      - "r+"   打开一个用于更新的文件，可读取也可写入。该文件必须存在。
     *      - "w+"   创建一个用于读写的空文件。
     *      - "a+"   打开一个用于读取和追加的文件。
     *
     * @param filename 字符串，表示要打开的文件名称。
     * @param mode     字符串，表示文件的访问模式，可以是以下表格中的值：
     *
     * @return 该函数返回一个 FILE 指针。否则返回 NULL，且设置全局变量 errno 来标识错误。
     */
    long fopen(String filename, String mode);

    /**
     * C 库函数 size_t fread(void *ptr, size_t size, size_t nmemb, FILE *fd)
     * 从给定流 fd 读取数据到 ptr 所指向的数组中。
     *
     * @param ptr   这是指向带有最小尺寸 size*nmemb 字节的内存块的指针。
     * @param size  这是要读取的每个元素的大小，以字节为单位。
     * @param nmemb 这是元素的个数，每个元素的大小为 size 字节。
     * @param fd    这是指向 FILE 对象的指针，该 FILE 对象指定了一个输入流。
     *
     * @return 成功读取的元素总数会以 size_t 对象返回，size_t 对象是一个整型数据类型。
     *         如果总数与 nmemb 参数不同，则可能发生了一个错误或者到达了文件末尾。
     */
    int fread(byte[] ptr, int size, int nmemb, long fd);

    /**
     * C 库函数 size_t fwrite(const void *ptr, size_t size, size_t nmemb, FILE *fd)
     * 把 ptr 所指向的数组中的数据写入到给定流 fd 中。
     *
     * @param buf    这是指向要被写入的元素数组的指针。
     * @param size   这是要被写入的每个元素的大小，以字节为单位。
     * @param nmemb  这是元素的个数，每个元素的大小为 size 字节。
     * @param fd     这是指向 FILE 对象的指针，该 FILE 对象指定了一个输出流。
     *
     * @return 如果成功，该函数返回一个 size_t 对象，表示元素的总数，该对象是一个整型数据类型。
     *         如果该数字与 nmemb 参数不同，则会显示一个错误。
     */
    int fwrite(byte[] buf, int size, int nmemb, long fd);

    /**
     * C 库函数 int fputs(const char *str, FILE *fd) 把字符串写入到指定的流 fd 中，
     * 但不包括空字符。
     *
     * @param str 这是一个字符串对象，包含了要写入的以空字符终止的字符序列。
     * @param fd  这是指向 FILE 对象的指针，该 FILE 对象标识了要被写入字符串的流。
     *
     * @return 该函数返回一个非负值，如果发生错误则返回 EOF。
     */
    int fputs(String str, long fd);

    /**
     * C 库函数 int fflush(FILE *fd) 刷新流 fd 的输出缓冲区。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象指定了一个缓冲流。
     * @return 如果成功，该函数返回零值。如果发生错误，则返回 EOF，且设置错误标识符（即 feof）。
     */
    int fflush(long fd);

    /**
     * C 库函数 int fseek(FILE *fd, long int offset, int whence)
     * 设置流 fd 的文件位置为给定的偏移 offset，参数 offset 意味着从给定的 whence 位置查找的字节数。
     *
     * @param fd     这是指向 FILE 对象的指针，该 FILE 对象标识了流。
     * @param offset 这是相对 whence 的偏移量，以字节为单位。
     * @param origin 这是表示开始添加偏移 offset 的位置。它一般指定为下列常量之一：
     *
     *                  - {@link #SEEK_SET} 文件的开头
     *                  - {@link #SEEK_CUR} 文件指针的当前位置
     *                  - {@link #SEEK_END} 文件的末尾
     *
     * @return 如果成功，则该函数返回零，否则返回非零值。
     */
    int fseek(long fd, long offset, int origin);

    /**
     * C 库函数 int fclose(FILE *fd) 关闭流 fd。刷新所有的缓冲区。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象指定了要被关闭的流。
     * @return 如果流成功关闭，则该方法返回零。如果失败，则返回 EOF。
     */
    int fclose(long fd);

    static void main(String[] args) {
        // 打开
        long fd = CLibInstance.fopen("D:\\Temp\\value.txt", "r+");
        // 写入
        byte[] writeBytes = "Hello Fucker!!!\n".getBytes();
        CLibInstance.fwrite(writeBytes, writeBytes.length, 1, fd);
        // 写入字符流
        CLibInstance.fputs("size_t fread(void *ptr, size_t size, size_t nmemb, FILE *stream)", fd);
        CLibInstance.fputs("int fclose(FILE *fd)", fd);
        // 设置指针位置
        CLibInstance.fseek(fd, 0, SEEK_SET);
        // 读取
        byte[] a = new byte[50];
        CLibInstance.fread(a, a.length, 1, fd);
        System.out.println(IOUtils.toCharArray(a));
        // 关闭
        CLibInstance.fclose(fd);
    }

}

