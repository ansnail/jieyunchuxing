package com.ish.jieyun.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ish.jieyun.R;
import com.ish.jieyun.base.BaseActivity;
import com.ish.jieyun.base.UserManager;

public class WelcomeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				entryNext();
			}

		}, 2000);
	}

	private void entryNext() {
		Intent intent = null;
		if (UserManager.getInstance().isLogin()) {
			intent = new Intent(mContext, MainActivity.class);
		}else {
			intent = new Intent(mContext, LoginActivity.class);
		}
		startActivity(intent);
		finish();
	}

	@Override
	public void bindData(int tag, Map<String, Object> responeMap) {
		
	}

	@Override
	public void errorData(int tag, Map<String, Object> responeMap) {
		
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
