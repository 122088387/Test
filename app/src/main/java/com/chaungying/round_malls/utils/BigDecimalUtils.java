package com.chaungying.round_malls.utils;

import java.math.BigDecimal;

/**
 * @author 王晓赛 or 2016/9/23
 *         <p/>
 *         保留两位小数 工具类
 */
public class BigDecimalUtils {

    public static String jia(String a1, String a2) {
        BigDecimal temp = new BigDecimal(a1).add(new BigDecimal(a2));
        return temp.toString();
    }

    public static String jian(String j1, String j2) {
        BigDecimal temp = new BigDecimal(j1).subtract(new BigDecimal(j2));
        return temp.toString();
    }

    public static String cheng(String c1, String c2) {
        BigDecimal temp = new BigDecimal(c1).multiply(new BigDecimal(c2));
        return temp.toString();
    }

    public static String chu(String c1, String c2) {
        BigDecimal temp = new BigDecimal(c1).divide(new BigDecimal(c2), 2, BigDecimal.ROUND_UP);
        return temp.toString();
    }

    public static String rounding(String str) {
        BigDecimal temp = new BigDecimal(str);
//        temp = temp.setScale(2,BigDecimal.ROUND_DOWN);//直接删除多余的小数位  11.116约=11.11
        temp = temp.setScale(2, BigDecimal.ROUND_UP);//临近位非零，则直接进位；临近位为零，不进位。11.114约=11.12
//        temp = temp.setScale(2,BigDecimal.ROUND_HALF_UP);//四舍五入 2.335约=2.33，2.3351约=2.34
//        temp = temp.setScale(2,BigDecimal.ROUND_HALF_DOWN);//四舍五入；2.335约=2.33，2.3351约=2.34，11.117约11.12
        return temp.toString();
    }

}
