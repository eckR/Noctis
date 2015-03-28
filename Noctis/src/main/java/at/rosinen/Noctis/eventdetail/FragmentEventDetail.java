package at.rosinen.Noctis.eventdetail;

import android.os.Bundle;
import android.widget.TextView;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.base.EventBusFragment;
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
