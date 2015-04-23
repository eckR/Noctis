package com.rosinen.noctis.eventdetail;

import android.util.Log;
import android.widget.TextView;
import com.rosinen.noctis.R;
import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.base.EventBusFragment;
import com.rosinen.noctis.eventdetail.event.RequestCloseDetailViewEvent;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Created by harald on 3/28/15.
 */

@EFragment(R.layout.fragment_event_details)
public class FragmentEventDetail extends EventBusFragment {

    public static final String TAG = FragmentEventDetail.class.getSimpleName();

    @ViewById(R.id.textViewEventDate)
    TextView textViewEventDate;

    @ViewById(R.id.textViewDescription)
    TextView textViewDescription;

    NoctisEvent mEvent;

    public FragmentEventDetail(){


    }


    public void updateFields(final NoctisEvent event) {
        if (!checkForEvent(event)){
            return;
        }
        this.mEvent = event;
    }

    @AfterViews
    void updateFields() {
        if (!checkForEvent(mEvent)){
            return;
        }
        SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat("EEEE dd.MM.yyyy HH:mm");
        SimpleDateFormat simpleDateFormatEnd = new SimpleDateFormat("HH:mm");

        if (mEvent.getStart() !=null && mEvent.getEndTime() != null) {
            StringBuilder dateBuilder = new StringBuilder();
            dateBuilder.append(simpleDateFormatStart.format(mEvent.getStart()));
            dateBuilder.append(" - ");
            dateBuilder.append(simpleDateFormatEnd.format(mEvent.getEndTime()));
            textViewEventDate.setText(dateBuilder.toString());
        }
        textViewDescription.setText(mEvent.getDescription());
    }

    private boolean checkForEvent(NoctisEvent event){
        if (event == null) {
            Log.e(TAG, "Event should not be null!");
            mEventBus.postSticky(new RequestCloseDetailViewEvent());
            return false;
        }
        return  true;
    }

    public void onEvent(Object object){}


}
