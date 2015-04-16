package com.rosinen.noctis.activity.event;

import android.view.View;

/**
 * Created by Harald on 15.04.2015.
 */
public class SliderDragViewSetterEvent {


    private View dragView;
    public View getDragView() {
        return dragView;
    }

    public void setDragView(View dragView) {
        this.dragView = dragView;
    }



    public SliderDragViewSetterEvent(View dragView) {
        this.dragView = dragView;
    }
}
