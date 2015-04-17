package com.rosinen.noctis.login;

import android.content.Context;
import android.util.Log;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.login.event.SendUserTokenEvent;
import com.rosinen.noctis.noctisevents.NoctisRestHandler;
import hugo.weaving.DebugLog;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Simon on 15.04.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class FacebookService extends AbstractService{

    private static final String TAG = FacebookService.class.getSimpleName();

    @RestService
    NoctisRestHandler noctisRestHandler;

    @DebugLog
    void onEventBackgroundThread(SendUserTokenEvent event){
        Log.d(TAG, "Sending Usertoken: " + event.userToken);
        noctisRestHandler.postFBUserToken(event.userToken);
    }

}
