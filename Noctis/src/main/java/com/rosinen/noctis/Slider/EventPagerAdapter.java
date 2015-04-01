package com.rosinen.noctis.Slider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.NoctisApplication;
import com.rosinen.noctis.eventoverview.EventListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simon on 21.03.2015.
 */
public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<EventListFragment> fragments = new ArrayList<EventListFragment>();

    private Context ctx;

    public EventPagerAdapter(FragmentManager fm, List<EventListFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.ctx = NoctisApplication.getContext();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return ctx.getString(R.string.today);
            case 1: return ctx.getString(R.string.tomorrow);
            case 2:
            {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR,2);
                if(calendar.get(Calendar.HOUR_OF_DAY) < 6){
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                }
                Locale locale = Locale.getDefault();
                return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
            }

            default: throw new IllegalArgumentException("Viewpager is out of bounds");
        }
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

}
