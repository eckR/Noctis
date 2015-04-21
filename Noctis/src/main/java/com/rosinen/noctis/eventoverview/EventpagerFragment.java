package com.rosinen.noctis.eventoverview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.astuetz.PagerSlidingTabStrip;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Slider.EventPagerAdapter;
import com.rosinen.noctis.activity.event.SliderDragViewSetterEvent;
import com.rosinen.noctis.base.EventBusFragment;
import com.rosinen.noctis.eventoverview.event.EventListPageChangedEvent;
import com.rosinen.noctis.eventoverview.event.UpdateEventCount;
import com.rosinen.noctis.map.MapEventBus;
import com.rosinen.noctis.map.event.MarkEventsOnMapEvent;
import com.rosinen.noctis.noctisevents.event.RequestPicturesEvent;
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
public class EventpagerFragment extends EventBusFragment {

    private static String TAG = EventListFragment.class.getSimpleName();

    @Bean
    MapEventBus mapEventBus;

    @ViewById
    ViewPager viewPager;

    @ViewById
    PagerSlidingTabStrip pagerHeaderIndicator;

    @ViewById
    TextView eventCount;

    @ViewById
    View eventPagerDragHandle;

    EventPagerAdapter eventPagerAdapter;

    private List<EventListFragment> eventListFragments = new ArrayList<EventListFragment>(3);



    @Override
    public void onCreate(Bundle savendInstanceState) {
        super.onCreate(savendInstanceState);
        for (int i = 0 ; i < 3; ++i){
            eventListFragments.add(i, EventListFragment_.builder().day(i).build());
        }
        eventPagerAdapter = new EventPagerAdapter(getChildFragmentManager(),eventListFragments);

        //Fix Touch Bug for header
        mEventBus.postSticky(new EventListPageChangedEvent(0));

    }

    @AfterViews
    void afterViews(){

        viewPager.setAdapter(eventPagerAdapter);

        PagerListener pgList = new PagerListener();

        pagerHeaderIndicator.setViewPager(viewPager);
        pagerHeaderIndicator.setOnPageChangeListener(pgList);
        mEventBus.postSticky(new SliderDragViewSetterEvent(eventPagerDragHandle));

    }


    public void onEventMainThread(UpdateEventCount updateEventCount){

        int page = mEventBus.getStickyEvent(EventListPageChangedEvent.class).page;

        if(updateEventCount.day == page){
            eventCount.setText(updateEventCount.count + "");
        }
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

            mEventBus.postSticky(new EventListPageChangedEvent(position));

            //TODO think of fireing an event here!!
            //or maybe not because the counter has to be refreshed too
            List<NoctisEvent> noctisEventList = eventListFragments.get(position).mAdapter.getNoctisEventList();

            mapEventBus.getEventBus().post(new MarkEventsOnMapEvent(noctisEventList, position));
            EventBus.getDefault().post(new RequestPicturesEvent(noctisEventList, position));

            eventCount.setText(noctisEventList.size() + "");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //do nothing
//            Log.d(TAG, "onPageScrollStateChanged || state: " + state);
        }
    }
}
