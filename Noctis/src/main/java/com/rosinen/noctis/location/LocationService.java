package com.rosinen.noctis.location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.NoctisApplication;
import com.rosinen.noctis.activity.event.AlertDialogEvent;
import com.rosinen.noctis.activity.event.StartActivityEvent;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.base.SharedPreferences_;
import com.rosinen.noctis.location.event.GoogleAPIClientEvent;
import com.rosinen.noctis.location.event.FoundLocationEvent;
import com.rosinen.noctis.location.event.RequestLocationEvent;
import com.rosinen.noctis.map.MapEventBus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Simon on 23.03.2015.
 */
@EBean
public class LocationService extends AbstractService implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bean
    MapEventBus mapEventBus;

    @SystemService
    LocationManager locationManager;

    @Pref
    SharedPreferences_ sharedPref;

    private Context ctx;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation = null;

    private boolean mRequestedLocationUpdate = false;


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
        mEventBus.post(new AlertDialogEvent(
                ctx.getString(R.string.locatingError),
                ctx.getString(R.string.noGpsAskToEnable),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEventBus.post(new StartActivityEvent(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
                    }
                }
        ));
    }



    public void onEvent(RequestLocationEvent requestLocationEvent){
        mRequestedLocationUpdate = true;
        if(mLastLocation != null){
            mRequestedLocationUpdate = false;
            mapEventBus.getEventBus().postSticky(new FoundLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
            mEventBus.postSticky(new FoundLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
        }else{
            getLocation();
        }
    }

    /**
     * //TODo implement as stategy loop through every strategy and if non works fire event to ask for gps
     */
    private void getLocation(){
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (lastLocation != null) {

            if(mLastLocation == null || mLastLocation.distanceTo(lastLocation) > 1){ //todo check distance

                mLastLocation = lastLocation;
            }


            if(mRequestedLocationUpdate){
                mRequestedLocationUpdate = false;
                mapEventBus.getEventBus().postSticky(new FoundLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                mEventBus.postSticky(new FoundLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));

                sharedPref.edit().latitude().put((float) mLastLocation.getLatitude()).apply();
                sharedPref.edit().longitude().put((float) mLastLocation.getLongitude()).apply();
            }
        }

        else if(sharedPref.latitude().exists() && sharedPref.longitude().exists()){
            mapEventBus.getEventBus().postSticky(new FoundLocationEvent(new LatLng(sharedPref.latitude().get(), sharedPref.longitude().get() ) ));
            mEventBus.postSticky(new FoundLocationEvent(new LatLng(sharedPref.latitude().get(), sharedPref.longitude().get())));
        }

        else if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || //TODO implement location request
                !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            fireNoGpsAlertDialogEvent();
        }
    }

    /**
     * This method has to be called in the onStart and onStop methods of the calling object
     * in order to fulfill the needs of the googleapiclient
     *
     * @param googleAPIClientEvent
     */
    public void onEventMainThread(GoogleAPIClientEvent googleAPIClientEvent) {
        switch (googleAPIClientEvent.action) {
            case CONNECT:
                mGoogleApiClient.connect();
                break;
            case DISCONNECT:
                mGoogleApiClient.disconnect();
                break;
        }
    }


}
