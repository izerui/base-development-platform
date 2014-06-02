/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xxx.yyy.framework.common.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 阳历转农历工具（带节气等）
 *
 * @author LiuLiJun
 */
public class ChineseCalendar {
    private int year;
    private int month;
    private int day;
    private boolean leap;
    final static String chineseNumber[] = {
            "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
            "yyyy年MM月dd日");
    final static long[] lunarInfo = new long[]
            {
                    0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0,
                    0x09ad0, 0x055d2,
                    0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
                    0x095b0, 0x14977,
                    0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570,
                    0x052f2, 0x04970,
                    0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
                    0x1c8d7, 0x0c950,
                    0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2,
                    0x0a950, 0x0b557,
                    0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
                    0x0e950, 0x06aa0,
                    0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
                    0x05b57, 0x056a0,
                    0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540,
                    0x0b5a0, 0x195a6,
                    0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
                    0x0ab60, 0x09570,
                    0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60,
                    0x096d5, 0x092e0,
                    0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
                    0x092d0, 0x0cab5,
                    0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176,
                    0x052b0, 0x0a930,
                    0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
                    0x0ea65, 0x0d530,
                    0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250,
                    0x0d520, 0x0dd45,
                    0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255,
                    0x06d20, 0x0ada0
            };

    //======   传回农历   y年的总天数
    final private int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    //======   传回农历   y年闰月的天数
    final private int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    //======   传回农历   y年闰哪个月   1-12   ,   没闰传回   0
    final private int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    //======   传回农历   y年m月的总天数
    final private int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }
    //======   传回农历   y年的生肖

    /**
     * 返货年属相
     */
    public String getAnimalsYear() {
        final String[] Animals = new String[]{
                "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }
//======   传入   offset   传回干支,   0=甲子

    /**
     * 返回年(甲子)
     */
    public String getCyclical() {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }

    //======   传入   月日的offset   传回干支,   0=甲子
    final private static String cyclicalm(int num) {
        final String[] Gan = new String[]{
                "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        final String[] Zhi = new String[]{
                "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /**
     * 节气
     */
    private String solar = null;

    /**
     * 传入日期格式为:2012-01-01
     *
     * @param dateStr
     * @throws java.text.ParseException
     */
    public ChineseCalendar(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dateChange(cal);
            this.solar = getChineseTwentyFourDay(date);
        } catch (Exception e) {
            System.out.println("ChineseCalendar传入时间格式出错:" + e.toString());
        }

    }

    /**
     * 传入日期Date
     *
     * @throws java.text.ParseException
     */
    public ChineseCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        dateChange(cal);
        this.solar = getChineseTwentyFourDay(date);

    }

    public String getChineseDay() {
        return getChinaDayString(day);
    }

    /**
     * 返回农历
     *
     * @return
     */
    public String getChineseMouthDay() {
        return chineseNumber[month - 1] + "月" + getChinaDayString(day);
    }

    /**
     * 显示详细信息
     */
    public String toString() {
        return getCyclical() + "年" + (leap ? "闰" : "") + chineseNumber[month - 1] + "月" +
                getChinaDayString(day) + " 【 " + getAnimalsYear() + "年】";
    }

    /**
     * 返回节气
     *
     * @return
     */
    public String getSolartem() {
        return solar;
    }

    /**
     * 获得节气
     *
     * @param date
     * @return
     */
    public static String getSolartem(Date date) {
        return getChineseTwentyFourDay(date);
    }

    /**
     * 获得气节
     * 日期格式:2012-01-01
     *
     * @param dateStr
     * @return
     */
    public static String getSolartem(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = sdf.parse(dateStr);
            return getChineseTwentyFourDay(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 传出y年m月d日对应的农历.
     * yearCyl3:农历年与1864的相差数                             ?
     * monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40             ?
     *
     * @param cal
     * @return
     */
    private void dateChange(Calendar cal) {
        int monCyl;
        int leapMonth = 0;
        Date baseDate = null;
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
        } catch (ParseException e) {
            e.printStackTrace(); //To   change   body   of   catch   statement   use   Options   |   File   Templates.
        }

        //求出和1900年1月31日相差的天数
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) /
                86400000L);
        monCyl = 14;

        //用offset减去每农历年的天数
        //   计算当天是农历第几天
        //i最终结果是农历的年份
        //offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        //农历年份
        year = iYear;
        leapMonth = leapMonth(iYear); //闰哪个月,1-12
        leap = false;

        //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            //闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = monthDays(year, iMonth);

            offset -= daysOfMonth;
            //解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
            if (!leap)
                monCyl++;
        }
        //offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
                --monCyl;
            }
        }
        //offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --monCyl;
        }
        month = iMonth;
        day = offset + 1;
    }

    //农历显示格式
    private String getChinaDayString(int day) {
        String chineseTen[] = {
                "初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }


    //节气计算

    /**
     * 日期格式
     */
    private static String formatData(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 计算节气
     *
     * @param date1
     * @return
     */
    private static String getChineseTwentyFourDay(Date date1) {

        String[] SolarTerm = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
        int[] sTermInfo = new int[]{0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
        //Date baseDateAndTime = new Date(1900, 1, 6, 2, 5, 0); //#1/6/1900 2:05:00 AM#
        Calendar cal = Calendar.getInstance();
        cal.set(1900, 0, 6, 2, 5, 0);//月份从0开始
        final Date baseDateAndTime = cal.getTime();
        double num;
        int y;
        String tempStr = "";
        //获取年份
        y = Integer.parseInt(formatData(date1, "yyyy"));

        for (int i = 1; i <= 24; i++) {
            num = 525948.76 * (y - 1900) + sTermInfo[i - 1];
            Calendar calendarMin = Calendar.getInstance();
            calendarMin.setTime(baseDateAndTime);
            //添加分钟
            calendarMin.add(Calendar.MINUTE, (int) Math.ceil(num) + 5);
            //对传递的天数进行处理
            Calendar calendarDate1 = Calendar.getInstance();
            calendarDate1.setTime(date1);

            //对时间进行格式化字符串比较
            if (formatData(calendarMin.getTime(), "yyyy-MM-dd").trim().equals(formatData(calendarDate1.getTime(), "yyyy-MM-dd").trim())) {
                tempStr = SolarTerm[i - 1];
                break;
            }
        }
        return tempStr;
    }

}