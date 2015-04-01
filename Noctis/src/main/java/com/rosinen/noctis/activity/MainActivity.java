package com.rosinen.noctis.activity;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Slider.SlidingUpPanelApplier;
import com.rosinen.noctis.base.ServiceHandler;
import com.rosinen.noctis.login.LoginFragement_;
import com.rosinen.noctis.map.MapEventBus;
import com.rosinen.noctis.map.event.ChangeBottomPaddingMapEvent;
import com.rosinen.noctis.activity.event.*;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private EventBus mEventBus = EventBus.getDefault();

    private SlidingUpPanelApplier applier;

    private SlidingUpPanelApplier applierDetails;

    @Bean
    ServiceHandler serviceHandler;

    @Bean
    MapEventBus mapEventBus;

    @ViewById
    View fragmentBase;

    @ViewById
    View swipeUpPanel;

    @ViewById
    View dragHandleSwipeUp;

    @ViewById
    View eventDetailSwipeUpPanel;

    @ViewById
    View evenDetailHandle;

    /**
     * TODO this has to be properly aligned with the loginevent....!!!! This is a real performance issue
     */
    @AfterViews
    public void afterLoad() {
        mEventBus.post(new FragmentChangeEvent(new LoginFragement_(), false, R.id.fragmentBase));

        applierDetails = new SlidingUpPanelApplier(eventDetailSwipeUpPanel,
                evenDetailHandle, 0, eventDetailSwipeUpPanel.getLayoutParams().height, this) {
            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        };
        applierDetails.collapse();
//        applierDetails.expand();

        applier = new SlidingUpPanelApplier(swipeUpPanel, dragHandleSwipeUp, this) {
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

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
        serviceHandler.startServices();
        afterLoad();
    }

    /**
     * Change a given fragment and replace it with the one from the event
     * choose in what layout this has to be placed and whether to add it on the
     * backstack or not
     *
     * @param fragmentChangeEvent
     */
    public void onEventMainThread(FragmentChangeEvent fragmentChangeEvent) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.overshoot);
//        ft.setCustomAnimations()
//                loginFragment.startAnimation(animation);
//        ft.setCustomAnimations(R.anim.overshoot,R.anim.accelerate);

        ft.replace(fragmentChangeEvent.placeholderFragmentId, fragmentChangeEvent.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragmentChangeEvent.addToBackstack) {
            ft.addToBackStack(fragmentChangeEvent.fragment.getClass().getName());
        }
        ft.commit();
    }


    /**
     * TODO needs an update .. remove unnecessary framelayout
     *
     * @param loginNavigationEvent
     */
    public void onEventMainThread(final LoginNavigationEvent loginNavigationEvent) {
        swipeUpPanel.setVisibility(View.VISIBLE);
        dragHandleSwipeUp.setVisibility(View.VISIBLE);
        eventDetailSwipeUpPanel.setVisibility(View.VISIBLE);
        evenDetailHandle.setVisibility(View.VISIBLE);
    }

    /**
     * start an intent with the context of the main activity
     *
     * @param startActivityEvent
     */
    public void onEventMainThread(final StartActivityEvent startActivityEvent) {
        startActivity(startActivityEvent.intent);
    }

    /**
     * Show a Toast with a given message but don't have a context or don't want to worry about it
     *
     * @param toastMeEvent
     */
    public void onEventMainThread(final ToastMeEvent toastMeEvent) {
        Toast.makeText(this, toastMeEvent.message, toastMeEvent.length).show();
    }

    /**
     * Show an alert dialog without a context
     *
     * @param alertDialogEvent
     */
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

    /**
     * TODO in my opinion (simon) this has to be done an other way .. namly change the fragment on the
     * TODO eventDetailSwipeUpPanel and apply a proper animation
     *
     * @param event
     */
    public void onEvent(final ShowDetailsEvent event) {
        applierDetails.expand();
        Log.d("EVENT", "SHOW DETAILS");
    }


    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
        serviceHandler.stopServices();
    }
}
