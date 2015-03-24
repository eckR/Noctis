package at.rosinen.Noctis.activity;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import android.widget.Toast;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.base.ServiceHandler;
import at.rosinen.Noctis.View.Slider.SlidingUpPanelApplier;
import at.rosinen.Noctis.activity.event.AlertDialogEvent;
import at.rosinen.Noctis.activity.event.FragmentChangeEvent;
import at.rosinen.Noctis.activity.event.StartActivityEvent;
import at.rosinen.Noctis.activity.event.ToastMeEvent;
import at.rosinen.Noctis.eventoverview.EventpagerFragment_;
import at.rosinen.Noctis.map.MapEventBus;
import at.rosinen.Noctis.map.MapsFragment_;
import at.rosinen.Noctis.map.event.ChangeBottomPaddingMapEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends FragmentActivity {

    private EventBus mEventBus = EventBus.getDefault();

    @Bean
    ServiceHandler serviceHandler;

    @ViewById
    View swipeUpPanel;

    @ViewById
    View dragHandleSwipeUp;

    @ViewById
    View fragmentBase;

    @Bean
    MapEventBus mapEventBus;

    @AfterInject
    public void afterInject() {
//        EventBus.getDefault().register(this);
    }

    @AfterViews
    public void afterLoad() {
        mEventBus.post(new FragmentChangeEvent(new MapsFragment_(), false, R.id.fragmentBase));
        mEventBus.post(new FragmentChangeEvent(new EventpagerFragment_(), false, R.id.swipeUpPanel));


        SlidingUpPanelApplier applier = new SlidingUpPanelApplier(swipeUpPanel, dragHandleSwipeUp, this) {
            @Override
            public void onExpand() {
                ChangeBottomPaddingMapEvent event = new ChangeBottomPaddingMapEvent(getMaxHeight());
                mapEventBus.getEventBus().post(event);
            }

            @Override
            public void onCollapse() {
                ChangeBottomPaddingMapEvent event = new ChangeBottomPaddingMapEvent(getMinHeight());
                mapEventBus.getEventBus().post(event);
            }
        };
        mapEventBus.getEventBus().post(new ChangeBottomPaddingMapEvent(applier.getMaxHeight()));

    }

    /**
     * @param fragmentChangeEvent
     */
    public void onEventMainThread(FragmentChangeEvent fragmentChangeEvent) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(fragmentChangeEvent.placeholderFragmentId, fragmentChangeEvent.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragmentChangeEvent.addToBackstack) {
            ft.addToBackStack(fragmentChangeEvent.fragment.getClass().getName());
        }
        ft.commit();

//        dragHandleSwipeUp.bringToFront();
//        swipeUpPanel.bringToFront();
//        swipeUpPanel.invalidate();
    }

    public void onEventMainThread(final AlertDialogEvent alertDialogEvent) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertDialogEvent.title)
                .setMessage(alertDialogEvent.message)
                .setCancelable(false)
                .setPositiveButton(alertDialogEvent.yesString, alertDialogEvent.onYesListener)
                .setNegativeButton(alertDialogEvent.noString, alertDialogEvent.onNoListener);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onEventMainThread(final StartActivityEvent startActivityEvent) {
        startActivity(startActivityEvent.intent);
    }

    public void onEventMainThread(final ToastMeEvent toastMeEvent) {
        Toast.makeText(this,toastMeEvent.message, toastMeEvent.length).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
        serviceHandler.startServices();
        afterLoad();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
        serviceHandler.stopServices();
    }
}
