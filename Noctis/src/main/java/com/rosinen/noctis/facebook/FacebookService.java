package com.rosinen.noctis.facebook;

import android.util.Log;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.AbstractService;
import com.rosinen.noctis.facebook.event.JoinButtonClickedEvent;
import com.rosinen.noctis.facebook.event.SendUserTokenEvent;
import com.rosinen.noctis.facebook.entity.UserTokenDTO;
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


    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;


    @AfterInject
    void afterInject(){
        noctisRestHandler.setHeader("Content-Type", "application/json");
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    mEventBus.post(new ToastMeEvent("CurrentAccessToken was null.. logged out?", Toast.LENGTH_SHORT));
                    return;
                }
                mEventBus.post(new SendUserTokenEvent(currentAccessToken.getToken()));
                mEventBus.post(new ToastMeEvent("Current access token: " + currentAccessToken.getToken(), Toast.LENGTH_SHORT));
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                mEventBus.post(new ToastMeEvent(constructWelcomeMessage(currentProfile), Toast.LENGTH_SHORT));
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    /**
     * called from the login fragment and the tokentracker
     * @param event
     */
    @DebugLog
    public void onEventBackgroundThread(SendUserTokenEvent event){
        Log.d(TAG, "Sending Usertoken: " + event.userToken);
        noctisRestHandler.postFBUserToken(new UserTokenDTO(event.userToken));
    }

    /**
     * called from the eventdetail fragment
     * @param buttonClickedEvent
     */
    @DebugLog
    public void onEventBackgroundThread(final JoinButtonClickedEvent buttonClickedEvent){
        Log.d(TAG, "joinbutton clicked for event: " + buttonClickedEvent.event.getFacebookId());
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getName());
        }
        return stringBuffer.toString();
    }

}
