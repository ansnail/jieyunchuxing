package com.ish.jieyun.network.http;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ResolveDataLayer {

    /**
     * 主解析方法
     * @param context
     * @param result
     * @param bindData
     * @param tag
     */
    public static void resolveResponeData(Context context,String result, IBindData bindData, int tag) throws Exception {
        //数据检验
    		Object response = (Map<String, String>) resolveResponeObject(result);
            if (bindData != null) {
                ErrorDealLayer.filterError(context,response,bindData,tag);
            }

    }

    /**
     * 解析返回数据
     *
     * @param response
     * @return
     */
    public static Object resolveResponeObject(String response) {
        try {
            JSONObject object = new JSONObject(response);
            Map<String, Object> map = new HashMap<String, Object>();
            map = jsonToMap(object);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json字符串转化为map
     *
     * @param json
     * @return
     * @throws JSONException
     */
    private static Map<String, Object> jsonToMap(JSONObject json)
            throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * JSONObject转化为Map
     * @param object
     * @return
     * @throws JSONException
     */
    private static Map<String, Object> toMap(JSONObject object)
            throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray转化为List
     * @param array
     * @return
     * @throws JSONException
     */
    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
