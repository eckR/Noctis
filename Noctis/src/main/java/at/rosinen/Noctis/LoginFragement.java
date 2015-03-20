package at.rosinen.Noctis;


import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


@EFragment(R.layout.fragment_login_fragement)
public class LoginFragement extends Fragment {

    @AfterInject
    public void afterInject(){
        fragementTest.setText("cool");
    }

    @ViewById
    TextView fragementTest;
}

