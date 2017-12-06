package com.sunlands.feo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by huang on 2017/11/9.
 */
public class DateUtil {

    /**
     * 日期转换字符串
     *
     * @param date 时间（yyyy-MM-dd）
     * @return 字符串
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 日期转换时间字符串
     *
     * @param date（HH:mm:ss）
     * @return 字符串
     */
    public static String dateToStringTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 日期转换时间字符串
     *
     * @param date 时间（yyyyMMddHHmmss）
     * @return 字符串
     */
    public static String dateToStringDetailTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * 字符串转换日期
     *
     * @param date 字符串（yyyy-MM-dd）
     * @return 时间
     * @throws ParseException 时间格式化异常
     */
    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    /**
     * 日期回滚
     *
     * @param amount 需要会滚天数
     * @return 字符串
     */
    public static String getRollDate(Integer amount) {
        String str;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.roll(Calendar.DATE, -amount);
        str = sdf.format(lastDate.getTime());
        return str;
    }

//    //封装星期名称
//    public static List<Map<String, Object>> convertWeek(List<Map<String, Object>> list) {
//        for (Map<String, Object> map : list) {
//            Integer weekDay = (Integer) map.get("weekDay");
//            if (weekDay == 1) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期天");
//            }
//            if (weekDay == 2) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期一");
//            }
//            if (weekDay == 3) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期二");
//            }
//            if (weekDay == 4) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期三");
//            }
//            if (weekDay == 5) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期四");
//            }
//            if (weekDay == 6) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期五");
//            }
//            if (weekDay == 7) {
//                map.remove("weekDay");
//                map.put("weekDay", "星期六");
//            }
//        }
//        return list;
//    }
}
