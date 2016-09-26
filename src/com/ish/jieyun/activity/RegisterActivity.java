package com.ish.jieyun.activity;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ish.jieyun.R;
import com.ish.jieyun.base.BaseActivity;
import com.ish.jieyun.network.NetConfig;
import com.ish.jieyun.network.RequestFactory;

public class RegisterActivity extends BaseActivity {

	private EditText phone;
	private EditText passwd;
	private TextView register;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		phone = (EditText) findViewById(R.id.phone);
		passwd = (EditText) findViewById(R.id.passwd);
		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestMap.clear();
				requestMap.put("teleno", phone.getText().toString());
				requestMap.put("password", passwd.getText().toString());
				RequestFactory.register(mContext, NetConfig.REGISTER_CODE, RegisterActivity.this, requestMap);
			}
		});

	}


	@Override
	public void bindData(int tag, Map<String, Object> responeMap) {
		Toast.makeText(mContext, "注册成功啦!", 0).show();
		finish();
	}


	@Override
	public void errorData(int tag, Map<String, Object> responeMap) {
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
