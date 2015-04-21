package com.rosinen.noctis.facebook.event;

import com.rosinen.noctis.Model.NoctisEvent;

/**
 * Created by Simon on 20.04.2015.
 */
public class JoinButtonClickedEvent {

    public final NoctisEvent event;

    public JoinButtonClickedEvent(NoctisEvent event) {
        this.event = event;
    }
}
