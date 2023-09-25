package com.v.ad.vadenhancerLibrary.Classes.Modules;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdLoadListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.greedygame.core.app_open_ads.general.AppOpenAdsEventsListener;
import com.greedygame.core.app_open_ads.general.GGAppOpenAds;
import com.greedygame.core.models.general.AdErrors;
import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;
import com.v.ad.vadenhancerLibrary.SessionManager;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import java.util.Objects;

public class VAEAppOpenModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private CountDownTimer noAppOpenAdLoadedWaitingTimer;

    private static volatile VAEAppOpenModule INSTANCE = null;
    private VAdEnhancer vAdEnhancer;
    private VAdEnhancerRegister vAdEnhancerRegister;

    //admob
    private AppOpenAd ADMOB_APPOPEN_AD;
    private AppOpenAd.AppOpenAdLoadCallback ADMOB_APPOPEN_LISTENER;
    private FullScreenContentCallback ADMOB_APPOPEN_LISTENER_2;
    //gam
    private AppOpenAd GOOGLEADMANAGER_APPOPEN_AD;
    private AppOpenAd.AppOpenAdLoadCallback GOOGLEADMANAGER_APPOPEN_LISTENER;
    private FullScreenContentCallback GOOGLEADMANAGER_APPOPEN_LISTENER_2;
    //pangle
    private PAGAppOpenAd APP_OPEN_AD_PANGLE;
    private PAGAppOpenAdInteractionListener APP_OPEN_LISTENER_PANGLE;


    private VAEAppOpenModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAEAppOpenModule", "VAEAppOpenModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;
        vAdEnhancerRegister = registerx;

    }

    public static VAEAppOpenModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAEAppOpenModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAEAppOpenModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadAppOpenAds(Activity activity) {
        Log.e("VAEAppOpenModule", "loadAppOpenAds() vAdEnhancerRegister.keyValueTableHashMap " + vAdEnhancerRegister.keyValueTableHashMap);

        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAEAppOpenModule", "loadAppOpenAds() keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAEAppOpenModule", "loadAppOpenAds() keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAEAppOpenModule", "loadAppOpenAds() keyValueTableHashMap.get(activated) false");
        } else {
            Log.e("VAEAppOpenModule", "loadAppOpenAds() loading...");

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    loadPangleAppOpenAd(activity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("greedygames_activated"), "true")) {
                    loadGreedyGamesAppOpenAd(activity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    loadAdMobAppOpenAd(activity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    loadGoogleAdManagerAppOpenAd(activity);
                }
            }


        }
    }

    public boolean isAppOpenAdReady(Activity activity) {
        Log.e("VAEAppOpenModule", "isAppOpenAdReady() ");


        boolean ready = false;

        if (vAdEnhancerRegister.adNetworkOrderTableAppOpenList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableAppOpenList.size(); i++) {
                Log.e("VAEAppOpenModule", "isAppOpenAdReady  " + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "gam") && GOOGLEADMANAGER_APPOPEN_AD != null) {
                    Log.e("VAEAppOpenModule", "isAppOpenAdReady() ready googleadmanager");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "greedygames") && GGAppOpenAds.isAdLoaded()) {
                    Log.e("VAEAppOpenModule", "isAppOpenAdReady() ready greedygames");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "admob") && ADMOB_APPOPEN_AD != null) {
                    Log.e("VAEAppOpenModule", "isAppOpenAdReady() ready admob");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "pangle") && APP_OPEN_AD_PANGLE != null) {
                    Log.e("VAEAppOpenModule", "isAppOpenAdReady() ready pangle");
                    ready = true;
                    break;
                } else {
                    Log.e("VAEAppOpenModule", "isAppOpenAdReady() ready none");
                }

            }
        }

        if (ready) {
            if (noAppOpenAdLoadedWaitingTimer != null) {
                noAppOpenAdLoadedWaitingTimer.cancel();
            }
        } else {
            if (noAppOpenAdLoadedWaitingTimer == null) {
                noAppOpenAdLoadedWaitingTimer();
            }
        }

        return ready;
    }

    public void showAppOpenAd() {
        Log.e("VAEAppOpenModule", "showAppOpenAd() ");

        if (vAdEnhancerRegister.adNetworkOrderTableAppOpenList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableAppOpenList.size(); i++) {
                Log.e("VAEAppOpenModule", "showAppOpenAd  " + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "gam") && GOOGLEADMANAGER_APPOPEN_AD != null) {
                    GOOGLEADMANAGER_APPOPEN_AD.show(currentActivity);
                    if (noAppOpenAdLoadedWaitingTimer != null) {
                        noAppOpenAdLoadedWaitingTimer.cancel();
                    }
                    logFirebase("true", "appopenad", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "greedygames") && GGAppOpenAds.isAdLoaded()) {
                    GGAppOpenAds.show(currentActivity);
                    if (noAppOpenAdLoadedWaitingTimer != null) {
                        noAppOpenAdLoadedWaitingTimer.cancel();
                    }
                    logFirebase("true", "appopenad", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "admob") && ADMOB_APPOPEN_AD != null) {
                    ADMOB_APPOPEN_AD.show(currentActivity);
                    if (noAppOpenAdLoadedWaitingTimer != null) {
                        noAppOpenAdLoadedWaitingTimer.cancel();
                    }
                    logFirebase("true", "appopenad", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableAppOpenList.get(i).getAdnetwork(), "pangle") && APP_OPEN_AD_PANGLE != null) {
                    APP_OPEN_AD_PANGLE.show(currentActivity);
                    if (noAppOpenAdLoadedWaitingTimer != null) {
                        noAppOpenAdLoadedWaitingTimer.cancel();
                    }
                    logFirebase("true", "appopenad", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                    break;
                } else {

                }

            }
        }

    }

    public void noAppOpenAdLoadedWaitingTimer() {
        Log.e("VAEAppOpenModule", "noAppOpenAdLoadedWaitingTimer()");

        if (noAppOpenAdLoadedWaitingTimer != null) {
            noAppOpenAdLoadedWaitingTimer.cancel();
        }

        noAppOpenAdLoadedWaitingTimer = new CountDownTimer(30000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAEAppOpenModule", "noAppOpenAdLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAEAppOpenModule", "noAppOpenAdLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appopen"), "true")) {
                    loadAppOpenAds(currentActivity);
                }
                if (noAppOpenAdLoadedWaitingTimer != null) {
                    noAppOpenAdLoadedWaitingTimer.cancel();
                    noAppOpenAdLoadedWaitingTimer = null;
                }
            }
        }.start();
    }


    //pangle
    private void loadPangleAppOpenAd(Activity activity) {
        Log.e("VAEAppOpenModule", "loadPangleAppOpenAd()");

        vAdEnhancerRegister.PangleInit(activity);


        PAGAppOpenRequest request = new PAGAppOpenRequest();

        PAGAppOpenAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "appopen", currentActivity), request, new PAGAppOpenAdLoadListener() {

            @Override
            public void onAdLoaded(PAGAppOpenAd appOpenAd) {
                Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onAdLoaded()");
                APP_OPEN_AD_PANGLE = appOpenAd;
                APP_OPEN_AD_PANGLE.setAdInteractionListener(APP_OPEN_LISTENER_PANGLE);
            }

            @Override
            public void onError(int code, String message) {
                Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onError() " + message);
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onError() reloading in " + millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadPangleAppOpenAd(activity);
                            }
                        }
                    }
                }.start();
            }


        });


        APP_OPEN_LISTENER_PANGLE = new PAGAppOpenAdInteractionListener() {

            @Override
            public void onAdShowed() {
                Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onAdShowed()");
                loadPangleAppOpenAd(activity);
            }

            @Override
            public void onAdClicked() {
                Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onAdClicked()");
            }

            @Override
            public void onAdDismissed() {
                Log.e("VAEAppOpenModule", "loadPangleAppOpenAd onAdDismissed()");
            }
        };

    }

    //greedygames
    private void loadGreedyGamesAppOpenAd(Activity activity) {
        Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd()");

        vAdEnhancerRegister.GreedyGamesInit(activity);

        //GGAppOpenAds.setOrientation(AdOrientation.PORTRAIT);
        GGAppOpenAds.setListener(new AppOpenAdsEventsListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd onAdLoaded()");
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd onAdLoadFailed()");
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadGreedyGamesAppOpenAd(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdOpened() {
                Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd onAdOpened()");
            }

            @Override
            public void onAdShowFailed() {
                Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd onAdShowFailed()");

            }

            @Override
            public void onAdClosed() {
                Log.e("VAEAppOpenModule", "loadGreedyGamesAppOpenAd onAdClosed()");
                loadGreedyGamesAppOpenAd(activity);
            }
        });

        GGAppOpenAds.loadAd(vAdEnhancerRegister.getPlacementId("greedygames", "appopen", activity));

    }

    //admob
    private void loadAdMobAppOpenAd(Activity activity) {
        Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd()");

        vAdEnhancerRegister.AdmobInit(activity);

        ADMOB_APPOPEN_LISTENER = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdLoaded()");
                ADMOB_APPOPEN_AD = ad;
                ADMOB_APPOPEN_AD.setFullScreenContentCallback(ADMOB_APPOPEN_LISTENER_2);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdFailedToLoad()");
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadAdMobAppOpenAd(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(activity, vAdEnhancerRegister.getPlacementId("admob", "appopen", activity), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, ADMOB_APPOPEN_LISTENER);


        ADMOB_APPOPEN_LISTENER_2 = new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdClicked()");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdDismissedFullScreenContent()");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdFailedToShowFullScreenContent()");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdImpression()");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                Log.e("VAEAppOpenModule", "loadAdMobAppOpenAd onAdShowedFullScreenContent()");
                loadAdMobAppOpenAd(activity);
            }
        };

    }

    //gam
    private void loadGoogleAdManagerAppOpenAd(Activity activity) {
        Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd()");

        vAdEnhancerRegister.GoogleAdManagerInit(activity);

        GOOGLEADMANAGER_APPOPEN_LISTENER = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdLoaded()");
                GOOGLEADMANAGER_APPOPEN_AD = ad;
                GOOGLEADMANAGER_APPOPEN_AD.setFullScreenContentCallback(GOOGLEADMANAGER_APPOPEN_LISTENER_2);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdFailedToLoad()");
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadGoogleAdManagerAppOpenAd(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(activity, vAdEnhancerRegister.getPlacementId("gam", "appopen", activity), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, GOOGLEADMANAGER_APPOPEN_LISTENER);


        GOOGLEADMANAGER_APPOPEN_LISTENER_2 = new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdClicked()");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdDismissedFullScreenContent()");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdFailedToShowFullScreenContent()");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdImpression()");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                Log.e("VAEAppOpenModule", "loadGoogleAdManagerAppOpenAd onAdShowedFullScreenContent()");
                loadGoogleAdManagerAppOpenAd(activity);
            }
        };

    }


    //firebase logs
    private void logFirebase(String value, String type, String action, String source, String
            error_code, String error_message) {
        Bundle params = new Bundle();
        params.putString(action, value);
        if (!error_code.equals("")) {
            params.putString("error", value);
        }
        //FirebaseAnalytics.getInstance(activity).logEvent("a_" + type + "_" + action + "_" + source, params);
    }

    public void logFirebaseChoice(String action) {
        //FirebaseAnalytics.getInstance(activity).logEvent("ad_pop_up_choice_" + action, null);
    }
}
