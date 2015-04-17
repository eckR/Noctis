package com.rosinen.noctis.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private EventBus mEventBus = EventBus.getDefault();

    //private SlidingUpPanelApplier applier;

    //private SlidingUpPanelApplier applierDetails;

    @Bean
    ServiceHandler serviceHandler;

    @Bean
    MapEventBus mapEventBus;

    @ViewById
    View fragmentBase;

    @ViewById
    View swipeUpPanel;

    @ViewById
    SlidingUpPanelLayout slidingUpPanelLayout;

//    @ViewById
//    View dragHandleSwipeUp;
//
//    @ViewById
//    View eventDetailSwipeUpPanel;

    @ViewById
    View loginFragment;
//
//    @ViewById
//    View evenDetailHandle;

    @Pref
    SharedPreferences_ sharedPrefs;

    Animation loginAnimation;



    Fragment mapsFragment = new MapsFragment_();
    Fragment eventpagerFragment = new EventpagerFragment_();
    //Fragment eventDetailPagerFragment = new EventDetailPagerFragment_();


    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus.register(this);

        loginAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_fade_out);
        loginAnimation.setAnimationListener(new Animation.AnimationListener() {
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
    }

    /**
     *
     */
    @DebugLog
    @AfterViews
    public void afterLoad() {
        //TODO @david set loggedIn status to true
        // never logged in since the app started && never logged in via fb
        // if either one, the shared pref file or the sticky event equals to not skip the loginscreen
        // shared pref = show login
        // sticky event = null -> show login
        // if an event has already been sent ( means the app has been started before ) skip it

        boolean isEventAvailable = mEventBus.getStickyEvent(LoginNavigationEvent.class) != null;


        if (!isEventAvailable | sharedPrefs.showLoginScreen().get()) {
            onEventMainThread(new FragmentChangeEvent(new LoginFragement_(), false, R.id.loginFragment, loginFragment));
        }else {
            loginFragment.setVisibility(View.GONE);
        }

        onEventMainThread(new FragmentChangeEvent(mapsFragment, false, R.id.fragmentBase));

        onEventMainThread(new FragmentChangeEvent(eventpagerFragment, false, R.id.swipeUpPanel, swipeUpPanel));


        slidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelCollapsed(View view) {
                int height = slidingUpPanelLayout.getPanelHeight();
                ChangeBottomPaddingMapEvent event = new ChangeBottomPaddingMapEvent(height);
                mapEventBus.getEventBus().post(event);
            }

            @Override
            public void onPanelExpanded(View view) {
                int height = swipeUpPanel.getLayoutParams().height;
                ChangeBottomPaddingMapEvent event = new ChangeBottomPaddingMapEvent(height);
                mapEventBus.getEventBus().post(event);
            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });



        changePadding();

    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceHandler.startServices();
    }

    @UiThread
    void changePadding() {

        int height = slidingUpPanelLayout.getPanelHeight();
        mapEventBus.getEventBus().postSticky(new ChangeBottomPaddingMapEvent(slidingUpPanelLayout.getPanelHeight()));
    }

    /**
     * Change a given fragment and replace it with the one from the event
     * choose in what layout this has to be placed and whether to add it on the
     * backstack or not
     *
     * @param fragmentChangeEvent
     */
    @DebugLog
    public void onEventMainThread(final FragmentChangeEvent fragmentChangeEvent) {
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

    public void onEventMainThread(final SliderDragViewSetterEvent sliderDragViewSetterEvent) {
        slidingUpPanelLayout.setDragView(sliderDragViewSetterEvent.getDragView());
    }

    /**
     * TODO needs an update .. remove unnecessary framelayout
     *
     * @param loginNavigationEvent
     */
    @DebugLog
    public void onEvent(final LoginNavigationEvent loginNavigationEvent) {
        fadeLoginFragment();
    }

    /**
     * start login fragment animation on ui thread
     */
    @DebugLog
    @UiThread
    void fadeLoginFragment() {
        Log.i(TAG, "Start login fragment animation");
        loginFragment.startAnimation(loginAnimation);
    }


    /**
     * start an intent with the context of the main activity
     *
     * @param startActivityEvent
     */
    @DebugLog
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
    @DebugLog
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

//    /**
//     * TODO in my opinion (simon) this has to be done an other way .. namly change the fragment on the
//     * TODO eventDetailSwipeUpPanel and apply a proper animation
//     *
//     * @param event
//     */
//    @DebugLog
//    public void onEventMainThread(final ShowDetailsEvent event) {
        //applierDetails.expand();
//        showingDetails = true;
//        Log.d(TAG, "SHOW DETAILS");
//    }


//    boolean showingDetails = false;
    //TODO implement onBackPressed for the overview to collapse
    //@DebugLog
//    @Override
//    public void onBackPressed() {
//        Log.d(TAG, "onBackPressed");
//        if (showingDetails) {
//            //applierDetails.collapse();
//            showingDetails = false;
//        } else {
//            super.onBackPressed();
//        }
//
//    }

    @DebugLog
    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
        serviceHandler.stopServices();
    }
}
