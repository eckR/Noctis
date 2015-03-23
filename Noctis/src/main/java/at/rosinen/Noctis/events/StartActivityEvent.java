package at.rosinen.Noctis.events;

import android.content.Intent;

/**
 * Created by Simon on 23.03.2015.
 */
public class StartActivityEvent {
    public final Intent intent;

    public StartActivityEvent(Intent intent) {
        this.intent = intent;
    }
}
