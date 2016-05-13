package com.json.flow.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.json.flow.ui.LoginActivity;

public class JavaScriptinterface {
	private Context mContext;
	private SharedPreferences sp;

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
		sp = SPUtils.getInstance(mContext, Constants.SPNAME,
				Context.MODE_PRIVATE);
		List<String> keysList = new ArrayList<String>();
		keysList.add(Constants.SP_USER_NAME);
		keysList.add(Constants.SP_token);
		SPUtils.removeSharedPreferences(sp, keysList);
		Intent intent = new Intent(mContext, LoginActivity.class);
		mContext.startActivity(intent);
	}
}
