package com.rosinen.noctis.activity;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.EApplication;

/**
 * Created by Simon on 23.03.2015.
 */
@EApplication
public class NoctisApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Crashlytics.start(this);
        EventBus.getDefault().post(new ToastMeEvent("Crashlytics started", Toast.LENGTH_SHORT));
    }

    public static Context getContext(){
        return mContext;
    }

}
