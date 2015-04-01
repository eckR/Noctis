package com.rosinen.noctis.noctisevents.event;

import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 25.03.2015.
 */
public class ImageDownloadAvailableEvent {

    public final NoctisEvent event;
    public final ImageDownloadTarget target;
    public final int day;

    public ImageDownloadAvailableEvent(NoctisEvent event, ImageDownloadTarget target, int day) {
        this.event = event;
        this.target = target;
        this.day = day;
    }
}
