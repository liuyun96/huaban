package com.json.flow.view.components;

import java.util.ArrayList;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.json.flow.ui.HomeActivity;
import com.json.flow.util.UrlContant;

/**
 * Convenient extension of WebViewClient.
 */
public class CustomWebViewClient extends WebViewClient {

	static ArrayList<String> histories = new ArrayList<String>();

	public static String CURRENT_URL = UrlContant.URL_INDEX;

	private HomeActivity activity;

	public CustomWebViewClient() {

	}

	public CustomWebViewClient(HomeActivity activty) {
		this.activity = activty;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		CURRENT_URL = url;
		addUrl(url);
		view.loadUrl(url);
		activity.requestedOrientation(url);
		return true;
	}

	public static void addUrl(String url) {
		Log.v("url addUrl", url);
		histories.add(url);
		if (histories.size() >= 50) {
			// 删除最后一个
			histories.remove(histories.size() - 1);
		}
	}

	/***
	 * 获取上一个url
	 */
	public static String getPreUrl() {
		if (histories.size() > 1) {
			Log.v("url getPreUrl ", histories.get(0));
			return histories.get(0);
		}
		return null;
	}
}
