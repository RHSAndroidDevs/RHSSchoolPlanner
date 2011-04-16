package edu.rhs.school_planner;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    private ImageView banner;
    private Display display;
    private int screenW, imageW;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        banner = (ImageView) findViewById(R.id.banner);
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