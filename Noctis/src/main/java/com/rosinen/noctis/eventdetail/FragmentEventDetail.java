package com.rosinen.noctis.eventdetail;

import android.widget.TextView;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.base.EventBusFragment;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

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
        textViewEventDate.setText(event.getStart().toString());
        textViewDescription.setText(event.getDescription());
    }

    public void onEvent(Object object){}


}
