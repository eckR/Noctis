package at.rosinen.Noctis.noctisevents.event;

import at.rosinen.Noctis.Model.NoctisEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Simon on 24.03.2015.
 */
public class NoctisEventsAvailableEvent {

    public final RequestEventsEvent requestEventsEvent;
    public final List<NoctisEvent> eventList;

    public NoctisEventsAvailableEvent(RequestEventsEvent requestEventsEvent, List<NoctisEvent> eventList) {
        this.requestEventsEvent = requestEventsEvent;
        this.eventList = eventList;
    }
}
