package com.ish.jieyun.utils;

/**
 * 字符串工具类
 */

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class StringUtils {

    /**
     * 是否不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }



    /**
     * 给电话号加星号
     *
     * @param phone
     * @return
     */
    public static String addStartPhoneNum(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 给电话号码加空格
     *
     * @param phone
     * @return
     */
    public static String addPhoneSpace(String phone) {
        return phone.substring(0, 3) + " " + phone.substring(3, 7) + " "
                + phone.substring(7);
    }


    /**
     * 返回指定Map中指定key的value值
     *
     * @param responeMap
     * @param key
     * @return
     */
    public static String get(Map<String, Object> responeMap, String key) {
        String value;
        if (responeMap.get(key) == null) {
            value = "";
        } else {
            value = responeMap.get(key).toString();
        }
        return value;
    }


    /**
     * 把毫秒数转换成 00:00:00 的格式
     *
     * @param time 毫秒数
     * @return 字符串
     */
    public static String formatTimeToString(long time) {
        long day = ((time / 1000) / (3600 * 24));
        // 得到小时数
        long hour = ((time / 1000) - day * 86400) / 3600;
        // 得到分钟数
        long minutes = ((time / 1000) - day * 86400 - hour * 3600) / 60;
        // 得到秒数
        long seconds = (time / 1000) - day * 86400 - hour * 3600 - minutes * 60;
        return fitLen(hour) + ":" + fitLen(minutes) + ":" + fitLen(seconds);
    }

    private static String fitLen(long i) {
        String str = i + "";
        if (str.length() == 1) {
            return "0" + i;
        } else {
            return str;
        }
    }
    
    
    /**
     * 截取乘车人数
     * @param value
     * @return
     */
    public static String getAcountValue(String value) {
    	return value.substring(0,1);
	}




}
