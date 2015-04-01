package com.rosinen.noctis.eventoverview;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Slider.EventPagerAdapter;
import com.rosinen.noctis.eventoverview.EventListFragment_;
import com.rosinen.noctis.map.MapEventBus;
import com.rosinen.noctis.map.event.MarkEventsOnMapEvent;
import com.rosinen.noctis.noctisevents.event.RequestPicturesEvent;
import com.astuetz.PagerSlidingTabStrip;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_eventpager)
public class EventpagerFragment extends Fragment {

    private static String TAG = EventListFragment.class.getName();

    @Bean
    MapEventBus mapEventBus;

    @ViewById
    ViewPager viewPager;

    @ViewById
    PagerSlidingTabStrip pagerHeader;


    EventPagerAdapter eventPagerAdapter;

    private List<EventListFragment> eventListFragments = new ArrayList<EventListFragment>(3);


    @AfterViews
    void afterViews(){

        for (int i = 0 ; i < 3; ++i){
            eventListFragments.add(i, EventListFragment_.builder().day(i).build());
        }

        eventPagerAdapter = new EventPagerAdapter(getFragmentManager(),eventListFragments);

        viewPager.setAdapter(eventPagerAdapter);

        PagerListener pgList = new PagerListener();

        pagerHeader.setViewPager(viewPager);
        pagerHeader.setOnPageChangeListener(pgList);

    }


    /**
     * //TODO rename me and comment here :P
     */
    private class PagerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //do nothing
//            Log.d(TAG, "onPageScrolled || position: " + position + " positionOffset: " + positionOffset + " positionoffsetPixels: " +positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected || position: " + position);
            //TODO think of fireing an event here!!
            List<NoctisEvent> noctisEventList = eventListFragments.get(position).adapter.getNoctisEventList();
            mapEventBus.getEventBus().post(new MarkEventsOnMapEvent(noctisEventList));
            EventBus.getDefault().post(new RequestPicturesEvent(noctisEventList,position));

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //do nothing
//            Log.d(TAG, "onPageScrollStateChanged || state: " + state);
        }
    }
}
