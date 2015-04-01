package com.rosinen.noctis.eventoverview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.R;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Harald on 20.03.2015.
 */

@EViewGroup(R.layout.event_list_item)
public class EventListItemView extends LinearLayout{

    @ViewById
    ImageView itemImage;
    @ViewById
    TextView itemTitle;
    @ViewById
    TextView  itemAttendance;
    @ViewById
    TextView itemLocation;
    @ViewById
    TextView itemDistance;


    public EventListItemView(Context context) {
        super(context);
    }

    public void bind(NoctisEvent event) {
        //TODO check for null if it cant be loaded usefully in the noctisevent
        itemImage.setImageBitmap(event.getPictureBig());
        itemTitle.setText(event.getName());
        itemLocation.setText(event.getLocation());
        itemAttendance.setText(event.getAttending()+"");
        itemDistance.setText(event.getDistance() + getContext().getString(R.string.distanceString));
        
    }
}
