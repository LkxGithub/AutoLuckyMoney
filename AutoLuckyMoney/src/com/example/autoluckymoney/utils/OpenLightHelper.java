package com.example.autoluckymoney.utils;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.example.app.AutoLuckyMoneyApp;

public class OpenLightHelper {

	private WakeLock mWakeLock = null;
	private PowerManager mPowerManager;

	private static OpenLightHelper instance;
	private  Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(mWakeLock != null){
				mWakeLock.release();
			}
		};
	};

	public static OpenLightHelper getInstance() {
		if (instance == null)
			instance = new OpenLightHelper();
		return instance;
	}

	private OpenLightHelper() {
		mPowerManager = (PowerManager) AutoLuckyMoneyApp.getAppContext().getSystemService(Context.POWER_SERVICE);
	}

	public boolean isScreenOn() {
		return mPowerManager.isScreenOn();
	}

	public boolean wakeScreenLight() {
		if (mPowerManager.isScreenOn()) {
			return false;
		}
		if (mWakeLock == null) {
			mWakeLock = mPowerManager.newWakeLock(
					PowerManager.SCREEN_BRIGHT_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "dotools");
		}
		try {
			if (!mWakeLock.isHeld()) {
				mWakeLock.acquire();
				handler.removeMessages(0);
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
