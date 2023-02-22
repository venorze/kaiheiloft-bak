package devtools.framework.generators;

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

/* Creates on 2022/8/5. */

import devtools.framework.HackFunction01;
import devtools.framework.HackFunction11;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;

/**
 * Id、随机数等内容的生成器
 *
 * @author Vincent Luo
 */
public class VGenerator {

    ////////////////////////////////////////////////////////////
    /// SHA256生成
    ////////////////////////////////////////////////////////////

    /**
     * 根据Byte数组生成对应的Sha256值
     *
     * @param a      byte数组
     * @return sha256
     */
    public static String vsha256(byte[] a) {
        return vsha256(a, 0, a.length);
    }

    /**
     * 根据Byte数组生成对应的Sha256值
     *
     * @param a      byte数组
     * @param offset 数组偏移量
     * @param len    截取长度
     * @return sha256
     */
    public static String vsha256(byte[] a, int offset, int len) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(a, offset, len);
            byte[] hash = digest.digest();

            // 生成 Sha256 字符串
            StringBuilder sb = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                String hex = Integer.toHexString(b);
                if (hex.length() == 1) {
                    sb.append("0");
                } else if (hex.length() == 8) {
                    hex = hex.substring(6);
                }
                sb.append(hex);
            }

            return sb.toString().toLowerCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    ////////////////////////////////////////////////////////////
    /// 唯一Id生成
    ////////////////////////////////////////////////////////////

    /**
     * @return 生成不带符号的UUID
     */
    public static String uuid() {
        return java.util.UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toUpperCase();
    }

    /**
     * @param number 随机从uuid中取number位字符
     * @return 生成不带符号的UUID
     */
    public static String uuid(int number) {
        String uuid = uuid();
        int len = uuid.length();
        int i = random_of_number(len);
        return (i + number) >= len ? uuid.substring(len - number, len) : uuid.substring(i, i + number);
    }

    ////////////////////////////////////////////////////////////
    /// 随机数生成
    ////////////////////////////////////////////////////////////

    /**
     * @return 生成 0 - x 的随机数
     */
    public static int random_of_number(int x) {
        return (int) (Math.random() * x);
    }

    /**
     * @return 生成 x - y 范围内的随机数
     */
    public static int random_of_range(int x, int y) {
        return new Random().nextInt(
                Math.max(x, y) - Math.min(x, y)) + Math.min(x, y);
    }

    ////////////////////////////////////////////////////////////
    /// 验证码生成
    ////////////////////////////////////////////////////////////

    /* 数字和字母的字符串数组 */
    private static final char[] STATIC_NUMBERS_AND_LETTERS_CHAR_ARRAY = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * @return 六位数带数字和字母的验证码
     */
    public static String random_complex_captcha() {
        return random_complex_captcha(4);
    }

    /**
     * 生成数字带字母的验证码
     *
     * @param length 验证码长度
     */
    public static String random_complex_captcha(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(STATIC_NUMBERS_AND_LETTERS_CHAR_ARRAY[
                    VGenerator.random_of_number(STATIC_NUMBERS_AND_LETTERS_CHAR_ARRAY.length)]);
        return sb.toString();
    }

    /**
     * @return 六位数纯数字的验证码
     */
    public static String random_simple_captcha() {
        return random_simple_captcha(4);
    }

    /**
     * 生成纯数字的验证码
     *
     * @param length 验证码长度
     */
    public static String random_simple_captcha(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(VGenerator.random_of_number(10));
        return sb.toString();
    }

    @Data
    @AllArgsConstructor
    public static class Captcha {
        private String code;
        private String image;
    }

    /**
     * 生成验证码图片 base64 编码；
     *
     * @param f_gencode lambda 函数，可选有:
     *                  <p>
     *                  - Generators::random_complex_captcha(int);
     *                  - Generators::random_simple_captcha(int);
     * @param n         验证码长度
     *                  <p>
     *                  生成代码示例：<code>
     *                  Generators::generate_captcha_image(Generators::random_complex_captcha, 4);
     *                  </code>
     * @return 图像 base64 字符串
     */
    public static Captcha random_captcha_image(HackFunction11<Integer, String> f_gencode, int n) {
        String code = f_gencode.call(n);
        return new Captcha(
                code,
                new CaptchaImageGenerator(code).generateImageBase64Code()
        );
    }

    /**
     * 生成验证码图片 base64 编码；
     *
     * @param f_gencode lambda 函数，可选有:
     *                  <p>
     *                  - Generators::random_complex_captcha();
     *                  - Generators::random_simple_captcha();
     *                  <p>
     *                  生成代码示例：<code>
     *                  Generators::generate_captcha_image(Generators::random_complex_captcha);
     *                  </code>
     * @return 图像 base64 字符串
     */
    public static Captcha random_captcha_image(HackFunction01<String> f_gencode) {
        String code = f_gencode.call();
        return new Captcha(
                code,
                new CaptchaImageGenerator(code).generateImageBase64Code()
        );
    }

}
