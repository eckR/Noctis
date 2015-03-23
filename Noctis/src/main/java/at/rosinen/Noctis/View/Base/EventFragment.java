package at.rosinen.Noctis.View.Base;

import android.support.v4.app.Fragment;
import de.greenrobot.event.EventBus;

/**
 * Created by Simon on 23.03.2015.
 */
public class EventFragment extends Fragment {

    protected EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }


}
