package at.rosinen.Noctis.activity.event;

import android.widget.Toast;

/**
 * Created by Simon on 23.03.2015.
 */
public class ToastMeEvent {
    public final String message;
    public final int length;

    public ToastMeEvent(String message, int length) {
        this.message = message;
        this.length = length;
    }
}
