package com.example.autoluckymoney.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

public class AccessibilityUtils {
    public static  boolean isAccessiblityOk(Context ct){
    	int accessibilityEnabled = 0;
        final String ACCESSIBILITY_SERVICE_NAME = ct.getPackageName()+ "/com.example.autoluckymoney.AutoLuckyMoneyService";
        try {
            accessibilityEnabled = Settings.Secure.getInt(ct.getContentResolver(),android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Exception e) {
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter( ':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(ct.getContentResolver(),Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    if (accessabilityService.equalsIgnoreCase(ACCESSIBILITY_SERVICE_NAME)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void toAccessibilityPage(Context ct){
    	ct.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
