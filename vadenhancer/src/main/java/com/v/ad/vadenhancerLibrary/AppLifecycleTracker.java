package com.v.ad.vadenhancerLibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;

public class AppLifecycleTracker implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {


    private static volatile AppLifecycleTracker INSTANCE = null;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private AppLifecycleTracker() {
        Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker()");
    }

    public static AppLifecycleTracker getInstance() {
        if (INSTANCE == null) {
            synchronized (AppLifecycleTracker.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLifecycleTracker();
                }
            }
        }
        return INSTANCE;
    }

    public void setEnableLifeCycleTracker(Application application, VAdEnhancerRegister vAdEnhancerRegister) {
        Log.e("VAdEnhancerAppLifecycle", "setEnableLifeCycleTracker()");

        this.vAdEnhancerRegister = vAdEnhancerRegister;

        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onActivityCreated" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onActivityResumed" + activity.getLocalClassName());
        if (vAdEnhancerRegister != null) {
            Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onActivityResumed USERONLINE true");
            vAdEnhancerRegister.USERONLINE = true;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }


    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onResume");
        if (vAdEnhancerRegister != null) {
            Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onResume USERONLINE true");
            vAdEnhancerRegister.USERONLINE = true;
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onPause(owner);
        Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onPause");
        if (vAdEnhancerRegister != null) {
            Log.e("VAdEnhancerAppLifecycle", "AppLifecycleTracker onPause USERONLINE false");
            vAdEnhancerRegister.USERONLINE = false;
        }
    }
}
