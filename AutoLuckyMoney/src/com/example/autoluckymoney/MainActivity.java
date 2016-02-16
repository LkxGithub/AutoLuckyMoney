package com.example.autoluckymoney;

import com.example.autoluckymoney.utils.AccessibilityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView mTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		mTv = (TextView) findViewById(R.id.tv);
		mTv.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshStatus();
	}

	private void refreshStatus() {
	     mTv.setText(AccessibilityUtils.isAccessiblityOk(getApplicationContext()) ? R.string.close_service : R.string.open_service);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.tv){
			AccessibilityUtils.toAccessibilityPage(getApplicationContext());
		}
	}
}
