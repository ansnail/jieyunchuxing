package com.ish.jieyun.picker;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

import com.ish.jieyun.R;

public class DateTimePicker implements OnClickListener {
	private Context mContext;
	private NumberPicker hourpicker;
	private NumberPicker minutepicker;
	private NumberPicker datepicker;
	private int yearStr;
	private String dateStr;
	private String hourStr;
	private String minuteStr;
	private String showDateStr;
	private String showHourStr;
	private String showMinuteStr;
	private String[] dateDatas;
	private String[] hourDatas;
	private String[] minuteDatas;
	private Dialog dialog;
	private long currentTimeMillis;
	private String[] weeks_zh = {"日","一","二","三","四","五","六"};
	private int indexMinutes;
	private OnItemClickListener listener;
	private String[] dayArrays;
	private String[] hourArrays;
	private String[] minuteArrs;

	public DateTimePicker(Context context) {
		this.mContext = context;
		dateTimePicKDialog();
	}

	public void show() {
		dialog.show();
	}
	
	@SuppressLint("NewApi") 
	private void initPicker() {
		Calendar calendar = Calendar.getInstance();
		
		yearStr = calendar.get(Calendar.YEAR);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);

		if (minutes == 0){
			indexMinutes = 0;
		}else if (minutes > 0 && minutes <=10) {
			indexMinutes = 1;
		}else if (minutes > 10 && minutes <=20) {
			indexMinutes = 2;
		}else if (minutes > 20 && minutes <=30) {
			indexMinutes = 3;
		}else if (minutes > 30 && minutes <=40) {
			indexMinutes = 4;
		}else if (minutes > 40 && minutes <=50) {
			indexMinutes = 5;
		}else if (minutes > 50) {
			indexMinutes = 0;
		}

		dayArrays = new String[2];
		dateDatas = new String[2];
		for (int i = 0; i < dayArrays.length; i++) {
			currentTimeMillis = System.currentTimeMillis() + i * 24 * 3600 * 1000;
			calendar.setTime(new Date(currentTimeMillis));
			if (i == 0) {
				dayArrays[i] = calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + "  今天";
			} else {
				dayArrays[i] = calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + "  星期"+weeks_zh[(calendar.get(Calendar.DAY_OF_WEEK)-1)];
			}
			if (calendar.get(Calendar.MONTH) + 1 < 10) {
				dateDatas[i] = "0" + (calendar.get(Calendar.MONTH) + 1) + "" + calendar.get(Calendar.DAY_OF_MONTH);
			} else {
				dateDatas[i] = calendar.get(Calendar.MONTH) + 1 + "" + calendar.get(Calendar.DAY_OF_MONTH);
			}
			
		}
		datepicker.setDisplayedValues(dayArrays);
		datepicker.setMinValue(0);
		datepicker.setMaxValue(dayArrays.length - 1);
		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			dateStr = "0" + (calendar.get(Calendar.MONTH) + 1) + "" + (calendar.get(Calendar.DAY_OF_MONTH)-1);
		} else {
			dateStr = calendar.get(Calendar.MONTH) + 1 + "" + (calendar.get(Calendar.DAY_OF_MONTH)-1);
		}
		showDateStr = calendar.get(Calendar.MONTH) + 1 + "月" + (calendar.get(Calendar.DAY_OF_MONTH)-1) + "日";
		datepicker.setValue(0);//设置显示初始值

		hourArrays = new String[25];
		hourDatas = new String[25];
		for (int i = 0; i < hourArrays.length; i++) {
			hourArrays[i] = i+"点";
			if (hours < 10) {
				hourDatas[i] = "0" + i;
			}else {
				hourDatas[i] = i + "";
			}
		}
		hourpicker.setDisplayedValues(hourArrays);
		hourpicker.setMaxValue(hourArrays.length - 1);
		hourpicker.setMinValue(1);
		hourpicker.setValue(hours+1);
		if (hours < 10) {
			hourStr = "0" + hours;
		}else {
			hourStr = hours + "";
		}
		showHourStr = hours + "点";

		minuteArrs = new String[6];
		minuteDatas = new String[6];
		for (int i = 0; i < minuteArrs.length; i++) {
			if (i == 0) {
				minuteArrs[i] = "00分";
				minuteDatas[i] = "00";
			}else {
				minuteArrs[i] = i + "0分";
				minuteDatas[i] = i + "0";
			}
		}
		minutepicker.setDisplayedValues(minuteArrs);
		minutepicker.setMinValue(0);
		minutepicker.setMaxValue(minuteArrs.length - 1);
		minutepicker.setValue(indexMinutes);
		minuteStr = minuteDatas[indexMinutes];// 初始值
		showMinuteStr = minuteDatas[indexMinutes] + "分";
		
	}

	/**
	 * 弹出日期时间选择框方法
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Dialog dateTimePicKDialog() {
		View dateTimeLayout = View.inflate(mContext, R.layout.picker_data_time,null);
		dateTimeLayout.findViewById(R.id.tv_cancel).setOnClickListener(this);
		dateTimeLayout.findViewById(R.id.tv_confirm).setOnClickListener(this);

		datepicker = (NumberPicker) dateTimeLayout.findViewById(R.id.datepicker);
		datepicker.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				dateStr = dateDatas[newVal];
				showDateStr = dayArrays[newVal];
			}
		});
		hourpicker = (NumberPicker) dateTimeLayout.findViewById(R.id.hourpicker);
		hourpicker.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				if (Integer.parseInt(hourDatas[newVal - 1]) < 10) {
					hourStr = "0" + hourDatas[newVal - 1];
				} else {
					hourStr = hourDatas[newVal - 1];
				}
				showHourStr = hourDatas[newVal - 1] + "点";
			}
		});
		minutepicker = (NumberPicker) dateTimeLayout.findViewById(R.id.minuteicker);
		minutepicker.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				minuteStr = minuteDatas[newVal];
				showMinuteStr = minuteDatas[newVal] + "分";
			}
		});
		datepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		hourpicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		minutepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		initPicker();
		dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(dateTimeLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.picker_animation);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围隐藏
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_cancel:
			dialog.dismiss();
			break;
		case R.id.tv_confirm:
			if (listener != null) {
				listener.confirm(yearStr+dateStr+hourStr+minuteStr, showDateStr+" "+showHourStr+showMinuteStr);
			}
			dialog.dismiss();
			break;
		default:
			break;
		}
	}
	
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}
	
	public interface OnItemClickListener{
		public void confirm(String valueTime,String showTime);
	}
}
