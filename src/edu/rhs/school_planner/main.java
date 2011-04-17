package edu.rhs.school_planner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    private ImageView banner;
    private Display display;
    private GridView gv;
    private int screenW, imageW;
    private String[] mock = new String[31];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        banner = (ImageView) findViewById(R.id.banner);
        gv = (GridView) findViewById(R.id.GVcalendar);
        setBannerSize();
        setGridColumns();
        testgrid();
        
        
        
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
    public void setGridColumns(){
    	gv.setNumColumns(7);
    }
    public void testgrid(){
    	for (int x = 0; x<31; x++) {
    		mock[x]=(x+1)+"";
    	}
    	gv.setAdapter(new ArrayAdapter<String>(this,R.layout.dates ,mock));
    }

}
