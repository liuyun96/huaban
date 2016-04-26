package com.json.flow.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.json.flow.R;
import com.json.flow.util.UrlContant;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

@SuppressLint("ShowToast")
public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_name, et_pass;
	private Button mLoginButton;
	private String userNameValue, passwordValue;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		et_name = (EditText) findViewById(R.id.username);
		et_pass = (EditText) findViewById(R.id.password);
		mLoginButton = (Button) findViewById(R.id.login);
		mLoginButton.setOnClickListener(this);
		sp = getSharedPreferences("SP", MODE_PRIVATE);
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			userNameValue = et_name.getText().toString();
			passwordValue = et_pass.getText().toString();
			login(userNameValue, passwordValue);
			break;
		default:
			break;
		}
	}

	private boolean login(final String userName, final String password) {

		new Thread() {
			public void run() {
				post(userName, password);
			}
		}.start();

		return false;
	}

	private boolean post(String userName, String password) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		String clientid = PushManager.getInstance().getClientid(this.getApplicationContext());
		/*
		 * //添加请求参数 params.addBodyParameter(key, value);
		 */
		params.addHeader("Content-Type", "application/json");
		params.addBodyParameter("userName", userName);
		params.addBodyParameter("password", password);
		params.addBodyParameter("os", "android");
		params.addBodyParameter("clientId", clientid);
		http.send(HttpMethod.POST, UrlContant.URL_LOGIN, params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String json = "{'resultCode':'0','resultMsg':'帐号密码验证成功','result':{'token':'123fa02e6eb7402db3a794c830884031'}}";
						json = responseInfo.result;
						try {
							JSONObject jsonObject = new JSONObject(json);
							if (jsonObject.getString("resultCode").equals("0")) {
								Toast.makeText(LoginActivity.this, "登录成功",
										Toast.LENGTH_SHORT).show();
								// 登录成功和记住密码框为选中状态才保存用户信息
								// 记住用户名、密码、
								Editor editor = sp.edit();
								editor.putString(UrlContant.SP_USER_NAME,
										userNameValue);
								jsonObject = new JSONObject(jsonObject.getString("result"));
								editor.putString(UrlContant.SP_token,
										jsonObject.getString("token"));
								editor.commit();
								// 跳转界面
								Intent intent = new Intent(LoginActivity.this,
										HomeActivity.class);
								LoginActivity.this.startActivity(intent);
								finish();
							} else {
								Toast.makeText(LoginActivity.this,
										jsonObject.getString("resultMsg"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(LoginActivity.this, "登录失败",
									Toast.LENGTH_SHORT).show();
						}// 我们需要把json串看成一个大的对象
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(LoginActivity.this, "登录失败",
								Toast.LENGTH_SHORT).show();
					}

				});
		return false;
	}

	public static void main(String[] args) {
		LoginActivity activity = new LoginActivity();
		activity.login("json", "1233");
	}
}
