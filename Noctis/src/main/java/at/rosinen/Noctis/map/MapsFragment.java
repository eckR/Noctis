package at.rosinen.Noctis.map;


import android.support.v4.app.Fragment;
import android.util.Log;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.location.event.GoogleAPIClientEvent;
import at.rosinen.Noctis.location.event.NewLocationEvent;
import at.rosinen.Noctis.location.event.RequestLocationEvent;
import at.rosinen.Noctis.map.event.ChangeBottomPaddingMapEvent;
import at.rosinen.Noctis.map.event.MarkEventsOnMapEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.HashMap;


@EFragment(R.layout.fragment_maps)
public class MapsFragment extends Fragment implements OnMapReadyCallback {

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



    public void onEventMainThread(MarkEventsOnMapEvent event) {
        //this check should be obsolete because of the mapeventbus!
//        if (map == null) {
//            //TODO handle that maybe with a list that gets added when the map is ready again?
//            return;
//        }





        map.clear();
        markerOptionsHashMap.clear();

        for (NoctisEvent noctisEvent : event.events) {


           MarkerOptions marker = new MarkerOptions()
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mask))
                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                    .position(noctisEvent.getCoords());
            map.addMarker(marker);
            markerOptionsHashMap.put(noctisEvent.getFBID(), marker);
        }
    }
    HashMap<Long,MarkerOptions> markerOptionsHashMap = new HashMap<Long, MarkerOptions>();


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

