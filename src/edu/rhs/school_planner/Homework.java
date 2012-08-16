package edu.rhs.school_planner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import edu.rhs.school_planner_adapters.HomeworkAdapter;
import edu.rhs.school_planner_objects.HomeworkAssignment;

public class Homework extends Activity {
	private Button mButtonYesterday, mButtonTomorrow, mButtonDate, mButtonNewEvent;
	private ListView mHomeworkListView;
	private Calendar mCalendar;
	private HomeworkAdapter mHomeworkAdapter;

	@Override
	public void onCreate(Bundle onSavedInstanceState) {
		super.onCreate(onSavedInstanceState);
		setContentView(R.layout.homework);
		mHomeworkListView = (ListView) findViewById(R.id.LVhomework);
		mHomeworkAdapter = new HomeworkAdapter(this);
		mHomeworkListView.setAdapter(mHomeworkAdapter);
		mCalendar = new GregorianCalendar(0, 0, 0);

		setDate(0);

		mButtonDate = (Button) findViewById(R.id.Bdate);
		mButtonDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(Homework.this, mDateSetListener, mCalendar.get(Calendar.YEAR),
						mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

				dlg.show();
			}
		});

		mButtonYesterday = (Button) findViewById(R.id.Byesterday);
		mButtonYesterday.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				setDate(-1*DateUtils.DAY_IN_MILLIS);
			}
		});

		mButtonTomorrow = (Button) findViewById(R.id.Btomorrow);
		mButtonTomorrow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setDate(DateUtils.DAY_IN_MILLIS);
			}
		});

		mButtonNewEvent = (Button) findViewById(R.id.Bnew_Assignment);
		mButtonNewEvent.setOnClickListener(new OnClickListener(){
			@Override
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
	@Override
	public void onResume(){
		super.onResume();

		getEvents();
	}

	/**
	 * Creates a listener for the date picker. After a date has been selected we change
	 * our calendar to match and call set date to change text and events viewed
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mCalendar.set(Calendar.YEAR, year);
			mCalendar.set(Calendar.DAY_OF_MONTH,day);
			mCalendar.set(Calendar.MONTH,month);
			setDate(0);
		}
	};

	/**
	 * used to change the text of the date button and set the date to select calendar
	 * events from
	 */
	private void setDate(long i) {
		mCalendar.setTimeInMillis(mCalendar.getTimeInMillis() + i);

		mButtonDate.setText((mCalendar.get(Calendar.MONTH) + 1) + "/" + mCalendar.get(Calendar.DAY_OF_MONTH) +
				"/" + mCalendar.get(Calendar.YEAR));

		getEvents();
	}

	/**
	 * Delegate the getEvents to the proper method depending on the
	 * version of Android the device is running
	 */
	private void getEvents() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getEventsOld();
		} else {
			getEventsModern();
		}
	}

	/**
	 * Queries the calendar database to find all available databases on the phone and then gets all
	 * the events for the date selected to put into list form.
	 * 
	 * This is for devices running versions of Android prior to ICS.
	 */
	public void getEventsOld(){
		mHomeworkAdapter.getHomework().clear();
		//content resolver is how we access android databases like text messages and the calendar
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayName", "selected"  }, null, null, null);

		HashSet<String> calendarIds = new HashSet<String>();

		while (cursor.moveToNext()) {
			final String _id = cursor.getString(0);
			final String displayName = cursor.getString(1);
			final Boolean selected = !cursor.getString(2).equals("0");

			Log.v("test","Id: " + _id + " Display Name: " + displayName + " Selected: " + selected);
			calendarIds.add(_id);
		}

		for (String id: calendarIds){
			Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
			ContentUris.appendId(builder, mCalendar.getTimeInMillis());
			Log.v("calendar",mCalendar.getTime().toString());
			ContentUris.appendId(builder, mCalendar.getTimeInMillis() + (DateUtils.DAY_IN_MILLIS-21600000));
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
				if (allDay) {
					begin.setTime(begin.getTime()+DateUtils.DAY_IN_MILLIS);
				}
				mHomeworkAdapter.addAssignment(new HomeworkAssignment(title,begin.toString()));
			}

			mHomeworkAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * Get the list of events for devices running ICS and higher.
	 * 
	 * ICS introduced a set of APIs that allow us to get calendar
	 * information officially instead of the hack that was necessary
	 * before.
	 */
	@TargetApi(14)
	public void getEventsModern() {
		mHomeworkAdapter.getHomework().clear();
		//content resolver is how we access android databases like text messages and the calendar
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(CalendarContract.Calendars.CONTENT_URI,
				new String[]{ CalendarContract.Calendars._ID, CalendarContract.Calendars.NAME, CalendarContract.Calendars.VISIBLE },
				null, null, null);

		HashSet<String> calendarIds = new HashSet<String>();
		while (cursor.moveToNext()) {
			final String _id = cursor.getString(0);
			final String displayName = cursor.getString(1);
			final Boolean selected = !cursor.getString(2).equals("0");

			Log.v("test","Id: " + _id + " Display Name: " + displayName + " Selected: " + selected);
			calendarIds.add(_id);
		}

		for (String id: calendarIds) {
			Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
			ContentUris.appendId(builder, mCalendar.getTimeInMillis());
			Log.v("calendar",mCalendar.getTime().toString());
			ContentUris.appendId(builder, mCalendar.getTimeInMillis() + (DateUtils.DAY_IN_MILLIS-21600000));
			Cursor eventCursor = cr.query(builder.build(),
					new String[] { CalendarContract.Instances.TITLE, CalendarContract.Instances.BEGIN,
				CalendarContract.Instances.END, CalendarContract.Instances.ALL_DAY},
				CalendarContract.Events.CALENDAR_ID + "=" + id,
				null, CalendarContract.Instances.BEGIN + " ASC");

			while (eventCursor.moveToNext()) {
				final String title = eventCursor.getString(0);
				final Date begin = new Date(eventCursor.getLong(1));
				final Date end = new Date(eventCursor.getLong(2));
				final Boolean allDay = !eventCursor.getString(3).equals("0");

				Log.v("test","Title: " + title + " Begin: " + begin + " End: " + end +
						" All Day: " + allDay);

				if (allDay) {
					begin.setTime(begin.getTime() + DateUtils.DAY_IN_MILLIS);
				}

				mHomeworkAdapter.addAssignment(new HomeworkAssignment(title,begin.toString()));
			}

			mHomeworkAdapter.notifyDataSetChanged();
		}
	}

}
