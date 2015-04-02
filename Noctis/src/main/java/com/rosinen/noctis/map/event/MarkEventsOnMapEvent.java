package com.rosinen.noctis.map.event;

import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by Simon on 22.03.2015.
 * Used to deliver Events that should be displayed on the Map
 */
public class MarkEventsOnMapEvent {

    private static final String TAG = MarkEventsOnMapEvent.class.getSimpleName();

    public final List<NoctisEvent> events;
    public final int day;

    public MarkEventsOnMapEvent(List<NoctisEvent> events, int day) {
        this.events = events;
        this.day = day;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "MarkEventsOnMapEvent{" +
                "events=" + events +
                ", day=" + day +
                '}';
    }
}
