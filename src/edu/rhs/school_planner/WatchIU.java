package edu.rhs.school_planner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class WatchIU extends Activity {

	private WebView webView;
	private String htmlPre = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"></head><body style='margin:0; pading:0; background-color: black;'>";
	private String htmlCode =
			" <embed style='width:100%; height:100%' src='http://www.platipus.nl/flvplayer/download/1.0/FLVPlayer.swf?fullscreen=true&video=@VIDEO@' " +
					"  autoplay='true' " +
					"  quality='high' bgcolor='#000000' " +
					"  name='VideoPlayer' align='middle'" + // width='640' height='480'
					"  allowScriptAccess='*' allowFullScreen='true'" +
					"  type='application/x-shockwave-flash' " +
					"  pluginspage='http://www.macromedia.com/go/getflashplayer' />" +
					"";
	private String htmlPost = "</body></html>";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.watch_iu);

		webView = (WebView) findViewById(R.id.webview);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY); //thanks Patrick!

		htmlCode = htmlCode.replaceAll("@VIDEO@", "http://www.district196.org/rhs/irishupdate/iu120606/IU06062012.flv");
		webView.loadDataWithBaseURL("fake://fake/fake", htmlPre+htmlCode+htmlPost, "text/html", "UTF-8", null);
	}

	@Override
	protected void onPause(){
		super.onPause();

		callHiddenWebViewMethod("onPause");

		webView.pauseTimers();
		if (isFinishing()) {
			webView.loadUrl("about:blank");
			setContentView(new FrameLayout(this));
		}
	}

	@Override
	protected void onResume(){
		super.onResume();

		callHiddenWebViewMethod("onResume");
		webView.resumeTimers();
	}

	private void callHiddenWebViewMethod(String name){
		// credits: http://stackoverflow.com/questions/3431351/how-do-i-pause-flash-content-in-an-android-webview-when-my-activity-isnt-visible
		if (webView != null) {
			try {
				Method method = WebView.class.getMethod(name);
				method.invoke(webView);
			} catch (NoSuchMethodException e) {
				Log.v("","No such method: " + name + e);
			} catch (IllegalAccessException e) {
				Log.v("","Illegal Access: " + name + e);
			} catch (InvocationTargetException e) {
				Log.v("","Invocation Target Exception: " + name + e);
			}
		}
	}
}
