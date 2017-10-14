package com.vincenttetau.weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vincenttetau.weatherapp.networking.NetworkingManager;
import com.vincenttetau.weatherapp.utils.UnixTimeAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.verify;

public class BaseTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private NetworkingManager networkingManager;

    @Before
    public void setup() {
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return immediate;
            }
        });

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return immediate;
            }
        });

        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return immediate;
            }
        });
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
    }

    protected String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    protected Gson createCustomGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new UnixTimeAdapter())
                .create();
    }

    protected Scheduler immediate = new Scheduler() {
        @Override
        public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(new Executor() {
                @Override
                public void execute(@android.support.annotation.NonNull Runnable command) {
                    command.run();
                }
            });
        }
    };

}