package com.rosinen.noctis.activity.event;

import android.support.v4.app.Fragment;

/**
 * Created by David on 20.03.2015.
 */
public class FragmentChangeEvent {

    public final Fragment fragment;
    public final boolean addToBackstack;
    public final int placeholderFragmentId;

    public FragmentChangeEvent(Fragment fragment, boolean addToBackstack, int placeholderFragmentId){
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
        this.placeholderFragmentId = placeholderFragmentId;
    }
}
