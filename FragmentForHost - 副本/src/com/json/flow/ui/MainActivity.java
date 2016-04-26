package com.json.flow.ui;

import com.igexin.sdk.PushManager;
import com.json.flow.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	// ����3��Fragment�Ķ���
	private Fragment1 fg1;
	private Fragment2 fg2;
	private Fragment3 fg3;
	// ֡���ֶ���,�����������Fragment������
	private FrameLayout flayout;
	// ����ײ����������������
	private RelativeLayout course_layout;
	private RelativeLayout found_layout;
	private RelativeLayout settings_layout;
	// ����ײ��������е�ImageView��TextView
	private ImageView course_image;
	private ImageView found_image;
	private ImageView settings_image;
	private TextView course_text;
	private TextView settings_text;
	private TextView found_text;
	// ����Ҫ�õ���ɫֵ
	private int whirt = 0xFFFFFFFF;
	private int gray = 0xFF7597B3;
	private int blue = 0xFF0AB2FB;
	// ����FragmentManager����
	FragmentManager fManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	public void initViews() {
		course_image = (ImageView) findViewById(R.id.course_image);
		found_image = (ImageView) findViewById(R.id.found_image);
		settings_image = (ImageView) findViewById(R.id.setting_image);
		course_text = (TextView) findViewById(R.id.course_text);
		found_text = (TextView) findViewById(R.id.found_text);
		settings_text = (TextView) findViewById(R.id.setting_text);
		course_layout = (RelativeLayout) findViewById(R.id.course_layout);
		found_layout = (RelativeLayout) findViewById(R.id.found_layout);
		settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		course_layout.setOnClickListener(this);
		found_layout.setOnClickListener(this);
		settings_layout.setOnClickListener(this);

		setChioceItem(0);
	}

	// ��дonClick�¼�
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.course_layout:
			setChioceItem(0);
			break;
		case R.id.found_layout:
			setChioceItem(1);
			break;
		case R.id.setting_layout:
			setChioceItem(2);
			break;
		default:
			break;
		}

	}

	// ����һ��ѡ��һ��item��Ĵ���
	public void setChioceItem(int index) {
		// ����ѡ��+��������Fragment
		FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
			course_text.setTextColor(blue);
			course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg1 == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				fg1 = new Fragment1();
				transaction.add(R.id.content, fg1);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fg1);
			}
			break;

		case 1:
			found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
			found_text.setTextColor(blue);
			found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg2 == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				fg2 = new Fragment2();
				transaction.add(R.id.content, fg2);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fg2);
			}
			break;

		case 2:
			settings_image
					.setImageResource(R.drawable.ic_tabbar_settings_pressed);
			settings_text.setTextColor(blue);
			settings_layout
					.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg3 == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				fg3 = new Fragment3();
				transaction.add(R.id.content, fg3);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fg3);
			}
			break;
		}
		transaction.commit();
	}

	// �������е�Fragment,����fragment����
	private void hideFragments(FragmentTransaction transaction) {
		if (fg1 != null) {
			transaction.hide(fg1);
		}
		if (fg2 != null) {
			transaction.hide(fg2);
		}
		if (fg3 != null) {
			transaction.hide(fg3);
		}
	}

	// ����һ����������ѡ��ķ���
	public void clearChioce() {
		course_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		course_layout.setBackgroundColor(whirt);
		course_text.setTextColor(gray);
		found_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		found_layout.setBackgroundColor(whirt);
		found_text.setTextColor(gray);
		settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		settings_layout.setBackgroundColor(whirt);
		settings_text.setTextColor(gray);
	}

}
