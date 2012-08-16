package edu.rhs.school_planner;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.rhs.school_planner_adapters.IUAdapter;

public class IrishUpdate extends Activity {
	private ListView mEpisodesListView;
	private ArrayList<String> mEpisodes;

	private Handler mHandler = new Handler();
	private Runnable mRunnable;

	@Override
	public void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.irish_update);
		super.onCreate(savedInstanceState);
		mEpisodes = new ArrayList<String>();
		mEpisodesListView = (ListView) findViewById(R.id.LVepisodes);
		mEpisodesListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent i = new Intent(IrishUpdate.this, WatchIU.class);
				i.putExtra("URL", mEpisodes.get(position).toString());
				startActivity(i);
			}
		});

		mRunnable = new Runnable() {
			@Override
			public void run() {
				mEpisodesListView.setAdapter(new IUAdapter(mEpisodes, IrishUpdate.this));
			}
		};

		getAvailableEpisodes();
	}

	/**
	 * Get a list of episodes from the District website
	 * 
	 * Runs on a separate thread so that we don't get a NetworkOnMainThreadException
	 */
	private void getAvailableEpisodes() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// TODO: grab the anchors and stories as well as the date
					Document doc = Jsoup.connect("http://www.district196.org/rhs/irishupdate/index.cfm").get();
					Elements elements = doc.select("a[href]");

					for (Element e : elements) {
						if (e.attr("href").charAt(0)=='i') {
							mEpisodes.add(e.attr("href"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(IrishUpdate.this, "Couldn't contact server, try again later", Toast.LENGTH_SHORT).show();
				}

				mHandler.post(mRunnable);
			}
		}).start();
	}
}
