package com.ish.jieyun.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ish.jieyun.R;
import com.ish.jieyun.base.BaseActivity;
import com.ish.jieyun.base.UserManager;
import com.ish.jieyun.network.NetConfig;
import com.ish.jieyun.network.RequestFactory;
import com.ish.jieyun.network.utils.StringUtil;

public class LoginActivity extends BaseActivity{

	private TextView register;
	private EditText phone;
	private EditText passwd;
	private TextView login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(this);
		login = (TextView) findViewById(R.id.login);
		login.setOnClickListener(this);
		phone = (EditText) findViewById(R.id.phone);
		passwd = (EditText) findViewById(R.id.passwd);
		
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.register) {
			Intent intent = new Intent(mContext, RegisterActivity.class);
			startActivity(intent);
		}
		if (v.getId() == R.id.login) {
			requestMap.clear();
			requestMap.put("teleno", phone.getText().toString());
			requestMap.put("password", passwd.getText().toString());
			RequestFactory.login(mContext, NetConfig.LOGIN_CODE, this, requestMap);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void bindData(int tag, Map<String, Object> responeMap) {
		Map<String, String> map = (Map<String, String>)(responeMap.get("obj"));
		UserManager.getInstance().saveUserInfo(map);
		Intent intent = new Intent(mContext, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void errorData(int tag, Map<String, Object> responeMap) {
		
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
