package com.rosinen.noctis.base;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Simon on 30.03.2015.
 */
@SharedPref
public interface SharedPreferences  {

    //coordinates where the user is
    float longitude();
    float latitude();

    @DefaultString("")
    String locationName();

    @DefaultBoolean(false)
    boolean loggedIn();
}
