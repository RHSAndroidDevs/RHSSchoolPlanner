package edu.rhs.school_planner_adapters;

import java.net.URL;
import java.util.ArrayList;

import edu.rhs.school_planner.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IUAdapter extends BaseAdapter {
	private ArrayList<String> ALepisodes;
	private Activity context;
	public IUAdapter(ArrayList<String> episodes, Activity c)
	{
		super();
		ALepisodes = episodes;
		context = c;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return ALepisodes.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ALepisodes.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.event_row, parent, false);
		}
		TextView header = (TextView) row.findViewById(R.id.events_header);
		header.setText(ALepisodes.get(position).toString());
		return row;
	}

}
