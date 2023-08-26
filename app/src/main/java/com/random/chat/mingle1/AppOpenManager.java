package com.random.chat.mingle1;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;

import javax.annotation.Nullable;

public class AppOpenManager implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks{

    private static final String LOG_TAG = "AppOpenManager";
  // private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";//test
  // private static final String AD_UNIT_ID = "ca-app-pub-7481777880413356/6877082119";//prod
   /* private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private long loadTime = 0;*/
    private Activity currentActivity;


    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final MyApplication myApplication;
    public AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    //ad up

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
    //activity wide up


    //app wide down
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        showAdIfAvailable();
    }
    private void showAdIfAvailable() {
        if (currentActivity != null) {
            VAdEnhancer vAdEnhancer = VAdEnhancer.getInstance(currentActivity);
            View view = vAdEnhancer.getSingleNativeAd(currentActivity);
            if (vAdEnhancer.isAppOpenAdReady(currentActivity)) {
                vAdEnhancer.showAppOpenAd();
            }else if (view!= null){
                showBetweenActivityAd(currentActivity, view);
            }
        }
    }
    private void showBetweenActivityAd(Activity currentActivity, View view) {
        Log.e("AppOpenManager", "activity " + currentActivity + " nativeAd " + view.getClass());
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(currentActivity);
        View mView = currentActivity.getLayoutInflater().inflate(R.layout.dialog_between_activity_ad, null);
        builder.setView(mView);
        android.app.AlertDialog NewCategory_dialog = builder.create();
        NewCategory_dialog.show();
        NewCategory_dialog.setCancelable(false);
        NewCategory_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout ad_layout = mView.findViewById(R.id.ad_layout);
        Button ad_close_button = mView.findViewById(R.id.ad_close_button);
        ad_layout.addView(view);
        ad_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Messages", "on click");
                NewCategory_dialog.dismiss();
            }
        });



    }
}
