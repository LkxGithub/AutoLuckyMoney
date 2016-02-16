package com.example.app;

import com.example.autoluckymoney.utils.OpenLightHelper;

import android.app.Application;
import android.os.Handler;

public class AutoLuckyMoneyApp extends Application{
	private static Application mContext;
	private Handler mHandler = new Handler();
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	public static Application getAppContext(){
		return mContext;
	}
}
