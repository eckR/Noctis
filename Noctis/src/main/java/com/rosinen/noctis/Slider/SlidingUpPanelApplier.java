package com.rosinen.noctis.Slider;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

/**
 */
public abstract class SlidingUpPanelApplier {
    private final View panel;
    private final View slider;
    private final int maxHeight;
    private final int minHeight;
    public OnTouchListener listener;
    public SlidingUpPanelApplier(final View panel, final View slider, Context applicationContext){
        this.panel=panel;
        this.slider=slider;
        maxHeight=panel.getLayoutParams().height;
        minHeight=slider.getLayoutParams().height;
        listener=null;
        applyDetectors(applicationContext);
    }
    public int getMaxHeight(){
        return maxHeight;
    }
    public int getMinHeight(){
        return minHeight;
    }
    public SlidingUpPanelApplier(final View panel, final View slider, final int minHeight, final int maxHeight, Context applicationContext){
        this.panel=panel;
        this.slider=slider;
        this.maxHeight=maxHeight;
        this.minHeight=minHeight;
        listener=null;
        applyDetectors(applicationContext);

    }
    public void expand(){
        ResizeHeightAnimation animation = new ResizeHeightAnimation(panel, maxHeight);
        Log.d("Panel", "MaxHeight: " + this.maxHeight);
        animation.setDuration(300);
        panel.startAnimation(animation);
        ((View) panel.getParent()).invalidate();
        onExpand();
    }
    public void collapse(){
        ResizeHeightAnimation animation = new ResizeHeightAnimation(panel, minHeight);
        animation.setDuration(300);
        panel.startAnimation(animation);
        ((View) panel.getParent()).invalidate();
        onCollapse();
    }
    public abstract void onExpand();
    public abstract void onCollapse();


    private void applyDetectors(Context applicationContext){
        final GestureDetector detector=new GestureDetector(applicationContext, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (panel.getHeight()==slider.getHeight()) {
                    expand();
                } else {
                    collapse();
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return false;
            }
        });
        listener=new View.OnTouchListener() {
            float prevent=-1;
            int direction=1; //0 for up, 1 for down
            float offset=0;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
            	Log.d("TOUCH EVENT CALLED", view.toString());
                if(detector.onTouchEvent(motionEvent)==false) {
                    //if (prevent != -1) {
                        //set Direction of current scroll based on previous event;
                        if (prevent > motionEvent.getRawY()) {
                            direction = 0;
                        } else if (prevent < motionEvent.getRawY()) {
                            direction = 1;
                        }
                    //}
                    prevent = motionEvent.getRawY();
                    int action = motionEvent.getAction();

                    switch (action) {
                        case (MotionEvent.ACTION_DOWN):
                            offset = motionEvent.getY();
                        case (MotionEvent.ACTION_MOVE):

                            ViewGroup.LayoutParams params = panel.getLayoutParams();
                            int newheight = params.height - (int) motionEvent.getY() + (int) offset;
                            if (newheight >= minHeight && newheight <= maxHeight) {
                                params.height = newheight;
                                panel.setLayoutParams(params);
                            }
                            return true;

                        case (MotionEvent.ACTION_UP):
                        	if(offset==0){
                        		Log.d("PanelApplier", "singletap");
                        	}
                            offset = 0;
                            if (direction == 0) {
                                    expand();
                            }
                            else {
                                    collapse();

                            }

                    }
                }

                return true;
            }
        };
        slider.setOnTouchListener(listener);

    }

}

