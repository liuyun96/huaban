package com.json.flow.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.json.flow.R;
import com.json.flow.view.components.CustomWebViewClient;

public class Fragment2 extends Fragment {
	private WebView webview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg2, container, false);
		webview = (WebView) view.findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		// 加载需要显示的网页
		webview.loadUrl("http://m.jd.com/");
		webview.setWebViewClient(new CustomWebViewClient());
		return view;
	}
}
