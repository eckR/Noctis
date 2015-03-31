package at.rosinen.Noctis.activity;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.View.Slider.SlidingUpPanelApplier;
import at.rosinen.Noctis.activity.event.*;
import at.rosinen.Noctis.base.ServiceHandler;
import at.rosinen.Noctis.login.LoginFragement_;
import at.rosinen.Noctis.map.MapEventBus;
import at.rosinen.Noctis.map.event.ChangeBottomPaddingMapEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends FragmentActivity {

    private EventBus mEventBus = EventBus.getDefault();

    private SlidingUpPanelApplier applier;

    private SlidingUpPanelApplier applierDetails;

    @Bean
    ServiceHandler serviceHandler;

    @ViewById
    View swipeUpPanel;

    @ViewById
    View loginFragment;

    @ViewById
    View dragHandleSwipeUp;

    @ViewById
    View fragmentBase;

    @ViewById
    View eventDetailSwipeUpPanel;

    @ViewById
    View evenDetailHandle;

    @Bean
    MapEventBus mapEventBus;

    /**
     * TODO this has to be properly aligned with the loginevent....!!!! This is a real performance issue
     */
    @AfterViews
    public void afterLoad() {
        mEventBus.post(new FragmentChangeEvent(new LoginFragement_(), false, R.id.loginFragment));

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
        //applierDetails.expand();


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
    }



    /**
     * TODO needs an update .. remove unnecessary framelayout
     * @param changeFragmentVisibilityEvent
     */
    public void onEventMainThread(final LoginNavigationEvent changeFragmentVisibilityEvent)
    {
        if(changeFragmentVisibilityEvent.show){
            loginFragment.setVisibility(View.VISIBLE);
        }
        else{
            loginFragment.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * start an intent with the context of the main activity
     * @param startActivityEvent
     */
    public void onEventMainThread(final StartActivityEvent startActivityEvent) {
        startActivity(startActivityEvent.intent);
    }

    /**
     * Show a Toast with a given message but don't have a context or don't want to worry about it
     * @param toastMeEvent
     */
    public void onEventMainThread(final ToastMeEvent toastMeEvent) {
        Toast.makeText(this,toastMeEvent.message, toastMeEvent.length).show();
    }

    /**
     * Show an alert dialog without a context
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
