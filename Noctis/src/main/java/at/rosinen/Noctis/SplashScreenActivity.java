package at.rosinen.Noctis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import android.widget.Toast;
import at.rosinen.Noctis.Service.ServiceHandler;
import at.rosinen.Noctis.View.EventpagerFragment_;
import at.rosinen.Noctis.View.Maps.MapsFragment_;
import at.rosinen.Noctis.View.Slider.SlidingUpPanelApplier;
import at.rosinen.Noctis.events.AlertDialogEvent;
import at.rosinen.Noctis.events.FragmentChangeEvent;
import at.rosinen.Noctis.events.StartActivityEvent;
import at.rosinen.Noctis.events.ToastMeEvent;
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

    @AfterInject
    public void afterInject() {
//        EventBus.getDefault().register(this);
    }

    @AfterViews
    public void afterLoad() {
        mEventBus.post(new FragmentChangeEvent(new MapsFragment_(), false, R.id.fragmentBase));
        mEventBus.post(new FragmentChangeEvent(new EventpagerFragment_(), false, R.id.swipeUpPanel));

        new SlidingUpPanelApplier(swipeUpPanel, dragHandleSwipeUp, this) {
            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        };

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
