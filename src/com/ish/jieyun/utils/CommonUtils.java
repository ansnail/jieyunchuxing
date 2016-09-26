package com.ish.jieyun.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/17 13:32.
 * @类说明:
 */
public class CommonUtils {


    /**
     * 将map对象转化为class
     *
     * @param map
     * @return
     */
    public static Object map2Bean(Map<String, String> map, Class clazz) {
        Object object = null;
        if (map != null) {
            Field[] fields = clazz.getDeclaredFields();
            try {
                object = clazz.newInstance();
                for (Field field : fields) {
                    String key = field.getName();
                    if (map.containsKey(key)) {
                        field.set(object, map.get(key));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }


    /**
     * 当Scrollview中嵌套ListView时，会无法正确的计算ListView的大小
     *
     * @param listView
     */
    public static void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 将map参数转化为字符串
     *
     * @param map
     * @return
     */
    public static String map2Str(Map<String, String> map) {
        if (map != null) {
            StringBuffer buffer = new StringBuffer();
            for (String key : map.keySet()) {
                if (map.get(key) != null) {
                    buffer.append("&" + key + "=" + map.get(key).toString());
                }
            }
            return buffer.toString().substring(1);
        }
        return "";

    }

    /**
     * 字符串转化为Map
     *
     * @param string
     * @return
     */
    public static Map<String, String> str2Map(String string) {
        Map<String, String> map = new HashMap<String, String>();
        String[] dui = string.split("&");
        for (String string2 : dui) {
            if (string2.contains("=")) {
                if (string2.split("=").length == 2) {
                    map.put(string2.split("=")[0], string2.split("=")[1]);
                }
            }
        }
        return map;
    }


    /**
     * Map<String, Object>转化为Map<String, String>
     *
     * @param map
     * @return
     */
    public static Map<String, String> ObjToStrMap(Map<String, Object> map) {
        Map<String, String> hashMap = new HashMap<String, String>();
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                hashMap.put(key, (String) map.get(key));
            }
        }
        return hashMap;
    }


    /**
     * 手机号码校验
     *
     * @param num
     * @return
     */
    public static boolean isPhoneNum(String num) {
        String dex = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
        return num.matches(dex);
    }


    /**
     * 自定义双击事件
     */
    private static long[] mHits = new long[2];
    public static boolean isDoubleClick() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
            return true;
        }
        return false;
    }


}
