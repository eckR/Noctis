package at.rosinen.Noctis;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.Service.INoctisEventObtainer;
import at.rosinen.Noctis.Service.Impl.MockNoctisEventObtainer;
import at.rosinen.Noctis.View.EventListItemView;
import at.rosinen.Noctis.View.EventListItemView_;
import at.rosinen.Noctis.events.NoctisEventsQueryEvent;
import at.rosinen.Noctis.events.NoctisQueryEnum;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harald on 21.03.2015.
 */
@EBean
public class NoctisEventAdapter extends BaseAdapter {

    private List<NoctisEvent> noctisEventList = new ArrayList<NoctisEvent>();

    @Bean(MockNoctisEventObtainer.class)
    INoctisEventObtainer noctisEventObtainer;

    @RootContext
    Context context;

    @AfterViews
    void afterInject(){
        Log.d("XXX", "after inject");
        refreshListData();
//        EventBus.getDefault().post(new NoctisEventsQueryEvent(NoctisQueryEnum.START_QUERY));
    }

    @Background
    void refreshListData() {
        Log.d("XXXX","refreshListData()");
        noctisEventList = noctisEventObtainer.obtainNoctisEvents();
        Log.d("XXXX","send query finished event");
        EventBus.getDefault().post(new NoctisEventsQueryEvent(NoctisQueryEnum.QUERY_FINISHED));
    }

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
}
