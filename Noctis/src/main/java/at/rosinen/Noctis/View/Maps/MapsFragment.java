package at.rosinen.Noctis.View.Maps;


import android.util.Log;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.View.Base.EventFragment;
import at.rosinen.Noctis.events.GoogleAPIClientEvent;
import at.rosinen.Noctis.events.MarkEventsOnMapEvent;
import at.rosinen.Noctis.events.NewLocationEvent;
import at.rosinen.Noctis.events.RequestLocationEvent;
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
import org.androidannotations.annotations.EFragment;


@EFragment(R.layout.fragment_maps)
public class MapsFragment extends EventFragment implements OnMapReadyCallback {

    private static final int ZOOM_DEFAULT = 12;

    private GoogleMap map;


    @AfterViews
    void afterViews() {
        SupportMapFragment fragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gMap));
        fragment.getMapAsync(this);
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
        if (map == null) {
            //TODO handle that maybe with a list that gets added when the map is ready again?
            return;
        }
        map.clear();
        for (NoctisEvent noctisEvent : event.events) {
            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.kalender))
                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                    .position(noctisEvent.getCoords()));
        }
    }

    public void onEventMainThread(NewLocationEvent newLocationEvent){
        moveMapCameraToLocation(newLocationEvent.coordinate, ZOOM_DEFAULT);
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
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    public void onStop() {
        super.onStop();
        mEventBus.post(new GoogleAPIClientEvent(GoogleAPIClientEvent.Action.CONNECT));
    }
}

