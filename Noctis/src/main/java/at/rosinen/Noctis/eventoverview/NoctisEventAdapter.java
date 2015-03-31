package at.rosinen.Noctis.eventoverview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import at.rosinen.Noctis.Model.NoctisEvent;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harald on 21.03.2015.
 */
@EBean
public class NoctisEventAdapter extends BaseAdapter {

    private List<NoctisEvent> noctisEventList = new ArrayList<NoctisEvent>();

    @RootContext
    Context context;


    @Override
    public int getCount() {
        return noctisEventList.size();
    }

    @Override
    public NoctisEvent getItem(int position) {
        return noctisEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventListItemView eventListItemView;
        if (convertView == null) {
            eventListItemView = EventListItemView_.build(context);
        }
        else {
            eventListItemView = (EventListItemView) convertView;
        }
        eventListItemView.bind(getItem(position));
        return eventListItemView;
    }

//    public void setNoctisEventList(List<NoctisEvent> eventList) {
//        this.noctisEventList = eventList;
//    }

    public List<NoctisEvent> getNoctisEventList() {
        return noctisEventList;
    }
}
