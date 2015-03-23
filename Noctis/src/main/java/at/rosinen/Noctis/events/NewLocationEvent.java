package at.rosinen.Noctis.events;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 22.03.2015.
 * Is used to report a location update of the device
 */
public class NewLocationEvent {

    public final LatLng coordinate;


    public NewLocationEvent(LatLng coordinate) {
        this.coordinate = coordinate;
    }


}
