package org.example.phonemanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // 日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // 时间格式
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 将Date对象格式化为字符串
     * @param date Date对象
     * @param format 格式化模式
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 将Date对象格式化为默认的日期时间格式
     * @param date Date对象
     * @return 格式化后的字符串
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DATE_TIME_FORMAT);
    }

    /**
     * 将Date对象格式化为默认的日期格式
     * @param date Date对象
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 将Date对象格式化为默认的时间格式
     * @param date Date对象
     * @return 格式化后的字符串
     */
    public static String formatTime(Date date) {
        return formatDate(date, TIME_FORMAT);
    }

    /**
     * 将字符串解析为Date对象
     * @param dateString 日期字符串
     * @param format 格式化模式
     * @return Date对象
     * @throws ParseException 解析异常
     */
    public static Date parseDate(String dateString, String format) throws ParseException {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.parse(dateString);
    }

    /**
     * 将字符串解析为默认的日期时间格式
     * @param dateString 日期字符串
     * @return Date对象
     * @throws ParseException 解析异常
     */
    public static Date parseDateTime(String dateString) throws ParseException {
        return parseDate(dateString, DATE_TIME_FORMAT);
    }

    /**
     * 将时间戳转换为Date对象
     * @param timestamp 时间戳（毫秒）
     * @return Date对象
     */
    public static Date timestampToDate(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return new Date(timestamp);
    }

    /**
     * 将Date对象转换为时间戳
     * @param date Date对象
     * @return 时间戳（毫秒）
     */
    public static long dateToTimestamp(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 将时间戳格式化为字符串
     * @param timestamp 时间戳（毫秒）
     * @param format 格式化模式
     * @return 格式化后的字符串
     */
    public static String timestampToString(long timestamp, String format) {
        Date date = timestampToDate(timestamp);
        return formatDate(date, format);
    }

    /**
     * 将时间戳格式化为默认的日期时间格式
     * @param timestamp 时间戳（毫秒）
     * @return 格式化后的字符串
     */
    public static String timestampToDateTimeString(long timestamp) {
        return timestampToString(timestamp, DATE_TIME_FORMAT);
    }
}