package com.rosinen.noctis.eventdetail;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.activity.event.ShowDetailsEvent;
import com.rosinen.noctis.base.EventBusFragment;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by harald on 3/28/15.
 */
@EFragment(R.layout.fragment_event_detail_pager)
public class EventDetailPagerFragment extends EventBusFragment {

    @ViewById
    ImageView eventImage;

    @ViewById
    TextView eventLocation;

    @ViewById
    TextView eventTitle;

    @ViewById
    ViewPager detailViewPager;

    EventDetailPagerAdapter adapter;

    @AfterViews
    void prepare() {
        adapter = new EventDetailPagerAdapter(getFragmentManager());
        detailViewPager.setAdapter(adapter);
        detailViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateHeader(adapter.getItemByPosition(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void onEvent(ShowDetailsEvent event) {
        adapter.setNoctisEventList(event.getEvents());
        adapter.notifyDataSetChanged();
        updateHeader(event.getEvents().get(event.getClickedPosition()));

    }

    private void updateHeader(NoctisEvent event) {
        eventLocation.setText(event.getLocation());
        eventTitle.setText(event.getName());
    }

}
