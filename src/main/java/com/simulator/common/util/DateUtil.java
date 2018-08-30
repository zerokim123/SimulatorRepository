/*
 * @(#)DateUtil.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */
package com.simulator.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 日付ツール.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 */
public class DateUtil {
    /**
     * フォーマット文字列
     */
    private static final String FORMATSTR = "HH:mm";

    /**
     * SimpleDateFormat.
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat(FORMATSTR);

    /**
     * システム時間の開始時間を取得する
     *
     * @return システム時間の開始時間
     */
    public static Date getTodayStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 日付フォーマット
     *
     * @param date 日付
     * @param formater フォーマット
     * @return フォーマットした日付
     */
    public static String getDateStrCompact(Date date, String formater) {
        if (date == null){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formater);
        String str = format.format(date);
        return str;
    }

    /**
     * 日付フォーマット
     *
     * @param date 日付
     * @param formatStr フォーマット
     * @return フォーマットした日付
     */
    public static String dateToString(Date date, String formatStr) {
        if (formatStr == null || "".equals(formatStr.trim())){
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        return getDateStrCompact(date, formatStr);
    }

    /**
     * 文字列は日付に変換する
     *
     * @param dateStr 文字列
     * @param formater フォーマット
     * @return 日付
     */
    public static Date stringToDate(String dateStr, String formater){
        Date date = null;
        if (formater == null || "".equals(formater.trim())){
            formater = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(formater);

        if (!StringUtils.isNotBlank(dateStr)){
            return date;
        }
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日数を増加する
     *
     * @param date 日付
     * @param days 日数
     * @return 日付
     */
    public static Date addDate(Date date, int days) {
        return addDate(date, days, Calendar.DATE);
    }

    /**
     * 日付の年、月、日を増加する
     *
     * @param date 日付
     * @param counts 増加数
     * @param dateField 年、月、日
     * @return 日付
     */
    public static Date addDate(Date date, int counts, int dateField) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateField, counts);
        return calendar.getTime();
    }

    /**
     * 日付のlongを取得する
     *
     * @param date 日付
     * @return long日付
     */
    public static long getTimeInMillis(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * システム時間の文字列を取得する
     *
     * @return システム時間の文字列
     */
    public static String getDateNowStr() {
        Date date = new Date();
        String formater = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formater);
        String str = format.format(date);
        return str;
    }

    /**
     * 該当月の第1日を取得する
     *
     * @param date 日付
     * @return 該当月の第1日
     */
    @SuppressWarnings("deprecation")
    public static Date getMonthFirstDay(Date date){
        return new Date(date.getYear(), date.getMonth(), 1);
    }

    /**
     * 該当月の最後日を取得する
     *
     * @param date 日付
     * @return 該当月の最後日
     */
    public static Date getMonthLastDay(Date date){
        Date nextMonth = addDate(date, 1, Calendar.MONTH);

        Date nextMonthFirstDay = getMonthFirstDay(nextMonth);

        return addDate(nextMonthFirstDay, -1, Calendar.DATE);
    }

    /**
     * 月の計算
     *
     * @param count 増加する数
     * @param f フォーマット
     * @return 月
     */
    public static String addMonth(int count, String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, count);
        String newMounth = format.format(c.getTime());
        return newMounth;
    }

    /**
     * 日期の計算
     *
     * @param beginDate 開始日
     * @param endDate 終了日
     * @return 日数
     */
    public static long getDaySub(String beginDate, String endDate) {

        long day = 0;
        Date begin;
        Date end;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            begin = sdf.parse(beginDate);
            end = sdf.parse(endDate);
            day = (begin.getTime() - end.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 営業時間の判定
     *
     * @param businessOpenTime 営業時間開始
     * @param businessCloseTime 営業時間終了
     * @return 営業時間フラグ
     * @throws ParseException ParseException
     */
    public static boolean isInBussinessTime(
            String businessOpenTime,
            String businessCloseTime) throws ParseException {

        boolean flag = false;
        flag = getLong(businessOpenTime) <= getCurrentTime()
                && getCurrentTime() <= getLong(businessCloseTime);

        return flag;
    }

    /**
     * 時間を取得する
     *
     * @param timeStr 時間文字列
     * @return 時間
     * @throws ParseException ParseException
     */
    private static long getLong(String timeStr) throws ParseException {
        return sdf.parse(timeStr).getTime();
    }

    /**
     * 現在の時間を取得する
     *
     * @return 時間
     * @throws ParseException ParseException
     */
    private static long getCurrentTime() throws ParseException {
        return getLong(sdf.format(new Date()));
    }

    /**
     * タイムスタンプの判定
     *
     * @param transTime 時間
     * @return 判定結果
     * @throws ParseException ParseException
     */
    public static boolean CheckTransTime(String transTime)
            throws ParseException {
        boolean flag = false;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.getDefault());
        Date date = sdf.parse(transTime);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        String result = sdf.format(date);
        Date date2 = sdf.parse(result);
        calendar.setTime(date2);
        calendar.add(Calendar.MINUTE, 5);
        long currentTime = sdf.parse(sdf.format(new Date())).getTime();
        long transDateTimeFrom = sdf.parse(
                sdf.format(calendar.getTime())).getTime();
        calendar.add(Calendar.MINUTE, -10);
        long transDateTimeTo = sdf.parse(
                sdf.format(calendar.getTime())).getTime();

        //システム時間<=入力値.タイムスタンプ+5min
        // && システム時間>=入力値.タイムスタンプ-5min
        if (currentTime <= transDateTimeFrom
                && transDateTimeTo <= currentTime)
        {
            flag = true;
        }

        return flag;
    }

    /**
     * 時間判定
     * @param date1 日付1(YYYYMMDD)
     * @param date2 日付2(YYYYMMDD)
     * @return 判定結果
     */
    public static boolean compareDate(String date1, String date2) {
        boolean flag = false;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt2.getTime() < dt1.getTime()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (ParseException e) {
            return false;
        }
        return flag;
    }
}
