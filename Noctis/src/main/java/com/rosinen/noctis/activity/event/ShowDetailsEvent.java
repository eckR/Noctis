package com.rosinen.noctis.activity.event;

import com.rosinen.noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by harald on 3/28/15.
 */
public class ShowDetailsEvent {
    private List<NoctisEvent> events;
    private int clickedPosition;

    public ShowDetailsEvent(List<NoctisEvent> events, int clickedPosition) {
        this.events = events;
        this.clickedPosition = clickedPosition;
    }

    public int getClickedPosition() {
        return this.clickedPosition;
    }

    public List<NoctisEvent> getEvents() {
        return this.events;
    }
}
