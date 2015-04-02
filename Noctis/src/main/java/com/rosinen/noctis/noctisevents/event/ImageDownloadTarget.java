package com.rosinen.noctis.noctisevents.event;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.map.event.RequestMarkerBitmapEvent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import de.greenrobot.event.EventBus;


/**
 * Created by Simon on 25.03.2015.
 */
public class ImageDownloadTarget implements Target {

    public enum ImageDownloadTargetType {
        BIG, SMALL
    }

    public final int day;

    public final ImageDownloadTargetType type;

    public final NoctisEvent noctisEvent;


    private EventBus mEventBus = EventBus.getDefault();


    public ImageDownloadTarget(int day, ImageDownloadTargetType type, NoctisEvent noctisEvent) {
        this.day = day;
        this.type = type;
        this.noctisEvent = noctisEvent;

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


        if (type == ImageDownloadTargetType.SMALL) {
            noctisEvent.setPictureSmall(bitmap);
            mEventBus.post(new RequestMarkerBitmapEvent(noctisEvent, day));

            if (noctisEvent.getPictureBig() == null) {
                noctisEvent.setPictureBig(bitmap);
            }
        } else { //if(type == ImageDownloadTargetType.BIG){
            noctisEvent.setPictureBig(bitmap);
        }
        mEventBus.post(new ImageDownloadAvailableEvent(noctisEvent, this, day));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
