package com.rosinen.noctis.base;

import android.graphics.Typeface;
import com.rosinen.noctis.activity.NoctisApplication;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

/**
 * Created by Simon on 01.04.2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Typefaces {

    public Typeface quicksand;


    @AfterInject
    void initTypefaces(){
        quicksand = Typeface.createFromAsset(NoctisApplication.getContext().getAssets(), "fonts/Quicksand_Book.ttf");
    }


}
