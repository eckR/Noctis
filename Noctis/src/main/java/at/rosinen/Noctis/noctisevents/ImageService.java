package at.rosinen.Noctis.noctisevents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.activity.NoctisApplication;
import at.rosinen.Noctis.base.AbstractService;
import at.rosinen.Noctis.noctisevents.event.ImageDownloadAvailableEvent;
import at.rosinen.Noctis.noctisevents.event.ImageDownloadTarget;
import at.rosinen.Noctis.noctisevents.event.NoctisEventsAvailableEvent;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.DrawableRes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simon on 25.03.2015.
 */
@EBean
public class ImageService extends AbstractService {

    public static final String TAG = ImageService.class.getSimpleName();

    Context ctx;

    Picasso picasso;

    /**
     * 3 hashmaps for every day one seperate
     * if new events are available the download on the old one is cancelled and the download of the new ones is started
     */
    HashMap[] downloadTargetHashMaps = new HashMap[3];

    @Override
    public void onStart() {
        super.onStart();
        ctx = NoctisApplication.getContext();
        picasso = Picasso.with(ctx);
        picasso.setLoggingEnabled(true);
        for (int i = 0; i < 3; ++i) {
            downloadTargetHashMaps[i] = new HashMap<String, ImageDownloadTarget>();
        }

    }


    public void onEventBackgroundThread(NoctisEventsAvailableEvent noctisEvents) {

        int day = noctisEvents.requestEventsEvent.day;

        stopAllDownloadsInMap(downloadTargetHashMaps[day]);
        downloadTargetHashMaps[day].clear();

        for (final NoctisEvent event : noctisEvents.eventList) {

            ImageDownloadTarget bigTarget = new ImageDownloadTarget(day, ImageDownloadTarget.ImageDownloadTargetType.BIG, event);
            launchDownload(bigTarget, event.getPictureBigUrl(),day, Picasso.Priority.LOW);
            Log.i(TAG, "starting to fetch big picture for: " + event.getFBID());

            ImageDownloadTarget smallTarget = new ImageDownloadTarget(day, ImageDownloadTarget.ImageDownloadTargetType.SMALL,event);
            launchDownload(smallTarget, event.getSmallPicUrl(), day, Picasso.Priority.NORMAL);
            Log.i(TAG, "starting to fetch small picture for: " + event.getFBID());
        }
    }

    @UiThread
    public void launchDownload(ImageDownloadTarget target, String url,int day, Picasso.Priority priority) {
        picasso.load(url).priority(priority).into(target);
        downloadTargetHashMaps[day].put(getIDForHashSet(target), target);
    }

    private void stopAllDownloadsInMap(HashMap<String, ImageDownloadTarget> map) {
        for (Map.Entry<String, ImageDownloadTarget> entry : map.entrySet()) {
            picasso.cancelRequest(entry.getValue());
        }
    }

    private String getIDForHashSet(ImageDownloadTarget target) {
        return target.noctisEvent.getFBID() + "" + target.type;
    }


    public void onEventBackgroundThread(ImageDownloadAvailableEvent event) {
        //TODO maybe null check???!!
        downloadTargetHashMaps[event.target.day].remove(getIDForHashSet(event.target));
    }


}
