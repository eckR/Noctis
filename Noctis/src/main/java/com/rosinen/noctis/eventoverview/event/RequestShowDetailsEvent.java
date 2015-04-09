package com.rosinen.noctis.eventoverview.event;

import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 09.04.2015.
 */
public class RequestShowDetailsEvent {
    public final NoctisEvent event;

    public RequestShowDetailsEvent(NoctisEvent event) {
        this.event = event;
    }
}
