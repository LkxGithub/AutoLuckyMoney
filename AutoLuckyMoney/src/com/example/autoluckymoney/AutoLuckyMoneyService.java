package com.example.autoluckymoney;

import java.util.ArrayList;
import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.autoluckymoney.utils.AccessibilityEventUtils;
import com.example.autoluckymoney.utils.LockScreenUtils;
import com.example.autoluckymoney.utils.LogUtils;
import com.example.autoluckymoney.utils.OpenLightHelper;

public class AutoLuckyMoneyService extends AccessibilityService{
	private final static String WECHAT_NOTIFICATION_TIP = "[微信红包]";
	private static final String WECHAT_VIEW_OTHERS_CH = "领取红包";
	private final static String regex = ".*你领取了.*的红包.*";
	private static AutoLuckyMoneyService sInstance;
	
	public static  AutoLuckyMoneyService getInstance(){
		return sInstance;
	}
	
	Handler mHandler = new Handler (){
		public void handleMessage(android.os.Message msg) {
			performGlobalAction(GLOBAL_ACTION_BACK);
			if(LogUtils.isDebug){
				Log.i(LogUtils.TAG, "global back");
			}
		};
	};
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if(LogUtils.isDebug){
			Log.i(LogUtils.TAG, AccessibilityEventUtils.getEventStr(event));
		}
		//通知发现红包　进入微信
		if(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED ){
			List<CharSequence> text = event.getText();
			if(!text.isEmpty()){
				if(LogUtils.isDebug){
					Log.i(LogUtils.TAG, "ntf 内容 "+text.toString());
				}
				if(text.toString().contains(WECHAT_NOTIFICATION_TIP)){
					try {
						if(LogUtils.isDebug){
							Log.i(LogUtils.TAG, "发现微信红包");
						}
						OpenLightHelper.getInstance().wakeScreenLight();
						((Notification)event.getParcelableData()).contentIntent.send();
						LockScreenUtils.disableLockscreen();
					} catch (CanceledException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		//进入微信后点红包
		if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED){
			AccessibilityNodeInfo nodeInfo = event.getSource();
			doClickLuckMoney(nodeInfo);
		}
		
		//领取红包
		if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
			AccessibilityNodeInfo source = event.getSource();
			if(source != null){
				if(source.getChildCount() == 5){
					if(LogUtils.isDebug){
						Log.i(LogUtils.TAG, "领取红包");
					}
					source.getChild(3).performAction(AccessibilityNodeInfo.ACTION_CLICK);
					Toast.makeText(getApplicationContext(), "成功领取红包", 1).show();
					mHandler.removeMessages(0);
					mHandler.sendEmptyMessageDelayed(0, 2000);
				}
			}
		}
	}
	public  void doClickLuckMoney(AccessibilityNodeInfo nodeInfo) {
		if(nodeInfo == null){
			nodeInfo = getRootInActiveWindow();
		}
		if(nodeInfo != null){
			List<AccessibilityNodeInfo> nodeInfos = findAccessibilityNodeInfosByTexts(nodeInfo, new String[]{WECHAT_VIEW_OTHERS_CH});
			if(!nodeInfos.isEmpty()){
				if(LogUtils.isDebug){
					Log.i(LogUtils.TAG, "点击最后一个红包");
				}
			 doNodeClick(nodeInfos.get(nodeInfos.size() - 1));
			}
		}
	}
	private void doNodeClick(AccessibilityNodeInfo toClickNode) {
		if(!checkNodeIsClicked(toClickNode)){
			toClickNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}
	}
	
	private boolean checkNodeIsClicked(AccessibilityNodeInfo toClickNode) {
		AccessibilityNodeInfo parentListView = toClickNode.getParent().getParent().getParent();
		int childeCount = parentListView.getChildCount();
		for(int i = 0; i  < childeCount; i++){
			if(parentListView.getChild(i).equals(toClickNode.getParent().getParent())){
				if((i + 1) < childeCount){
					if(parentListView.getChild(i+1).getClassName().equals("android.widget.LinearLayout")){
						if(parentListView.getChild(i+1).getChild(0).getText().toString().matches(regex)){
							if(LogUtils.isDebug){
								Log.i(LogUtils.TAG, "已经点击过最后一个红包");
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		if(LogUtils.isDebug){
			Log.i(LogUtils.TAG, "service create ");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		sInstance = null;
	}
	
	@Override
	protected void onServiceConnected() {
		if(LogUtils.isDebug){
			Log.i(LogUtils.TAG, "service conn");
		}
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.packageNames = new String[]{"com.tencent.mm"};
        info.notificationTimeout = 100;
        info.loadDescription(getPackageManager());
        setServiceInfo(info);
	}
	
	private List<AccessibilityNodeInfo> findAccessibilityNodeInfosByTexts(
			AccessibilityNodeInfo nodeInfo, String[] texts) {
		for (String text : texts) {
			if (text == null)
				continue;

			List<AccessibilityNodeInfo> nodes = nodeInfo
					.findAccessibilityNodeInfosByText(text);

			if (!nodes.isEmpty())
				return nodes;
		}
		return new ArrayList<>();
	}
	
	public  boolean isLockyMoneyClicked(AccessibilityNodeInfo node,String s) {
		if (null == node)
			return false ;
		final int count = node.getChildCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				AccessibilityNodeInfo childNode = node.getChild(i);
				isLockyMoneyClicked(childNode,s);
			}
		} else {
			CharSequence text = node.getText();
			if(!TextUtils.isEmpty(text)){
				if(text.toString().trim().matches(regex)){
					if(LogUtils.isDebug){
						Log.i(LogUtils.TAG, "已经领取过红包");
					}
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	@Override
	public void onInterrupt() {
	}
}
