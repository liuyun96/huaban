package com.json.flow.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.json.flow.R;
import com.json.flow.util.Constants;
import com.json.flow.util.JavaScriptinterface;
import com.json.flow.util.SPUtils;
import com.json.flow.util.UrlContant;
import com.json.flow.view.components.CustomWebView;
import com.json.flow.view.components.CustomWebViewClient;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class HomeActivity extends Activity {
	private CustomWebView webview;
	private SharedPreferences sp;
	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    sp = SPUtils.getInstance(this, Constants.SPNAME, Context.MODE_PRIVATE);
	    String userName = sp.getString(Constants.SP_USER_NAME, "NONE");
		if ("NONE".equals(userName)) {
			Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
			HomeActivity.this.startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_home);
		String url = UrlContant.URL_INDEX
				+ UrlContant.getUri(sp.getString(Constants.SP_token, "NONE"));
		// url = "https://m.baidu.com/";
		CustomWebViewClient.addUrl(url);
		initView(url);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				Log.v("Himi", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				Log.v("Himi", "onConfigurationChanged_ORIENTATION_PORTRAIT");
			}
		} catch (Exception ex) {
		}
	}

	private void initView(String url) {
		webview = (CustomWebView) findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavaScriptinterface(
				HomeActivity.this), "FlowAndroid");
		// 加载需要显示的网页
		webview.loadUrl(url);
		webview.setWebViewClient(new CustomWebViewClient(HomeActivity.this));
		// CustomWebChromeClient webChromeClient = new
		// CustomWebChromeClient(HomeActivity.this);
		// webview.setWebChromeClient(webChromeClient);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& !CustomWebViewClient.CURRENT_URL.contains("index.html")) {
			if (CustomWebViewClient.getPreUrl() != null) {
				CustomWebViewClient.CURRENT_URL = CustomWebViewClient
						.getPreUrl();
				requestedOrientation(CustomWebViewClient.getPreUrl());
				initView(CustomWebViewClient.getPreUrl());
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& CustomWebViewClient.CURRENT_URL.contains("index.html")) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this,
						getResources().getString(R.string.press_again_exit),
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
				return true;
			} else {
				// 退出浏览器
				// SplashActivity.removeView();
				HomeActivity.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void requestedOrientation(String url) {
		if (url.contains("/deal")) {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

}
