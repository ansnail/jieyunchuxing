package com.ish.jieyun.activity;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ish.jieyun.R;
import com.ish.jieyun.base.BaseActivity;
import com.ish.jieyun.network.NetConfig;
import com.ish.jieyun.network.RequestFactory;
import com.ish.jieyun.network.utils.ObjEarth;
import com.ish.jieyun.picker.DateTimePicker;
import com.ish.jieyun.picker.DateTimePicker.OnItemClickListener;
import com.ish.jieyun.picker.NumberPicker;
import com.ish.jieyun.picker.OptionPicker;
import com.ish.jieyun.utils.StringUtils;


public class MainActivity extends BaseActivity {
	
    private LinearLayout start_people_num_ll;
	private TextView start_people_num;
	private TextView submit;
	private String stime;
	private TextView my_order;
	private TextView my_info;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


	@Override
	public void bindData(int tag, Map<String, Object> responeMap) {
		showToast("成功啦");
	}

	@Override
	public void errorData(int tag, Map<String, Object> responeMap) {
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_time_ll) {
	        DateTimePicker picker = new DateTimePicker(MainActivity.this);
	        picker.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void confirm(String valueTime, String showTime) {
					start_time.setText(showTime);
					stime = valueTime;
				}});
	        picker.show();
	    }
		if (v.getId() == R.id.start_people_num_ll) {
	        NumberPicker picker = new NumberPicker(this);
	        picker.setRange(1, 4);//数字范围
	        picker.setSelectedItem(2);
	        picker.setLabel("人");
	        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
	            @Override
	            public void onOptionPicked(String option) {
	            	start_people_num.setText(option + "人");
	            	setTextColorBlack(start_people_num);
	            }
	        });
	        picker.show();
	    }
		if (v.getId() == R.id.submit) {
			requestMap.clear();
			requestMap.put("token", userInfo.token);
			requestMap.put("uid", userInfo.uid);
			requestMap.put("saddress", start_place.getText().toString());
			requestMap.put("daddress", destination.getText().toString());
			requestMap.put("ocount", StringUtils.getAcountValue(start_people_num.getText().toString()));
			requestMap.put("stime", start_time.getText().toString());
			requestMap.put("price", "");
			requestMap.put("seat", "");
			requestMap.put("remark", "");
			RequestFactory.submitOrder(mContext, NetConfig.SUBMIT_ORDER_CODE, this, requestMap);
		}
		if (v.getId() == R.id.my_order) {
			requestMap.clear();
			requestMap.put("token", userInfo.token);
			requestMap.put("uid", userInfo.uid);
			requestMap.put("page", "1");
			RequestFactory.getOrderList(mContext, NetConfig.ORDER_LIST_CODE, this, requestMap);
		}
		if (v.getId() == R.id.my_info) {
			requestMap.clear();
			requestMap.put("token", userInfo.token);
			requestMap.put("uid", userInfo.uid);
			requestMap.put("id", "fbdaa077575f2f3e01575f49e5ef0002");
			RequestFactory.getOrderDetails(mContext, NetConfig.ORDER_DETAILS_CODE, this, requestMap);
		}
	}

	@Override
	public void initData() {
		
	}
	
	
	
	
	/**
	 * 设置字体颜色
	 * @param view
	 */
	private void setTextColorBlack(TextView view) {
		view.setTextColor(getResources().getColor(R.color.text_black));
	}
	
	
	/**
     * 再按一次退出应用程序
     */

    long exitTime = 0;
	private LinearLayout start_time_ll;
	private TextView start_time;
	private EditText start_place;
	private EditText destination;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > ObjEarth.EXIT_APP_TIME) {
            showToast(R.string.exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


	@Override
	public void initView() {
		my_order = (TextView) findViewById(R.id.my_order);
		my_order.setOnClickListener(this);
		my_info = (TextView) findViewById(R.id.my_info);
		my_info.setOnClickListener(this);
		start_place = (EditText) findViewById(R.id.start_place);
		destination = (EditText) findViewById(R.id.destination);
		start_time_ll = (LinearLayout) findViewById(R.id.start_time_ll);
		start_time_ll.setOnClickListener(this);
		start_time = (TextView) findViewById(R.id.start_time);
		start_people_num_ll = (LinearLayout) findViewById(R.id.start_people_num_ll);
		start_people_num_ll.setOnClickListener(this);
		start_people_num = (TextView) findViewById(R.id.start_people_num);
		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
	}

}
