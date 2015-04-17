package com.rosinen.noctis.login.event;

/**
 * Created by Simon on 17.04.2015.
 */
public class SendUserTokenEvent {

    public final String userToken;

    public SendUserTokenEvent(String userToken) {
        this.userToken = userToken;
    }
}
