package edu.rhs.school_planner;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    private TextView text;
    private String tdText="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        
        try {
        	Log.d("test","in try");
			Document doc = Jsoup.connect("http://www.southsuburbanconference.org/g5-bin/client.cgi?G5genie=184&school_id=10").get();
			Log.d("internet", "got connection");
			Elements tds = doc.select("td.bcopyfooter");
			Log.d("Element","selected text");
			for (int x = 5; x<tds.size(); x++) {
				tdText = tdText+ "\n" + tds.get(x).text();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Cannot contact website... please check internet connection", Toast.LENGTH_LONG).show();
		}
		text.setText(tdText);
    }
    
}