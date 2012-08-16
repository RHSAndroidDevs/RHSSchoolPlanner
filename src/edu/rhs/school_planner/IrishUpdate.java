package edu.rhs.school_planner;

import java.util.ArrayList;

import org.jsoup.Jsoup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.rhs.school_planner_adapters.IUAdapter;

public class IrishUpdate extends Activity {
	private ListView LVepisodes;
	private ArrayList<String> ALepisodes;

	@Override
	public void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.irish_update);
		super.onCreate(savedInstanceState);
		ALepisodes = new ArrayList<String>();
		getAvailableEpisodes();
		LVepisodes = (ListView) findViewById(R.id.LVepisodes);
		LVepisodes.setAdapter(new IUAdapter(ALepisodes, this));
		LVepisodes.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Intent i = new Intent (IrishUpdate.this, WatchIU.class);
				i.putExtra("URL", ALepisodes.get(position).toString());
				startActivity(i);
			}

		});

	}

	private void getAvailableEpisodes() {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect("http://www.district196.org/rhs/irishupdate/index.cfm").get();
			org.jsoup.select.Elements elements = doc.select("a[href]");
			System.out.println(elements);
			for (org.jsoup.nodes.Element e: elements) {
				if(e.attr("href").charAt(0)=='i')
					ALepisodes.add(e.attr("href"));
				Log.v("test",ALepisodes.get(ALepisodes.size()-1));
			}

		} catch (Exception e) {
			Toast.makeText(this, "Couldn't contact server, try again later", Toast.LENGTH_SHORT).show();
		}
	}
}
