package com.rosinen.noctis.eventoverview.event;

import android.util.Log;

/**
 * Created by Simon on 02.04.2015.
 */
public class UpdateEventCount {

    private static final String TAG = UpdateEventCount.class.getSimpleName();

    public final int count;
    public final int day;

    public UpdateEventCount(int count, int day) {
        this.count = count;
        this.day = day;
        Log.i(TAG,toString());
    }

    @Override
    public String toString() {
        return "UpdateEventCount{" +
                "count=" + count +
                ", day=" + day +
                '}';
    }
}
