package at.rosinen.Noctis.eventoverview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import at.rosinen.Noctis.R;
import at.rosinen.Noctis.activity.event.ToastMeEvent;
import at.rosinen.Noctis.base.EventBusFragment;
import at.rosinen.Noctis.location.event.NewLocationEvent;
import at.rosinen.Noctis.map.MapEventBus;
import at.rosinen.Noctis.map.event.MarkEventsOnMapEvent;
import at.rosinen.Noctis.noctisevents.event.ImageDownloadAvailableEvent;
import at.rosinen.Noctis.noctisevents.event.NoctisEventsAvailableEvent;
import at.rosinen.Noctis.noctisevents.event.RequestEventsEvent;
import org.androidannotations.annotations.*;

/**
 * Created by Harald on 20.03.2015.
 */

@EFragment(R.layout.event_list_fragment)
public class EventListFragment extends EventBusFragment {

    private static String TAG = EventListFragment.class.getName();

    private static final int DEFAULT_RADIUS = 100;

    @ViewById(android.R.id.list)
    ListView list;

    @ViewById
    SwipeRefreshLayout eventListRefresher;

    @Bean
    NoctisEventAdapter adapter;

    @ViewById
    RelativeLayout emptyIndicator;

    @Bean
    MapEventBus mapEventBus;

    @FragmentArg
    public int day;

    @AfterViews
    void bindAdapter() {
        list.setAdapter(adapter);
        eventListRefresher.setOnRefreshListener(new EventRefreshListener());

        list.setEmptyView(emptyIndicator);
        // workaround to show the loading circle
//        eventListRefresher.setProgressViewOffset(false, 0,
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
//        eventListRefresher.setRefreshing(true);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("XXXX", "resume me");
//        mEventBus.post(new MarkEventsOnMapEvent(adapter.getNoctisEventList()));
    }




    public void onEvent(NewLocationEvent newLocationEvent) {
        mEventBus.post(new RequestEventsEvent(newLocationEvent.coordinate, DEFAULT_RADIUS, day));
        eventListRefresher.setRefreshing(true);
    }

    public void onEventMainThread(NoctisEventsAvailableEvent noctisEventsAvailableEvent) {
        if (noctisEventsAvailableEvent.requestEventsEvent.day != day) {
            return;
        }
        adapter.setNoctisEventList(noctisEventsAvailableEvent.eventList);
        adapter.notifyDataSetChanged();
        eventListRefresher.setRefreshing(false);

        mapEventBus.getEventBus().post(new MarkEventsOnMapEvent(adapter.getNoctisEventList()));

    }

    /**
     * Listener for NoctisEventFragment
     */
    private class EventRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            NewLocationEvent newLocationEvent = mEventBus.getStickyEvent(NewLocationEvent.class);
            if (newLocationEvent != null) {
                mEventBus.post(new RequestEventsEvent(newLocationEvent.coordinate, DEFAULT_RADIUS, day));
            } else {
                mEventBus.post(new ToastMeEvent(getString(R.string.needLocationFirst), Toast.LENGTH_LONG));
            }
        }
    }

    public void onEventMainThread(ImageDownloadAvailableEvent imgDownloadAvailableEvent){
        Log.i(TAG, "image download recieved, updating adapter");
        adapter.notifyDataSetChanged();
    }

    public void setDay(int day) {
        this.day = day;
    }


}

