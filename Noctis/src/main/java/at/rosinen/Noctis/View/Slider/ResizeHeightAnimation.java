package at.rosinen.Noctis.View.Slider;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Harald on 06.04.2014.
 */
public class ResizeHeightAnimation extends Animation {
    final int startHeight;
    final int targetHeight;
    View view;

    public ResizeHeightAnimation(View view, int targetHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
        startHeight = view.getHeight();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        Log.d("APPLYTransform", "Done");
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}

