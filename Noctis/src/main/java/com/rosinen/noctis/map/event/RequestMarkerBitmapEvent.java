package com.rosinen.noctis.map.event;

import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 25.03.2015.
 */
public class RequestMarkerBitmapEvent {
    public final NoctisEvent noctisEvent;

    public RequestMarkerBitmapEvent(NoctisEvent noctisEvent) {
        this.noctisEvent = noctisEvent;
    }
}
