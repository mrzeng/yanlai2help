/**
 * <p>Description: </p>
 * <p>Title: StringUtil.java</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: cei</p>
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 下午12:05:51</p>
 */
package org.spring.springboot.zw.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: StringUtil  
 * Description: 
 * @author zhaowei
 * @version 2018年4月26日 下午12:05:51
 */
public class StringUtil {
    /**
     * 将以逗号分隔的字符串转换成字符串数组
     * @param valStr
     * @return String[]
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

            i++;
        }
        return returnStr;
    }

    /**判断对象是否为空
     * @param object
     * @return
     * @author zhaowei
     * @version 2018年4月26日 下午12:10:09
     */
    public static boolean isEmpty(Object object) {
        boolean returnState = true;
        if (null == object || "".equals(object.toString().trim())) {
            returnState = false;
        }
        return returnState;
    }
    /**
     * 判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据数字获取26个英文字母--nun从0开始
     * @返回值：String
     * @创建时间：2018年4月15日 下午1:05:25
     */
    public static String getCharByNum(Integer num) {
        char word = (char) (num + 65);
        return String.valueOf(word);
    }

    /**
     * double保留一位小数
     * @返回值：String
     * @创建时间：2018年1月25日 上午10:12:08
     */
    public static String getStr1FromDouble(Double data) {
        DecimalFormat df = new DecimalFormat("######0.0");
        return df.format(data);
    }

    /**
     * double保留两位小数
     *
     * @创建人：zhaowei
     * @返回值：String
     * @创建时间：2018年1月25日 上午10:12:08
     */
    public static String getStr2FromDouble(Double data) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(data);
    }

    /**
     * double保留整数
     * @返回值：String
     * @创建时间：2018年1月25日 上午10:12:08
     */
    public static String getStrFromDouble(Double data) {
        DecimalFormat df = new DecimalFormat("######0");
        return df.format(Math.round(data));
    }

    /**
     * 时分秒转秒 创建时间：2018年2月5日 下午6:13:10 创建人： zhaowei
     * @param HOUR
     * @param MINUTE
     * @param SECOND
     * @return 整型
     */
    public static int getTimeToSecond(String HOUR, String MINUTE, String SECOND) {
        int totaltime = 0;
        if (HOUR != null && !"".equals(HOUR.trim())) {
            totaltime = totaltime + 3600 * Integer.parseInt(HOUR);
        }
        if (MINUTE != null && !"".equals(MINUTE.trim())) {
            totaltime = totaltime + 60 * Integer.parseInt(MINUTE);
        }
        if (SECOND != null && !"".equals(SECOND.trim())) {
            totaltime = totaltime + Integer.parseInt(SECOND);
        }
        return totaltime;
    }

    /**
     * 判断字符串是否为空或者0 true为非空不为0，其他为false 创建时间：2018年2月11日 下午2:36:29 创建人： zhaowei
     * @param input
     * @return
     */
    public static boolean checkIfEmptyAndZero(String input) {
        boolean returnState = false;
        if (input != null && !"".equals(input) && !"0".equals(input)) {
            returnState = true;
        }
        return returnState;
    }

    /**一位小数
     * @param str
     * @return
     * @author zhaowei
     * @version 2018年6月6日 下午2:34:26
     */
    public static String getOneDecimalFromString(String str) {
        Double result = Double.parseDouble(str);//转换成Double
        DecimalFormat df = new DecimalFormat("0.0");//格式化
        return df.format(result);
    }
}
