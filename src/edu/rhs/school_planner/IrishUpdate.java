package edu.rhs.school_planner;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class IrishUpdate extends Activity {
	private ListView LVepisodes;
	private ArrayList<URI> ALepisodes;
	private Document html;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getAvailableEpisodes();
		LVepisodes = (ListView) findViewById(R.id.LVepisodes);
		
	}
	private void getAvailableEpisodes()  {
		try{
			html = Jsoup.connect("http://www.district196.org/rhs/irishupdate/index.cfm").get();
			
		}
		catch(Exception e ){}
		
		
		
	}
	
	
}
