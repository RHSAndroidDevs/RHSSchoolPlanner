package edu.rhs.school_planner;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    private ImageView banner;
    private Display display;
    private int screenW, imageW;
    private String[] mock = new String[31];
    private Button Bevents;
	private OnClickListener OCL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createOnClickListenter();
        banner = (ImageView) findViewById(R.id.banner);
        Bevents = (Button) findViewById (R.id.Bview_events);
        Bevents.setOnClickListener(OCL);
        setBannerSize();
        
        
        
    }
    private void createOnClickListenter() {
		OCL = new OnClickListener(){

			public void onClick(View v) {
				
				if(v == Bevents)
				{
					startActivity(new Intent(main.this, Events.class));
				}
				
			}
			
		};
		
	}
	public void setBannerSize(){
    	display = getWindowManager().getDefaultDisplay();
        screenW = display.getWidth();
        
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.bannertop3a);
        imageW = bitmapOrg.getWidth();
        
        float scaleWidth = ((float)screenW)/imageW;
        
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1);
        
        Bitmap resizedBitMap = Bitmap.createBitmap(bitmapOrg, 0, 0, imageW, bitmapOrg.getHeight(), matrix, true);
        
        BitmapDrawable bmd = new BitmapDrawable(resizedBitMap);
        
        banner.setImageDrawable(bmd);
    }

}
