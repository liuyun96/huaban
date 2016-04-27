package com.json.flow.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.json.flow.ui.LoginActivity;

public class JavaScriptinterface {
	private Context mContext;

	/** Instantiate the interface and set the context */
	public JavaScriptinterface(Context c) {
		mContext = c;
	}

	/** Show a toast from the web page */
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}

	/**********
	 * 退出登入
	 */
	public void logout() {
		Intent intent = new Intent(mContext, LoginActivity.class);
		mContext.startActivity(intent);
	}
}
