package com.ish.jieyun.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/13 20:07.
 * @类说明:SharedPreferences管理类
 */
public class SharePreStore {


    /**
     * 保存用户信息
     *
     * @param context
     * @param userInfo
     */
    public static void saveUserInfo(Context context, String userInfo) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor myeditor = sp.edit();
        myeditor.putString("UserInfo", userInfo);
        myeditor.commit();
    }

    /**
     * 读取用户信息
     *
     * @param context
     */
    public static String readUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", 0);
        String userInfo = sp.getString("UserInfo", "");
        return userInfo;
    }

    /**
     * 清除用户信息
     *
     * @param context
     */
    public static void clearUserInfo(Context context) {
        saveUserInfo(context, "");
    }
}
