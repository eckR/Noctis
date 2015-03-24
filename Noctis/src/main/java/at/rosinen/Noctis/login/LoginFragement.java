package at.rosinen.Noctis.login;

import at.rosinen.Noctis.R;
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
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.security.MessageDigest;

import android.content.pm.Signature;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;


@EFragment(R.layout.fragment_login_fragement)
public class LoginFragement extends Fragment {
    private static final String TAG="LoginFragment";

    private View loginView;
    private UiLifecycleHelper uiHelper;
    private LoginButton loginButton;

    @ViewById(R.id.button2)
    public Button button2;

    @Click(R.id.button2)
    public void Click(){
        if(loginButton != null) loginButton.performClick();
    }
    private Session.StatusCallback callback=new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStatechange(session, state, exception);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginView = inflater.inflate(R.layout.fragment_login_fragement, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand_Book.ttf");
        TextView tv = (TextView) loginView.findViewById(R.id.NoctisFont);
        tv.setTypeface(tf);

        loginButton = (LoginButton) loginView.findViewById(R.id.loginBtn);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("user_events")); //"rsvp_event",
        return loginView;
    }

    private void onSessionStatechange(Session session,SessionState state,Exception exception)
    {
        if(state.isOpened()){
            Log.i(TAG, "LOGGED IN....");

            Log.i(TAG,session.getAccessToken());
            Session s = Session.getActiveSession();

            Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {

                        Log.d(TAG, user.toString());
                        Log.d(TAG, response.toString());
                        Log.i(TAG, "Email " + user.asMap().get("email"));
                        button2.setText("logged in");
                        //Todo: Navigate to other fragment
                    }
                }
            }).executeAsync();
        }
        else
        {
            Log.i(TAG, "LOGGED OUT....");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper =new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session=Session.getActiveSession();
        if((session!=null)&&(session.isOpened()||session.isClosed()))
        {
            onSessionStatechange(session, session.getState(), null);

        }
        else{
//            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("user_events"));
//            session.requestNewReadPermissions(newPermissionsRequest);
        }
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        uiHelper.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

}
