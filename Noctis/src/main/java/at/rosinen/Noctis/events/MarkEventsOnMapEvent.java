package at.rosinen.Noctis.events;

import at.rosinen.Noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by Simon on 22.03.2015.
 * Used to deliver Events that should be displayed on the Map
 */
public class MarkEventsOnMapEvent {

    public final List<NoctisEvent> events;

    public MarkEventsOnMapEvent(List<NoctisEvent> events) {
        this.events = events;
    }
}
