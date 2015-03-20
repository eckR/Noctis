package at.rosinen.Noctis;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import at.rosinen.Noctis.events.FragmentChangeEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends FragmentActivity {

    @AfterInject
    public void afterInject() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new FragmentChangeEvent(new LoginFragement_(), false));
    }

    public void onEventMainThread(FragmentChangeEvent fragmentChangeEvent) {
        this.getSupportFragmentManager();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragementBase, fragmentChangeEvent.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragmentChangeEvent.addToBackstack) {
            ft.addToBackStack(fragmentChangeEvent.fragment.getClass().getName());
        }
        ft.commit();
    }
}
