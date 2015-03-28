package at.rosinen.Noctis.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.base.AbstractService;
import at.rosinen.Noctis.map.event.RequestMarkerBitmapEvent;
import org.androidannotations.annotations.EBean;

/**
 * Created by Simon on 25.03.2015.
 */
@EBean
public class MarkerService extends AbstractService {

    @DrawableRes
    public Bitmap marker_mask;



    public void onEventBackgroundThread(RequestMarkerBitmapEvent requestMarkerBitmapEvent){
        Context ctx;
        Rect topRect = new Rect(5,5,134,50);
        Rect bottomLeft = new Rect(5,5,134,50);
        Rect bottomRight = new Rect(5,5,134,50);

//        ctx.getDrawable(R.drawable.marker_mask)
        Canvas canvas = new Canvas( marker_mask);
//        canvas.drawBitmap(requestMarkerBitmapEvent.noctisEvent.getBitmap().,);

//        canvas.d

    }


}
