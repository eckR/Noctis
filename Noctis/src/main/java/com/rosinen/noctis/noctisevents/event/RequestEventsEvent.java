package com.rosinen.noctis.noctisevents.event;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 24.03.2015.
 */
public class RequestEventsEvent {

    private static final String TAG = RequestEventsEvent.class.getSimpleName();

    public final LatLng coordinate;
    public final int radius;
    public final int day;

    public RequestEventsEvent(LatLng coordinate, int radius, int day) {
        this.coordinate = coordinate;
        this.radius = radius;
        this.day = day;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "RequestEventsEvent{" +
                "coordinate=" + coordinate +
                ", radius=" + radius +
                ", day=" + day +
                '}';
    }
}
