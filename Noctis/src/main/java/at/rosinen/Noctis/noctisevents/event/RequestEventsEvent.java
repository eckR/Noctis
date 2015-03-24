package at.rosinen.Noctis.noctisevents.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 24.03.2015.
 */
public class RequestEventsEvent {

    public final LatLng coordinate;
    public final int radius;
    public final int day;

    public RequestEventsEvent(LatLng coordinate, int radius, int day) {
        this.coordinate = coordinate;
        this.radius = radius;
        this.day = day;
    }
}
