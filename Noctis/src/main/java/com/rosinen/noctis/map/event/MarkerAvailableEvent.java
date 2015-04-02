package com.rosinen.noctis.map.event;

import android.graphics.Bitmap;
import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 28.03.2015.
 */
public class MarkerAvailableEvent {

    private static final String TAG = MarkerAvailableEvent.class.getSimpleName();

    public final NoctisEvent noctisEvent;
    public final Bitmap markerBitmap;
    public final int day;


    public MarkerAvailableEvent(NoctisEvent noctisEvent, Bitmap markerBitmap, int day) {
        this.noctisEvent = noctisEvent;

        this.markerBitmap = markerBitmap;
        this.day = day;
        Log.i(TAG,toString());
    }

    @Override
    public String toString() {
        return "MarkerAvailableEvent{" +
                "noctisEvent=" + noctisEvent +
                ", markerBitmap=" + markerBitmap +
                ", day=" + day +
                '}';
    }
}
