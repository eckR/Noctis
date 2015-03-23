package at.rosinen.Noctis;

import android.app.usage.UsageEvents;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ListView;
import at.rosinen.Noctis.events.MarkEventsOnMapEvent;
import at.rosinen.Noctis.events.NoctisEventsQueryEvent;
import at.rosinen.Noctis.events.NoctisQueryEnum;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.*;

/**
 * Created by Harald on 20.03.2015.
 */

@EFragment(R.layout.event_list_fragment)
public class EventListFragment extends Fragment{

    @AfterInject
    void afterInject() {
        EventBus.getDefault().register(this);
    }

    @ViewById(android.R.id.list)
    ListView list;

    @ViewById
    SwipeRefreshLayout eventListRefresher;

    @Bean
    NoctisEventAdapter adapter;



    @AfterViews
    void bindAdapter() {
        list.setAdapter(adapter);
        eventListRefresher.setOnRefreshListener(new EventRefreshListener());

        // workaround to show the loading circle
        eventListRefresher.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        eventListRefresher.setRefreshing(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("XXXX","resume me");
        EventBus.getDefault().post(new MarkEventsOnMapEvent(adapter.getNoctisEventList()));
    }

    public void onEventMainThread(NoctisEventsQueryEvent event) {
        if (event.getQueryEnum() == NoctisQueryEnum.QUERY_FINISHED) {
            adapter.notifyDataSetChanged();
            eventListRefresher.setRefreshing(false);
            EventBus.getDefault().post(new MarkEventsOnMapEvent(adapter.getNoctisEventList()));
        }
        else if(event.getQueryEnum() == NoctisQueryEnum.START_QUERY) {

            eventListRefresher.setRefreshing(true);
            adapter.refreshListData();
        }
    }

    /**
     * Listener for NoctisEventFragment
     */
    private class EventRefreshListener implements  SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
//            EventBus.getDefault().post(new NoctisEventsQueryEvent(NoctisQueryEnum.START_QUERY));
        }
    }



}

