package com.hantiansoft.framework;

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

/* Creates on 2020/3/11. */

import com.hantiansoft.framework.collections.Lists;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Vincent Luo
 */
public
final class StringUtils {

    /**
     * 字符串是否为空
     *
     * @param input 目标字符串
     * @return true为空，false反之。
     */
    public static boolean isEmpty(final String input) {
        return input == null || input.length() == 0;
    }

    public static boolean isNotEmpty(final String input) {
        return !isEmpty(input);
    }

    /**
     * 两个字符串做比较
     */
    public static boolean equals(String x, String y) {
        return Objects.equals(x, y);
    }

    /**
     * 判断字符串是否不为空
     *
     * @return true表示当前不为空，false反之
     */
    public static boolean is_not_empty(final String input) {
        return !isEmpty(input);
    }

    /**
     * 判断是不是null字符，或者是空
     */
    public static boolean isNull(final CharSequence input) {
        return input == null || "null".contentEquals(input);
    }

    /**
     * 截取index之前的字符串
     */
    public static String strcut(String input, int index) {
        if (input == null || isNull(input)) {
            return input;
        }
        return input.substring(0, index);
    }

    /**
     * 截取index之后的字符串
     */
    public static String xstrcut(String input, int index) {
        if (input == null || isNull(input)) {
            return input;
        }
        return input.substring(index);
    }

    /**
     * 判断字符串是不是数字
     */
    public static boolean isNumber(String input) {
        Pattern pattern = Pattern.compile("\\d*");
        return pattern.matcher(input).matches();
    }

    /**
     * String格式化,大约比String.format()快17倍
     * 格式化的字符为两个花括号"{}"
     */
    public static String vfmt(String input, Object... args) {
        if (isEmpty(input)) {
            return input;
        }
        int argsLen = 0;
        int offset = 0;
        int subscript = 0;
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = '#';
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (previous == '{' && current == '}') {
                if (argsLen >= args.length) {
                    return builder.toString().concat(new String(chars));
                }
                char[] temp = new char[(i - offset) - 1];
                System.arraycopy(chars, offset, temp, 0, (offset = i - 1));
                Object argv = args[subscript];
                builder.append(temp).append(argv == null ? "" : argv);
                temp = new char[chars.length - offset - 2];
                System.arraycopy(chars, offset + 2, temp, 0, temp.length);
                // reset
                chars = temp;
                subscript++;
                i = 0;
                offset = 0;
                argsLen++;
            } else {
                previous = current;
            }
        }
        return builder.append(chars).toString();
    }

    /**
     * 将字符串合并成一行
     */
    public static String toLine(String text) {
        StringBuilder content = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            String str = tokenizer.nextToken();
            content.append(str);
        }
        return content.toString();
    }

    /**
     * 把某个字符替换成空格
     */
    public static String replaceNull(String input, String... patterns) {
        for (String pattern : patterns) {
            input = input.replaceAll(pattern, "");
        }
        return input.trim().replaceAll(" ", "").trim();
    }

    /**
     * 驼峰转下划线
     */
    public static String hump_to_underline(String string) {
        StringBuilder builder = new StringBuilder(string);
        int temp = 0; // 定位
        for (int i = 0, len = string.length(); i < len; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                builder.insert(i + temp, "_");
                temp++;
            }
        }
        return builder.toString().toLowerCase();
    }

    /**
     * 下划线转驼峰
     */
    public static String underline_to_hump(String string) {
        return character_to_hump(string, "_");
    }

    /**
     * 根据某个字符分割然后转驼峰命名
     */
    public static String character_to_hump(String string, String ch) {
        StringBuilder builder = new StringBuilder();
        String[] strs = string.split(ch);
        builder.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            StringBuilder v = new StringBuilder(strs[i]);
            v.replace(0, 1, String.valueOf(v.charAt(0)).toUpperCase());
            builder.append(v);
        }

        return builder.toString();
    }

    public static String asString(Object input) {
        return asString(input, null);
    }

    public static String asString(Object input, String def) {
        try {
            if (input != null) {
                if (input instanceof String) {
                    return (String) input;
                } else {
                    return input.toString();
                }
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    public static Integer asInteger(Object input) {
        return asInteger(input, 0);
    }

    public static Integer asInteger(Object input, Integer def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return Integer.valueOf(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }


    public static Long asLong(Object input) {
        return asLong(input, 0L);
    }

    public static Long asLong(Object input, Long def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return Long.valueOf(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    public static Float asFloat(Object input) {
        return asFloat(input, 0F);
    }

    public static Float asFloat(Object input, Float def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return Float.valueOf(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    public static Double asDouble(Object input) {
        return asDouble(input, 0D);
    }

    public static Double asDouble(Object input, Double def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return Double.valueOf(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    public static Boolean asBoolean(Object input) {
        return asBoolean(input, false);
    }

    public static Boolean asBoolean(Object input, Boolean def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return Boolean.valueOf(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    public static BigDecimal asBigDecimal(Object input) {
        return asBigDecimal(input, null);
    }

    public static BigDecimal asBigDecimal(Object input, BigDecimal def) {
        try {
            if (input != null) {
                String strValue = asString(input);
                return new BigDecimal(strValue);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    /**
     * 获取异常的堆栈打印
     */
    public static String getStackTrace(Throwable e) {
        StringWriter strWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(strWriter);
        try {
            e.printStackTrace(printWriter);
            strWriter.close();
            printWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return strWriter.toString();
    }

    /**
     * 从字符串开始位置根据指定数字删除
     *
     * @param src 源字符串
     * @param n   要删除几个字符
     */
    public static String remove_of_start(String src, int n) {
        return src.substring(n);
    }

    /**
     * 从字符串开始位置根据指定字符删除
     *
     * @param src     源字符串
     * @param vdelete 要删除的字符串，如果匹配到没有这个字符串则不会删除。
     */
    public static String remove_of_start(String src, String vdelete) {
        return src.startsWith(vdelete) ? remove_of_start(src, vdelete.length()) : src;
    }

    /**
     * 从字符串末尾开始根据指定数字删除
     *
     * @param src 源字符串
     * @param n   要删除几个字符
     */
    public static String remove_of_end(String src, int n) {
        return src.substring(0, src.length() - n);
    }

    /**
     * 从字符串末尾开始根据指定字符删除
     *
     * @param src     源字符串
     * @param vdelete 要删除的字符串，如果匹配到没有这个字符串则不会删除。
     */
    public static String remove_of_end(String src, String vdelete) {
        return src.endsWith(vdelete) ? remove_of_end(src, vdelete.length()) : src;
    }

    /**
     * 合并字符串集合
     *
     * @param list      集合对象
     * @param separator 分隔符
     */
    public static String list_merge(List<String> list, String separator) {
        if (Lists.isEmpty(list))
            return "";

        // 合并字符
        StringBuilder builder = new StringBuilder();
        for (String str : list)
            builder.append(str).append(separator);

        return remove_of_end(builder.toString(), separator);
    }

}

