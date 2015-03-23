package at.rosinen.Noctis.Service;

import at.rosinen.Noctis.Model.NoctisEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Harald on 20.03.2015.
 */
public interface INoctisEventObtainer {

    List<NoctisEvent> obtainNoctisEvents();

    public List<NoctisEvent> obtainNoctisEvents(LatLng coordinate, int radius,int rangeFrom, int rangeTo);

}
