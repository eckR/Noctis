package at.rosinen.Noctis.map.event;

import android.graphics.Bitmap;
import android.util.LruCache;
import at.rosinen.Noctis.Model.NoctisEvent;

/**
 * Created by Simon on 28.03.2015.
 */
public class MarkerAvailableEvent {

    public final NoctisEvent noctisEvent;
    public final Bitmap markerBitmap;

    public MarkerAvailableEvent(NoctisEvent noctisEvent, Bitmap markerBitmap) {
        this.noctisEvent = noctisEvent;

        this.markerBitmap = markerBitmap;
    }


}
