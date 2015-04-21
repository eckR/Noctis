package com.rosinen.noctis.eventoverview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.event.FragmentChangeEvent;
import com.rosinen.noctis.activity.event.ShowDetailsEvent;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.EventBusFragment;
import com.rosinen.noctis.eventdetail.EventDetailPagerFragment_;
import com.rosinen.noctis.eventoverview.event.RequestShowDetailsEvent;
import com.rosinen.noctis.eventoverview.event.UpdateEventCount;
import com.rosinen.noctis.location.event.FoundLocationEvent;
import com.rosinen.noctis.location.event.RequestLocationEvent;
import com.rosinen.noctis.map.MapEventBus;
import com.rosinen.noctis.map.event.MarkEventsOnMapEvent;
import com.rosinen.noctis.map.event.MoveAndZoomToLocationEvent;
import com.rosinen.noctis.noctisevents.event.ImageDownloadAvailableEvent;
import com.rosinen.noctis.noctisevents.event.NoctisEventsAvailableEvent;
import com.rosinen.noctis.noctisevents.event.RequestEventsEvent;
import org.androidannotations.annotations.*;

/**
 * Created by Harald on 20.03.2015.
 * Edited by Simon on 31.03.2015
 */
@EFragment(R.layout.event_list_fragment)
public class EventListFragment extends EventBusFragment {

    private static final String TAG = EventListFragment.class.getSimpleName();

    private static final int DEFAULT_RADIUS = 100; //TODO check this value -> maybe set in sharedpreffile -> option menu

    @ViewById
    ListView eventListView;

//    @ViewById
//    TextView listEmptyRefreshBtn;

    @ViewById
    SwipeRefreshLayout eventListRefresher;

    @ViewById
    SwipeRefreshLayout emptySwipeRefresh;

@ViewById
Button emptyBtn;

    @Bean
    NoctisEventAdapter mAdapter;

//    @ViewById
//    View emptyIndicator;

    @Bean
    MapEventBus mapEventBus;



    /**
     * set day with builder
     * EventListFragment_.build()...
     * depending on this value new events are fetched
     * most likely the viewpager index will suffice so this is gonna be used for now
     * else an extra event to the fragmentlist would have to be dispatched.
     */
    @FragmentArg
    public int day;

    @Click(R.id.emptyBtn)
    void clickonemptyBtn(){
        Log.d(TAG, "clicked me :))");
        emptySwipeRefresh.setRefreshing(true);
        requestEventUpdates();
    }

    @AfterViews
    void bindAdapter() {
        EventRefreshListener swipeRefreshListener = new EventRefreshListener();

        eventListRefresher.setOnRefreshListener(swipeRefreshListener);
        emptySwipeRefresh.setOnRefreshListener(swipeRefreshListener);

        eventListView.setEmptyView(emptySwipeRefresh);
        eventListView.setAdapter(mAdapter);

    }

//    @Click(R.id.listEmptyRefreshBtn)
//    void emptyListRefreshButtonClicked(){
//        //TODO disable button and enable when update finished and returned no events
//        Log.d(TAG,"pls refresh me :)");
//        eventListRefresher.setRefreshing(true);
//        requestEventUpdates();
//    }


    @ItemClick(R.id.eventListView)
    void itemClicked(int position) {
//        mAdapter.getItem(position);
        //TODO change call of showdetailevent to supply the event directly else nullpointer can occur because of to late calls to the list
//        new FragmentChangeEvent(new ShowDetailsEvent(mAdapter.getNoctisEventList(),position),true, R.layout.event_list_fragment);
//        EventDetailPagerFragment.
        mapEventBus.getEventBus().post(new MoveAndZoomToLocationEvent(mAdapter.getNoctisEventList().get(position).getCoords(),16)); //TODO get from config file
        mEventBus.post(new FragmentChangeEvent(new EventDetailPagerFragment_(), true, R.id.swipeUpPanel,R.anim.slide_up,R.anim.alpha_fade_out));

        ShowDetailsEvent detailsEvent = new ShowDetailsEvent(mAdapter.getNoctisEventList(), position, day);
        mEventBus.postSticky(detailsEvent);
    }


    /**
     * called from the marker
     *
     * @param requestShowDetailsEvent
     */
    public void onEventBackgroundThread(final RequestShowDetailsEvent requestShowDetailsEvent) {

        for (int i = 0; i < mAdapter.getNoctisEventList().size(); ++i) {
            if (mAdapter.getNoctisEventList().get(i).getFacebookId().equals(requestShowDetailsEvent.event.getFacebookId())) {

                mEventBus.post(new FragmentChangeEvent(new EventDetailPagerFragment_(), true, R.id.swipeUpPanel));

                mEventBus.postSticky(new ShowDetailsEvent(mAdapter.getNoctisEventList(), i, day));
                break;
            }
        }
    }

    /**
     * called by the location service
     * will be called by the map to i think and also
     * when a text field to enter a city name
     * <p/>
     * issued when a new Location update has to be delivered
     * <p/>
     * requests immediatly the events for that location
     *
     * @param foundLocationEvent
     */
    public void onEventMainThread(final FoundLocationEvent foundLocationEvent) {
        mEventBus.post(new RequestEventsEvent(foundLocationEvent.coordinate, DEFAULT_RADIUS, day));
        eventListRefresher.setRefreshing(true);

    }

    /**
     * called from Eventservice
     * check if the events are for that fragment and if so display them
     *
     * @param noctisEventsAvailableEvent
     */
    public void onEventMainThread(final NoctisEventsAvailableEvent noctisEventsAvailableEvent) {
        if (noctisEventsAvailableEvent.requestEventsEvent.day != day) {
            return;
        }
        mAdapter.getNoctisEventList().clear();
        mAdapter.getNoctisEventList().addAll(noctisEventsAvailableEvent.eventList);
//        mAdapter.setNoctisEventList(noctisEventsAvailableEvent.eventList); //TODO check whether the one or the other is better
        mAdapter.notifyDataSetChanged();
        eventListRefresher.setRefreshing(false);
        emptySwipeRefresh.setRefreshing(false);

        mapEventBus.getEventBus().post(new MarkEventsOnMapEvent(mAdapter.getNoctisEventList(), day));
        mEventBus.post(new UpdateEventCount(mAdapter.getNoctisEventList().size(), day));
    }

    /**
     * called from Imageservice
     * it basically only has to notify the mAdapter that the data has changed
     *
     * @param imgDownloadAvailableEvent
     */
    public void onEventMainThread(final ImageDownloadAvailableEvent imgDownloadAvailableEvent) {
        if (imgDownloadAvailableEvent.day != day) {
            return;
        }
        Log.i(TAG, "image download recieved, updating mAdapter");
        mAdapter.notifyDataSetChanged();
    }


    private void requestEventUpdates(){
        FoundLocationEvent foundLocationEvent = mEventBus.getStickyEvent(FoundLocationEvent.class);
        if (foundLocationEvent != null) {
            mEventBus.post(new RequestEventsEvent(foundLocationEvent.coordinate, DEFAULT_RADIUS, day));
        } else {
            mEventBus.post(new RequestLocationEvent());
            mEventBus.post(new ToastMeEvent(getString(R.string.needLocationFirst), Toast.LENGTH_LONG));
            mEventBus.post(new ToastMeEvent(getString(R.string.locationUpdateRequested), Toast.LENGTH_LONG));
        }
    }

    /**
     * set the day after construction!
     * it is needed for the RequestEventEvents
     *
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * OnRefreshListener for NoctisEventListFragment
     * <p/>
     * fires a RequestEventsEvent if there is already a location
     * if not a ToastMeEvent is fired which says that there is no location available
     */
    private class EventRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            requestEventUpdates();
        }
    }

}

