package at.rosinen.Noctis.noctisevents.event;

import at.rosinen.Noctis.Model.NoctisEvent;

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
