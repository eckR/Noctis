package com.rosinen.noctis.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.event.LoginNavigationEvent;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.ReceiverOnlyEventBusFragment;
import com.rosinen.noctis.base.SharedPreferences_;
import com.rosinen.noctis.base.Typefaces;
import com.rosinen.noctis.facebook.event.SendUserTokenEvent;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;


@EFragment(R.layout.fragment_login_fragement)
public class LoginFragement extends ReceiverOnlyEventBusFragment {

    private static final String TAG = LoginFragement.class.getSimpleName();

    @Pref
    SharedPreferences_ sharedPrefs;

    @Bean
    Typefaces typefaces;

    @ViewById
    TextView appNameText;

    @ViewById
    TextView goodNights;

    @ViewById
    ProgressBar loginSpinner;

    @ViewById
    Button skipBtn;

    @ViewById
    Button visibleFbLoginBtn;

    @ViewById
    TextView promiseTxt;

    @ViewById
    LoginButton invisibleFbLoginBtn;


    private CallbackManager mCallbackManager;

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG, "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            mEventBus.post(new ToastMeEvent("AccessToken: " + accessToken.getToken(), Toast.LENGTH_SHORT));
            mEventBus.post(new SendUserTokenEvent(accessToken.getToken()));
            goToMapScreen();
        }
        @Override
        public void onCancel() {
            // you get here if on the "already authorized screen" cancel was pressed
            Log.d(TAG, "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d(TAG, "onError " + e);
        }
    };


//    //TODO handle multiple clicks on the loginbutton the right way
//    //TODO show allert dialog or loading animation over skip button
//    //should be done now.. check back.. -> sticky loginevent
    @Click(R.id.visibleFbLoginBtn)
    public void Click() {
        if (invisibleFbLoginBtn != null) {
            invisibleFbLoginBtn.performClick();
        }
    }

    @Click(R.id.skipBtn)
    public void skipBtnClick() {
        loginSpinner.setVisibility(View.VISIBLE);
        skipBtn.setEnabled(false);
//        skipBtn.setVisibility(View.GONE);
        goToMapScreen();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
    }

    @AfterViews
    void afterViews(){
        appNameText.setTypeface(typefaces.quicksand);
        goodNights.setTypeface(typefaces.quicksand);
        skipBtn.setTypeface(typefaces.quicksand);
        visibleFbLoginBtn.setTypeface(typefaces.quicksand);
        promiseTxt.setTypeface(typefaces.quicksand);
        setupLoginButton();
    }



    @Background
    void goToMapScreen() {

        if (mEventBus.getStickyEvent(LoginNavigationEvent.class) == null) {
            mEventBus.postSticky(new LoginNavigationEvent());
        } else {
            mEventBus.post(new ToastMeEvent(getString(R.string.alreadySkipedLoginscreen), Toast.LENGTH_SHORT));
            mEventBus.postSticky(new LoginNavigationEvent());//TODO fix and remove
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        if(profile != null){ // already logged in!
            mEventBus.post(new ToastMeEvent("Welcome: " + profile.getName(), Toast.LENGTH_SHORT));
//            goToMapScreen();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }




    private void setupLoginButton() {
        invisibleFbLoginBtn.setFragment(this);
        invisibleFbLoginBtn.setReadPermissions("user_events");
        invisibleFbLoginBtn.registerCallback(mCallbackManager, mFacebookCallback);
    }

}
