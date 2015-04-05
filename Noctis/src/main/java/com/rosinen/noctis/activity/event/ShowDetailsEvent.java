package com.rosinen.noctis.activity.event;

import com.rosinen.noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by harald on 3/28/15.
 */
public class ShowDetailsEvent {
    private List<NoctisEvent> events;
    private int clickedPosition;
    private int day;

    public ShowDetailsEvent(List<NoctisEvent> events, int clickedPosition, int day) {
        this.events = events;
        this.clickedPosition = clickedPosition;
        this.day = day;
    }

    public int getClickedPosition() {
        return this.clickedPosition;
    }

    public List<NoctisEvent> getEvents() {
        return this.events;
    }

    public int getDay() {
        return this.day;
    }
}
