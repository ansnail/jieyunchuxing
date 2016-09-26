package com.ish.jieyun.network.utils;

import android.app.Dialog;

import java.util.ArrayList;

/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/13 20:04.
 * @类说明:常用数据暂存类
 */
public class ObjEarth {

    //显示的Dialog集合
    public static ArrayList<Dialog> DIALOG_LISTS = new ArrayList<Dialog>();
    //日志打印TAG
    public static String TAG = "tag";
    //退出应用间隔时间
    public static int EXIT_APP_TIME = 2000;
    //网络请求超时时间间隔
    public static int CONNTIMEOUT = 30 * 1000;
    //注册
    public static String REGISTER = "REGISTER";
    //打开的是哪一个Activity
    public static String WHO_AM_I = "WHO_AM_I_ACTIVITY";
}
