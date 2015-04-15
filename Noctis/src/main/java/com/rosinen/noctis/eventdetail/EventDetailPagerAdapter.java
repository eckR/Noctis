package com.rosinen.noctis.eventdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.rosinen.noctis.eventdetail.FragmentEventDetail_;
import com.rosinen.noctis.Model.NoctisEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harald on 3/28/15.
 */
public class EventDetailPagerAdapter extends FragmentPagerAdapter{

    private List<NoctisEvent> events;

    public EventDetailPagerAdapter(FragmentManager fm) {
        super(fm);
        events = new ArrayList<NoctisEvent>();

    }



    @Override
    public Fragment getItem(int position) {
        FragmentEventDetail_ fragment = new FragmentEventDetail_();
        fragment.updateFields(events.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    public void setNoctisEventList(List<NoctisEvent> events) {
        if (events != null) {
            this.events = events;
        }
    }
    public NoctisEvent getItemByPosition(int position) {
        return events.get(position);
    }
}
