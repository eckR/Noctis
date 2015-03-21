package at.rosinen.Noctis;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;
import java.util.Arrays;


@EFragment(R.layout.fragment_login_fragement)
public class LoginFragement extends Fragment {

    private static final String TAG="LoginFragment";

    @ViewById
    public LoginButton loginBtn;

    private UiLifecycleHelper uihelper;

    @InstanceState
    public Bundle state;

    private Session.StatusCallback callback=new Session.StatusCallback() {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStatechange(session, state, exception);
        }
    };

    @AfterViews
    public void AfterViews(){
        loginBtn.setReadPermissions(Arrays.asList("public_profile", "user_events"));

        uihelper=new UiLifecycleHelper(getActivity(), callback);
        uihelper.onCreate(state);
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "at.rosinen.Noctis",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e){
            Log.d("KeyHas:", "unable to get get keyhas");
        }

        printHashKey(state);
    }


    private void onSessionStatechange(Session session,SessionState state,Exception exception)
    {
        boolean open = state.isClosed();


        if(state.isOpened()){
            Log.i(TAG, "LOGGED IN....");

            Log.i(TAG,session.getAccessToken());
            Session s = Session.getActiveSession();

            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        Log.d(TAG, user.toString());
                        // Set view visibility to true
                    }
                }
            }).executeAsync();
        }
        else
        {
            Log.i(TAG, "LOGGED OUT....");
        }
    }

    public void printHashKey(Bundle savedInstanceState) {

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                Log.i(TAG, "Restoring session");
                session = Session.restoreSession(getActivity().getApplicationContext(), null, callback, savedInstanceState);
            }
            if (session == null) {
                Log.i(TAG,"Creating new session");
                session = new Session(getActivity().getApplicationContext());
            }
            Log.i(TAG,"Active session");
            Session.setActiveSession(session);
        } else {
            Log.i(TAG,"Session is null --> " + session.getState().toString());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Session session=Session.getActiveSession();
        if((session!=null)&&(session.isOpened()||session.isClosed()))
        {
            onSessionStatechange(session, session.getState(), null);

        }
        uihelper.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        uihelper.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        uihelper.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        uihelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uihelper.onActivityResult(requestCode, resultCode, data);
    }

    /*
    @ViewById
    LoginButton loginBtn;

    private final String TAG = "LoginForm";
    private UiLifecycleHelper uiHelper;

    @Click(R.id.button2)
    void updateBookmarksClicked() {
        Session session = Session.getActiveSession();
        String i = session.getAccessToken();
        String f = "test";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To maintain FB Login session

        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {

        session.getAccessToken();
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Set view visibility to true
                        button2.setText("Your Current Location: "
                                + user.getLocation().getProperty("name")
                                .toString());
                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            button2.setText("Logged out");
        }
    }
    @AfterInject
    public void afterInject(){

    }
    */
}

