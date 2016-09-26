package com.ish.jieyun.network.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.ish.jieyun.network.utils.AppUtils;
import com.ish.jieyun.network.utils.CommonUtils;


/**
 * Created by bigerman on 2016/5/12.
 * 参数处理层
 */
public class ParamsDealLayer {


    /**
     * 添加额外请求参数
     *
     * @param context
     * @param paras
     * @param url
     * @return
     */
    protected static Map<String, String> addDefaultParams(Context context, Map<String, String> paras, String url) {
        if (paras == null) {
            paras = new HashMap<String, String>();
        }
        paras.put("imei", AppUtils.getGUID(context));
        return quNull(paras);
    }


    /**
     * 参数去除null
     *
     * @param request
     * @return
     */
    private static Map<String, String> quNull(Map<String, String> request) {
        for (String key : request.keySet()) {
            if (CommonUtils.isEmpty(request.get(key))) {
                request.put(key, "");
            }
        }
        return request;
    }


	/**
	 * 层间接口
	 * 
	 * @param context	上下文对象
	 * @param bind		绑定实例对象
	 * @param tag		请求标志位
	 * @param url		请求url
	 * @param request	请求参数map
	 */
    protected static void doExecute(Context context, IBindData bind, int tag, String url, Map<String, String> request) {
        startLoading(context);
        RealNetworkLayer.doExecute(context, bind, tag, url, addDefaultParams(context, request, url));
    }

    /**
     * 开启加载框
     *
     * @param context
     */
    private static void startLoading(Context context) {
        //开启加载框
        CommonUtils.showLoadingDialog(context);
    }

}
