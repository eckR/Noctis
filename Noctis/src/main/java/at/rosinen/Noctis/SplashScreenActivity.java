package at.rosinen.Noctis;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import at.rosinen.Noctis.View.Slider.SlidingUpPanelApplier;
import at.rosinen.Noctis.events.FragmentChangeEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends FragmentActivity {


    @ViewById
    View swipeUpPanel;

    @ViewById
    View dragHandleSwipeUp;


    @AfterInject
    public void afterInject() {
        EventBus.getDefault().register(this);

    }

    @AfterViews
    public void afterLoad(){
        EventBus.getDefault().post(new FragmentChangeEvent(new EventListFragment_(), false, R.id.fragementBase));

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
    }

//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//
//    }
}
