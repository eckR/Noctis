package com.rosinen.noctis.activity.event;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import com.rosinen.noctis.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by David on 20.03.2015.
 */
public class FragmentChangeEvent {

    public final Fragment fragment;
    public final boolean addToBackstack;
    public final int placeholderFragmentId;

    public final int inAnimationRes;
    public final int outAnimationRes;

    public final View[] viewsToSetVisible;

    /**
     *
     * @param fragment
     * @param addToBackstack
     * @param placeholderFragmentId
     */
    public FragmentChangeEvent(Fragment fragment, boolean addToBackstack, int placeholderFragmentId){
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
        this.placeholderFragmentId = placeholderFragmentId;
        this.inAnimationRes = R.anim.do_nothing;
        this.outAnimationRes = R.anim.do_nothing;
        this.viewsToSetVisible = new View[0];
    }

    /**
     *
     * @param fragment
     * @param addToBackstack
     * @param placeholderFragmentId
     * @param viewsToSetVisible
     */
    public FragmentChangeEvent(Fragment fragment, boolean addToBackstack, int placeholderFragmentId, View... viewsToSetVisible){
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
        this.placeholderFragmentId = placeholderFragmentId;
        this.inAnimationRes = R.anim.do_nothing;
        this.outAnimationRes = R.anim.do_nothing;

        this.viewsToSetVisible =  viewsToSetVisible;
    }

    /**
     *
     * @param fragment
     * @param addToBackstack
     * @param placeholderFragmentId
     * @param inAnimationRes
     * @param outAnimationRes
     * @param viewsToSetVisible
     */
    public FragmentChangeEvent(Fragment fragment, boolean addToBackstack, int placeholderFragmentId, int inAnimationRes, int outAnimationRes,View... viewsToSetVisible){
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
        this.placeholderFragmentId = placeholderFragmentId;
        this.inAnimationRes = inAnimationRes;
        this.outAnimationRes = outAnimationRes;

        this.viewsToSetVisible =  viewsToSetVisible;
    }
}
