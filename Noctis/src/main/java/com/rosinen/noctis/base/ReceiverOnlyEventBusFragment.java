package com.rosinen.noctis.base;

import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

public class ReceiverOnlyEventBusFragment extends Fragment {

    protected EventBus mEventBus = EventBus.getDefault();



}
