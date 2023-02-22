package devtools.framework.io;

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

/* Creates on 2022/8/9. */

import devtools.framework.Assert;
import devtools.framework.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * IO工具类
 *
 * @author Vincent Luo
 */
public final class IOUtils {
    /**
     * 文件指针的当前位置
     */
    public static final int SEEK_CUR =
            StdioCLib.SEEK_CUR;

    /**
     * 文件的末尾
     */
    public static final int SEEK_END =
            StdioCLib.SEEK_END;

    /**
     * 文件的开头
     */
    public static final int SEEK_SET =
            StdioCLib.SEEK_SET;

    /**
     * 表示已经读取到文件末尾
     */
    public static final int EOF =
            StdioCLib.EOF;

    /**
     * 文件缓冲区大小, 默认4k缓冲区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = (1024 * 4);

    /**
     * 标准输入流
     */
    public static InputStream stdin = System.in;

    /**
     * 标准输出流
     */
    public static OutputStream stdout = System.out;

    /**
     * 标准错误流
     */
    public static OutputStream stderr = System.err;

    /**
     * 空的字节数组
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * 换行符
     */
    public static final byte[] LINE_SEPARATOR =
            System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8);

    /**
     * 将字符串转换成InputStream
     */
    public static InputStream toInputStream(String a) {
        return toInputStream(a.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将字节数组转换成InputStream
     */
    public static InputStream toInputStream(byte[] a) {
        return new ByteArrayInputStream(a);
    }

    /**
     * 字符序列转字节数组，默认UTF-8编码
     */
    public static byte[] toByteArray(CharSequence charSequence) {
        return toByteArray(charSequence.toString(), StandardCharsets.UTF_8);
    }

    /**
     * 字符串转字节数组，根据编码字符串进行转换
     */
    public static byte[] toByteArray(CharSequence charSequence, String encoding) {
        return toByteArray(charSequence.toString(), Charset.forName(encoding));
    }

    /**
     * 字符转字节数组，根据编码对象进行转换
     */
    public static byte[] toByteArray(CharSequence charSequence, Charset encoding) {
        return charSequence.toString().getBytes(encoding);
    }

    /**
     * char转字节数组, 默认UTF-8编码
     */
    public static byte[] toByteArray(char chr) {
        return toByteArray(chr, StandardCharsets.UTF_8);
    }

    /**
     * char转字节数组, 根据编码字符串进行转换
     */
    public static byte[] toByteArray(char chr, String encoding) {
        return toByteArray(chr, Charset.forName(encoding));
    }

    /**
     * char转字节数组, 根据编码对象进行转换
     */
    public static byte[] toByteArray(char chr, Charset encoding) {
        return encoding.encode(String.valueOf(chr)).array();
    }

    /**
     * int转字节数组
     */
    public static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    /**
     * float转字节数组
     *
     * @see <a href="https://www.programmerall.com/article/1579468716/">from</a>
     */
    public static byte[] toByteArray(float f) {
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++)
            b[i] = (byte) (fbit >> (24 - i * 8));

        int len = b.length;
        //  Create an array with the same element type as the source array
        byte[] dest = new byte[len];
        //  To prevent modifying the source array, make a copy of the source array
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        //  Swap the i-th in the order with the i-th from the bottom
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }

        return dest;
    }

    /**
     * long类型转字节数组
     */
    public static byte[] toByteArray(long l) {
        byte[] a = getByteArray(8);
        for (int i = 0; i < 8; i++) {
            int off = 64 - (i + 1) * 8;
            a[i] = (byte) ((l >> off) & 0xFF);
        }

        return a;
    }

    /**
     * 字节数组转int，默认偏移量为0，也就是将数组的前四个字节转换
     * 成int类型。
     */
    public static int toInt(byte[] a) {
        return toInt(a, 0);
    }

    /**
     * 字节数组转int，根据索引的位置开始查找
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public static int toInt(byte[] a, int index) {
        int l = 0;
        l += (a[index + 0] & 0xFF) << (3 * 8);
        l += (a[index + 1] & 0xFF) << (2 * 8);
        l += (a[index + 2] & 0xFF) << (1 * 8);
        l += (a[index + 3] & 0xFF) << (0 * 8);
        return l;
    }

    /**
     * 字节数组转float，默认偏移量为0，也就是将数组的前四个字节转换
     * 成float类型。
     */
    public static float toFloat(byte[] a) {
        return toFloat(a, 0);
    }

    /**
     * 字节数组转float，根据索引的位置开始查找。
     *
     * @see <a href="https://www.programmerall.com/article/1579468716/">from</a>
     */
    public static float toFloat(byte[] a, int index) {
        int l;
        l = a[index];
        l &= 0xff;
        l |= ((long) a[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) a[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) a[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * 字节数组转long类型，默认偏移量为0，往后移8位
     */
    public static long toLong(byte[] a) {
        return toLong(a, 0);
    }

    /**
     * byte数组转long, 根据偏移量往后移8位转long
     */
    public static long toLong(byte[] a, int index) {
        long l = 0;
        for (int i = 0; i < 8; i++) {
            l <<= 8;
            l |= (a[index + i] & 0xFF);
        }

        return l;
    }

    /**
     * 字节数组转char数组，默认编码为UTF-8
     */
    public static char[] toCharArray(byte[] a) {
        return toCharArray(a, StandardCharsets.UTF_8);
    }

    /**
     * 字节数组转char数组，传入编码字符串
     */
    public static char[] toCharArray(byte[] a, String encoding) {
        return toCharArray(a, Charset.forName(encoding));
    }

    /**
     * 字节数组转char数组，传入编码对象
     */
    public static char[] toCharArray(byte[] a, Charset encoding) {
        ByteBuffer buf = ByteBuffer.allocate(a.length);
        buf.put(a);
        buf.flip();
        return encoding.decode(buf).array();
    }

    /**
     * @return 创建一个默认大小4k的缓冲区
     */
    public static byte[] getByteArray() {
        return getByteArray(DEFAULT_BUFFER_SIZE);
    }

    /**
     * @return 创建一个给定大小的字节缓冲区
     */
    public static byte[] getByteArray(int size) {
        return new byte[size];
    }

    /**
     * BYTE数组拼接
     */
    public static byte[] arrcat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 拷贝一份字节数组
     */
    public static byte[] arrcpy(byte[] a, int off, int len) {
        if (off == 0 && len == a.length)
            return a;

        len = len - off;
        byte[] b = new byte[len];
        System.arraycopy(a, off, b, 0, len);
        return b;
    }

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
    public static FileDescriptor fopen(String filename, String mode) {
        long fd = StdioCLib.CLibInstance.fopen(filename, mode);
        if (fd == StdioCLib.NULL)
            throw new RuntimeException(filename);

        return new FileDescriptor(fd);
    }

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
    public static int fread(byte[] ptr, int size, int nmemb, FileDescriptor fd) {
        return StdioCLib.CLibInstance.fread(ptr, size, nmemb, fd.fptr);
    }

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
    public static int fwrite(byte[] buf, int size, int nmemb, FileDescriptor fd) {
        return StdioCLib.CLibInstance.fwrite(buf, size, nmemb, fd.fptr);
    }

    /**
     * C 库函数 int fputs(const char *str, FILE *fd) 把字符串写入到指定的流 fd 中，
     * 但不包括空字符。
     *
     * @param str 这是一个字符串对象，包含了要写入的以空字符终止的字符序列。
     * @param fd  这是指向 FILE 对象的指针，该 FILE 对象标识了要被写入字符串的流。
     *
     * @return 该函数返回一个非负值，如果发生错误则返回 EOF。
     */
    public static int fputs(String str, FileDescriptor fd) {
        return StdioCLib.CLibInstance.fputs(str, fd.fptr);
    }

    /**
     * C 库函数 char *fgets(char *str, int n, FILE *stream) 从指定的流 stream 读取一行，
     * 并把它存储在 str 所指向的字符串内。
     *
     * 当读取 (n-1) 个字符时，或者读取到换行符时，或者到达文件末尾时，它会停止，具体视情况而定。
     *
     * @param a 这是指向一个字符数组的指针，该数组存储了要读取的字符串。
     * @param n 这是要读取的最大字符数（包括最后的空字符）。通常是使用以 str 传递的数组长度。
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象标识了要从中读取字符的流。
     *
     * @return 如果成功，该函数返回相同的 str 参数。如果到达文件末尾或者没有读取到任何字符，str 的内容保持不变，
     *         并返回一个空指针。如果发生错误，返回一个空指针。
     */
    public static String fgets(char[] a, int n, FileDescriptor fd) {
        return StdioCLib.CLibInstance.fgets(a, n, fd.fptr);
    }

    /**
     * C 库函数 int fputc(int char, FILE *stream) 把参数 char 指定的字符（一个无符号字符）写入到指定的流 stream 中，
     * 并把位置标识符往前移动。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象标识了要被写入字符的流。
     * @return 如果没有发生错误，则返回被写入的字符。如果发生错误，则返回 EOF，并设置错误标识符。
     */
    public static int fputc(int chr, FileDescriptor fd) {
        return StdioCLib.CLibInstance.fputc(chr, fd.fptr);
    }

    /**
     * C 库函数 int fgetc(FILE *stream) 从指定的流 stream 获取下一个字符（一个无符号字符），
     * 并把位置标识符往前移动。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象标识了要在上面执行操作的流。
     * @return 该函数以无符号 char 强制转换为 int 的形式返回读取的字符，如果到达文件末尾或发生读错误，则返回 EOF。
     */
    public static char fgetc(FileDescriptor fd) {
        return StdioCLib.CLibInstance.fgetc(fd.fptr);
    }

    /**
     * C 库函数 int ferror(FILE *stream) 测试给定流 stream 的错误标识符。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象标识了流。
     * @return 如果设置了与流关联的错误标识符，该函数返回一个非零值，否则返回一个零值。
     */
    public static boolean ferror(FileDescriptor fd) {
        return StdioCLib.CLibInstance.ferror(fd.fptr);
    }

    /**
     * C 库函数 int feof(FILE *stream) 测试给定流 stream 的文件结束标识符。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象标识了要在上面执行操作的流。
     * @return 当设置了与流关联的文件结束标识符时，该函数返回一个非零值，否则返回零。
     */
    public static boolean feof(FileDescriptor fd) {
        return StdioCLib.CLibInstance.feof(fd.fptr);
    }

    /**
     * C 库函数 int fflush(FILE *fd) 刷新流 fd 的输出缓冲区。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象指定了一个缓冲流。
     * @return 如果成功，该函数返回零值。如果发生错误，则返回 EOF，且设置错误标识符（即 feof）。
     */
    public static int fflush(FileDescriptor fd) {
        return StdioCLib.CLibInstance.fflush(fd.fptr);
    }

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
    public static int fseek(FileDescriptor fd, long offset, int origin) {
        return StdioCLib.CLibInstance.fseek(fd.fptr, offset, origin);
    }

    /**
     * C 库函数 int fclose(FILE *fd) 关闭流 fd。刷新所有的缓冲区。
     *
     * @param fd 这是指向 FILE 对象的指针，该 FILE 对象指定了要被关闭的流。
     * @return 如果流成功关闭，则该方法返回零。如果失败，则返回 EOF。
     */
    public static int fclose(FileDescriptor fd) {
        return StdioCLib.CLibInstance.fclose(fd.fptr);
    }

    /**
     * 将输入流中的数据复制到输出流中，输出流中原有的数据将会被覆盖掉，
     * 替换为输入流中的数据。
     */
    public static int copy(final InputStream input, final OutputStream output) {
        Assert.throwIfNull(input, "InputStream");
        Assert.throwIfNull(output, "OutputStream");
        byte[] b = read(input);
        write(b, output);
        return b.length;
    }

    /**
     * 从输入流中读取文本
     */
    public static String strread(InputStream stream, String strCharset) {
        Charset encoding = StringUtils.isEmpty(strCharset) ?
                StandardCharsets.UTF_8 : Charset.forName(strCharset);

        return new String(read(stream), encoding);
    }

    /**
     * 读取字节数据, 数据读取完后会关闭流。
     */
    public static byte[] read(InputStream stream) {
        ByteBuf buf = ByteBuf.alloc();

        try {
            int len = 0;
            byte[] xbuf = IOUtils.getByteArray();

            // 写入数据到缓冲区
            while ((len = stream.read(xbuf)) != EOF)
                buf.write(xbuf, 0, len);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(stream);
        }

        return buf.getBufferArray();
    }

    /**
     * 格式化字符串并写入到输出流。
     *
     * @param output 输出流，通常使用stdout或stderr,再或者使用
     *               FileOutputStream写入到文件。
     *
     * @param fmt    格式化字符串
     * @param args   格式化参数
     *
     * @see #stdout
     * @see #stderr
     * @see FileOutputStream
     */
    public static void vfprintf(OutputStream output, String fmt,
                                Object... args) {
        if (fmt != null)
            write(toByteArray(StringUtils.vfmt(fmt, args)), output);
    }

    /**
     * 指定字节数组，把整个字节数组中的数据写入到输出流中。
     *
     * @param a      要写入的数据
     * @param output 输出流
     */
    public static void write(byte[] a, OutputStream output) {
        write(a, 0, a.length, output);
    }

    /**
     * 指定一个字节数组，并根据偏移量和长度写入数据到输出流中。
     *
     * @param a      要写入的数据
     * @param off    偏移量
     * @param len    写入长度
     * @param output 输出流
     */
    public static void write(byte[] a, int off, int len,
                             OutputStream output) {
        try {
            if (a == null
                    || off < 0
                    || len < 0
                    || off + len > a.length)
                throw new IndexOutOfBoundsException();

            output.write(a, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 刷新缓存
     */
    public static void flushQuietly(Flushable flushable) {
        if (flushable != null)
            Assert.throwIfError(flushable::flush);
    }

    /**
     * 关闭流但不抛出异常
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null)
            Assert.throwIfError(closeable::close);
    }

}
