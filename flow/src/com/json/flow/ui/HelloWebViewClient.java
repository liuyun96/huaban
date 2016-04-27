package com.json.flow.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelloWebViewClient extends WebViewClient{
	
	private Activity activity;
	
	public HelloWebViewClient() {
	
	}

	public HelloWebViewClient(Activity activty){
		this.activity = activty;
	}
	
	@Override 
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(!url.contains("deal")){
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
        view.loadUrl(url);
        return true;  
    }  
}
