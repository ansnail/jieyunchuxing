package com.ish.jieyun.network;

import android.content.Context;


import java.util.Map;

import com.ish.jieyun.network.http.IBindData;
import com.ish.jieyun.network.http.ParamsDealLayer;



public class RequestFactory extends ParamsDealLayer {

    /**
     * 注册页面
     *
     * @param context     context
     * @param requestCode 请求码
     * @param bind        数据绑定对象
     * @param requster    请求参数
     */
    public static void register(Context context, int requestCode, IBindData bind, Map<String, String> requster) {
        doExecute(context, bind, requestCode, NetConfig.REGISTER_URL, requster);
    }
    
    /**
     * 登录页面
     *
     * @param context     context
     * @param requestCode 请求码
     * @param bind        数据绑定对象
     * @param requster    请求参数
     */
    public static void login(Context context, int requestCode, IBindData bind, Map<String, String> requster) {
    	doExecute(context, bind, requestCode, NetConfig.LOGIN_URL, requster);
    }
    
    /**
     * 提交订单
     *
     * @param context     context
     * @param requestCode 请求码
     * @param bind        数据绑定对象
     * @param requster    请求参数
     */
    public static void submitOrder(Context context, int requestCode, IBindData bind, Map<String, String> requster) {
    	doExecute(context, bind, requestCode, NetConfig.SUBMIT_ORDER_URL, requster);
    }
    
    /**
     * 订单列表
     *
     * @param context     context
     * @param requestCode 请求码
     * @param bind        数据绑定对象
     * @param requster    请求参数
     */
    public static void getOrderList(Context context, int requestCode, IBindData bind, Map<String, String> requster) {
    	doExecute(context, bind, requestCode, NetConfig.ORDER_LIST_URL, requster);
    }
    /**
     * 订单详情
     *
     * @param context     context
     * @param requestCode 请求码
     * @param bind        数据绑定对象
     * @param requster    请求参数
     */
    public static void getOrderDetails(Context context, int requestCode, IBindData bind, Map<String, String> requster) {
    	doExecute(context, bind, requestCode, NetConfig.ORDER_DETAILS_URL, requster);
    }

}
