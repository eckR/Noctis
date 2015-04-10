package com.rosinen.noctis.map;

import com.google.android.gms.maps.model.LatLng;
import com.rosinen.noctis.base.SharedPreferences_;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Simon on 11.04.2015.
 */
public class LocationFromPrefFile implements LocationFinderStrategy {
    @Pref
    SharedPreferences_ sharedPreferences;
    @Override
    public LatLng getLocation() {
        return new LatLng(sharedPreferences.latitude().get(),sharedPreferences.longitude().get());
    }
}
