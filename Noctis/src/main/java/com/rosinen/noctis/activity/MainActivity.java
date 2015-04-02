package com.rosinen.noctis.activity;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.rosinen.noctis.BuildConfig;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Slider.SlidingUpPanelApplier;
import com.rosinen.noctis.activity.event.*;
import com.rosinen.noctis.base.ServiceHandler;
import com.rosinen.noctis.base.SharedPreferences_;
import com.rosinen.noctis.eventdetail.EventDetailPagerFragment_;
import com.rosinen.noctis.eventoverview.EventpagerFragment_;
import com.rosinen.noctis.login.LoginFragement_;
import com.rosinen.noctis.map.MapEventBus;
import com.rosinen.noctis.map.MapsFragment_;
import com.rosinen.noctis.map.event.ChangeBottomPaddingMapEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private static String TAG = MainActivity.class.getSimpleName();

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
    View loginFragment;

    @ViewById
    View evenDetailHandle;

    @Pref
    SharedPreferences_ sharedPrefs;

    boolean showingDetails = false;
    /**
     *
     */
    @AfterViews
    public void afterLoad() {
        //TODO @david set loggedIn status to true
        if (!sharedPrefs.loggedIn().get()) { // never logged in or logged out again
            onEventMainThread(new FragmentChangeEvent(new LoginFragement_(), false, R.id.loginFragment));
        }

    }


    void applySlider() {
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
        applier.collapse();

        mapEventBus.getEventBus().post(new ChangeBottomPaddingMapEvent(applier.getMaxHeight()));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();

        mEventBus.register(this);
        serviceHandler.startServices();
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

        ft.setCustomAnimations(fragmentChangeEvent.inAnimationRes, fragmentChangeEvent.outAnimationRes);


        ft.replace(fragmentChangeEvent.placeholderFragmentId, fragmentChangeEvent.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragmentChangeEvent.addToBackstack) {
            ft.addToBackStack(fragmentChangeEvent.fragment.getClass().getName());
        }
        ft.commit();

        for (View view : fragmentChangeEvent.viewsToSetVisible) {
            view.setVisibility(View.VISIBLE);
        }
    }


    /**
     * TODO needs an update .. remove unnecessary framelayout
     *
     * @param loginNavigationEvent
     */
    public void onEventMainThread(final LoginNavigationEvent loginNavigationEvent) {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_fade_out);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loginFragment.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loginFragment.startAnimation(anim);
        Log.i(TAG, "Start mapsfragment");
        onEventMainThread(new FragmentChangeEvent(new MapsFragment_(), false, R.id.fragmentBase));

        Log.i(TAG, "apply slider");
        applySlider();
        onEventMainThread(new FragmentChangeEvent(new EventpagerFragment_(), false, R.id.swipeUpPanel, swipeUpPanel, dragHandleSwipeUp));
        onEventMainThread(new FragmentChangeEvent(new EventDetailPagerFragment_(), false, R.id.eventDetailSwipeUpPanel, eventDetailSwipeUpPanel,evenDetailHandle));

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
        showingDetails = true;
        Log.d("EVENT", "SHOW DETAILS");
    }



    @Override
    public void onBackPressed() {

        if (showingDetails) {
            applierDetails.collapse();
            showingDetails = false;
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
        serviceHandler.stopServices();
    }
}
