package at.rosinen.Noctis.activity;

import android.app.Application;
import android.content.Context;
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
    }

    public static Context getContext(){
        return mContext;
    }

}
