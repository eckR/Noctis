package com.rosinen.noctis.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.widget.LoginButton;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.event.LoginNavigationEvent;
import com.rosinen.noctis.activity.event.ToastMeEvent;
import com.rosinen.noctis.base.ReceiverOnlyEventBusFragment;
import com.rosinen.noctis.base.SharedPreferences_;
import com.rosinen.noctis.base.Typefaces;
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

//    //TODO handle multiple clicks on the loginbutton the right way
//    //TODO show allert dialog or loading animation over skip button
//    //should be done now.. check back.. -> sticky loginevent
//    @Click(R.id.button2)
//    public void Click() {
//        if (loginButton != null) {
//            loginButton.performClick();
//        }
//    }

    @Click(R.id.skipBtn)
    public void skipBtnClick() {
        loginSpinner.setVisibility(View.VISIBLE);
        skipBtn.setEnabled(false);
//        skipBtn.setVisibility(View.GONE);
        goToMapScreen();
    }

//    private Session.StatusCallback callback = new Session.StatusCallback() {
//        @Override
//        public void call(Session session, SessionState state, Exception exception) {
//            onSessionStatechange(session, state, exception);
//        }
//    };


    @Background
    void goToMapScreen() {

        if (mEventBus.getStickyEvent(LoginNavigationEvent.class) == null) {
            mEventBus.postSticky(new LoginNavigationEvent());
        } else {
            mEventBus.post(new ToastMeEvent(getString(R.string.alreadySkipedLoginscreen), Toast.LENGTH_SHORT));
            mEventBus.postSticky(new LoginNavigationEvent());//TODO fix and remove
        }
    }

//    @AfterViews
//    public void initView() {
//        tv.setTypeface(typefaces.quicksand);
//
//        loginButton.setFragment(this);
//        loginButton.setReadPermissions("user_events");
////        loginButton.setReadPermissions(Arrays.asList("user_events")); //"rsvp_event",
//    }

//    private void onSessionStatechange(Session session, SessionState state, Exception exception) {
//        if (state.isOpened()) {
//            Log.i(TAG, "LOGGED IN to facebook ....");
//
//            Log.i(TAG, session.getAccessToken());
//            Session s = Session.getActiveSession();
//
//            Request.newMeRequest(session, new Request.GraphUserCallback() {
//                @Override
//                public void onCompleted(GraphUser user, Response response) {
//                    if (user != null) {
//                        Log.d(TAG, user.toString());
//                        Log.d(TAG, response.toString());
//                        Log.i(TAG, "Email " + user.asMap().get("email"));
//                        button2.setText("Loading...");
//                        goToMapScreen();
//                    }
//                }
//            }).executeAsync();
//        } else {
//            Log.i(TAG, "LOGGED OUT from facebook ....");
//        }
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        uiHelper = new UiLifecycleHelper(getActivity(), callback);
//        uiHelper.onCreate(savedInstanceState);
//        try {
//            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
//                    getString(R.string.app_package_name),
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d(TAG, "Keyhash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (Exception e) {
//            Log.e(TAG, getString(R.string.unableToGetKeyHash));
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
//        Session session = Session.getActiveSession();
//        if ((session != null) && (session.isOpened() || session.isClosed())) {
//            onSessionStatechange(session, session.getState(), null);
//        }
////        else {
//////            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("user_events"));
//////            session.requestNewReadPermissions(newPermissionsRequest);
////        }
//        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        uiHelper.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

}
