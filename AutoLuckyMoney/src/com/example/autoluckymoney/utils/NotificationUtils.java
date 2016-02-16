package com.example.autoluckymoney.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.RemoteViews;

public class NotificationUtils {
	   public static ArrayList<String> getText(Notification notification) {
	        RemoteViews views = null;
	        if (views == null)
	            views = notification.contentView;
	        if (views == null)
	            return null;
	        ArrayList<String> text = new ArrayList<String>();
	        try {
	            Field field = views.getClass().getDeclaredField("mActions");
	            field.setAccessible(true);
	            @SuppressWarnings("unchecked")
	            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);
	            for (Parcelable p : actions) {
	                Parcel parcel = Parcel.obtain();
	                p.writeToParcel(parcel, 0);
	                parcel.setDataPosition(0);
	                int tag = parcel.readInt();
	                if (tag != 2)
	                    continue;
	                parcel.readInt();

	                String methodName = parcel.readString();
	                if (methodName == null)
	                    continue;
	                else if (methodName.equals("setText")) {
	                    parcel.readInt();
	                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString()
	                            .trim();
	                    text.add(t);
	                }
	                else if (methodName.equals("setTime")) {
	                    parcel.readInt();
	                    String t = new SimpleDateFormat("h:mm a").format(new Date(parcel.readLong()));
	                    text.add(t);
	                }

	                parcel.recycle();
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return text;
	    }
}
