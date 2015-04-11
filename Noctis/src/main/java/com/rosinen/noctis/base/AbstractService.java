package com.rosinen.noctis.base;

import de.greenrobot.event.EventBus;

/**
 * Created by Simon on 23.03.2015.
 */
public class AbstractService {

    protected EventBus mEventBus = EventBus.getDefault();

    public void onStart(){
        mEventBus.registerSticky(this);
    }

    public void onStop() {
        mEventBus.unregister(this);
    }


}
