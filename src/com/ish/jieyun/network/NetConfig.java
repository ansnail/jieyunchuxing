package com.ish.jieyun.network;

/**
 * 配置文件
 */
public class NetConfig {

    // 线上环境为true,测试环境为false
    public static final boolean NET_WORK = false;
    // 是否打印Log
    public static final boolean DEBUG = true;

    public static String getServiceUrl() {
        if (NET_WORK) {
            return "http://110.104.146.19:8080/JYCX";// 线上服务器地址
        } else {
            return "http://110.104.146.19:8080/JYCX";// 测试服务器地址
        }
    }

    public static final String CONFIG_PACKAGE_NAME = "com.ish.jieyun";
    
    /**
     * ================请求码开始======================
     */
    public static final int REQ_CODE_BASE = 100;//
    public static final int REQ_CODE_END = 999;//
    public static final int REGISTER_CODE = REQ_CODE_BASE + 1; // 注册页面
    public static final int LOGIN_CODE = REQ_CODE_BASE + 2; // 登录页面
    public static final int SUBMIT_ORDER_CODE = REQ_CODE_BASE + 3; // 提交订单页面
    public static final int ORDER_LIST_CODE = REQ_CODE_BASE + 4; //订单列表
    public static final int ORDER_DETAILS_CODE = REQ_CODE_BASE + 5; //订单详情

    /**
     * ================请求码结束======================
     */

    /**
     * ================请求URL开始======================
     */
    public static final String REGISTER_URL = "/passengerController.do?register";//注册页面
    public static final String LOGIN_URL = "/passengerController.do?login";//登录页面
    public static final String SUBMIT_ORDER_URL = "/orderController.do?placeOrder";//提交订单页面
    public static final String ORDER_LIST_URL = "/orderController.do?orderList";//订单列表
    public static final String ORDER_DETAILS_URL = "/orderController.do?detail";//订单详情

    /**
     * ================请求URL结束======================
     */

}
