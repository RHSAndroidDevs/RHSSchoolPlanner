package edu.rhs.school_planner_adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.rhs.school_planner.R;

public class IUAdapter extends BaseAdapter {
	private ArrayList<String> ALepisodes;
	private Activity context;
	public IUAdapter(ArrayList<String> episodes, Activity c)
	{
		super();
		ALepisodes = episodes;
		context = c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ALepisodes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ALepisodes.get(position);
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
			row = inflater.inflate(R.layout.iu_row, parent, false);
		}
		TextView header = (TextView) row.findViewById(R.id.iu_header);
		String label=ALepisodes.get(position).toString();
		String year = label.substring(2, 6);
		String month = label.substring(6, 8);
		String day = label.substring(8, 10);
		header.setText(month+"/"+day+"/"+year);
		return row;
	}

}
