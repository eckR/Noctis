package com.rosinen.noctis.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Simon on 11.04.2015.
 */
public interface LocationFinderStrategy {

    LatLng getLocation();
}
