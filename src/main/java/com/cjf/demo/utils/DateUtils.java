package com.cjf.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtils {
    /**
     * 时间格式字符串：yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 时间格式字符串：yyyy.MM.dd
     */
    public static final String FORMAT_DATE_POINT = "yyyy.MM.dd";

    /**
     * 时间格式字符串：yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式字符串：yyyy-MM
     */
    public static final String FORMAT_DATE_YEAR_MONTH = "yyyy-MM";

    /**
     * 时间格式字符串：yyyyMM
     */
    public static final String FORMAT_DATE_YEAR_MONTH_NOSEGMENT = "yyyyMM";

    /**
     * 时间格式字符串：yyyy.MM
     */
    public static final String FORMAT_DATE_YEAR_MONTH_POINT = "yyyy.MM";

    /**
     * 根据日期格式字符串解析日期字符串
     *
     * @param source 日期字符串
     * @param format 日期格式字符串
     * @return 解析后日期
     * @throws ParseException
     */
    public static Date parseDate(String source, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(source);
    }

    /**
     * Date 转化为Calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Calendar转化为Date
     */
    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @return String
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @return String
     */
    public static String formatCalendar(Calendar calendar, String format) {
        Date date = calendarToDate(calendar);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @return String
     */
    public static String formatDate(Calendar cal, String format) {
        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 得到day的起始时间点， 即date 时分秒毫秒置零
     *
     * @param sourceDate
     * @return
     */
    public static Date getDayStart(Date sourceDate) {
        if (sourceDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sourceDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return calendarToDate(cal);
    }

    /**
     * 计算日期时间差 date2 - date1，如2022-04-02 13:00:00 与 2022-04-03 14:01:01 相差1天1小时1分1秒
     *
     * @param date1
     * @param date2
     * @return 数组：天，小时，分钟，秒
     */
    public static int[] getDateScope(Date date1, Date date2) {

        long nd = 1000 * 24 * 60 * 60;// ⼀天的毫秒数
        long nh = 1000 * 60 * 60;// ⼀⼩时的毫秒数
        long nm = 1000 * 60;// ⼀分钟的毫秒数
        long ns = 1000;// ⼀秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 相差总的毫秒数
        diff = date2.getTime() - date1.getTime();
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少⼩时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒

        int[] scopes = new int[]{(int)day, (int)(hour - day * 24), (int)(min - day * 24 * 60), (int)sec};

        return scopes;
    }

    /**
     * @param originFormat
     * @param targetFormat
     * @param origin
     * @return
     * @Description: 日期格式转换
     */
    public static String formatDate(String originFormat, String targetFormat, String origin) {
        try {
            Date date = new SimpleDateFormat(originFormat).parse(origin);
            return new SimpleDateFormat(targetFormat).format(date);
        } catch (ParseException e) {
            log.error("日期格式转换 出现异常，异常信息 ： {}", e.getMessage());
            return null;
        }
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     * @param date2
     * @return date1>date2: 1 | date1<date2: -1 | date1==date2: 0
     */
    public static int compareDate(Date date1, Date date2) throws Exception {
        if (null == date1 || null == date2) {
            throw new Exception("date1 and date2 can not be null");
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 > year2) {
            return 1;
        } else if (year1 < year2) {
            return -1;
        } else {
            if (month1 > month2) {
                return 1;
            } else if (month1 < month2) {
                return -1;
            } else {
                if (day1 > day2) {
                    return 1;
                } else if (day1 < day2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * 获得两个日期相差天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDateDifference(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return 0;
        }

        try {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            int year1 = 0;
            int month1 = 0;
            int day1 = 0;

            c2.setTime(endDate);
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH);
            int day2 = c2.get(Calendar.DAY_OF_MONTH);

            int pointer = -1;
            int compareResult = compareDate(beginDate, endDate);
            switch (compareResult) {
                case 0:
                    return 0;
                case -1:
                    do {
                        pointer++;
                        c1.setTime(beginDate);
                        c1.add(Calendar.DAY_OF_MONTH, pointer);
                        year1 = c1.get(Calendar.YEAR);
                        month1 = c1.get(Calendar.MONTH);
                        day1 = c1.get(Calendar.DAY_OF_MONTH);
                    } while (year1 != year2 || month1 != month2 || day1 != day2);
                    return pointer;
                case 1:
                    return 0;
                default:
                    return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 日期加法
     */
    public static Date addDate(Date date, int i) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendarToDate(calendar);
    }

    /**
     * 日期加上固定小时
     * @param date
     * @param i
     * @return
     */
    public static Date addHourDate(Date date, int i) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.HOUR, i);
        return calendarToDate(calendar);
    }

    /**
     * 获取指定月份所有日期
     * @param sourceMonth 月份，格式：yyyy-MM
     * @return
     */
    public static List<Date> getDatesOfMonth(String sourceMonth) throws ParseException {
        List<Date> resultList = new ArrayList(31);
        Date date = parseDate(sourceMonth + "-01", FORMAT_DATE);
        Calendar calendar = dateToCalendar(date);
        int month = calendar.get(Calendar.MONTH);
        do{
            resultList.add(calendarToDate(calendar));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }while(calendar.get(Calendar.MONTH) == month);

        return resultList;
    }

    /**
     * 获取上个月所有date
     * @return
     */
    public static List<Date> getAllDatesOfLastMonth() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        List<Date> resultList = getDatesOfMonth(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1));
        return resultList;
    }

    /**
     * 获取本月月所有date
     * @return
     */
    public static List<Date> getAllDatesOfCurrentMonth() throws Exception {
        Calendar cal = Calendar.getInstance();
        List<Date> resultList = getDatesOfMonth(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1));
        return resultList;
    }

    /**
     * 复制一个 Calendar
     * @param source
     * @return
     */
    public static Calendar copyCalendar(Calendar source){
        if(source == null){
            return null;
        }
        Calendar temp = Calendar.getInstance();
        temp.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source.get(Calendar.DAY_OF_MONTH));
        return temp;
    }

    /**
     * 获取： 前 n 个月 + 指定月 + 后 m 个月
     * @param n
     * @param cal
     * @param m
     * @return
     */
    public static List<Calendar> getRecentMonthsList(int n, Calendar cal, int m){
        List<Calendar> resultList = new ArrayList<>(n+m+1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Calendar copyForBefore = copyCalendar(cal);
        while (n > 0) {
            copyForBefore.add(Calendar.MONTH, -1);
            resultList.add(copyCalendar(copyForBefore));
            n--;
        }
        resultList.add(cal);
        Calendar copyForAfter = copyCalendar(cal);
        while (m > 0) {
            copyForAfter.add(Calendar.MONTH, 1);
            resultList.add(copyCalendar(copyForAfter));
            m--;
        }
        resultList.sort(Calendar::compareTo);
        return resultList;
    }

    /**
     * 获取： 前 n 个月 + 指定月 + 后 m 个月 字符串
     * @param n
     * @param cal
     * @param m
     * @return
     */
    public static List<String> getRecentMonthsStrList(int n, Calendar cal, int m, String format){
        List<String> resultList = new ArrayList<>();
        List<Calendar> list = getRecentMonthsList(n, cal, m);
        for (Calendar calendar : list) {
            resultList.add(formatDate(calendar, format));
        }
        return resultList;
    }

    /**
     * 获取： 前 n 个月 + 本月 + 后 m 个月 字符串
     * @param n
     * @param m
     * @param format
     * @return
     */
    public static List<String> getRecentMonthsStrList(int n, int m, String format){
        return getRecentMonthsStrList(n, Calendar.getInstance(), m, format);
    }

    /**
     * 按周分月
     * @param month 格式：yyyy-MM
     * @throws ParseException
     */
    public static List<List<Date>> splitMonthByWeek(String month) throws ParseException {
        // 获取当月所有日期
        List<Date> datesOfMonth = getDatesOfMonth(month);
        int daysCount = datesOfMonth.size();

        List<List<Date>> resultList = new ArrayList<>();
        Date date = parseDate(month + "-01", FORMAT_DATE);
        // 第一周
        List<Date> firstWeek = new ArrayList<>(7);
        Calendar calendar = dateToCalendar(date);
        int dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK);
        int index = 0;
        if(1 == dayOfWeek){
            firstWeek.add(datesOfMonth.get(index));
            index ++;
        }else{
            int daysOfFirstWeek = 9 - dayOfWeek;
            firstWeek.add(datesOfMonth.get(index));
            index ++;
            for (int i = 1; i < daysOfFirstWeek; i++) {
                firstWeek.add(datesOfMonth.get(index));
                index ++;
            }
        }
        resultList.add(firstWeek);

        int firstWeekDaysCount = firstWeek.size();
        // 除第一周外，中间周数量
        int midWeeksCount = (daysCount - firstWeekDaysCount) / 7;
        for (int i = 0; i < midWeeksCount; i++) {
            List<Date> fullWeekDays =  new ArrayList<>(7);
            for (int j = 0; j < 7; j++) {
                fullWeekDays.add(datesOfMonth.get(index));
                index ++;
            }
            resultList.add(fullWeekDays);
        }

        // 最后一周
        int lastWeeksCount = (daysCount - firstWeekDaysCount) % 7;
        if(0 == lastWeeksCount){
            return resultList;
        }

        List<Date> lastWeekDays = new ArrayList<>(lastWeeksCount);
        for (int i = 0; i < lastWeeksCount; i++) {
            lastWeekDays.add(datesOfMonth.get(index));
            index++;
        }
        resultList.add(lastWeekDays);
        return resultList;
    }

    /**
     * 判断两个date是否同一天
     * @param thisDate
     * @param thatDate
     * @return
     */
    public static boolean isTheSameDay(Date thisDate, Date thatDate){
        Calendar thisCal = DateUtils.dateToCalendar(thisDate);
        Calendar thatCal = DateUtils.dateToCalendar(thatDate);
        return thisCal.get(Calendar.YEAR) == thatCal.get(Calendar.YEAR) && thisCal.get(Calendar.MONTH) == thatCal.get(Calendar.MONTH) && thisCal.get(Calendar.DAY_OF_MONTH) == thatCal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期所在周(周日期集合)
     * @param date
     * @return
     */
    public static List<Date> getWeekOfDate(Date date) throws ParseException {
        List<List<Date>> splitMonthByWeek = splitMonthByWeek(formatDate(date, FORMAT_DATE_YEAR_MONTH));
        for (List<Date> dates : splitMonthByWeek) {
            for (Date d : dates) {
                if(isTheSameDay(d, date)){
                    return dates;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定月份的最后一天
     * @param month 月份字符串，格式 yyyy-MM
     * @return
     */
    public static Date getLastDayOfMonth(String month) throws ParseException {
        Date date = parseDate(month + "-01 00:00:00", FORMAT_DATE_TIME);
        Calendar calendar = dateToCalendar(date);

        // 月末
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期所处的本周一
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
