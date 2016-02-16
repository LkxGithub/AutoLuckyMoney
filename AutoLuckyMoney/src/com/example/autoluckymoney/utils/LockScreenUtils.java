package com.example.autoluckymoney.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.example.app.AutoLuckyMoneyApp;

public class LockScreenUtils {

    public static KeyguardLock mKeyguardLock;
    public static KeyguardManager mKeyguardManager;
    public  static boolean isDisabled;
	public static void disableKeyGuard(Context ct){
		if(mKeyguardManager == null){
			 mKeyguardManager = (KeyguardManager) ct.getSystemService(Context.KEYGUARD_SERVICE);
		     mKeyguardLock = mKeyguardManager.newKeyguardLock("lockymoney");
		}
		if(mKeyguardManager.inKeyguardRestrictedInputMode()){
			mKeyguardLock.disableKeyguard();
			isDisabled = true;
		}
	}
	
	public static void reenableKeyguard(){
		if(mKeyguardLock != null && isDisabled){
			mKeyguardLock.reenableKeyguard();
			isDisabled = false;
		}
	}
	
	public static void reenableLockscreen(){
		if(isDisabled){
			ScreenHelperActivity.finishSelf();
		}
	}
	
	public static void disableLockscreen(){
		if(mKeyguardManager == null){
			 mKeyguardManager = (KeyguardManager) AutoLuckyMoneyApp.getAppContext().getSystemService(Context.KEYGUARD_SERVICE);
		     mKeyguardLock = mKeyguardManager.newKeyguardLock("lockymoney");
		}
		if(mKeyguardManager.inKeyguardRestrictedInputMode() && !isDisabled){
			if(LogUtils.isDebug){
				Log.i(LogUtils.TAG, "锁屏 ");
			}
			AutoLuckyMoneyApp.getAppContext().startActivity(new Intent(AutoLuckyMoneyApp.getAppContext(),ScreenHelperActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
	
	   public static void disableKeyguard(Activity acv) {
				 acv.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			     acv.getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
			     isDisabled = true;
			if(LogUtils.isDebug){
				Log.i(LogUtils.TAG, "disable ");
			}
	    }
}
