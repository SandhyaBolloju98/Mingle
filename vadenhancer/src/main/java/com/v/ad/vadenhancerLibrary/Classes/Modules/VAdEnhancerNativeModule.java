package com.v.ad.vadenhancerLibrary.Classes.Modules;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.Native;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGMediaView;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.my.target.common.CachePolicy;
import com.my.target.common.models.IAdLoadingError;
import com.my.target.common.models.ImageData;
import com.my.target.nativeads.banners.NativePromoBanner;
import com.my.target.nativeads.factories.NativeViewsFactory;
import com.my.target.nativeads.views.IconAdView;
import com.my.target.nativeads.views.MediaAdView;
import com.my.target.nativeads.views.NativeAdContainer;
import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;
import com.v.ad.vadenhancerLibrary.R;
import com.v.ad.vadenhancerLibrary.SessionManager;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class VAdEnhancerNativeModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private static volatile VAdEnhancerNativeModule INSTANCE = null;
    private VAdEnhancer vAdEnhancer;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private CountDownTimer nativeAdsLoadedWaitingTimer;
    private CountDownTimer loopThroughLoadedNativeAdsTimer;
    private CountDownTimer noNativeAdsLoadedWaitingTimer;
    private String loopThroughLoadedNativeAdsActivity = "null";

    //native
    private NativeAd NATIVE_AD_GOOGLEADMANAGER;
    //meta
    private com.facebook.ads.NativeAd NATIVE_AD_META;
    //mytarget
    private NativePromoBanner NATIVE_AD_MYTARGET_PROMO_BANNER;
    private com.my.target.nativeads.NativeAd NATIVE_AD_MYTARGET;
    //pangle
    private PAGNativeAd NATIVE_AD_PANGLE;
    //admob
    private NativeAd NATIVE_AD_ADMOB;
    //appodeal
    private com.appodeal.ads.NativeAd NATIVE_AD_APPODEAL;
    private NativeCallbacks NATIVE_LISTENER_APPODEAL;
    private List<com.appodeal.ads.NativeAd> NATIVE_AD_APPODEAL_LIST;
    //inmobi
    private InMobiNative NATIVE_AD_INMOBI;
    //applovin
    private MaxNativeAdView NATIVE_AD_APPLOVIN;


    private boolean NATIVE_AD_GOOGLEADMANAGER_LOADED = false;
    private boolean NATIVE_AD_META_LOADED = false;
    private boolean NATIVE_AD_MYTARGET_LOADED = false;
    private boolean NATIVE_AD_PANGLE_LOADED = false;
    private boolean NATIVE_AD_ADMOB_LOADED = false;
    private boolean NATIVE_AD_APPODEAL_LOADED = false;
    private boolean NATIVE_AD_APPLOVIN_LOADED = false;
    private boolean NATIVE_AD_INMOBI_LOADED = false;


    private VAdEnhancerNativeModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAdEnhancerNativeModule", "VAdEnhancerNativeModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;
        vAdEnhancerRegister = registerx;

    }

    public static VAdEnhancerNativeModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAdEnhancerNativeModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancerNativeModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadNativeAds(String LOAD_AD) {
        Log.e("VAdEnhancerNativeModule", "loadNativeAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD);

        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAdEnhancerNativeModule", "loadNativeAds() register.keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAdEnhancerNativeModule", "loadNativeAds() register.keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAdEnhancerNativeModule", "loadNativeAds() register.keyValueTableHashMap.get(activated) false");
        } else if (Objects.equals(LOAD_AD, "all")) {
            Log.e("VAdEnhancerNativeModule", "loadBannerAds() loading...");


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("meta_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("meta_activated"), "true")) {
                    loadMetaNativeAd(currentActivity);//not tested //native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    loadPangleNativeAd(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appodeal_activated"), "true")) {
                    loadAppodealNativeAd(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("inmobi_activated"), "true")) {
                    loadInmobiNativeAd(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mytarget_activated"), "true")) {
                    loadMyTargetNativeAd(currentActivity);//not tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    loadAdmobNativeAd(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("applovin_activated"), "true")) {
                    loadApplovinNativeAd(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    loadGoogleAdManagerNativeAd(currentActivity);
                }
            }


        } else if (Objects.equals(LOAD_AD, "googleadmanager")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadGoogleAdManagerNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "meta")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadMetaNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "mytarget")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadMyTargetNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "pangle")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadPangleNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "admob")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadAdmobNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appodeal")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadAppodealNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "applovin")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadApplovinNativeAd(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "inmobi")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    loadInmobiNativeAd(currentActivity);
                }
            }.start();
        }


    }

    public void getMultipleNativeAds(Activity activity) {

        currentActivity = activity;

        if (loopThroughLoadedNativeAdsTimer != null) {
            loopThroughLoadedNativeAdsTimer.cancel();
        }

        if (nativeAdsLoadedWaitingTimer != null) {
            nativeAdsLoadedWaitingTimer.cancel();
        }

        if (vAdEnhancerRegister != null) {
            if (!vAdEnhancerRegister.USERONLINE) {
                Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds user not online");
                return;
            }
        }


        loopThroughLoadedNativeAdsActivity = currentActivity.getLocalClassName();

        loopThroughLoadedNativeAdsTimer = new CountDownTimer(600000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds " + millisUntilFinished + " activity " + currentActivity.getLocalClassName());

                if (vAdEnhancerRegister.keyValueTableHashMap != null) {
                    if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("native"), "false")) {
                        Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds native not enabled");
                        loopThroughLoadedNativeAdsTimer.cancel();
                    }
                }

                if (vAdEnhancerRegister.adNetworkOrderTableNativeList != null) {
                    for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableNativeList.size(); i++) {
                        Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds  " + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getPreference());


                        if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "gam") && NATIVE_AD_GOOGLEADMANAGER != null && NATIVE_AD_GOOGLEADMANAGER_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_GOOGLEADMANAGER");
                            NATIVE_AD_GOOGLEADMANAGER_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getGoogleAdManagerNativeAdView(currentActivity, NATIVE_AD_GOOGLEADMANAGER));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("googleadmanager");
                            logFirebase("true", "native", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "meta") && NATIVE_AD_META != null && NATIVE_AD_META_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_META");
                            NATIVE_AD_META_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getMetaNativeAdView(currentActivity, NATIVE_AD_META));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("meta");
                            logFirebase("true", "native", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "mytarget") && NATIVE_AD_MYTARGET != null && NATIVE_AD_MYTARGET_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_MYTARGET");
                            NATIVE_AD_MYTARGET_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getMyTargetNativeAdView(currentActivity, NATIVE_AD_MYTARGET_PROMO_BANNER, NATIVE_AD_MYTARGET));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("mytarget");
                            logFirebase("true", "native", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "pangle") && NATIVE_AD_PANGLE != null && NATIVE_AD_PANGLE_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_PANGLE");
                            NATIVE_AD_PANGLE_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getPangleNativeAdView(currentActivity, NATIVE_AD_PANGLE));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("pangle");
                            logFirebase("true", "native", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "admob") && NATIVE_AD_ADMOB != null && NATIVE_AD_ADMOB_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_ADMOB");
                            NATIVE_AD_ADMOB_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getAdmobNativeAdView(currentActivity, NATIVE_AD_ADMOB));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("admob");
                            logFirebase("true", "native", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "appodeal") && NATIVE_AD_APPODEAL != null && NATIVE_AD_APPODEAL_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_APPODEAL");
                            NATIVE_AD_APPODEAL_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getAppodealNativeAdView(currentActivity, NATIVE_AD_APPODEAL));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("appodeal");
                            logFirebase("true", "native", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "applovin") && NATIVE_AD_APPLOVIN != null && NATIVE_AD_APPLOVIN_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_APPLOVIN");
                            NATIVE_AD_APPLOVIN_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getApplovinNativeAdView(currentActivity, NATIVE_AD_APPLOVIN));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("applovin");
                            logFirebase("true", "native", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "inmobi") && NATIVE_AD_INMOBI != null && NATIVE_AD_INMOBI_LOADED) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found NATIVE_AD_INMOBI");
                            NATIVE_AD_INMOBI_LOADED = false;
                            if (loopThroughLoadedNativeAdsTimer != null) {
                                loopThroughLoadedNativeAdsTimer.cancel();
                            }
                            if (noNativeAdsLoadedWaitingTimer != null) {
                                noNativeAdsLoadedWaitingTimer.cancel();
                            }
                            sendNativeAd(getInmobiNativeAdView(currentActivity, NATIVE_AD_INMOBI));
                            nativeAdsLoadedWaitingTimer();
                            loadNativeAds("inmobi");
                            logFirebase("true", "native", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                            break;
                        } else {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found non");
                        }

                        if (i == (vAdEnhancerRegister.adNetworkOrderTableNativeList.size() - 1)) {
                            Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds found non in loop");
                            if (noNativeAdsLoadedWaitingTimer == null) {
                                noNativeAdsLoadedWaitingTimer();
                            }
                        }

                    }
                }

            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerNativeModule", "getMultipleNativeAds onFinish() ");
            }
        }.start();
    }

    public void noNativeAdsLoadedWaitingTimer() {
        Log.e("VAdEnhancerNativeModule", "noNativeAdsLoadedWaitingTimer()");

        if (noNativeAdsLoadedWaitingTimer != null) {
            noNativeAdsLoadedWaitingTimer.cancel();
        }

        noNativeAdsLoadedWaitingTimer = new CountDownTimer(30000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerNativeModule", "noNativeAdsLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerNativeModule", "noNativeAdsLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("native"), "true")) {
                    loadNativeAds("all");
                }
                if (noNativeAdsLoadedWaitingTimer != null) {
                    noNativeAdsLoadedWaitingTimer.cancel();
                    noNativeAdsLoadedWaitingTimer = null;
                }
            }
        }.start();
    }

    public void nativeAdsLoadedWaitingTimer() {
        Log.e("VAdEnhancerNativeModule", "nativeAdsLoadedWaitingTimer()");

        if (nativeAdsLoadedWaitingTimer != null) {
            nativeAdsLoadedWaitingTimer.cancel();
        }

        nativeAdsLoadedWaitingTimer = new CountDownTimer(vAdEnhancerRegister.NATIVELOADEDWAITINGTIMER, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerNativeModule", "nativeAdsLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerNativeModule", "nativeAdsLoadedWaitingTimer onFinish() ");
                if (Objects.equals(loopThroughLoadedNativeAdsActivity, currentActivity.getLocalClassName())) {
                    getMultipleNativeAds(currentActivity);
                }
            }
        }.start();
    }

    public View getSingleNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd()");

        currentActivity = activity;

        View view = null;

        if (vAdEnhancerRegister.adNetworkOrderTableNativeList != null) {
            Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd() register.adNetworkOrderTableNativeList.size() " + vAdEnhancerRegister.adNetworkOrderTableNativeList.size());
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableNativeList.size(); i++) {
                Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd " + i + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "gam") && NATIVE_AD_GOOGLEADMANAGER != null && NATIVE_AD_GOOGLEADMANAGER_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_GOOGLEADMANAGER");
                    NATIVE_AD_GOOGLEADMANAGER_LOADED = false;
                    loadNativeAds("googleadmanager");
                    logFirebase("true", "native", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getGoogleAdManagerNativeAdView(currentActivity, NATIVE_AD_GOOGLEADMANAGER);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "meta") && NATIVE_AD_META != null && NATIVE_AD_META_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_META");
                    NATIVE_AD_META_LOADED = false;
                    loadNativeAds("meta");
                    logFirebase("true", "native", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getMetaNativeAdView(currentActivity, NATIVE_AD_META);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "mytarget") && NATIVE_AD_MYTARGET != null && NATIVE_AD_MYTARGET_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_MYTARGET");
                    NATIVE_AD_MYTARGET_LOADED = false;
                    loadNativeAds("mytarget");
                    logFirebase("true", "native", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getMyTargetNativeAdView(currentActivity, NATIVE_AD_MYTARGET_PROMO_BANNER, NATIVE_AD_MYTARGET);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "pangle") && NATIVE_AD_PANGLE != null && NATIVE_AD_PANGLE_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_PANGLE");
                    NATIVE_AD_PANGLE_LOADED = false;
                    loadNativeAds("pangle");
                    logFirebase("true", "native", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getPangleNativeAdView(currentActivity, NATIVE_AD_PANGLE);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "admob") && NATIVE_AD_ADMOB != null && NATIVE_AD_ADMOB_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_ADMOB");
                    NATIVE_AD_ADMOB_LOADED = false;
                    loadNativeAds("admob");
                    logFirebase("true", "native", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getAdmobNativeAdView(currentActivity, NATIVE_AD_ADMOB);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "appodeal") && NATIVE_AD_APPODEAL != null && NATIVE_AD_APPODEAL_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_APPODEAL");
                    NATIVE_AD_APPODEAL_LOADED = false;
                    loadNativeAds("appodeal");
                    logFirebase("true", "native", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getAppodealNativeAdView(currentActivity, NATIVE_AD_APPODEAL);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "applovin") && NATIVE_AD_APPLOVIN != null && NATIVE_AD_APPLOVIN_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_APPLOVIN");
                    NATIVE_AD_APPLOVIN_LOADED = false;
                    loadNativeAds("applovin");
                    logFirebase("true", "native", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getApplovinNativeAdView(currentActivity, NATIVE_AD_APPLOVIN);
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableNativeList.get(i).getAdnetwork(), "inmobi") && NATIVE_AD_INMOBI != null && NATIVE_AD_INMOBI_LOADED) {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found NATIVE_AD_INMOBI");
                    NATIVE_AD_INMOBI_LOADED = false;
                    loadNativeAds("inmobi");
                    logFirebase("true", "native", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                    if (noNativeAdsLoadedWaitingTimer != null) {
                        noNativeAdsLoadedWaitingTimer.cancel();
                    }
                    view = getInmobiNativeAdView(currentActivity, NATIVE_AD_INMOBI);
                    break;
                } else {
                    Log.e("VAdEnhancerNativeModule", "getAvailableNativeAd found none");

                }

            }
        }


        return view;
    }

    public void sendNativeAd(View view) {
        Log.e("VAdEnhancerNativeModule", "sendNativeAd() ");

        if (view.getParent() != null) {
            Log.e("VAdEnhancerNativeModule", "sendNativeAd has parent " + view);
        }

        try {
            Method method = currentActivity.getClass().getMethod("placeNativeAd", View.class);
            method.invoke(currentActivity, view);
        } catch (Exception e) {
            Log.e("VAdEnhancerNativeModule", "exception error " + e);
        }

    }


    private void loadGoogleAdManagerNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd()");

        logFirebase("true", "native", "requested", "googleadmanager", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GoogleAdManagerInit(activity);

        AdLoader adLoader = new AdLoader.Builder(activity, vAdEnhancerRegister.getPlacementId("gam", "native", activity))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd() onNativeAdLoaded");
                        NATIVE_AD_GOOGLEADMANAGER = nativeAd;
                        NATIVE_AD_GOOGLEADMANAGER_LOADED = true;
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd onAdFailedToLoad() " + adError.getMessage());
                        logFirebase("true", "native", "failed", "googleadmanager", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                        new CountDownTimer(60000, 5000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                if (vAdEnhancerRegister != null) {
                                    if (vAdEnhancerRegister.USERONLINE) {
                                        loadGoogleAdManagerNativeAd(activity);
                                    }
                                }
                            }
                        }.start();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd() onAdClicked");
                        logFirebase("true", "native", "clicked", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd() onAdClosed");
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd() onAdImpression");
                        logFirebase("true", "native", "impression", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        Log.e("VAdEnhancerNativeModule", "loadGoogleAdManagerNativeAd() onAdOpened");
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();

        adLoader.loadAd(new AdManagerAdRequest.Builder().build());
    }

    private View getGoogleAdManagerNativeAdView(Activity activity, NativeAd nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getGoogleAdManagerNativeAdView()");

        NativeAdView nativeAdViewInflated = (NativeAdView) activity.getLayoutInflater()
                .inflate(R.layout.card_google_native_ad, null);

        NativeAdView native_ad_view_layout = (NativeAdView) nativeAdViewInflated.findViewById(R.id.native_ad_view_layout);
        CircleImageView ad_profile_image = (CircleImageView) nativeAdViewInflated.findViewById(R.id.ad_profile_image);
        TextView ad_headline = (TextView) nativeAdViewInflated.findViewById(R.id.ad_headline);
        TextView ad_body = (TextView) nativeAdViewInflated.findViewById(R.id.ad_body);
        MediaView ad_media_view = (MediaView) nativeAdViewInflated.findViewById(R.id.ad_media_view);

        if (nativeAd.getIcon() != null) {
            Log.e("VAdEnhancerNativeModule", "getGoogleAdManagerNativeAdView() nativeAd.getIcon() " + nativeAd.getIcon().getUri());
            Glide.with(activity)
                    .load(nativeAd.getIcon().getUri())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.circle_background_blue)
                    .error(R.drawable.circle_background_blue)
                    .into(ad_profile_image);
            native_ad_view_layout.setIconView(ad_profile_image);
        } else {
            ad_profile_image.setVisibility(View.GONE);
        }

        if (nativeAd.getHeadline() != null) {
            Log.e("VAdEnhancerNativeModule", "getGoogleAdManagerNativeAdView() nativeAd.getHeadline()");
            ad_headline.setText(nativeAd.getHeadline());
            native_ad_view_layout.setHeadlineView(ad_headline);
        } else {
            ad_headline.setVisibility(View.GONE);
        }

        if (nativeAd.getBody() != null) {
            Log.e("VAdEnhancerNativeModule", "getGoogleAdManagerNativeAdView() nativeAd.getBody()");
            ad_body.setText(nativeAd.getBody());
            native_ad_view_layout.setBodyView(ad_body);
        } else {
            ad_body.setVisibility(View.GONE);
        }

        if (nativeAd.getMediaContent() != null) {
            Log.e("VAdEnhancerNativeModule", "getGoogleAdManagerNativeAdView() nativeAd.getMediaContent()");
            ad_media_view.setMediaContent(nativeAd.getMediaContent());
            native_ad_view_layout.setMediaView(ad_media_view);

            ad_media_view.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;
                        imageView.setAdjustViewBounds(true);
                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });

        } else {
            ad_media_view.setVisibility(View.GONE);
        }

        native_ad_view_layout.setNativeAd(nativeAd);


        return nativeAdViewInflated;
    }

    private void loadMetaNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd()");

        logFirebase("true", "native", "requested", "meta", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MetaInit(activity);


        NATIVE_AD_META = new com.facebook.ads.NativeAd(activity, vAdEnhancerRegister.getPlacementId("meta", "native", activity));


        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd() onMediaDownloaded");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd() onError " + adError.getErrorMessage());
                logFirebase("true", "native", "failed", "meta", String.valueOf(adError.getErrorCode()), String.valueOf(adError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadMetaNativeAd(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd() onAdLoaded");
                NATIVE_AD_META_LOADED = true;
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd() onAdClicked");
                logFirebase("true", "native", "clicked", "meta", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("VAdEnhancerNativeModule", "loadMetaNativeAd() onLoggingImpression");
                logFirebase("true", "native", "impression", "meta", String.valueOf(""), String.valueOf(""));
            }
        };

        NATIVE_AD_META.loadAd(NATIVE_AD_META.buildLoadAdConfig()
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .withAdListener(nativeAdListener)
                .build());
    }

    private View getMetaNativeAdView(Activity activity, com.facebook.ads.NativeAd nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getMetaNativeAdView()");

        NativeAdLayout nativeAdViewInflated = (NativeAdLayout) activity.getLayoutInflater()
                .inflate(R.layout.card_meta_native_ad, null);

        NativeAdLayout native_ad_view_layout = (NativeAdLayout) nativeAdViewInflated.findViewById(R.id.native_ad_view_layout);
        CircleImageView ad_profile_image = (CircleImageView) nativeAdViewInflated.findViewById(R.id.ad_profile_image);
        TextView ad_headline = (TextView) nativeAdViewInflated.findViewById(R.id.ad_headline);
        TextView ad_body = (TextView) nativeAdViewInflated.findViewById(R.id.ad_body);
        com.facebook.ads.MediaView ad_media_view = nativeAdViewInflated.findViewById(R.id.ad_media_view);

        if (nativeAd.getAdIcon() != null) {
            Log.e("VAdEnhancerNativeModule", "getMetaNativeAdView() nativeAd.getIcon() " + nativeAd.getAdIcon().getUrl());
            Glide.with(activity)
                    .load(nativeAd.getAdIcon().getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.circle_background_blue)
                    .error(R.drawable.circle_background_blue)
                    .into(ad_profile_image);
        } else {
            ad_profile_image.setVisibility(View.GONE);
        }

        if (nativeAd.getAdHeadline() != null) {
            Log.e("VAdEnhancerNativeModule", "getMetaNativeAdView() nativeAd.getHeadline()");
            ad_headline.setText(nativeAd.getAdHeadline());
        } else {
            ad_headline.setVisibility(View.GONE);
        }

        if (nativeAd.getAdBodyText() != null) {
            Log.e("VAdEnhancerNativeModule", "getMetaNativeAdView() nativeAd.getBody()");
            ad_body.setText(nativeAd.getAdBodyText());
        } else {
            ad_body.setVisibility(View.GONE);
        }

        if (nativeAd.getAdCoverImage() != null) {
            Log.e("VAdEnhancerNativeModule", "getMetaNativeAdView() nativeAd.getMediaContent()");
           /* ad_media_view.set(nativeAd.getAdCoverImage());
            native_ad_view_layout.setMediaView(ad_media_view);

            ad_media_view.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;
                        imageView.setAdjustViewBounds(true);
                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });*/

        } else {
            ad_media_view.setVisibility(View.GONE);
        }


        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(ad_headline);
        clickableViews.add(ad_body);

        nativeAd.registerViewForInteraction(nativeAdViewInflated, ad_media_view, ad_profile_image, clickableViews);


        return nativeAdViewInflated;
    }

    private void loadMyTargetNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd()");

        logFirebase("true", "native", "requested", "mytarget", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MyTargetInit(activity);


        NATIVE_AD_MYTARGET = new com.my.target.nativeads.NativeAd(vAdEnhancerRegister.getPlacementIdInt("mytarget", "native", activity), activity);

        NATIVE_AD_MYTARGET.setListener(new com.my.target.nativeads.NativeAd.NativeAdListener() {

            @Override
            public void onLoad(@NonNull NativePromoBanner nativePromoBanner, @NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onLoad");
                NATIVE_AD_MYTARGET_PROMO_BANNER = nativePromoBanner;
                NATIVE_AD_MYTARGET = nativeAd;
                NATIVE_AD_MYTARGET_LOADED = true;
            }

            @Override
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onNoAd " + iAdLoadingError.getMessage());
                logFirebase("true", "native", "failed", "mytarget", String.valueOf(iAdLoadingError.getCode()), String.valueOf(iAdLoadingError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadMyTargetNativeAd(activity);
                            }
                        }
                    }
                }.start();
            }



            @Override
            public void onClick(@NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onClick");
                logFirebase("true", "native", "clicked", "mytarget", String.valueOf(""), String.valueOf(""));

            }

            @Override
            public void onShow(@NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onShow");
                logFirebase("true", "native", "impression", "mytarget", String.valueOf(""), String.valueOf(""));

            }

            @Override
            public void onVideoPlay(@NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onVideoPlay");

            }

            @Override
            public void onVideoPause(@NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onVideoPause");

            }

            @Override
            public void onVideoComplete(@NonNull com.my.target.nativeads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadMyTargetNativeAd() onVideoComplete");

            }
        });

        NATIVE_AD_MYTARGET.setCachePolicy(CachePolicy.ALL);
        NATIVE_AD_MYTARGET.load();


    }

    private View getMyTargetNativeAdView(Activity activity, NativePromoBanner
            nativePromoBanner, com.my.target.nativeads.NativeAd nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getMyTargetNativeAdView()");

        String title = nativePromoBanner.getTitle();
        String description = nativePromoBanner.getDescription();
        ImageData icon = nativePromoBanner.getIcon();
        String ctaText = nativePromoBanner.getCtaText();

        LinearLayout adViewLayout = new LinearLayout(activity);
        adViewLayout.setOrientation(LinearLayout.VERTICAL);

        TextView titleView = new TextView(activity);
        titleView.setText(title);
        adViewLayout.addView(titleView);

        TextView descriptionView = new TextView(activity);
        titleView.setText(description);
        adViewLayout.addView(descriptionView);

        MediaAdView mediaView = NativeViewsFactory.getMediaAdView(activity);
        adViewLayout.addView(mediaView);

        IconAdView iconView = new IconAdView(activity);
        adViewLayout.addView(iconView);

        Button btn = new Button(activity);
        btn.setText(ctaText);
        adViewLayout.addView(btn);

        NativeAdContainer nativeAdContainer = new NativeAdContainer(activity);
        nativeAdContainer.addView(adViewLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        nativeAd.registerView(adViewLayout);

        return nativeAdContainer;

    }

    private void loadPangleNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadPangleNativeAd()");

        logFirebase("true", "native", "requested", "pangle", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.PangleInit(activity);


        PAGNativeAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "native", activity),
                new PAGNativeRequest(),
                new PAGNativeAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e("VAdEnhancerNativeModule", "loadPangleNativeAd() onError " + message);
                        logFirebase("true", "native", "failed", "pangle", String.valueOf(code), String.valueOf(message));
                        new CountDownTimer(60000, 5000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                if (vAdEnhancerRegister != null) {
                                    if (vAdEnhancerRegister.USERONLINE) {
                                        loadPangleNativeAd(activity);
                                    }
                                }
                            }
                        }.start();

                    }

                    @Override
                    public void onAdLoaded(PAGNativeAd pagNativeAd) {
                        Log.e("VAdEnhancerNativeModule", "loadPangleNativeAd() onAdLoaded");
                        NATIVE_AD_PANGLE = pagNativeAd;
                        NATIVE_AD_PANGLE_LOADED = true;
                    }
                });

    }

    private View getPangleNativeAdView(Activity activity, PAGNativeAd pagNativeAd) {
        Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView()");

        ConstraintLayout nativeAdViewInflated = (ConstraintLayout) activity.getLayoutInflater()
                .inflate(R.layout.card_pangle_native_ad, null);

        CircleImageView ad_profile_image = (CircleImageView) nativeAdViewInflated.findViewById(R.id.ad_profile_image);
        TextView ad_headline = (TextView) nativeAdViewInflated.findViewById(R.id.ad_headline);
        TextView ad_body = (TextView) nativeAdViewInflated.findViewById(R.id.ad_body);
        PAGMediaView ad_media_view = (PAGMediaView) nativeAdViewInflated.findViewById(R.id.ad_media_view);

        if (pagNativeAd.getNativeAdData().getIcon().getImageUrl() != null) {
            Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() nativeAd.getIcon() " + pagNativeAd.getNativeAdData().getIcon().getImageUrl());
            Glide.with(activity)
                    .load(pagNativeAd.getNativeAdData().getIcon().getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.circle_background_blue)
                    .error(R.drawable.circle_background_blue)
                    .into(ad_profile_image);
        } else {
            ad_profile_image.setVisibility(View.GONE);
        }

        if (pagNativeAd.getNativeAdData().getTitle() != null) {
            Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() nativeAd.getHeadline()");
            ad_headline.setText(pagNativeAd.getNativeAdData().getTitle());
        } else {
            ad_headline.setVisibility(View.GONE);
        }

        if (pagNativeAd.getNativeAdData().getDescription() != null) {
            Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() nativeAd.getBody()");
            ad_body.setText(pagNativeAd.getNativeAdData().getDescription());
        } else {
            ad_body.setVisibility(View.GONE);
        }

        if (pagNativeAd.getNativeAdData().getMediaView() != null) {
            Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() nativeAd.getMediaContent()");
            ad_media_view.addView(pagNativeAd.getNativeAdData().getMediaView());
        } else {
            ad_media_view.setVisibility(View.GONE);
        }

        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(ad_profile_image);
        clickViewList.add(ad_headline);
        clickViewList.add(ad_body);
        clickViewList.add(ad_media_view);

        List<View> creativeViewList = new ArrayList<>();
        creativeViewList.add(ad_media_view);

        pagNativeAd.registerViewForInteraction((ViewGroup) nativeAdViewInflated, clickViewList, null, null, new PAGNativeAdInteractionListener() {
            @Override
            public void onAdShowed() {
                Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() onAdShowed()");
                logFirebase("true", "native", "impression", "pangle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() onAdClicked()");
                logFirebase("true", "native", "click", "pangle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDismissed() {
                Log.e("VAdEnhancerNativeModule", "getPangleNativeAdView() onAdDismissed()");
            }
        });

        return nativeAdViewInflated;
    }

    private void loadAdmobNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd()");

        logFirebase("true", "native", "requested", "admob", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AdmobInit(activity);

        AdLoader adLoader = new AdLoader.Builder(activity, vAdEnhancerRegister.getPlacementId("admob", "native", activity))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd() onNativeAdLoaded");
                        NATIVE_AD_ADMOB = nativeAd;
                        NATIVE_AD_ADMOB_LOADED = true;
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd onAdFailedToLoad() " + adError.getMessage());
                        logFirebase("true", "native", "failed", "admob", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                        new CountDownTimer(60000, 5000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                if (vAdEnhancerRegister != null) {
                                    if (vAdEnhancerRegister.USERONLINE) {
                                        loadAdmobNativeAd(activity);
                                    }
                                }
                            }
                        }.start();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd() onAdClicked");
                        logFirebase("true", "native", "clicked", "admob", String.valueOf(""), String.valueOf(""));
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd() onAdClosed");
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd() onAdImpression");
                        logFirebase("true", "native", "impression", "admob", String.valueOf(""), String.valueOf(""));
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        Log.e("VAdEnhancerNativeModule", "loadAdmobNativeAd() onAdOpened");
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private View getAdmobNativeAdView(Activity activity, NativeAd nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getAdmobNativeAdView()");

        NativeAdView nativeAdViewInflated = (NativeAdView) activity.getLayoutInflater()
                .inflate(R.layout.card_google_native_ad, null);

        NativeAdView native_ad_view_layout = (NativeAdView) nativeAdViewInflated.findViewById(R.id.native_ad_view_layout);
        CircleImageView ad_profile_image = (CircleImageView) nativeAdViewInflated.findViewById(R.id.ad_profile_image);
        TextView ad_headline = (TextView) nativeAdViewInflated.findViewById(R.id.ad_headline);
        TextView ad_body = (TextView) nativeAdViewInflated.findViewById(R.id.ad_body);
        MediaView ad_media_view = (MediaView) nativeAdViewInflated.findViewById(R.id.ad_media_view);

        if (nativeAd.getIcon() != null) {
            Log.e("VAdEnhancerNativeModule", "getAdmobNativeAdView() nativeAd.getIcon() " + nativeAd.getIcon().getUri());
            Glide.with(activity)
                    .load(nativeAd.getIcon().getUri())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.circle_background_blue)
                    .error(R.drawable.circle_background_blue)
                    .into(ad_profile_image);
            native_ad_view_layout.setIconView(ad_profile_image);
        } else {
            ad_profile_image.setVisibility(View.GONE);
        }

        if (nativeAd.getHeadline() != null) {
            Log.e("VAdEnhancerNativeModule", "getAdmobNativeAdView() nativeAd.getHeadline()");
            ad_headline.setText(nativeAd.getHeadline());
            native_ad_view_layout.setHeadlineView(ad_headline);
        } else {
            ad_headline.setVisibility(View.GONE);
        }

        if (nativeAd.getBody() != null) {
            Log.e("VAdEnhancerNativeModule", "getAdmobNativeAdView() nativeAd.getBody()");
            ad_body.setText(nativeAd.getBody());
            native_ad_view_layout.setBodyView(ad_body);
        } else {
            ad_body.setVisibility(View.GONE);
        }

        if (nativeAd.getMediaContent() != null) {
            Log.e("VAdEnhancerNativeModule", "getAdmobNativeAdView() nativeAd.getMediaContent()");
            ad_media_view.setMediaContent(nativeAd.getMediaContent());
            native_ad_view_layout.setMediaView(ad_media_view);

            ad_media_view.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;
                        imageView.setAdjustViewBounds(true);
                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });

        } else {
            ad_media_view.setVisibility(View.GONE);
        }

        native_ad_view_layout.setNativeAd(nativeAd);


        return nativeAdViewInflated;
    }

    private void loadAppodealNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd()");

        logFirebase("true", "native", "requested", "appodeal", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppODealInit(activity);

        Appodeal.setRequiredNativeMediaAssetType(Native.MediaAssetType.ALL);
        Appodeal.setNativeAdType(Native.NativeAdType.Auto);

        NATIVE_AD_APPODEAL_LIST = Appodeal.getNativeAds(1);

        NATIVE_LISTENER_APPODEAL = new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd() onNativeAdLoaded");
                if (!NATIVE_AD_APPODEAL_LIST.isEmpty()) {
                    NATIVE_AD_APPODEAL = NATIVE_AD_APPODEAL_LIST.get(0);
                    NATIVE_AD_APPODEAL_LOADED = true;
                }
            }

            @Override
            public void onNativeFailedToLoad() {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd onAdFailedToLoad() ");
                logFirebase("true", "native", "failed", "appodeal", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadAppodealNativeAd(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onNativeShown(com.appodeal.ads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd() onNativeShown");
                logFirebase("true", "native", "impression", "appodeal", String.valueOf(""), String.valueOf(""));

            }

            @Override
            public void onNativeShowFailed(com.appodeal.ads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd() onNativeShowFailed");

            }

            @Override
            public void onNativeClicked(com.appodeal.ads.NativeAd nativeAd) {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd() onNativeClicked");
                logFirebase("true", "native", "click", "appodeal", String.valueOf(""), String.valueOf(""));

            }

            @Override
            public void onNativeExpired() {
                Log.e("VAdEnhancerNativeModule", "loadAppodealNativeAd() onNativeExpired");

            }
        };


        Appodeal.setNativeCallbacks(NATIVE_LISTENER_APPODEAL);


    }

    private View getAppodealNativeAdView(Activity activity, com.appodeal.ads.NativeAd nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getAppodealNativeAdView()");

        NativeAdViewContentStream nativeAdViewInflated = (NativeAdViewContentStream) activity.getLayoutInflater()
                .inflate(R.layout.card_appodeal_native_ad, null);


        nativeAdViewInflated.setNativeAd(nativeAd);


        return nativeAdViewInflated;
    }

    private void loadApplovinNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadApplovinNativeAd()");

        logFirebase("true", "native", "requested", "applovin", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MaxInit(activity);

        MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(vAdEnhancerRegister.getPlacementId("applovin", "native", activity), activity);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                Log.e("VAdEnhancerNativeModule", "loadApplovinNativeAd() onNativeAdLoaded");
                NATIVE_AD_APPLOVIN = nativeAdView;
                NATIVE_AD_APPLOVIN_LOADED = true;
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                Log.e("VAdEnhancerNativeModule", "loadApplovinNativeAd onAdFailedToLoad() ");
                logFirebase("true", "native", "failed", "applovin", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadApplovinNativeAd(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad) {
                Log.e("VAdEnhancerNativeModule", "loadApplovinNativeAd() onNativeAdClicked");
                logFirebase("true", "native", "click", "applovin", String.valueOf(""), String.valueOf(""));
            }
        });

        nativeAdLoader.loadAd();

    }

    private View getApplovinNativeAdView(Activity activity, MaxNativeAdView nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getAppodealNativeAdView()");

        MaxNativeAdView nativeAdViewInflated = (MaxNativeAdView) activity.getLayoutInflater()
                .inflate(R.layout.card_applovin_native_ad, null);


        nativeAdViewInflated.addView(nativeAd);


        return nativeAdViewInflated;
    }

    private void loadInmobiNativeAd(Activity activity) {
        Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd()");

        logFirebase("true", "native", "requested", "inmobi", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.InMobiInit(activity);

        NativeAdEventListener nativeAdEventListener = new NativeAdEventListener() {

            @Override
            public void onAdClicked(@NonNull InMobiNative inMobiNative) {
                super.onAdClicked(inMobiNative);
                Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd() onAdClicked");
                logFirebase("true", "native", "click", "inmobi", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onAdFetchSuccessful(@NonNull InMobiNative inMobiNative, @NonNull AdMetaInfo adMetaInfo) {
                super.onAdFetchSuccessful(inMobiNative, adMetaInfo);
                Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd() onAdFetchSuccessful");
                NATIVE_AD_INMOBI = inMobiNative;
                NATIVE_AD_INMOBI_LOADED = true;
            }

            @Override
            public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative, @NonNull AdMetaInfo adMetaInfo) {
                super.onAdLoadSucceeded(inMobiNative, adMetaInfo);
                Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd() onAdLoadSucceeded");
                NATIVE_AD_INMOBI = inMobiNative;
                NATIVE_AD_INMOBI_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdLoadFailed(inMobiNative, inMobiAdRequestStatus);
                Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd() onAdLoadFailed " + inMobiAdRequestStatus.getMessage());
                logFirebase("true", "native", "failed", "inmobi", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInmobiNativeAd(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression(@NonNull InMobiNative inMobiNative) {
                super.onAdImpression(inMobiNative);
                Log.e("VAdEnhancerNativeModule", "loadInmobiNativeAd() onAdImpression");
                logFirebase("true", "native", "impression", "inmobi", String.valueOf(""), String.valueOf(""));
            }
        };

        NATIVE_AD_INMOBI = new InMobiNative(activity, vAdEnhancerRegister.getPlacementIdInt("inmobi", "native", activity), nativeAdEventListener);
        NATIVE_AD_INMOBI.load();

    }

    private View getInmobiNativeAdView(Activity activity, InMobiNative nativeAd) {
        Log.e("VAdEnhancerNativeModule", "getInmobiNativeAdView()");

        ConstraintLayout nativeAdViewInflated = (ConstraintLayout) activity.getLayoutInflater()
                .inflate(R.layout.card_inmobi_native_ad, null);

        ConstraintLayout native_ad_view_layout = (ConstraintLayout) nativeAdViewInflated.findViewById(R.id.native_ad_view_layout);
        CircleImageView ad_profile_image = (CircleImageView) nativeAdViewInflated.findViewById(R.id.ad_profile_image);
        TextView ad_headline = (TextView) nativeAdViewInflated.findViewById(R.id.ad_headline);
        TextView ad_body = (TextView) nativeAdViewInflated.findViewById(R.id.ad_body);
        MediaView ad_media_view = (MediaView) nativeAdViewInflated.findViewById(R.id.ad_media_view);

        if (nativeAd.getAdIconUrl() != null) {
            Log.e("VAdEnhancerNativeModule", "getInmobiNativeAdView() nativeAd.getIcon() " + nativeAd.getAdIconUrl());
            Glide.with(activity)
                    .load(nativeAd.getAdIconUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.circle_background_blue)
                    .error(R.drawable.circle_background_blue)
                    .into(ad_profile_image);
        } else {
            ad_profile_image.setVisibility(View.GONE);
        }

        if (nativeAd.getAdTitle() != null) {
            Log.e("VAdEnhancerNativeModule", "getInmobiNativeAdView() nativeAd.getHeadline()");
            ad_headline.setText(nativeAd.getAdTitle());
        } else {
            ad_headline.setVisibility(View.GONE);
        }

        if (nativeAd.getAdDescription() != null) {
            Log.e("VAdEnhancerNativeModule", "getInmobiNativeAdView() nativeAd.getBody()");
            ad_body.setText(nativeAd.getAdDescription());
        } else {
            ad_body.setVisibility(View.GONE);
        }

        View primaryView = nativeAd.getPrimaryViewOfWidth(activity, nativeAdViewInflated, null, 320);

        if (primaryView != null) {
            Log.e("VAdEnhancerNativeModule", "getInmobiNativeAdView() nativeAd.getMediaContent()");
            ad_media_view.addView(primaryView);
        } else {
            ad_media_view.setVisibility(View.GONE);
        }


        return nativeAdViewInflated;
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
