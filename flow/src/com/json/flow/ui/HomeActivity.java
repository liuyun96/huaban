package com.json.flow.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

import com.json.flow.R;
import com.json.flow.util.UrlContant;

public class HomeActivity extends Activity {
	private WebView webview;
	private SharedPreferences sp;

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
		webview = (WebView) findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		// 加载需要显示的网页
		webview.loadUrl(UrlContant.URL_INDEX
				+ UrlContant.getUri(sp.getString(UrlContant.SP_token, "NONE")));
		webview.setWebViewClient(new HelloWebViewClient());
	}
}
