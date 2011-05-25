package edu.rhs.school_planner_adapters;

import java.util.ArrayList;

import edu.rhs.school_planner.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeworkAdapter extends BaseAdapter {
	private ArrayList<String> homeworkTitle;
	private ArrayList<String> homeworkDate;
	private Activity context;
	
	public HomeworkAdapter(Activity c, ArrayList<String> h, ArrayList<String> d){
		context=c;
		homeworkTitle=h;
		homeworkDate=d;
	}
	public HomeworkAdapter(Activity c)
	{
		context=c;
		homeworkTitle = new ArrayList<String>();
		homeworkDate = new ArrayList<String>();
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return homeworkTitle.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return homeworkTitle.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.assignment_row, parent, false);
		}
		TextView header = (TextView) row.findViewById(R.id.CTVtitle);
		header.setText(homeworkTitle.get(position));
		TextView bulk = (TextView) row.findViewById(R.id.TVdatelabel);
		bulk.setText(homeworkDate.get(position));
		return row;
	}
	
	public void setHomework(ArrayList<String> h, ArrayList<String> d) {
		homeworkTitle=h;
		homeworkDate=d;
	}

}
