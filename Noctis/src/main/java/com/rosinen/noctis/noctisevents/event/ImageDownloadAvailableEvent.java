package com.rosinen.noctis.noctisevents.event;

import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 25.03.2015.
 */
public class ImageDownloadAvailableEvent {

    private static final String TAG = ImageDownloadAvailableEvent.class.getSimpleName();

    public final NoctisEvent event;
    public final ImageDownloadTarget target;
    public final int day;

    public ImageDownloadAvailableEvent(NoctisEvent event, ImageDownloadTarget target, int day) {
        this.event = event;
        this.target = target;
        this.day = day;
        Log.i(TAG, toString());
    }

    @Override
    public String toString() {
        return "ImageDownloadAvailableEvent{" +
                "event=" + event +
                ", target=" + target +
                ", day=" + day +
                '}';
    }
}
