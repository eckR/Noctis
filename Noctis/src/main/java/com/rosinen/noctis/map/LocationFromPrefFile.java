package com.rosinen.noctis.map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.rosinen.noctis.base.SharedPreferences_;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Simon on 11.04.2015.
 */
@EBean
public class LocationFromPrefFile implements LocationFinderStrategy {
    @Pref
    SharedPreferences_ sharedPreferences;

    @Override
    public LatLng getLocation(GoogleApiClient mGoogleApiClient) {
        if (sharedPreferences.latitude().exists() && sharedPreferences.longitude().exists()) {
            return new LatLng(sharedPreferences.latitude().get(), sharedPreferences.longitude().get());
        }
        return null;

    }
}
