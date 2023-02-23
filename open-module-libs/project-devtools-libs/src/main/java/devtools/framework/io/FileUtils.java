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
import devtools.framework.generators.VGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @author Vincent Luo
 */
public final class FileUtils {

    /**
     * 2mb = 2000000 bytes
     */
    public static final long SIZE_OF_MB_2 = 2000000;

    /**
     * 4mb = 4000000 bytes
     */
    public static final long SIZE_OF_MB_4 = SIZE_OF_MB_2 * 2;

    /**
     * 8mb = 8000000 bytes
     */
    public static final long SIZE_OF_MB_8 = SIZE_OF_MB_4 * 2;

    /**
     * 16mb = 16000000 bytes
     */
    public static final long SIZE_OF_MB_16 = SIZE_OF_MB_8 * 2;

    /**
     * 32mb = 32000000 bytes
     */
    public static final long SIZE_OF_MB_32 = SIZE_OF_MB_16 * 2;

    /**
     * 从Resources目录读取文件
     */
    public static InputStreamTransferStation fromResource(String path) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        InputStream resourceStream = classLoader.getResourceAsStream(path);
        return resourceStream == null ? null : new InputStreamTransferStation(resourceStream);
    }

    /**
     * 从指定路径中读取文件
     */
    public static InputStreamTransferStation fromPath(String path) {
        return new InputStreamTransferStation(read(path));
    }

    /**
     * 自动创建路径中不存在的文件夹
     */
    public static boolean autoMkdirs(String path) {
        File file = new File(path);
        if (file.exists())
            return true;

        return file.mkdirs();
    }

    /**
     * 给一个路径强制删除文件，该函数不能删除文件夹
     */
    public static boolean forceDelete(String path) {
        return forceDelete(new File(path));
    }

    /**
     * 强制删除文件
     */
    public static boolean forceDelete(File file) {
        boolean retval;

        if (!(retval = file.delete())) {
            System.gc();
            retval = file.delete();
        }

        return retval;
    }

    /**
     * 给一个路径，然后强制删除目录或文件夹
     */
    public static boolean forceDeleteDirectory(String path) {
        return forceDeleteDirectory(new File(path));
    }

    /**
     * 强制删除文件或文件夹
     */
    public static boolean forceDeleteDirectory(File file) {
        if (!file.exists())
            return true;

        if (file.isFile())
            return forceDelete(file);

        for (File fchild : Objects.requireNonNull(file.listFiles())) {
            if (fchild.isDirectory())
                forceDeleteDirectory(fchild);

            forceDelete(fchild);
        }

        return file.delete();
    }

    /**
     * @return 获取当前工作目录
     */
    public static String currentWorkDirectory()
    {
        return System.getProperty("user.dir");
    }

    /**
     * 指定文件对象匹配后缀
     */
    public static boolean extensionEquals(File file, String extension) {
        return extensionEquals(file.getName(), extension);
    }

    /**
     * 指定文件名或路径匹配后缀
     */
    public static boolean extensionEquals(String name, String extension) {
        return name.endsWith(extension);
    }

    /**
     * 判断文件路径是否存在，如果不存在就创建
     */
    public static File fileCreate(String path) {
        File file = new File(path);

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("创建文件失败：" + path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file;
    }

    /**
     * 获取文件的输入流
     */
    public static InputStream openFileInputStreamQuietly(String path) {
        try {
            File file = new File(path);

            if (!file.exists())
                throw new RuntimeException("文件不存在：" + path);

            return Files.newInputStream(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件的输出流，默认写入模式为覆盖
     */
    public static OutputStream openFileOutputStreamQuietly(String path) {
        return openFileOutputStreamQuietly(path, false);
    }

    /**
     * 获取文件输出流，通过 append 参数决定写入模式是覆盖还是追加
     */
    public static OutputStream openFileOutputStreamQuietly(String path, boolean append) {
        return Assert.throwIfError(() -> new FileOutputStream(path, append));
    }

    /**
     * 将文件中的内容读取成字符串
     */
    public static String strread(String path) {
        return new String(read(path), StandardCharsets.UTF_8);
    }

    /**
     * 读取文件内容
     */
    public static byte[] read(String path) {
        try (InputStream stream = openFileInputStreamQuietly(path)) {
            return IOUtils.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return IOUtils.EMPTY_BYTE_ARRAY;
    }

    /**
     * 追加写入字符串并换行
     */
    public static void nwrite(String path, CharSequence charSequence) {
        nwrite(path, IOUtils.toByteArray(charSequence));
    }

    /**
     * 追加写入字节数组并换行
     */
    public static void nwrite(String path, byte[] a) {
        awrite(path, IOUtils.arrcat(IOUtils.LINE_SEPARATOR, a));
    }

    /**
     * 追加写入字符串到文件中
     */
    public static void awrite(String path, CharSequence charSequence) {
        awrite(path, IOUtils.toByteArray(charSequence));
    }

    /**
     * 追加写字节数组
     */
    public static void awrite(String path, byte[] a) {
        fileCreate(path); /* 确保写入数据时文件存在 */
        byte[] ra = read(path);
        write(path, IOUtils.arrcat(ra, a));
    }

    /**
     * 将缓冲区内容写入到文件，
     */
    public static void write(String path, ByteBuffer buf) {
        byte[] bytebuf = buf.getBytes();
        write(path, bytebuf, 0, bytebuf.length);
    }

    /**
     * 将缓冲区内容写入到文件，
     */
    public static void write(String path, byte[] a) {
        write(path, a, 0, a.length);
    }

    /**
     * 写入数据到文件中，根据off和len参数来决定写入的位置。
     * 如果文件不存在则会创建文件。
     */
    public static void write(String path, byte[] a, int off, int len) {
        try (OutputStream output = openFileOutputStreamQuietly(path)) {
            /* 写入数据 */
            IOUtils.write(a, off, len, output);
            /* 刷新缓冲区 */
            IOUtils.flushQuietly(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算文件的Sha256值
     *
     * @param path 文件路径
     * @return sha256
     */
    public static String vsha256(String path) {
        return VGenerator.vsha256(read(path));
    }

}
