package edu.rhs.school_planner_adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.rhs.school_planner.R;

public class IUAdapter extends BaseAdapter {
	private ArrayList<String> mEpisodes;
	private Activity context;

	public IUAdapter(ArrayList<String> episodes, Activity c) {
		super();
		mEpisodes = episodes;
		context = c;
	}

	@Override
	public int getCount() {
		return mEpisodes.size();
	}

	@Override
	public Object getItem(int position) {
		return mEpisodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.iu_row, parent, false);
		}

		TextView header = (TextView) row.findViewById(R.id.iu_header);
		String label = mEpisodes.get(position).toString();
		label = label.replace("iu", "");
		label = label.replace("/index.cfm", "");

		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(label));

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);

			header.setText((month + 1) + "/" + day + "/" + year);
		} catch (ParseException pe) {
			System.out.println("ERROR: Cannot parse \"" + label + "\"");
		}

		return row;
	}

}
