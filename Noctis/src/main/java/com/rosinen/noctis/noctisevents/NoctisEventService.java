package com.rosinen.noctis.noctisevents;

import com.rosinen.noctis.Model.NoctisEvent;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.noctisevents.event.NoctisEventsAvailableEvent;
import com.rosinen.noctis.noctisevents.event.RequestEventsEvent;
import hugo.weaving.DebugLog;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

/**
 * Created by Simon on 11.04.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NoctisEventService extends AbstractService implements  INoctisEventObtainer {

    @RestService
    NoctisRestHandler noctisRestHandler;


    @DebugLog
    @Override
    public void onEventBackgroundThread(RequestEventsEvent requestEventsEvent) {
        List<NoctisEvent> events = noctisRestHandler.getNoctisEvents(requestEventsEvent.coordinate.longitude,
                                                                        requestEventsEvent.coordinate.latitude,
                                                                        requestEventsEvent.radius,
                                                                        requestEventsEvent.day);
        mEventBus.post(new NoctisEventsAvailableEvent(requestEventsEvent,events));
    }
}
