package com.vincenttetau.weatherapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {

    public static void hideKeyboard(@NonNull EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
