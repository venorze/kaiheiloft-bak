package com.amaoai.framework.time;

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

/* Creates on 2022/3/30. */

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期操作工具类
 *
 * @author Vincent Luo
 */
public class DateUtils {

    /**
     * 当前时间戳
     */
    public static long currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 当前时间在是否在某个日期范围之内
     */
    public static boolean withinRangeCurrent(Date min, Date max) {
        return withinRange(min, new Date(), max);
    }

    /**
     * 指定时间在是否在某个日期时间范围之内
     */
    public static boolean withinRange(Date min, Date date, Date max) {
        return lteq(min, date) && gteq(max, date);
    }

    /**
     * 当前时间是否大于传入的时间
     */
    public static boolean gteq(Date time) {
        return gteq(new Date(), time);
    }

    /**
     * x是否大于y
     */
    public static boolean gteq(Date x, Date y) {
        return x.compareTo(y) >= 0;
    }


    /**
     * 当前时间是否小于传入的时间
     */
    public static boolean lteq(Date time) {
        return lteq(new Date(), time);
    }

    /**
     * x是否小于y
     */
    public static boolean lteq(Date x, Date y) {
        return x.compareTo(y) <= 0;
    }

    /**
     * 计算两个日期相差多少天
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差天数
     */
    public static int betweenDays(Date x, Date y) {
        return Math.abs((int) ((y.getTime() - x.getTime()) / (1000 * 60 * 60 * 24)));
    }

    /**
     * 计算两个日期相差多少小时
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差小时
     */
    public static int betweenHours(Date x, Date y) {
        return Math.abs((int) ((y.getTime() - x.getTime()) / (1000 * 60 * 60)));
    }

    /**
     * 计算两个日期相差多少分钟
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差分钟
     */
    public static int betweenMinutes(Date x, Date y) {
        return Math.abs((int) ((y.getTime() - x.getTime()) / (1000 * 60)));
    }

    /**
     * 日期格式化成执行类型, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    public static String vfmt() {
        return vfmt("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化成执行类型, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    public static String vfmt(String pattern) {
        return vfmt(pattern, new Date());
    }

    /**
     * 日期格式化成执行类型, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串
     */
    public static String vfmt(Date date) {
        return vfmt("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * 日期格式化成执行类型
     *
     * @param pattern 日期格式化表达式，比如：yyyy-MM-dd
     * @param date    需要格式化的日期
     * @return 格式化后的字符串
     */
    public static String vfmt(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 时间戳格式化成执行类型, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    public static String vfmt(long timestamp) {
        return vfmt("yyyy-MM-dd HH:mm:ss", timestamp);
    }

    /**
     * 时间戳格式化成执行类型, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @param pattern   日期格式化表达式，比如：yyyy-MM-dd
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    public static String vfmt(String pattern, long timestamp) {
        return vfmt(pattern, new Date(timestamp));
    }

    /**
     * string转日期, 格式化类型默认：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期字符串
     * @return 格式化后的Date对象
     */
    public static Date parse(String date) {
        return parse("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * string转日期
     *
     * @param pattern 日期格式化表达式，比如：yyyy-MM-dd
     * @param date    日期字符串
     * @return 格式化后的Date对象
     */
    public static Date parse(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期增加计算
     *
     * @param date      计算的日期
     * @param number    增加的天数或者是其他
     * @param timeUnits 计算类型
     * @return 计算后的日期
     */
    public static Date plus(Date date, int number, TimeUnits timeUnits) {
        DateTime dateTime = new DateTime(date);
        switch (timeUnits) {
            case SECONDS:
                return dateTime.plusSeconds(number).toDate();
            case MINUTES:
                return dateTime.plusMinutes(number).toDate();
            case HOURS:
                return dateTime.plusHours(number).toDate();
            case DAYS:
                return dateTime.plusDays(number).toDate();
            case MONTHS:
                return dateTime.plusMonths(number).toDate();
            case YEARS:
                return dateTime.plusYears(number).toDate();
        }
        ;

        return null;
    }

    /**
     * 日期减少计算
     *
     * @param date      计算的日期
     * @param number    减少的天数或者是其他
     * @param timeUnits 计算类型
     * @return 计算后的日期
     */
    public static Date minus(Date date, int number, TimeUnits timeUnits) {
        DateTime dateTime = new DateTime(date);
        switch (timeUnits) {
            case SECONDS:
                return dateTime.minusSeconds(number).toDate();
            case MINUTES:
                return dateTime.minusMinutes(number).toDate();
            case HOURS:
                return dateTime.minusHours(number).toDate();
            case DAYS:
                return dateTime.minusDays(number).toDate();
            case MONTHS:
                return dateTime.minusMonths(number).toDate();
            case YEARS:
                return dateTime.minusYears(number).toDate();
        }
        ;

        return null;
    }

}
