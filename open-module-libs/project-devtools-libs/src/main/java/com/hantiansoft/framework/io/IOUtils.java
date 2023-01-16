package com.hantiansoft.framework.io;

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

import com.hantiansoft.framework.Asserts;
import com.hantiansoft.framework.StringUtils;

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
     * 文件结尾
     */
    public static final int EOF = -1;

    /**
     * 文件缓冲区大小, 默认4k缓冲区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = (1024 * 4);

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
     * 将输入流中的数据复制到输出流中，输出流中原有的数据将会被覆盖掉，
     * 替换为输入流中的数据。
     */
    public static int copy(final InputStream input, final OutputStream output) {
        Asserts.throwIfNull(input, "InputStream");
        Asserts.throwIfNull(output, "OutputStream");
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
            Asserts.throwIfError(flushable::flush);
    }

    /**
     * 关闭流但不抛出异常
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null)
            Asserts.throwIfError(closeable::close);
    }

}
