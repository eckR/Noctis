package com.rosinen.noctis.noctisevents.event;

import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by Simon on 24.03.2015.
 */
public class NoctisEventsAvailableEvent {

    private static final String TAG = NoctisEventsAvailableEvent.class.getSimpleName();

    public final RequestEventsEvent requestEventsEvent;
    public final List<NoctisEvent> eventList;

    public NoctisEventsAvailableEvent(RequestEventsEvent requestEventsEvent, List<NoctisEvent> eventList) {
        this.requestEventsEvent = requestEventsEvent;
        this.eventList = eventList;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "NoctisEventsAvailableEvent{" +
                "requestEventsEvent=" + requestEventsEvent +
                ", eventList=" + eventList +
                '}';
    }
}
