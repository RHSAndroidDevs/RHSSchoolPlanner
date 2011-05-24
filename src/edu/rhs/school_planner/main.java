package edu.rhs.school_planner;

import edu.rhs.school_planner_adapters.ImageAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    private ImageView banner;
    private Display display;
    private int screenW, imageW;
    private String[] mock = new String[31];
    private Button Bevents, BirishUpdate;
	private OnClickListener OCL;
	private Gallery gallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createOnClickListenter();
        banner = (ImageView) findViewById(R.id.banner);
        Bevents = (Button) findViewById (R.id.Bview_events);
        Bevents.setOnClickListener(OCL);
        BirishUpdate = (Button) findViewById(R.id.Birish_update);
        BirishUpdate.setOnClickListener(OCL);
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
				
			}
			
		};
		
	}

}
