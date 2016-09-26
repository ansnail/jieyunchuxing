package com.ish.jieyun.network.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ish.jieyun.R;


/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/17 13:32.
 * @类说明:
 */
public class CommonUtils {

	private static Dialog loadingDialog = null;

	/**
	 * 生成等待loading对话框
	 * 
	 * @param context
	 * @param layoutId
	 * @return
	 */
	private static Dialog createLoadingDialog(Context context, int layoutId) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(layoutId, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_loading);// 加载动画
		layout.startAnimation(animation);// 使用ImageView显示动画
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 展示等待对话框
	 * 
	 * @param context
	 */
	public static void showLoadingDialog(Context context) {
		loadingDialog = createLoadingDialog(context,R.layout.common_widget_loading);
		if (ObjEarth.DIALOG_LISTS == null) {
			ObjEarth.DIALOG_LISTS = new ArrayList<Dialog>();
		}
		ObjEarth.DIALOG_LISTS.add(loadingDialog);
		loadingDialog.show();

	}

	/**
	 * 取消等待对话框
	 */
	public static void dismissLoadingDialog() {
		if (ObjEarth.DIALOG_LISTS != null) {
			for (Dialog loadingDialog : ObjEarth.DIALOG_LISTS) {
				if (loadingDialog != null && loadingDialog.isShowing()) {
					loadingDialog.dismiss();
				}
			}
		}
	}

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
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
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
	 * @return hashMap
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
	 * 删除List最后一个元素
	 * 
	 * @param list
	 */
	public static void deleteLast(ArrayList<String> list) {
		if (list.size() == 0) {
			return;
		}
		int size = list.size();
		list.remove(size - 1);
	}

	/**
	 * 删除重复项
	 * 
	 * @param list
	 * @return
	 */
	public static ArrayList<String> deleteRepeat(ArrayList<String> list) {
		ArrayList<String> tmp = new ArrayList<String>();
		for (String url : list) {
			if (!tmp.contains(url)) {
				tmp.add(url);
			}
		}
		return tmp;
	}

	/**
	 * 整型数组中是否包含某个元素
	 * 
	 * @param tags
	 *            数组
	 * @param tag
	 *            目标
	 * @return
	 */
	public static boolean isContain(int[] tags, int tag) {
		for (int t : tags) {
			if (t == tag) {
				return true;
			}
		}
		return false;
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

}
