package at.rosinen.Noctis.noctisevents;

import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.noctisevents.event.RequestEventsEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Harald on 20.03.2015.
 * Edited  by Simon  on 24.03.2015.
 */
public interface INoctisEventObtainer {

    public void onEventBackgroundThread(RequestEventsEvent requestEventsEvent);

}
