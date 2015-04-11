package com.rosinen.noctis.eventdetail;

import android.widget.TextView;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.base.EventBusFragment;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Created by harald on 3/28/15.
 */

@EFragment(R.layout.fragment_event_details)
public class FragmentEventDetail extends EventBusFragment {

    NoctisEvent event;

    @ViewById(R.id.textViewEventDate)
    TextView textViewEventDate;

    @ViewById(R.id.textViewDescription)
    TextView textViewDescription;

    public FragmentEventDetail(){


    }
    public void updateFields(NoctisEvent event) {
        this.event = event;

    }

    @AfterViews
    void updateFields() {
        SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat("EEEE dd.MM.yyyy HH:mm");
        SimpleDateFormat simpleDateFormatEnd = new SimpleDateFormat("HH:mm");
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(simpleDateFormatStart.format(event.getStart()));
        dateBuilder.append(" - ");
        dateBuilder.append(simpleDateFormatEnd.format(event.getEndTime()));
        textViewEventDate.setText(dateBuilder.toString());
        textViewDescription.setText(event.getDescription());
    }

    public void onEvent(Object object){}


}
