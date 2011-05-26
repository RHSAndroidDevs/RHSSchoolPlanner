package edu.rhs.school_planner_adapters;

import java.util.ArrayList;

import edu.rhs.school_planner.R;
import edu.rhs.school_planner_objects.HomeworkAssignment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeworkAdapter extends BaseAdapter {
	private ArrayList<HomeworkAssignment> homework;
	private Activity context;
	
	public HomeworkAdapter(Activity c, ArrayList<HomeworkAssignment> h){
		context=c;
		homework=h;
	}
	public HomeworkAdapter(Activity c)
	{
		context=c;
		homework = new ArrayList<HomeworkAssignment>();
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return homework.size();
	}

	public HomeworkAssignment getItem(int arg0) {
		// TODO Auto-generated method stub
		return homework.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.v("adapter", "getView called");
		View row = convertView;
		if( row == null){
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.assignment_row, parent, false);
		}
		
		TextView header = (TextView) row.findViewById(R.id.CTVtitle);
		header.setText(homework.get(position).getTitle());
		Log.v("adapter",position+"");
		TextView bulk = (TextView) row.findViewById(R.id.TVdatelabel);
		bulk.setText(homework.get(position).getDate());
		return row;
	}
	
	public ArrayList<HomeworkAssignment> getHomework(){
		return homework;
	}
	public void addAssignment(HomeworkAssignment h){
		homework.add(h);
	}
	public void setHomework(ArrayList<HomeworkAssignment> h)
	{
		homework=h;
	}
}	
