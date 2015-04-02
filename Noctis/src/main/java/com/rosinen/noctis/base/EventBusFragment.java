package com.rosinen.noctis.base;

import android.support.v4.app.Fragment;
import de.greenrobot.event.EventBus;

/**
 * Created by Simon on 23.03.2015.
 */
public class EventBusFragment extends Fragment {

    protected EventBus mEventBus = EventBus.getDefault();


    @Override
    public void onStart() {
        super.onStart();
//        mEventBus.register(this);

        mEventBus.registerSticky(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }


}
