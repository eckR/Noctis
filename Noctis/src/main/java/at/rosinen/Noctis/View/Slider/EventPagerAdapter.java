package at.rosinen.Noctis.View.Slider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.activity.NoctisApplication;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Simon on 21.03.2015.
 */
public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<Fragment>();

    private Context ctx;

    public EventPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
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

//    public List<Fragment> getFragments() {
//        return fragments;
//    }
}
