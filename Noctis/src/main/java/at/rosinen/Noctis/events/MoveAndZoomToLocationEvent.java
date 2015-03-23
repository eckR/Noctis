package at.rosinen.Noctis.events;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 22.03.2015.
 */
public class MoveAndZoomToLocationEvent {

    public final LatLng coordinate;
    public final int zoom;

    public MoveAndZoomToLocationEvent(LatLng coordinate, int zoom) {
        this.coordinate = coordinate;
        this.zoom = zoom;
    }
}
