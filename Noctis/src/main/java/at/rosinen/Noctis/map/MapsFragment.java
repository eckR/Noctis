package at.rosinen.Noctis.map;


import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.location.event.GoogleAPIClientEvent;
import at.rosinen.Noctis.location.event.NewLocationEvent;
import at.rosinen.Noctis.location.event.RequestLocationEvent;
import at.rosinen.Noctis.map.event.ChangeBottomPaddingMapEvent;
import at.rosinen.Noctis.map.event.MarkEventsOnMapEvent;
import at.rosinen.Noctis.map.event.MarkerAvailableEvent;
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

    private HashMap<String,Marker> markerOptionsHashMap = new HashMap<String, Marker>();

    private static final int ZOOM_DEFAULT = 12;
    private EventBus mEventBus = EventBus.getDefault();
    private GoogleMap map;

    @Bean
    MapEventBus mapEventBus;


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

    private void moveMapCameraToLocation(LatLng coordinate, int zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder() // Creates a CameraPosition from the builder
                .target(coordinate)
                .zoom(zoom)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    public  void onEventMainThread(MarkerAvailableEvent markerAvailableEvent){
        Marker marker = markerOptionsHashMap.get(markerAvailableEvent.noctisEvent.getKey());

        if (marker != null) {
            marker.remove();
        }
//        map.clear();
        //TODO put markerbitmap back into noctisevent else the memory will go missing
        addMarker(markerAvailableEvent.noctisEvent, BitmapDescriptorFactory.fromBitmap(markerAvailableEvent.markerBitmap));
    }

    public void onEventMainThread(MarkEventsOnMapEvent event) {

        map.clear();
        markerOptionsHashMap.clear();

        for (NoctisEvent noctisEvent : event.events) {
           addMarker(noctisEvent, BitmapDescriptorFactory.fromResource(R.drawable.marker_mask));
        }
    }

    private void addMarker(NoctisEvent noctisEvent, BitmapDescriptor iconDescriptor){
        MarkerOptions marker = new MarkerOptions()
                .alpha(0.8f)
                .icon(iconDescriptor)
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(noctisEvent.getCoords());

        markerOptionsHashMap.put(noctisEvent.getKey(), map.addMarker(marker));
    }


    public void onEventMainThread(NewLocationEvent newLocationEvent) {
        moveMapCameraToLocation(newLocationEvent.coordinate, ZOOM_DEFAULT);
    }

    public void onEventMainThread(ChangeBottomPaddingMapEvent event) {
        CameraPosition position = map.getCameraPosition();
        map.setPadding(0, 0, 0, event.bottomPadding);
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position));

    }

    /**
     * Mapcallback
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(getClass().getCanonicalName(), "Found map");
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
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        mapEventBus.getEventBus().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        mEventBus.post(new GoogleAPIClientEvent(GoogleAPIClientEvent.Action.CONNECT));
    }
}

