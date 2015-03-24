package at.rosinen.Noctis.eventoverview;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.View.Slider.EventPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_eventpager)
public class EventpagerFragment extends Fragment {

    @ViewById
    ViewPager viewPager;

    @ViewById
    PagerSlidingTabStrip pagerHeader;


    EventPagerAdapter eventPagerAdapter;


    @AfterViews
    void afterViews(){
        eventPagerAdapter = new EventPagerAdapter(getFragmentManager()
                ,new ArrayList<Fragment>()
                {
                    {
                        add(EventListFragment_.builder().day(0).build());
                        add(EventListFragment_.builder().day(1).build());
                        add(EventListFragment_.builder().day(2).build());
                    }
                });
        viewPager.setAdapter(eventPagerAdapter);

        pagerHeader.setViewPager(viewPager);

//        pagerHeader.setOnTouchListener(applier.listener);
//        pagerHeader.setToDispatch(findViewById(R.id.TopPaneEventList));
//        pagerHeader.requestDisallowInterceptTouchEvent(true);
    }


}
