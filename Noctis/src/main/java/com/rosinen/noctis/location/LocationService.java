package com.rosinen.noctis.location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.NoctisApplication;
import com.rosinen.noctis.activity.event.AlertDialogEvent;
import com.rosinen.noctis.activity.event.StartActivityEvent;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.base.SharedPreferences_;
import com.rosinen.noctis.location.event.FoundLocationEvent;
import com.rosinen.noctis.location.event.GoogleAPIClientEvent;
import com.rosinen.noctis.location.event.RequestLocationEvent;
import com.rosinen.noctis.map.MapEventBus;
import hugo.weaving.DebugLog;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.concurrent.TimeUnit;

/**
 * Created by Simon on 23.03.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocationService extends AbstractService implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = LocationService.class.getSimpleName();

    @Bean
    MapEventBus mapEventBus;

    @SystemService
    LocationManager locationManager;

    @Pref
    SharedPreferences_ sharedPref;

    private Context ctx;

    private GoogleApiClient mGoogleApiClient;


    @AfterInject
    public void initLocationService() {
        this.ctx = NoctisApplication.getContext();

        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.v(getClass().getCanonicalName(), "mGoogleApiClient connected");

        //TODO really call getLocation? maybe this is why it is called to often
        getLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(getClass().getCanonicalName(), "mGoogleApiClient connection failed" + connectionResult.toString());
        mEventBus.post(new ToastMeEvent(ctx.getString(R.string.connectionFailed), Toast.LENGTH_LONG));
    }

    @Override
    /**
     * Called when the client is temporarily in a disconnected state.
     */
    public void onConnectionSuspended(int i) {
        Log.v(getClass().getCanonicalName(), ctx.getString(R.string.connectionFailed) + i);
    }


    private void fireNoGpsAlertDialogEvent() {
        AlertDialogEvent alertDialogEvent = mEventBus.getStickyEvent(AlertDialogEvent.class);
        String title = ctx.getString(R.string.locatingError);
        if (alertDialogEvent != null && alertDialogEvent.title.equals(title)) {
            mEventBus.post(new ToastMeEvent("GPS muss aktiviert werden", Toast.LENGTH_SHORT));
            return;
        }
        mEventBus.postSticky(new AlertDialogEvent(
                title,
                ctx.getString(R.string.noGpsAskToEnable),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEventBus.post(new StartActivityEvent(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
                    }
                }
        ));
    }


    public void onEvent(RequestLocationEvent requestLocationEvent) {
        getLocation();
    }

    /**
     * //TODo implement as stategy loop through every strategy and if non works fire event to ask for gps
     */
    @Background
    void getLocation() {


        LatLng location = getLastLocation();

        switch (0) {
            case 0: {
                location = getLastLocation();
                if (location != null) {
                    break;
                }
            }//fall through
            case 1: {
                location = getLocationFromStickyEvent();
                if (location != null) {
                    break;
                }
            }//fall through
            case 2: {
                location = getLocationFromSharedPrefFile();
                if (location != null) {
                    break;
                }
            }//fall through
            case 3: {

                if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || //TODO implement location request
                        !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    fireNoGpsAlertDialogEvent();
                    break;
                }
                prepareRequestLocationUpdates();
            }
        }
        if (location != null) {
            storeLocationInSharedPrefFile(location);

            mapEventBus.getEventBus().postSticky(new FoundLocationEvent(location));
            mEventBus.postSticky(new FoundLocationEvent(location));
        }
    }


    /**
     * prepare request Location update
     */
    private void prepareRequestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(600000); //don't ever call twice to save battery;)
        locationRequest.setFastestInterval(10000);

        mGoogleApiClient.blockingConnect(2, TimeUnit.MINUTES);

        requestLocationUpdates(locationRequest);
    }

    /**
     * actually register for location updates
     *
     * @param locationRequest
     */
    @UiThread
    void requestLocationUpdates(LocationRequest locationRequest) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, locationRequest, this);
    }

    /**
     * get last foundlocationEvent form eventbus and use that location
     *
     * @return
     */
    private LatLng getLocationFromStickyEvent() {

        FoundLocationEvent foundLocationEvent = mEventBus.getStickyEvent(FoundLocationEvent.class);
        if (foundLocationEvent != null) {
            return foundLocationEvent.coordinate;
        }
        return null;
    }

    /**
     * get location form fusedlocationapi
     *
     * @return
     */
    private LatLng getLastLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        LatLng latLng = null;
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return latLng;
    }

    /**
     * store the aquired location to the shared pref file to have a fallback method for location aquireing instead of
     * turning on the gps
     *
     * @param location
     */
    private void storeLocationInSharedPrefFile(final LatLng location) {
        sharedPref.longitude().put((float) location.longitude);
        sharedPref.latitude().put((float) location.latitude);
    }

    /**
     * @return
     */
    private LatLng getLocationFromSharedPrefFile() {
        if (sharedPref.latitude().exists() && sharedPref.longitude().exists()) {
            return new LatLng(sharedPref.latitude().get(), sharedPref.longitude().get());
        }
        return null;
    }

    /**
     * This method has to be called in the onStart and onStop methods of the calling object
     * in order to fulfill the needs of the googleapiclient
     *
     * @param googleAPIClientEvent
     */
    public void onEventMainThread(final GoogleAPIClientEvent googleAPIClientEvent) {
        switch (googleAPIClientEvent.action) {
            case CONNECT:
                mGoogleApiClient.connect();
                break;
            case DISCONNECT:
                mGoogleApiClient.disconnect();
                break;
        }
    }


    @DebugLog
    @Override
    public void onLocationChanged(Location location) {
        if(location == null){
            Log.d(TAG, "onLocationChanged: location was null");
            return;
        }
        mapEventBus.getEventBus().postSticky(new FoundLocationEvent(new LatLng(location.getLatitude(), location.getLongitude())));
        mEventBus.postSticky(new FoundLocationEvent(new LatLng(location.getLatitude(), location.getLongitude())));

        mEventBus.post(new ToastMeEvent("Das GPS kann nun wieder abgeschalten werden",Toast.LENGTH_SHORT));
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }
}
