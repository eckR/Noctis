package at.rosinen.Noctis.View;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import at.rosinen.Noctis.EventListFragment_;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.View.Slider.EventPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

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
                        add(new EventListFragment_());
                        add(new EventListFragment_());
                        add(new EventListFragment_());
                    }
                });
        viewPager.setAdapter(eventPagerAdapter);

        pagerHeader.setViewPager(viewPager);

//        pagerHeader.setOnTouchListener(applier.listener);
//        pagerHeader.setToDispatch(findViewById(R.id.TopPaneEventList));
//        pagerHeader.requestDisallowInterceptTouchEvent(true);
    }


}
