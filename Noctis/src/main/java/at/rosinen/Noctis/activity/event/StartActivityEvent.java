package at.rosinen.Noctis.activity.event;

import android.content.Intent;

/**
 * Created by Simon on 23.03.2015.
 * Can be used to start a new Activity with an event
 */
public class StartActivityEvent {
    public final Intent intent;

    public StartActivityEvent(Intent intent) {
        this.intent = intent;
    }
}
