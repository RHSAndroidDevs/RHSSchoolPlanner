package edu.rhs.school_planner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import edu.rhs.school_planner_adapters.HomeworkAdapter;
import edu.rhs.school_planner_objects.HomeworkAssignment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

public class Homework extends Activity {
	private Button Byesterday, Btomorrow, Bdate,BnewEvent;
	private ListView LVhomework;
	private Calendar calendar;
	private HomeworkAdapter ha;
	private final int DATE_PICKER_ID=0;
	public void onCreate(Bundle onSavedInstanceState) {
		super.onCreate(onSavedInstanceState);
		setContentView(R.layout.homework);
		LVhomework = (ListView) findViewById(R.id.LVhomework);
		ha = new HomeworkAdapter(this);
		LVhomework.setAdapter(ha);
		calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		Bdate = (Button) findViewById(R.id.Bdate);
		Bdate.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showDialog(DATE_PICKER_ID);
				
			}
			
		});
		setDate(0);
		Byesterday = (Button) findViewById(R.id.Byesterday);
		Byesterday.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				setDate(-1*DateUtils.DAY_IN_MILLIS);
				
				
				
			}

			
			
		});
		Btomorrow = (Button) findViewById(R.id.Btomorrow);
		Btomorrow.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				setDate(DateUtils.DAY_IN_MILLIS);
				
				
			}
			
		});
		BnewEvent = (Button) findViewById(R.id.Bnew_Assignment);
		BnewEvent.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();              
				Intent intent = new Intent(Intent.ACTION_EDIT);
				intent.setType("vnd.android.cursor.item/event");
				intent.putExtra("beginTime", cal.getTimeInMillis());
				intent.putExtra("allDay", false);
				intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
				startActivity(intent);
				
			}
			
		});
	}
	public void onResume(){
		super.onResume();
		getEvents();
	}
	/*
	 * Creates a listener for the date picker. After a date has been selected we change
	 * our calendar to match and call set date to change text and events viewed
	 */
	 private DatePickerDialog.OnDateSetListener mDateSetListener =
         new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int month, int day) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.DAY_OF_MONTH,day);
				calendar.set(Calendar.MONTH,month);
				setDate(0);
				
			}
         };
         /*
          * creates dialogs based on their id
          * 
          */
    protected Dialog onCreateDialog(int id) {
    	switch (id){
    	case DATE_PICKER_ID:
    		return new DatePickerDialog(this, mDateSetListener, calendar.get(Calendar.YEAR),
    				calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    	default:
    		return null;
    	}
    	
    }
    /*
     * used to change the text of the date button and set the date to select calendar
     * events from
     */
	private void setDate(long i) {
		calendar.setTimeInMillis(calendar.getTimeInMillis()+i);
		Bdate.setText((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+
				calendar.get(Calendar.YEAR));
		getEvents();
		
	}
	/*
	 * Queries the calendar database to find all available databases on the phone and then gets all
	 * the events for the date selected to put into list form
	 */
	public void getEvents(){
		ha.getHomework().clear();
		//content resolver is how we access android databases like text messages and the calendar
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
			Log.v("calendar",calendar.getTime().toString());
			ContentUris.appendId(builder, calendar.getTimeInMillis() + (DateUtils.DAY_IN_MILLIS-21600000));
			Cursor eventCursor = cr.query(builder.build(),
					new String[] { "title", "begin", "end", "allDay"}, "Calendars._id=" + id,
					null, "startDay ASC, startMinute ASC"); 
			
			while (eventCursor.moveToNext()) {
				final String title = eventCursor.getString(0);
				final Date begin = new Date(eventCursor.getLong(1));
				final Date end = new Date(eventCursor.getLong(2));
				final Boolean allDay = !eventCursor.getString(3).equals("0");
				
				Log.v("test","Title: " + title + " Begin: " + begin + " End: " + end +
						" All Day: " + allDay);
				if(allDay)
					begin.setTime(begin.getTime()+DateUtils.DAY_IN_MILLIS);
				ha.addAssignment(new HomeworkAssignment(title,begin.toString()));
				
				
			}
			ha.notifyDataSetChanged();
		}
		
	}

}
