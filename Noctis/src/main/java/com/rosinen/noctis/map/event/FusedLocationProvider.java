package com.rosinen.noctis.map.event;

import android.location.Location;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.rosinen.noctis.map.LocationFinderStrategy;

/**
 * Created by Simon on 11.04.2015.
 */
public class FusedLocationProvider implements LocationFinderStrategy {


    @Override
    public LatLng getLocation(GoogleApiClient mGoogleApiClient) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (lastLocation != null) {
            return new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        }
        return null;
    }
}
