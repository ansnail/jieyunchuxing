package com.ish.jieyun.network.http;

import java.util.Map;


public interface IBindData {


    /**
     * 绑定数据
     *
     * @param tag        对应网络请求中的TAG，用于标识返回数据
     * @param responeMap 请求结果的返回Map，可以直接取得需要的数据
     */
    public abstract void bindData(int tag, Map<String, Object> responeMap);

    /**
     * 绑定数据
     *
     * @param tag        对应网络请求中的TAG，用于标识错误返回数据
     * @param responeMap 请求结果的返回Map，可以直接取得需要的数据
     */
    public abstract void errorData(int tag, Map<String, Object> responeMap);
}