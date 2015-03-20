package at.rosinen.Noctis.events;

import android.support.v4.app.Fragment;

/**
 * Created by David on 20.03.2015.
 */
public class FragmentChangeEvent {

    public final Fragment fragment;
    public final boolean addToBackstack;

    public FragmentChangeEvent(Fragment fragment, boolean addToBackstack){
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
    }
}
