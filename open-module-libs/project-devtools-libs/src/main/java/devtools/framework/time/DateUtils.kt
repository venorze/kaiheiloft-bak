package devtools.framework.time

import org.joda.time.DateTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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

/* Creates on 2022/3/30. */

/**
 * 日期操作工具类
 *
 * @author Amaoai
 */
object DateUtils {

    /**
     * 获取当前时间戳
     */
    @JvmStatic fun currentTimestamp(): Long = System.currentTimeMillis()

    /**
     * 当前时间在是否在某个日期范围之内
     */
    @JvmStatic fun withinRangeCurrent(min: Date, max: Date): Boolean = withinRange(min, Date(), max)

    /**
     * 指定时间在是否在某个日期时间范围之内
     */
    @JvmStatic fun withinRange(min: Date, date: Date, max: Date): Boolean = lteq(min, date) && gteq(max, date)

    /**
     * 当前时间是否大于传入的时间
     */
    @JvmStatic fun gteq(time: Date): Boolean = gteq(Date(), time)

    /**
     * x是否大于y
     */
    @JvmStatic fun gteq(x: Date, y: Date): Boolean = x >= y


    /**
     * 当前时间是否小于传入的时间
     */
    @JvmStatic fun lteq(time: Date): Boolean = lteq(Date(), time)

    /**
     * x是否小于y
     */
    @JvmStatic fun lteq(x: Date, y: Date): Boolean = x <= y

    /**
     * 计算两个日期相差多少天
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差天数
     */
    @JvmStatic fun betweenDays(x: Date, y: Date): Int = abs(((y.time - x.time) / (1000 * 60 * 60 * 24)).toInt())

    /**
     * 计算两个日期相差多少小时
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差小时
     */
    @JvmStatic fun betweenHours(x: Date, y: Date): Int = abs(((y.time - x.time) / (1000 * 60 * 60)).toInt())

    /**
     * 计算两个日期相差多少分钟
     *
     * @param x 日期1
     * @param y 日期2
     * @return 相差分钟
     */
    @JvmStatic fun betweenMinutes(x: Date, y: Date): Int = abs(((y.time - x.time) / (1000 * 60)).toInt())

    /**
     * 日期格式化成指定格式, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(): String = vfmt("yyyy-MM-dd HH:mm:ss")

    /**
     * 日期格式化成指定格式, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(pattern: String): String = vfmt(pattern, Date())

    /**
     * 日期格式化成指定格式, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(date: Date): String = vfmt("yyyy-MM-dd HH:mm:ss", date)

    /**
     * 日期格式化成指定格式
     *
     * @param pattern 日期格式化表达式，比如：yyyy-MM-dd
     * @param date    需要格式化的日期
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(pattern: String, date: Date): String = SimpleDateFormat(pattern).format(date)

    /**
     * 时间戳格式化成指定格式, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(timestamp: Long): String = vfmt("yyyy-MM-dd HH:mm:ss", timestamp)

    /**
     * 时间戳格式化成指定格式, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @param pattern   日期格式化表达式，比如：yyyy-MM-dd
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    @JvmStatic fun vfmt(pattern: String, timestamp: Long): String = vfmt(pattern, Date(timestamp))

    /**
     * string转日期, 默认：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期字符串
     * @return 格式化后的Date对象
     */
    @JvmStatic fun parse(date: String): Date = parse("yyyy-MM-dd HH:mm:ss", date)

    /**
     * string转日期
     *
     * @param pattern 日期格式化表达式，比如：yyyy-MM-dd
     * @param date    日期字符串
     * @return 格式化后的Date对象
     */
    @JvmStatic fun parse(pattern: String, date: String): Date {
        return try {
            SimpleDateFormat(pattern).parse(date)
        } catch (e: ParseException) {
            throw RuntimeException(e)
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
    @JvmStatic fun plus(date: Date, number: Int, timeUnits: TimeUnits): Date {
        val dateTime = DateTime(date)
        return when (timeUnits) {
            TimeUnits.SECONDS -> dateTime.plusSeconds(number).toDate()
            TimeUnits.MINUTES -> dateTime.plusMinutes(number).toDate()
            TimeUnits.HOURS -> dateTime.plusHours(number).toDate()
            TimeUnits.DAYS -> dateTime.plusDays(number).toDate()
            TimeUnits.MONTHS -> dateTime.plusMonths(number).toDate()
            TimeUnits.YEARS -> dateTime.plusYears(number).toDate()
        }
    }

    /**
     * 日期减少计算
     *
     * @param date      计算的日期
     * @param number    减少的天数或者是其他
     * @param timeUnits 计算类型
     * @return 计算后的日期
     */
    @JvmStatic fun minus(date: Date, number: Int, timeUnits: TimeUnits): Date? {
        val dateTime = DateTime(date)
        return when (timeUnits) {
            TimeUnits.SECONDS -> dateTime.minusSeconds(number).toDate()
            TimeUnits.MINUTES -> dateTime.minusMinutes(number).toDate()
            TimeUnits.HOURS -> dateTime.minusHours(number).toDate()
            TimeUnits.DAYS -> dateTime.minusDays(number).toDate()
            TimeUnits.MONTHS -> dateTime.minusMonths(number).toDate()
            TimeUnits.YEARS -> dateTime.minusYears(number).toDate()
        }
    }

}