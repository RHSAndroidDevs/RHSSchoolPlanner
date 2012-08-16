package edu.rhs.school_planner_adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.rhs.school_planner.R;
import edu.rhs.school_planner_objects.Event;

public class EventsAdapter extends BaseAdapter {
	ArrayList<Event> ALevents;
	Activity context;
	public EventsAdapter(ArrayList<Event> e, Activity c){
		super();
		ALevents=e;
		context = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ALevents.size();
	}

	@Override
	public Event getItem(int position) {
		// TODO Auto-generated method stub
		return ALevents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.event_row, parent, false);
		}
		TextView header = (TextView) row.findViewById(R.id.events_header);
		header.setText(ALevents.get(position).getTitle());
		return row;
	}

}
