package com.ac.reserve.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @function：时间处理类
 * @date：2015-12-14 上午11:34:46
 * @author:he.
 * @notice：获取标准格式时间出错时返回字符串0，获取指定格式时间的毫秒值出错时返回long型的0 保证有返回值不是null是为了在插入数据库或者返回给前端不会出现异常
 * 。
 */
public class TimeOperating {


    protected static final String zerostr = "0";
    protected static final String entitystr = "";
    protected static final String onestr = "1";
    protected static final String twostr = "2";

    public static final String dateFmt = "yyyy-MM-dd";
    public static final String datetimeFmt = "yyyy-MM-dd HH:mm:ss";


    /**
     * @Function：获取当前时间戳
     * @Author：yu
     * @date：2017/1/20 15:19
     * @notice：
     */
    public static Timestamp getNowTimestamp() {

        return new Timestamp(System.currentTimeMillis());

    }


    /**
     * 获取当前日期加时间
     *
     * @return
     */
    public static String getNowTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getTodayDate(String pattern) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getTodayDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getTodayDate2() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTodayTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 标准格式的时间转换成毫秒值
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static long standradTimeToLong(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds = sdf.parse(dateTime).getTime();// 毫秒
        return millionSeconds;
    }

    /**
     * @function：获取数据库格式的标准时间yyyy-MM-dd hh:mm:ss
     * @parameter:
     * @return：
     * @date：2016-3-23 下午08:31:58
     * @author:he
     * @notice:
     */
    public static Timestamp getTimestamp() throws Exception {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @function：获取包含时区信息的时间
     * @parameter:
     * @return：
     * @date：2016-3-31 下午09:12:51
     * @author:he
     * @notice:
     */
    public static String getTimeWithLocation() throws Exception {
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
        System.out.println(outputFormat.format(System.currentTimeMillis()));
        return outputFormat.format(System.currentTimeMillis());
    }

    /**
     * @function：将timestamp和date格式时间转换成包含时区的标准格式时间
     * @parameter:
     * @return：
     * @date：2016-3-31 下午09:21:39
     * @author:he
     * @notice:
     */
    public static String formatToLocationTime(Object time) throws Exception {
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
            return outputFormat.format(time);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @function：获取一个小时之前的数据库格式的时间
     * @parameter:
     * @return：
     * @date：2016-4-11 下午03:31:36
     * @author:he
     * @notice:
     */
    public static Timestamp getTimestampAHourseAgo() throws Exception {
        return new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000);
    }

    /**
     * @Function:
     * @Author:he
     * @Date：2017/3/9 22:40
     * @Notice:
     */
    public static Timestamp getTimestampThreeDayAgo() throws Exception {
        return new Timestamp(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000);
    }

    /**
     * @function：获取一天之前数据库格式的时间
     * @parameter:
     * @return：
     * @date：2016-4-13 上午10:17:54
     * @author:he
     * @notice:
     */
    public static Timestamp getTimestampADayAgo() throws Exception {
        return new Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    }


    /**
     * 计算两个日期之间的天数。 bdate-smdate
     *
     * @param bdate
     * @param smdate
     * @return yu
     * @throws ParseException
     */
    public static int daysBetween(String bdate, String smdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @Function：输入时间，多少分钟，获取一个多少分钟之前的标准时间
     * @Author：yu
     * @date：2016/11/23 19:48
     * @notice：
     */
    public static String someMinuteBefore(int howMin) {
        Date now = new Date();
        Date now_how = new Date(now.getTime() - 60000 * howMin); //10分钟前的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String nowTime_how = dateFormat.format(now_how);
        return nowTime_how;

    }


    /**
     * 获取平安付款的时间戳
     *
     * @return yyyyMMddHHmmss
     */
    public static String getPingAnPayTimeStamp() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = formatter.format(date);  //时间
//		String datetamp = timestamp.substring(0, 8);  //日期
        return timestamp;
    }


    /****
     * 传入具体日期 ，返回具体日期减monthNum个月。
     *
     * @param date
     *            日期(2014-04-20)
     * @return 2014-03-20
     * @throws ParseException
     */
    public static String addMonth(String date, Integer monthNum) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MONTH, +monthNum);
        rightNow.add(Calendar.DATE, -1);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    /**
     * 传入具体日期 ，返回具体日期加dayNum天。
     *
     * @param pattern
     * @param date
     * @param dayNum
     * @return
     * @throws ParseException
     */
    public static String addDay(String pattern, String date, Integer dayNum) {
        String reStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date dt = sdf.parse(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);

            rightNow.add(Calendar.DATE, dayNum);
            Date dt1 = rightNow.getTime();
            reStr = sdf.format(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reStr;
    }

    /**
     * @Function：输入的是现在的时候，
     * @Author：yu
     * @date：2017/1/3 22:01
     * @notice：
     */
    public static String reduceMin(String date, Integer minNum) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MINUTE, -minNum);
//		rightNow.add(Calendar.DATE, -1);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    /**
     * @Function:〈获取今年的年份〉
     * @Author：tang
     * @date：2018/6/19 18:53
     */
    public static String getToYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String data = format.format(new Date());
        return data;
    }

    /**
     * @Function:〈获取今天的年月〉
     * @Author：tang
     * @date：2018/6/19 18:53
     */
    public static String getTodayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String data = format.format(new Date());
        return data;
    }

    /**
     * @Function:〈获取现在的月份〉
     * @Author：tang
     * @date：2018/6/19 18:53
     */
    public static String getTodayOfYearMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String data = format.format(new Date());
        return data;
    }

    /**
     * @Function:〈获取现在的天〉
     * @Author：tang
     * @date：2018/6/19 18:53
     */
    public static String getTodayOfYearDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String data = format.format(new Date());
        return data;
    }

    /**
     * @Function:〈获取现在的月份〉
     * @Author：tang
     * @date：2018/6/19 18:53
     */
    public static String getDateOfYeanMonthDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = format.format(date);
        return dateStr;
    }

    /**
     * @Function:〈获取某个日期的上一个月〉
     * @Author：tang
     * @date：2018/7/12 15:19
     */
    public static String getLastMonthOfOneDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date parse = format.parse(date);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.MONTH, -1);
            Date dateStr = instance.getTime();
            String lastMonth = format.format(dateStr);
            return lastMonth;
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00";
        }

    }

    /**
     * @Function:〈获取某个日期的下一个月〉
     * @Author：tang
     * @date：2018/7/12 15:19
     */
    public static String getAfterMonthOfOneDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date parse = format.parse(date);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.MONTH, 1);
            Date dateStr = instance.getTime();
            String lastMonth = format.format(dateStr);
            return lastMonth;
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00";
        }

    }

    /**
     * @Function:〈判断某个时间是否属于某个时间段 属于返回true 不属于返回false〉
     * @Author：tang
     * @date：2018/7/18 17:00
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Function:〈判断某个时间是否属于某个时间段 属于返回true 不属于返回false〉
     * @Author：tang
     * @date：2018/7/18 17:00
     */
    public static boolean belongCalendar(String nowTime, String beginTime, String endTime, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {

            Calendar date = Calendar.getInstance();
            date.setTime(format.parse(nowTime));

            Calendar begin = Calendar.getInstance();
            begin.setTime(format.parse(beginTime));

            Calendar end = Calendar.getInstance();
            end.setTime(format.parse(endTime));

            if (date.after(begin) && date.before(end)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @Function:〈判断某两个时间大小 时间一>时间二返回0  时间一 = 时间二返回1 时间一 < 时间二返回2〉
     * @Author：tang
     * @date：2018/7/18 17:00
     */
    public static String compareTimeSizeOfTwoTime(String timeOne, String timeTwo, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {

            Calendar one = Calendar.getInstance();
            one.setTime(format.parse(timeOne));

            Calendar two = Calendar.getInstance();
            two.setTime(format.parse(timeTwo));

            if (one.after(two)) {
                //时间一>时间二返回0
                return zerostr;
            } else if (one.before(two)) {
                //时间一 < 时间二返回2
                return twostr;
            } else {
                //时间一 = 时间二返回1
                return onestr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * @Function:〈转化某个日期的格式〉
     * @Author：tang
     * @date：2018/9/4 12:03
     */
    public static String changeDateFormat(String sourceFormat, String targetFormat, String time) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(sourceFormat);
            Date date = format1.parse(time);
            SimpleDateFormat format2 = new SimpleDateFormat(targetFormat);
            String format = format2.format(date);
            return format;
        } catch (Exception e) {
            System.out.println("时间格式转化异常");
        }
        return null;

    }

    /**
     * @Function:〈判断某个日期是否是该月的最后一天〉
     * @Author：tang
     * @date：2018/10/16 15:09
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param pattern     传入日期格式
     * @param needPettern 返回日期格式
     * @Function:〈获取日期月份的第一天〉
     * @Author：songm
     * @date：2019-1-28 11:19
     */
    public static String getMonthStartDate(String date, String pattern, String needPettern) {
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        SimpleDateFormat format2 = new SimpleDateFormat(needPettern);
        try {
            Date theDate = format1.parse(date);
            GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
            gcLast.setTime(theDate);
            gcLast.set(Calendar.DAY_OF_MONTH, 1);
            date = format2.format(gcLast.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param pattern     传入日期格式
     * @param needPettern 返回日期格式
     * @Function:〈获取日期月份的最后一天〉
     * @Author：songm
     * @date：2019-1-28 11:19
     */
    public static String getMonthEndDate(String date, String pattern, String needPettern) {
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        SimpleDateFormat format2 = new SimpleDateFormat(needPettern);
        try {
            Date theDate = format1.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(theDate);
            // 加一个月
            calendar.add(Calendar.MONTH, 1);
            // 设置为该月第一天
            calendar.set(Calendar.DATE, 1);
            // 再减一天即为上个月最后一天
            calendar.add(Calendar.DATE, -1);
            date = format2.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param pattern     传入日期格式
     * @param needPettern 返回日期格式
     * @Function:〈获取某时间指定时间格式〉
     * @Author：songm
     * @date：2019-1-28 11:19
     */
    public static String getTimeByFormat(String date, String pattern, String needPettern) {
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        SimpleDateFormat format2 = new SimpleDateFormat(needPettern);
        try {
            date = format2.format(format1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @Function:〈获得某年份的上一年〉
     * @Author：songm
     * @date：2019/3/7 9:42
     */
    public static String getLastYear(String year) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            Date parse = format.parse(year);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.YEAR, -1);
            Date dateStr = instance.getTime();
            String lastMonth = format.format(dateStr);
            return lastMonth;
        } catch (Exception e) {
            e.printStackTrace();
            return year;
        }
    }

    /**
     * @Function:〈获得某年份的下一年〉
     * @Author：songm
     * @date：2019/3/7 9:42
     */
    public static String getNextYear(String year) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            Date parse = format.parse(year);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.YEAR, 1);
            Date dateStr = instance.getTime();
            String lastMonth = format.format(dateStr);
            return lastMonth;
        } catch (Exception e) {
            e.printStackTrace();
            return year;
        }
    }

    /**
     * @Function:〈获取两个时间间隔的天数〉
     * @Author：songm
     * @date：2019/3/12 20:04
     */
    public static int getDayNumByTwoDate(String time0, String time1, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
            Date fDate = sdf.parse(time0);
            Date oDate = sdf.parse(time1);
            long days = (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
            return (int) days;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @Function:〈获取两个时间间隔的毫秒数〉
     * @Author：wj
     * @date：2019年7月4日 14:31:39
     */
    public static long getMsNumByTwoDate(String time0, String time1, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date time0Date = sdf.parse(time0);
        Date time1Date = sdf.parse(time1);
        long ms = (time0Date.getTime() - time1Date.getTime());
        return ms;
    }

    /**
     * @Function：输入时间，多少秒，获取一个多少秒之前的标准时间
     * @Author：wj
     * @date：2019年7月5日 10:02:59
     * @notice：
     */
    public static Timestamp getTimestampBySomeSecond(int howSecond) {

        // xx秒前的时间
        return new Timestamp(System.currentTimeMillis() - 1000 * howSecond);

    }


    /**
     * @return 大于-[1]  等于-[0]  小于-[-1]			报错-null
     * @Function: <判断某时间与当前时间大小>
     * @Author：wj
     * @date：2019年8月16日 10:36:18
     */
    public static Integer compareOneTimeWithNow(String timeOne, String pattern) {


        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {


            Date timeOneDate = format.parse(timeOne);
            Date nowDate = new Date();

            int compare = timeOneDate.compareTo(nowDate);

            return compare;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    public static void main(String[] args) {
        TimeOperating.getTodayDate("hh:ss:mm");
        System.out.println("getTodayDate = " + TimeOperating.getTodayDate("HH:mm:ss"));
        System.out.println(belongCalendar("2019-01-02", "2019-01-02", "2019-01-03", "yyyy-MM-dd"));
    }

}
