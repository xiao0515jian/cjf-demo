package com.cjf.demo.config;

public interface CommonConst {
    String SPLIT=":";
    String FORMAT_MONTH="yyyy-MM";
    String FORMAT_DAY="yyyy-MM-dd";
    String FORMAT_YEAR="yyyy";
    /**
     *  当前年
     */
    String CURRENT_YEAR="currentYear";
    String CURRENT_MONTH_DAY="currentMonthDay";
    /**
     * 当前月
     */
    String CURRENT_MONTH="currentMonth";
    /**
     * 预测目标月份
      */
    String TARGET_MONTH="targetMonth";
    /**
     * 预测目标年份
     */
    String TARGET_YEAR="targetYear";
    /**
     * 去年的年份
     */
    String LAST_YEAR_YEAR="lastYearYear";
    /**
     * 去年的月份
     */
    String LAST_YEAR_MONTH="lastYearMonth";
    /**
     * 上个月的年份
     */
    String LAST_MONTH_YEAR="lastMonthYear";
    /**
     *  上个月的月份
     */
    String LAST_MONTH_MONTH="lastMonthMonth";
    /**
     *  开业月数
     */
    String OPEN_MONTH = "openMonth";
    int NEW_STORE = 1;
    int OLD_STORE = 2;
}
