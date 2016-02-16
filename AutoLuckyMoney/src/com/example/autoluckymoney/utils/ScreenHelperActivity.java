package com.example.autoluckymoney.utils;

import com.example.autoluckymoney.AutoLuckyMoneyService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ScreenHelperActivity extends Activity{
	private static Activity mInstance;
	private static Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			finishSelf();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		View view = new View(this);
		view.setAlpha(0);
		setContentView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if(LogUtils.isDebug){
			Log.i(LogUtils.TAG, "oncreate");
		}
		LockScreenUtils.disableKeyguard(this);
	}
	
	public static void finishSelf(){
		if(mInstance != null){
			mInstance.finish();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		handler.removeMessages(0);
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mInstance  = null;
		LockScreenUtils.isDisabled = false;
		AutoLuckyMoneyService instance = AutoLuckyMoneyService.getInstance();
		if(instance != null){
			instance.doClickLuckMoney(null);
		}
		if(LogUtils.isDebug){
			Log.i(LogUtils.TAG, "lock activity destroy");
		}
	}
}
