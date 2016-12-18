package com.chaungying.avery_menu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 王晓赛 or 2016/8/16
 */
public class GetWeekUtils {


    public static String getWeek(String date) {//2016-08-15
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String weekStr = "";
        try {
            Date d = sdf.parse(date);
            c.setTime(d);
            int week = c.get(Calendar.DAY_OF_WEEK);
            switch (week) {
                case 1:
                    weekStr = "日";
                    break;
                case 2:
                    weekStr = "一";
                    break;
                case 3:
                    weekStr = "二";
                    break;
                case 4:
                    weekStr = "三";
                    break;
                case 5:
                    weekStr = "四";
                    break;
                case 6:
                    weekStr = "五";
                    break;
                case 7:
                    weekStr = "六";
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekStr;
    }

    public static int getWeekInt(String week) {//日
        int num = -1;
        switch (week) {
            case "一":
                num = 0;
                break;
            case "二":
                num = 1;
                break;
            case "三":
                num = 2;
                break;
            case "四":
                num = 3;
                break;
            case "五":
                num = 4;
                break;
            case "六":
                num = 5;
                break;
            case "日":
                num = 6;
                break;
        }
        return num;
    }
}
