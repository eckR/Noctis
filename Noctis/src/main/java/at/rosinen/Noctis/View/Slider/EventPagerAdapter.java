package at.rosinen.Noctis.View.Slider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 21.03.2015.
 */
public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<Fragment>();


    public EventPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return "HEUTE";
            case 1: return "MORGEN";
            case 2: return "ÜBERMORGEN";
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

    public List<Fragment> getFragments() {
        return fragments;
    }
}
