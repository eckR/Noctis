package at.rosinen.Noctis.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.activity.NoctisApplication;
import at.rosinen.Noctis.base.AbstractService;
import at.rosinen.Noctis.map.event.MarkerAvailableEvent;
import at.rosinen.Noctis.map.event.RequestMarkerBitmapEvent;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by Simon on 25.03.2015.
 */
@EBean
public class MarkerService extends AbstractService {

    private static final String TAG = MarkerService.class.getName();

    @Bean
    MapEventBus mapEventBus;


    Context ctx = NoctisApplication.getContext();

    int cacheSize = 10 * 1024 * 1024; // 10 MiB

    LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize);

    LayoutInflater  mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    /**
     * called by the ImageService - downloadTarget after the small picture has been fetched
     *  creates the marker and caches it based on the fbid and attending count in an lrucache
     *
     *  fires a MarkerAvailableEvent to the mapBus
     * @param requestMarkerBitmapEvent
     */
    public void onEventBackgroundThread(RequestMarkerBitmapEvent requestMarkerBitmapEvent) {

        NoctisEvent noctisEvent = requestMarkerBitmapEvent.noctisEvent;
        String key = noctisEvent.getKey();

        Bitmap markerBitmap = lruCache.get(key);

        if(markerBitmap == null) {
            Log.i(TAG,"create maker bitmap for:" + noctisEvent.getKey());

            //Inflate the layout into a view and configure it the way you like
            RelativeLayout view = new RelativeLayout(ctx);
            mInflater.inflate(R.layout.marker_layout, view, true);

            ImageView eventImage = (ImageView) view.findViewById(R.id.eventImage);
            TextView textAttending = (TextView) view.findViewById(R.id.textAttending);
            TextView textFriendsAttending = (TextView) view.findViewById(R.id.textFriendsAttending);


            eventImage.setImageBitmap(noctisEvent.getPictureSmall());
            textAttending.setText(noctisEvent.getAttending() + "");
            textFriendsAttending.setText("nA");

            //Provide it with a layout params. It should necessarily be wrapping the
            //content as we not really going to have a parent for it.
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));

            //Pre-measure the view so that height and width don't remain null.
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            //Assign a size and position to the view and all of its descendants
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

            //Create the bitmap
            markerBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                    view.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
            //Create a canvas with the specified bitmap to draw into
            Canvas c = new Canvas(markerBitmap);

            //Render this view (and all of its children) to the given Canvas
            view.draw(c);

            lruCache.put(key,markerBitmap);
        }
        mapEventBus.getEventBus().post(new MarkerAvailableEvent(noctisEvent, markerBitmap));

    }



}
