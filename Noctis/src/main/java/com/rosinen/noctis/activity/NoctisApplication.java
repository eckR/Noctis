package com.rosinen.noctis.activity;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.EApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Simon on 23.03.2015.
 */
@EApplication
public class NoctisApplication extends Application {

    private static final String TAG = NoctisApplication.class.getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Crashlytics.start(this);
        EventBus.getDefault().post(new ToastMeEvent("Crashlytics started", Toast.LENGTH_SHORT));
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }


    /**
     * Method is to get the facebook hash
     * Call this method inside onCreate once to get your hash key
     */
    public static void printKeyHash() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getString(R.string.app_package_name), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
