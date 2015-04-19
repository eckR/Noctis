package com.rosinen.noctis.map.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 22.03.2015.
 */
public class MoveAndZoomToLocationEvent {

    public static final int ZOOM_DEFAULT = 12;
    public static final int ZOOM_TO_EVENT = 16;

    public final LatLng coordinate;
    public final int zoom;

    public MoveAndZoomToLocationEvent(LatLng coordinate, int zoom) {
        this.coordinate = coordinate;
        this.zoom = zoom;
    }
}
