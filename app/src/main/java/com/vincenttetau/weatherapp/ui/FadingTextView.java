package com.vincenttetau.weatherapp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class FadingTextView extends android.support.v7.widget.AppCompatTextView {

    private boolean fadingIn;

    @Nullable
    private String latestText;

    public FadingTextView(Context context) {
        super(context);
    }

    public FadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTextAnimated(final String text) {
        String currentText = getText().toString();

        if (!currentText.equals(text)) {
            latestText = text;

            if (!fadingIn) {
                fadingIn = true;
                fadeOut();
            }
        }
    }

    private void fadeOut() {
        animate().alpha(0)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fadingIn = false;
                        setText(latestText);
                        fadeIn();
                    }
                }).start();
    }

    private void fadeIn() {
        animate().alpha(1)
                .start();
    }
}
