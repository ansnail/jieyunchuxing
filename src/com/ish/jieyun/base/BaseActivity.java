package com.ish.jieyun.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import butterknife.ButterKnife;

import java.util.HashMap;
import java.util.Map;

import com.ish.jieyun.bean.UserInfo;
import com.ish.jieyun.network.http.IBindData;
import com.ish.jieyun.network.utils.ObjEarth;

/**
 * @author weiyanjie
 * @Version 1.0
 * @Created time 2016/4/13 16:16.
 * @类说明: Activity基类
 */
public abstract class BaseActivity extends Activity implements IBindData,
		View.OnClickListener {

	protected Context mContext = null;
	protected Map<String, String> requestMap = null;
	protected Intent intent = null;
	protected UserInfo userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(ObjEarth.WHO_AM_I, getClass().getSimpleName());
		mContext = this;
		/** 请求信使，主要作用是完成请求后台时的参数传递,使用前建议用clear()方法清空 */
		if (requestMap == null) {
			requestMap = new HashMap<String, String>();
		}
		// 无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 输入框默认隐藏
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		userInfo = UserManager.getInstance().getUserinfo();
		ButterKnife.bind(this);
	}

	/**
	 * 初始化数据
	 */
	public abstract void initData();
	
	/**
	 * 初始化数据
	 */
	public abstract void initView();


	/**
	 * 显示警告土司
	 * 
	 * @param message
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

	}

	public void showToast(int message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

	}

	/**
	 * Activity跳转，无参数,父Activity不销毁
	 * 
	 * @param to
	 *            目的Activity
	 */
	public void goTo(Class<?> to) {
		goTo(to, false);
	}

	/**
	 * Activity跳转，如果是多个参数，建议用map<String, object>
	 * 
	 * @param to
	 * @param finished
	 */
	public void goTo(Class<?> to, boolean finished) {
		if (finished) {
			finish();
		}
		Intent intent = new Intent(mContext, to);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
