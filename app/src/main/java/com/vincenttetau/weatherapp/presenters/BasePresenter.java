package com.vincenttetau.weatherapp.presenters;

import android.support.annotation.NonNull;

import com.vincenttetau.weatherapp.views.BaseView;

public class BasePresenter<V extends BaseView> {

    private V view;

    public void bindView(@NonNull V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

}
