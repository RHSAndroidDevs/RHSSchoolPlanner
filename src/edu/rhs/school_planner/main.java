package edu.rhs.school_planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import edu.rhs.school_planner_adapters.ImageAdapter;

public class main extends Activity {
	private Button mButtonEvents, mButtonIrishUpdate, mButtonHomework;

	private OnClickListener OCL = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if (v == mButtonEvents) {
				startActivity(new Intent(main.this, Events.class));
			} else if (v == mButtonIrishUpdate) {
				startActivity(new Intent(main.this, IrishUpdate.class));
			} else if (v == mButtonHomework) {
				startActivity(new Intent(main.this, Homework.class));
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mButtonEvents = (Button) findViewById (R.id.Bview_events);
		mButtonEvents.setOnClickListener(OCL);
		mButtonIrishUpdate = (Button) findViewById(R.id.Birish_update);
		mButtonIrishUpdate.setOnClickListener(OCL);
		mButtonHomework = (Button) findViewById(R.id.Bhomework);
		mButtonHomework.setOnClickListener(OCL);

		RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.imageAdapter);
		ViewPager imageGallery = new ViewPager(this);
		imageGallery.setAdapter(new ImageAdapter(this));
		imageLayout.addView(imageGallery);
	}
}
