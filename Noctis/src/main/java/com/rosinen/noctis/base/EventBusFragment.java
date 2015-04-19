package com.rosinen.noctis.base;

import android.support.v4.app.Fragment;
import android.util.Log;
import de.greenrobot.event.EventBus;

/**
 * Created by Simon on 23.03.2015.
 */
public class EventBusFragment extends Fragment {

    protected EventBus mEventBus = EventBus.getDefault();

    private static final String TAG = EventBusFragment.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
//        mEventBus.register(this);
        Log.d(TAG,"starting fragment: " + this.getClass().getSimpleName());
        mEventBus.registerSticky(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }


}
