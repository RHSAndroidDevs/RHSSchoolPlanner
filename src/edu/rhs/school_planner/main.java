package edu.rhs.school_planner;

import edu.rhs.school_planner_adapters.ImageAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;

public class main extends Activity {
    private Button Bevents, BirishUpdate, Bhomework;
	private OnClickListener OCL;
	private Gallery gallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createOnClickListenter();
        Bevents = (Button) findViewById (R.id.Bview_events);
        Bevents.setOnClickListener(OCL);
        BirishUpdate = (Button) findViewById(R.id.Birish_update);
        BirishUpdate.setOnClickListener(OCL);
        Bhomework = (Button) findViewById(R.id.Bhomework);
        Bhomework.setOnClickListener(OCL);
        gallery = (Gallery)findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        
        
        
    }
    private void createOnClickListenter() {
		OCL = new OnClickListener(){

			public void onClick(View v) {
				
				if(v == Bevents)
				{
					startActivity(new Intent(main.this, Events.class));
				}
				if(v == BirishUpdate)
				{
					startActivity(new Intent(main.this, IrishUpdate.class));
				}
				
				if(v == Bhomework)
				{
					startActivity(new Intent(main.this, Homework.class));
				}
			}
			
		};
		
	}

}
