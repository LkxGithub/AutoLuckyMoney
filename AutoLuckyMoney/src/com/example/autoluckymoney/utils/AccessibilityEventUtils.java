package com.example.autoluckymoney.utils;

import android.view.accessibility.AccessibilityEvent;

public class AccessibilityEventUtils {
	 public static  String getEventStr(AccessibilityEvent event){
	    	switch (event.getEventType()) {
			case AccessibilityEvent.TYPE_VIEW_CLICKED:
				return "TYPE_VIEW_CLICKED";
			case AccessibilityEvent.TYPE_VIEW_FOCUSED:
				return "TYPE_VIEW_FOCUSED";
			case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
				return "TYPE_VIEW_LONG_CLICKED";
			case AccessibilityEvent.TYPE_VIEW_SELECTED:
				return "TYPE_VIEW_SELECTED";
			case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
				return"TYPE_VIEW_TEXT_CHANGED";
			case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
				return "TYPE_WINDOW_STATE_CHANGED";
			case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
				return "TYPE_NOTIFICATION_STATE_CHANGED";
			case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
				return "TYPE_TOUCH_EXPLORATION_GESTURE_END";
			case AccessibilityEvent.TYPE_ANNOUNCEMENT:
				return "TYPE_ANNOUNCEMENT";
			case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
				return "TYPE_TOUCH_EXPLORATION_GESTURE_START";
			case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
				return "TYPE_VIEW_HOVER_ENTER";
			case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
				return"TYPE_VIEW_HOVER_EXIT";
			case AccessibilityEvent.TYPE_VIEW_SCROLLED:
				return "TYPE_VIEW_SCROLLED";
			case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
				return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
			case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
				return "TYPE_WINDOW_CONTENT_CHANGED";
			}
	    	return null;
	    }
}
