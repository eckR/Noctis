package at.rosinen.Noctis;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import at.rosinen.Noctis.View.EventpagerFragment;
import at.rosinen.Noctis.View.EventpagerFragment_;
import at.rosinen.Noctis.View.Maps.MapsFragment_;
import at.rosinen.Noctis.View.Slider.SlidingUpPanelApplier;
import at.rosinen.Noctis.events.FragmentChangeEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends FragmentActivity {


    @ViewById
    View swipeUpPanel;

    @ViewById
    View dragHandleSwipeUp;

    @ViewById
    View fragmentBase;

    @AfterInject
    public void afterInject() {
        EventBus.getDefault().register(this);

    }

    @AfterViews
    public void afterLoad(){
        EventBus.getDefault().post(new FragmentChangeEvent(new MapsFragment_(), false, R.id.fragmentBase));
        EventBus.getDefault().post(new FragmentChangeEvent(new EventpagerFragment_(), false, R.id.swipeUpPanel));

        new SlidingUpPanelApplier(swipeUpPanel, dragHandleSwipeUp, this) {
            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        };

    }

    public void onEventMainThread(FragmentChangeEvent fragmentChangeEvent) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(fragmentChangeEvent.placeholderFragmentId, fragmentChangeEvent.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragmentChangeEvent.addToBackstack) {
            ft.addToBackStack(fragmentChangeEvent.fragment.getClass().getName());
        }
        ft.commit();

        dragHandleSwipeUp.bringToFront();
        swipeUpPanel.bringToFront();
        swipeUpPanel.invalidate();
    }
//void test(){
//    OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
//    int mediumAnimTime = 10;
//
//    int tY = 52;
//
//
//}
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//
//    }
}
