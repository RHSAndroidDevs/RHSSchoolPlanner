package edu.rhs.school_planner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import edu.rhs.school_planner_adapters.HomeworkAdapter;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Homework extends Activity {
	private Button Byesterday, Btomorrow, Bdate;
	private ListView LVhomework;
	private Calendar calendar;
	private ArrayList<String> homeworkTitle = new ArrayList<String>(), homeworkDate = new ArrayList<String>();
	public void onCreate(Bundle onSavedInstanceState) {
		super.onCreate(onSavedInstanceState);
		setContentView(R.layout.homework);
		LVhomework = (ListView) findViewById(R.id.LVhomework);
		LVhomework.setAdapter(new HomeworkAdapter(this));
		calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		Bdate = (Button) findViewById(R.id.Bdate);
		setDate(0);
		Byesterday = (Button) findViewById(R.id.Byesterday);
		Byesterday.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				setDate(-1*DateUtils.DAY_IN_MILLIS);
				getEvents();
				
				
			}

			
			
		});
		Btomorrow = (Button) findViewById(R.id.Btomorrow);
		Btomorrow.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				setDate(DateUtils.DAY_IN_MILLIS);
				getEvents();
				
			}
			
		});
		getEvents();
		
	}
	private void setDate(long i) {
		calendar.setTimeInMillis(calendar.getTimeInMillis()+i);
		Bdate.setText((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+
				calendar.get(Calendar.YEAR));
		
	}
	public void getEvents(){
		homeworkTitle.clear();
		homeworkDate.clear();
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayName", "selected"  }, null, null, null);         
	   
		HashSet<String> calendarIds = new HashSet<String>();
		
		while(cursor.moveToNext()){

			final String _id = cursor.getString(0);
			final String displayName = cursor.getString(1);
			final Boolean selected = !cursor.getString(2).equals("0");
			
			Log.v("test","Id: " + _id + " Display Name: " + displayName + " Selected: " + selected);
			calendarIds.add(_id);
	    }
		
		for (String id: calendarIds){
			Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
			ContentUris.appendId(builder, calendar.getTimeInMillis());
			ContentUris.appendId(builder, calendar.getTimeInMillis() + DateUtils.DAY_IN_MILLIS);

			Cursor eventCursor = cr.query(builder.build(),
					new String[] { "title", "begin", "end", "allDay"}, "Calendars._id=" + id,
					null, "startDay ASC, startMinute ASC"); 
			// For a full list of available columns see http://tinyurl.com/yfbg76w

			while (eventCursor.moveToNext()) {
				final String title = eventCursor.getString(0);
				final Date begin = new Date(eventCursor.getLong(1));
				final Date end = new Date(eventCursor.getLong(2));
				final Boolean allDay = !eventCursor.getString(3).equals("0");
				
				Log.v("test","Title: " + title + " Begin: " + begin + " End: " + end +
						" All Day: " + allDay);
				homeworkTitle.add(title);
				homeworkDate.add(begin.toString());
				
				
			}
		}
		((HomeworkAdapter) LVhomework.getAdapter()).setHomework(homeworkTitle, homeworkDate);
	}

}
