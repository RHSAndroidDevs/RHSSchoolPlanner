package edu.rhs.school_planner;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WatchIU extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		String test = i.getStringExtra("URL");
		WebView wv = new WebView(this);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setAllowFileAccess(true);
		wv.getSettings().setPluginsEnabled(true);
		wv.loadUrl("http://www.district196.org/rhs/irishupdate/"+test);
		setContentView(wv);
	
	}

}
