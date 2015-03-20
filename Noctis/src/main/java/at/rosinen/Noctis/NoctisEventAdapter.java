package at.rosinen.Noctis;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import at.rosinen.Noctis.Model.NoctisEvent;
import at.rosinen.Noctis.Service.INoctisEventObtainer;
import at.rosinen.Noctis.Service.Impl.MockNoctisEventObtainer;
import at.rosinen.Noctis.View.EventListItemView;
import at.rosinen.Noctis.View.EventListItemView_;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by Harald on 21.03.2015.
 */
@EBean
public class NoctisEventAdapter extends BaseAdapter {

    private List<NoctisEvent> noctisEventList;

    @Bean(MockNoctisEventObtainer.class)
    INoctisEventObtainer noctisEventObtainer;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        noctisEventList = noctisEventObtainer.obtainNoctisEvents();
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
