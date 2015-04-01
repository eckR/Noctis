package com.rosinen.noctis.location.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 22.03.2015.
 * Is used to report a location update of the device
 */
public class FoundLocationEvent {

    public final LatLng coordinate;


    public FoundLocationEvent(LatLng coordinate) {
        this.coordinate = coordinate;
    }


}
