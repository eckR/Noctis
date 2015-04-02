package com.rosinen.noctis.map;


import android.support.v4.app.Fragment;
import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.R;
import com.rosinen.noctis.eventoverview.event.EventListPageChangedEvent;
import com.rosinen.noctis.location.event.GoogleAPIClientEvent;
import com.rosinen.noctis.location.event.FoundLocationEvent;
import com.rosinen.noctis.location.event.RequestLocationEvent;
import com.rosinen.noctis.map.event.ChangeBottomPaddingMapEvent;
import com.rosinen.noctis.map.event.MarkEventsOnMapEvent;
import com.rosinen.noctis.map.event.MarkerAvailableEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.HashMap;


@EFragment(R.layout.fragment_maps)
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static String TAG = MapsFragment.class.getSimpleName();

    private HashMap<String,Marker> markerOptionsHashMap = new HashMap<String, Marker>();

    private static final int ZOOM_DEFAULT = 12;
    private EventBus mEventBus = EventBus.getDefault();
    private GoogleMap map;

    @Bean
    MapEventBus mapEventBus;


    /**
     * find the map
     */
    @AfterViews
    void afterViews() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gMap));
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.post(new GoogleAPIClientEvent(GoogleAPIClientEvent.Action.CONNECT));
    }

    /**
     * comming from the markerservice and delivers one single marker
     * @param markerAvailableEvent
     */
    public  void onEventMainThread(final MarkerAvailableEvent markerAvailableEvent){

        int page = mEventBus.getStickyEvent(EventListPageChangedEvent.class).page;

        if(markerAvailableEvent.day != page){
            return;
        }

        Marker marker = markerOptionsHashMap.get(markerAvailableEvent.noctisEvent.getKey());

        if (marker != null) {
            marker.remove();
        }
//        map.clear();
        //TODO put markerbitmap back into noctisevent else the memory will go missing nullptr excetions maybe occur
        addMarker(markerAvailableEvent.noctisEvent, BitmapDescriptorFactory.fromBitmap(markerAvailableEvent.markerBitmap));

    }

    /**
     * called from EventListfragment
     * mark with empty markers before the picture was fetched
     * called when sliding viewpager or the events are done fetching
     * @param event
     */
    public void onEventMainThread(final MarkEventsOnMapEvent event) {

        int page = mEventBus.getStickyEvent(EventListPageChangedEvent.class).page;

        if(event.day != page){
            return;
        }
        map.clear();
        markerOptionsHashMap.clear();

        for (NoctisEvent noctisEvent : event.events) {
           addMarker(noctisEvent, BitmapDescriptorFactory.fromResource(R.drawable.marker_mask_72));
        }
    }

    /**
     * add a marker for the given event and add it to the hashmap for replacement
     * @param noctisEvent
     * @param iconDescriptor
     */
    private void addMarker(final NoctisEvent noctisEvent,final BitmapDescriptor iconDescriptor){
        MarkerOptions markerOptions = new MarkerOptions()
                .alpha(0.8f)
                .icon(iconDescriptor)
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(noctisEvent.getCoords());

        Marker marker = map.addMarker(markerOptions);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "marker clicked" + marker.getId());
                return false;
            }
        });
        markerOptionsHashMap.put(noctisEvent.getKey(),marker );
    }

    /**
     * TODO harry comment
     * @param event
     */
    public void onEventMainThread(final ChangeBottomPaddingMapEvent event) {
        CameraPosition position = map.getCameraPosition();
        map.setPadding(0, 0, 0, event.bottomPadding);
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    /**
     * called from the locationservice
     * found a location -> zoom into that part of the map
     * @param foundLocationEvent
     */
    public void onEventMainThread(final FoundLocationEvent foundLocationEvent) {
        moveMapCameraToLocation(foundLocationEvent.coordinate, ZOOM_DEFAULT);
    }

    /**
     * Mapcallback
     * Start locationrequest from here
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.i(TAG, "onMapReady");
        mEventBus.post(new RequestLocationEvent());
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mEventBus.post(new RequestLocationEvent());
                return true;
            }
        });
//        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        mapEventBus.getEventBus().register(this);
    }

    /**
     * Move the map actually to that postition
     * should only be called when the map is ready
     * that's what the mapEventbus is for.
     * @param coordinate
     * @param zoom
     */
    private void moveMapCameraToLocation(final LatLng coordinate, final int zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder() // Creates a CameraPosition from the builder
                .target(coordinate)
                .zoom(zoom)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void onStop() {
        super.onStop();
        mEventBus.post(new GoogleAPIClientEvent(GoogleAPIClientEvent.Action.DISCONNECT));
    }
}

