package com.json.flow.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.json.flow.R;
import com.json.flow.util.CustomWebView;
import com.json.flow.util.JavaScriptinterface;
import com.json.flow.util.UrlContant;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class HomeActivity extends Activity {
	private CustomWebView webview;
	private SharedPreferences sp;
	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("SP", MODE_PRIVATE);
		if ("NONE".equals(sp.getString(UrlContant.SP_USER_NAME, "NONE"))) {
			Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
			HomeActivity.this.startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_home);
		initView();
	}

	private void initView() {
		webview = (CustomWebView) findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavaScriptinterface(HomeActivity.this), "android");
		// 加载需要显示的网页
		//String url = UrlContant.URL_INDEX
			//	+ UrlContant.getUri(sp.getString(UrlContant.SP_token, "NONE"));
		webview.loadUrl(UrlContant.URL_INDEX
				+ UrlContant.getUri(sp.getString(UrlContant.SP_token, "NONE")));
		webview.setWebViewClient(new HelloWebViewClient(HomeActivity.this));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
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
		return super.onKeyUp(keyCode, event);
	}
}
