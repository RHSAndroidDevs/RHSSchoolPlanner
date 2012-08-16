package edu.rhs.school_planner_adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {

	private Context mContext;
	private Integer[] mImageIds = {
			edu.rhs.school_planner.R.drawable.band1011,
			edu.rhs.school_planner.R.drawable.band10111,
			edu.rhs.school_planner.R.drawable.band10112,
			edu.rhs.school_planner.R.drawable.band10113,
			edu.rhs.school_planner.R.drawable.baseball20708,
			edu.rhs.school_planner.R.drawable.baseballjv0910,
			edu.rhs.school_planner.R.drawable.basketballgirls0910,
			edu.rhs.school_planner.R.drawable.basketballgirls09101,
			edu.rhs.school_planner.R.drawable.bridge,
			edu.rhs.school_planner.R.drawable.ccboys1011,
			edu.rhs.school_planner.R.drawable.ccgirls1011,
			edu.rhs.school_planner.R.drawable.cheer0910,
			edu.rhs.school_planner.R.drawable.cheer1011,
			edu.rhs.school_planner.R.drawable.cheer10111,
			edu.rhs.school_planner.R.drawable.choir1011,
			edu.rhs.school_planner.R.drawable.choir_classact1011,
			edu.rhs.school_planner.R.drawable.class0910,
			edu.rhs.school_planner.R.drawable.class09101,
			edu.rhs.school_planner.R.drawable.dance0910,
			edu.rhs.school_planner.R.drawable.facultylanguages1011,
			edu.rhs.school_planner.R.drawable.fans1011,
			edu.rhs.school_planner.R.drawable.fans10111,
			edu.rhs.school_planner.R.drawable.fans10112,
			edu.rhs.school_planner.R.drawable.fans10113,
			edu.rhs.school_planner.R.drawable.fencing0607,
			edu.rhs.school_planner.R.drawable.football10111,
			edu.rhs.school_planner.R.drawable.football10112,
			edu.rhs.school_planner.R.drawable.football10113,
			edu.rhs.school_planner.R.drawable.football10114,
			edu.rhs.school_planner.R.drawable.football10115,
			edu.rhs.school_planner.R.drawable.golfgirls0910,
			edu.rhs.school_planner.R.drawable.golfgirls1011,
			edu.rhs.school_planner.R.drawable.graduation0910,
			edu.rhs.school_planner.R.drawable.graduation09101,
			edu.rhs.school_planner.R.drawable.graduation09102,
			edu.rhs.school_planner.R.drawable.greenteam1011,
			edu.rhs.school_planner.R.drawable.science08092,
			edu.rhs.school_planner.R.drawable.tennis0910
	};

	public ImageAdapter (Context c){
		mContext = c;
	}

	@Override
	public int getCount() {
		return mImageIds.length;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView i = new ImageView(mContext);
		i.setImageResource(mImageIds[position]);
		i.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setId(container.getId() + position);

		container.addView(i);
		return i;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO: Don't do anything right now...
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
