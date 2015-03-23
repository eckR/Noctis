package at.rosinen.Noctis.events;

import android.content.DialogInterface;
import at.rosinen.Noctis.NoctisApplication;
import at.rosinen.Noctis.R;

/**
 * Created by Simon on 23.03.2015.
 */
public class AlertDialogEvent {

    public final String title;
    public final String message;
    public final String yesString;
    public final String noString;
    public final DialogInterface.OnClickListener onYesListener;
    public final DialogInterface.OnClickListener onNoListener;

    public AlertDialogEvent(String title,String message, String yesString, String noString,
                            DialogInterface.OnClickListener onYesListener,
                            DialogInterface.OnClickListener onNoListener) {
        this.title = title;
        this.message = message;
        this.yesString = yesString;
        this.noString = noString;
        this.onYesListener = onYesListener;
        this.onNoListener = onNoListener;
    }


    public AlertDialogEvent(String title,String message, DialogInterface.OnClickListener onYesListener ) {
        this.title = title;
        this.message = message;
        this.yesString = NoctisApplication.getContext().getString(R.string.yes);
        this.noString = NoctisApplication.getContext().getString(R.string.no);
        this.onYesListener = onYesListener;
        this.onNoListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
    }




}
