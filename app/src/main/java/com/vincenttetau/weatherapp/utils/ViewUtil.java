package com.vincenttetau.weatherapp.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.view.View;

public class ViewUtil {

    private static final int DURATION_FADE_IN = 100;

    public static void fadeInDrawable(@NonNull View view, @NonNull Drawable drawable) {
        Drawable currentBackground = view.getBackground();

        if (currentBackground instanceof TransitionDrawable) {
            currentBackground = ((TransitionDrawable) currentBackground).getDrawable(1);
        }

        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{currentBackground, drawable});
        transitionDrawable.startTransition(DURATION_FADE_IN);

        view.setBackground(transitionDrawable);
    }

}
