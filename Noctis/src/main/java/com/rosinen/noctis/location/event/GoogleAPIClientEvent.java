package com.rosinen.noctis.location.event;

/**
 * Created by Simon on 23.03.2015.
 */
public class GoogleAPIClientEvent {
    public enum Action{
        CONNECT, DISCONNECT
    }
    public final Action action;

    public GoogleAPIClientEvent(Action action) {
        this.action = action;
    }
}
