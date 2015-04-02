package com.rosinen.noctis.map.event;

import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 25.03.2015.
 */
public class RequestMarkerBitmapEvent {

    private static final String TAG = RequestMarkerBitmapEvent.class.getSimpleName();

    public final NoctisEvent noctisEvent;
    public final int day;

    public RequestMarkerBitmapEvent(NoctisEvent noctisEvent, int day) {
        this.noctisEvent = noctisEvent;
        this.day = day;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "RequestMarkerBitmapEvent{" +
                "noctisEvent=" + noctisEvent +
                ", day=" + day +
                '}';
    }
}
