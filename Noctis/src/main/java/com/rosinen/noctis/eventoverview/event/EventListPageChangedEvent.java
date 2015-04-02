package com.rosinen.noctis.eventoverview.event;

import android.util.Log;

/**
 * Created by Simon on 02.04.2015.
 */
public class EventListPageChangedEvent {
    private static final String TAG = EventListPageChangedEvent.class.getSimpleName();

    public final int page;

    public EventListPageChangedEvent(int page) {
        this.page = page;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "EventListPageChangedEvent{" +
                "page=" + page +
                '}';
    }
}
