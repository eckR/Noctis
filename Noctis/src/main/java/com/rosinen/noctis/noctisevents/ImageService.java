package com.rosinen.noctis.noctisevents;

import android.content.Context;
import android.util.Log;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.activity.NoctisApplication;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.noctisevents.event.ImageDownloadAvailableEvent;
import com.rosinen.noctis.noctisevents.event.ImageDownloadTarget;
import com.rosinen.noctis.noctisevents.event.NoctisEventsAvailableEvent;
import com.rosinen.noctis.noctisevents.event.RequestPicturesEvent;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 25.03.2015.
 */
//TODO implement proper picture cache or at least test and configure the one from picasso!!
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

    public ImageService(){
        ctx = NoctisApplication.getContext();
        picasso = Picasso.with(ctx);
        picasso.setLoggingEnabled(true);
        for (int i = 0; i < 3; ++i) {
            downloadTargetHashMaps[i] = new HashMap<String, ImageDownloadTarget>();
        }
    }

    /**
     * called by the viewpager onchange listener
     *
     * @param requestPicturesEvent
     */
    public void onEventMainThread(final RequestPicturesEvent requestPicturesEvent) {
        downloadEvents(requestPicturesEvent.events,requestPicturesEvent.day);
    }

    /**
     * called from the Eventservice
     * translates to a RequestPicturesEvent
     * <p/>
     * starts the download for the picture and adds the downloadtarget(listener) to the hashmap
     * the downloadtarget fires events when it loaded too.
     *
     * @param noctisEvents
     */
    public void onEventMainThread(final NoctisEventsAvailableEvent noctisEvents) {

        int day = noctisEvents.requestEventsEvent.day;

       downloadEvents(noctisEvents.eventList,day);
    }

    private void downloadEvents(List<NoctisEvent> noctisEvents,int day){

        stopAllDownloadsInMap(downloadTargetHashMaps[day]);
        downloadTargetHashMaps[day].clear();

        for (final NoctisEvent event :noctisEvents) {

            ImageDownloadTarget bigTarget = new ImageDownloadTarget(day, ImageDownloadTarget.ImageDownloadTargetType.BIG, event);
            launchDownload(bigTarget, event.getKey().concat("b"), event.getPictureBigUrl(), day, Picasso.Priority.LOW);
            Log.i(TAG, "starting to fetch big picture for: " + event.getFBID());

            ImageDownloadTarget smallTarget = new ImageDownloadTarget(day, ImageDownloadTarget.ImageDownloadTargetType.SMALL, event);
            launchDownload(smallTarget, event.getKey().concat("s"), event.getSmallPicUrl(), day, Picasso.Priority.NORMAL);
            Log.i(TAG, "starting to fetch small picture for: " + event.getFBID());
        }
    }

    /**
     * remove the Target from the hashmap to release the memory
     *
     * @param event
     */
    public void onEventBackgroundThread(final ImageDownloadAvailableEvent event) {
        //TODO maybe null check???!! before call remove
        downloadTargetHashMaps[event.target.day].remove(getIDForHashSet(event.target));
    }

    /**
     * internal function that is only public because of the AA @UiThread annotation
     *
     * @param target
     * @param fbId
     * @param url
     * @param day
     * @param priority
     */
    @UiThread
    public void launchDownload(ImageDownloadTarget target, String fbId, String url, int day, Picasso.Priority priority) {
        picasso.load(url).tag(fbId).priority(priority).into(target);
        downloadTargetHashMaps[day].put(getIDForHashSet(target), target);
    }

    /**
     * cancel all requests from the map.
     * TODO maybe change to pause not cancel check on that
     *
     * @param map
     */
    private void stopAllDownloadsInMap(HashMap<String, ImageDownloadTarget> map) {
        for (Map.Entry<String, ImageDownloadTarget> entry : map.entrySet()) {
            picasso.cancelRequest(entry.getValue());
        }
    }

    /**
     * internal function to get a unique key for a picture download
     *
     * @param target
     * @return
     */
    private String getIDForHashSet(ImageDownloadTarget target) {
        return target.noctisEvent.getFBID() + "" + target.type;
    }
}
