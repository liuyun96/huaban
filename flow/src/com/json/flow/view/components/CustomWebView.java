package com.json.flow.view.components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * A convenient extension of WebView.
 */
public class CustomWebView extends WebView implements View.OnLongClickListener {

	private Context mContext;
	private int mProgress = 100;
	private boolean mIsLoading = false;
	private String mLoadedUrl;
	private static boolean mBoMethodsLoaded = false;

	private static Method mOnPauseMethod = null;
	private static Method mOnResumeMethod = null;
	private static Method mSetFindIsUp = null;
	private static Method mNotifyFindDialogDismissed = null;

	/**
	 * Constructor.
	 *
	 * @param context
	 *            The current context.
	 */
	public CustomWebView(Context context) {
		super(context);
		mContext = context;
		addedJavascriptInterface = false;
		initializeOptions();
		loadMethods();
	}

	/**
	 * Constructor.
	 *
	 * @param context
	 *            The current context.
	 * @param attrs
	 *            The attribute set.
	 */
	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		addedJavascriptInterface = false;
		initializeOptions();
		loadMethods();
	}

	/**
	 * Constructor.
	 *
	 * @param context
	 *            The current context.
	 * @param attrs
	 *            The attribute set.
	 */
	public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		addedJavascriptInterface = false;
		initializeOptions();
		loadMethods();
	}

	@Override
	public boolean canGoForward() {
		return super.canGoForward();
	}

	@Override
	public void addView(View child) {
		super.addView(child);

	}

	/**
	 * Initialize the WebView with the options set by the user through
	 * preferences.
	 */
	public void initializeOptions() {
		WebSettings settings = getSettings();
		settings.setLoadsImagesAutomatically(true);
		settings.setUserAgentString(settings.getUserAgentString());
		requestFocus();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);

		settings.setJavaScriptEnabled(true);
		settings.setAllowFileAccess(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		settings.setSupportZoom(true);
		settings.setSupportMultipleWindows(true);
		setOnLongClickListener(this);
		setLongClickable(true);
		setScrollbarFadingEnabled(true);
		setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		setDrawingCacheEnabled(true);

		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setDomStorageEnabled(true);
		addJavascriptInterface(this, "meapp4");
	}

	@android.webkit.JavascriptInterface
	public void layout(final String vcMemberInfo) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();

		// Enable / disable zoom support in case of multiple pointer, e.g.
		// enable zoom when we have two down pointers, disable with one pointer
		// or when pointer up.
		// We do this to prevent the display of zoom controls, which are not
		// useful and override over the right bubble.
		if ((action == MotionEvent.ACTION_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_1_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_2_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_3_DOWN)) {
			if (ev.getPointerCount() > 1) {
				this.getSettings().setBuiltInZoomControls(true);
				this.getSettings().setSupportZoom(true);
			} else {
				this.getSettings().setBuiltInZoomControls(false);
				this.getSettings().setSupportZoom(false);
			}
		} else if ((action == MotionEvent.ACTION_UP)
				|| (action == MotionEvent.ACTION_POINTER_UP)
				|| (action == MotionEvent.ACTION_POINTER_1_UP)
				|| (action == MotionEvent.ACTION_POINTER_2_UP)
				|| (action == MotionEvent.ACTION_POINTER_3_UP)) {
			this.getSettings().setBuiltInZoomControls(false);
			this.getSettings().setSupportZoom(false);
		}

		return super.onTouchEvent(ev);
	}

	@Override
	public void reload() {
		super.reload();
	}

	@Override
	public void loadUrl(String url) {
		super.loadUrl(url);
	}

	@Override
	public void goBack() {
		String lastUrl = getUrl();
		super.goBack();
	}

	public class JavascriptInterface {
		@android.webkit.JavascriptInterface
		@SuppressWarnings("unused")
		public void notifyVideoEnd() // Must match Javascript interface method
										// of VideoEnabledWebChromeClient
		{
			Log.d("___", "GOT IT");
			// This code is not executed in the UI thread, so we must force that
			// to happen
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (customWebChromeClient != null) {
						customWebChromeClient.onHideCustomView();
					}
				}
			});
		}
	}

	private CustomWebChromeClient customWebChromeClient;
	private boolean addedJavascriptInterface;

	/**
	 * Indicates if the video is being displayed using a custom view (typically
	 * full-screen)
	 *
	 * @return true it the video is being displayed using a custom view
	 *         (typically full-screen)
	 */
	@SuppressWarnings("unused")
	public boolean isVideoFullscreen() {
		return customWebChromeClient != null
				&& customWebChromeClient.isVideoFullscreen();
	}

	/**
	 * Pass only a VideoEnabledWebChromeClient instance.
	 */
	@Override
	@SuppressLint("SetJavaScriptEnabled")
	public void setWebChromeClient(WebChromeClient client) {
		getSettings().setJavaScriptEnabled(true);
		if (client instanceof CustomWebChromeClient) {
			this.customWebChromeClient = (CustomWebChromeClient) client;
		}
		super.setWebChromeClient(client);
	}

	@Override
	public void loadData(String data, String mimeType, String encoding) {
		addJavascriptInterface();
		super.loadData(data, mimeType, encoding);
	}

	@Override
	public void loadDataWithBaseURL(String baseUrl, String data,
			String mimeType, String encoding, String historyUrl) {
		addJavascriptInterface();
		super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
	}

	@Override
	public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
		addJavascriptInterface();
		super.loadUrl(url, additionalHttpHeaders);
	}

	private void addJavascriptInterface() {
		if (!addedJavascriptInterface) {
			// Add javascript interface to be called when the video ends (must
			// be done before page load)
			// noinspection all
			addJavascriptInterface(new JavascriptInterface(),
					"_VideoEnabledWebView"); // Must match Javascript interface
												// name of
												// VideoEnabledWebChromeClient
			addedJavascriptInterface = true;
		}
	}

	@Override
	public void goForward() {
		super.goForward();
	}

	/**
	 * Set the current loading progress of this view.
	 *
	 * @param progress
	 *            The current loading progress.
	 */
	public void setProgress(int progress) {
		mProgress = progress;
	}

	/**
	 * Get the current loading progress of the view.
	 *
	 * @return The current loading progress of the view.
	 */
	public int getProgress() {
		return mProgress;
	}

	/**
	 * Triggered when a new page loading is requested.
	 */
	public void notifyPageStarted() {
		mIsLoading = true;
	}

	/**
	 * Triggered when the page has finished loading.
	 */
	public void notifyPageFinished() {
		mProgress = 100;
		mIsLoading = false;
	}

	/**
	 * Check if the view is currently loading.
	 *
	 * @return True if the view is currently loading.
	 */
	public boolean isLoading() {
		return mIsLoading;
	}

	/**
	 * Get the loaded url, e.g. the one asked by the user, without redirections.
	 *
	 * @return The loaded url.
	 */
	public String getLoadedUrl() {
		return mLoadedUrl;
	}

	/**
	 * Reset the loaded url.
	 */
	public void resetLoadedUrl() {
		mLoadedUrl = null;
	}

	public boolean isSameUrl(String url) {
		if (url != null) {
			return url.equalsIgnoreCase(this.getUrl());
		}
		return false;
	}

	/**
	 * Perform an 'onPause' on this WebView through reflexion.
	 */
	public void doOnPause() {
		if (mOnPauseMethod != null) {
			try {
				mOnPauseMethod.invoke(this);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			}
		}
	}

	/**
	 * Perform an 'onResume' on this WebView through reflexion.
	 */
	public void doOnResume() {
		if (mOnResumeMethod != null) {
			try {
				mOnResumeMethod.invoke(this);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			}
		}
	}

	public void doSetFindIsUp(boolean value) {
		if (mSetFindIsUp != null) {
			try {
				mSetFindIsUp.invoke(this, value);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			}
		}
	}

	public void doNotifyFindDialogDismissed() {
		if (mNotifyFindDialogDismissed != null) {
			try {
				mNotifyFindDialogDismissed.invoke(this);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			}
		}
	}

	/**
	 * Load static reflected methods.
	 */
	private void loadMethods() {
		if (!mBoMethodsLoaded) {

			try {
				mOnPauseMethod = WebView.class.getMethod("onPause");
				mOnResumeMethod = WebView.class.getMethod("onResume");
			} catch (SecurityException e) {
				Log.e("CustomWebView", "loadMethods(): " + e.getMessage());
				mOnPauseMethod = null;
				mOnResumeMethod = null;
			} catch (NoSuchMethodException e) {
				Log.e("CustomWebView", "loadMethods(): " + e.getMessage());
				mOnPauseMethod = null;
				mOnResumeMethod = null;
			}

			try {
				mSetFindIsUp = WebView.class.getMethod("setFindIsUp",
						Boolean.TYPE);
				mNotifyFindDialogDismissed = WebView.class
						.getMethod("notifyFindDialogDismissed");
			} catch (SecurityException e) {
				Log.e("CustomWebView", "loadMethods(): " + e.getMessage());
				mSetFindIsUp = null;
				mNotifyFindDialogDismissed = null;
			} catch (NoSuchMethodException e) {
				Log.e("CustomWebView", "loadMethods(): " + e.getMessage());
				mSetFindIsUp = null;
				mNotifyFindDialogDismissed = null;
			}
			mBoMethodsLoaded = true;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		invalidate();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onLongClick(View view) {
		HitTestResult result = ((WebView) view).getHitTestResult();
		if (null == result) {
			return false;
		}
		return true;
	}

	private void anchorPop(HitTestResult result, View view) {

	}

}
