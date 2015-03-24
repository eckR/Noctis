package at.rosinen.Noctis.location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import at.rosinen.Noctis.activity.NoctisApplication;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.base.AbstractService;
import at.rosinen.Noctis.activity.event.AlertDialogEvent;
import at.rosinen.Noctis.activity.event.StartActivityEvent;
import at.rosinen.Noctis.activity.event.ToastMeEvent;
import at.rosinen.Noctis.location.event.GoogleAPIClientEvent;
import at.rosinen.Noctis.location.event.NewLocationEvent;
import at.rosinen.Noctis.location.event.RequestLocationEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

/**
 * Created by Simon on 23.03.2015.
 */
@EBean
public class LocationService extends AbstractService implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @SystemService
    LocationManager locationManager;

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
            mEventBus.postSticky(new NewLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
        }else{
            getLocation();
        }
    }

    /**
     *
     */
    private void getLocation(){
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (lastLocation != null) {

            if(mLastLocation == null || mLastLocation.distanceTo(lastLocation) > 1){
                mLastLocation = lastLocation;
            }


            if(mRequestedLocationUpdate){
                mRequestedLocationUpdate = false;
                mEventBus.postSticky(new NewLocationEvent(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude() ) ));
            }


        } else if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
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
