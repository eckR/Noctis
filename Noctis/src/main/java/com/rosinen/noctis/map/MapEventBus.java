package com.rosinen.noctis.map;

import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.EBean;

/**
 * Created by Harald on 24.03.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MapEventBus {

    private EventBus mapEventBus;
    public MapEventBus() {
        mapEventBus = EventBus.builder().logNoSubscriberMessages(true).build();
    }

    public EventBus getEventBus() {
        return mapEventBus;
    }
}
