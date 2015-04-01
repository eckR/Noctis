package com.rosinen.noctis.noctisevents;

import com.rosinen.noctis.noctisevents.event.RequestEventsEvent;

/**
 * Created by Harald on 20.03.2015.
 * Edited  by Simon  on 24.03.2015.
 */
public interface INoctisEventObtainer {

    void onEventBackgroundThread(RequestEventsEvent requestEventsEvent);

}
