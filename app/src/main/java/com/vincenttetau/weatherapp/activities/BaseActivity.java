package com.vincenttetau.weatherapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vincenttetau.weatherapp.presenters.BasePresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePresenter(savedInstanceState == null);
    }

    private void initialisePresenter(boolean firstLoad) {
        if (firstLoad) {
            presenter = createPresenter();
        } else {
            presenter = (P) getLastCustomNonConfigurationInstance();
        }
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

}
