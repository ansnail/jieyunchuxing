package com.ish.jieyun.network.http;

import android.content.Context;
import android.widget.Toast;


import java.util.Map;

import com.ish.jieyun.network.utils.CommonUtils;


/**
 * Created by bigerman on 2016/5/12.
 * 处理过滤返回的各种错误信息返回过滤后的正确信息
 */
public class ErrorDealLayer {
//	0：成功
//    -1：参数为空
//    -2：查找对象为空
//    -3：查找发生异常
//    -4：比较信息不匹配
//    -5：数据库中已重复 大部分情况下0就是成功状态，其他状态为异常状态
    private static String SUCCESS_TAG = "0";
    

    //返回标识
    private static String RESPONETAG = "";
    private static IBindData bindData = null;

    private static void dismissLoading() {
        //取消等待对话框
            CommonUtils.dismissLoadingDialog();
    }

    /**
     * 过滤处理各种错误的入口方法
     */
    public static void filterError(Context context, Object result, IBindData bindData, int tag) {
        //取消加载框
        ErrorDealLayer.bindData = bindData;
        dismissLoading();
        if (result != null) {
            final Map<String, Object> objMap = (Map<String, Object>) result;
            RESPONETAG = objMap.get("msgcode").toString();
            if (SUCCESS_TAG.equals(RESPONETAG)) {
                bindData.bindData(tag, objMap); // 将数据发送给注册的回调函数
            } else {
                dealError(context, RESPONETAG, objMap, tag);
            }
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理错误的各种情况
     *
     * @param context
     * @param tag
     */
    private static void dealError(Context context, String sign, Map<String, Object> objMap, int tag) {
        Toast.makeText(context, objMap.get("message").toString(), Toast.LENGTH_SHORT).show();
    }

}
