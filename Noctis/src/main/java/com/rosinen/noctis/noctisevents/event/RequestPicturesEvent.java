package com.rosinen.noctis.noctisevents.event;

import com.rosinen.noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by Simon on 31.03.2015.
 */
public class RequestPicturesEvent {
    public final List<NoctisEvent> events;
    public final int day;

    public RequestPicturesEvent(List<NoctisEvent> events, int day) {
        this.events = events;
        this.day = day;
    }
}
