package com.v.ad.vadenhancerLibrary.Classes.Modules;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.appnext.banners.BannerAdRequest;
import com.appnext.banners.BannerSize;
import com.appnext.core.AppnextAdCreativeType;
import com.appnext.core.AppnextError;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.MrecCallbacks;
import com.appodeal.ads.MrecView;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAd;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAdLoadListener;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerRequest;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerSize;
import com.chartboost.sdk.ads.Banner;
import com.chartboost.sdk.callbacks.BannerCallback;
import com.chartboost.sdk.events.CacheError;
import com.chartboost.sdk.events.CacheEvent;
import com.chartboost.sdk.events.ClickError;
import com.chartboost.sdk.events.ClickEvent;
import com.chartboost.sdk.events.ImpressionEvent;
import com.chartboost.sdk.events.ShowError;
import com.chartboost.sdk.events.ShowEvent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.greedygame.core.adview.general.AdLoadCallback;
import com.greedygame.core.adview.general.GGAdview;
import com.greedygame.core.models.general.AdErrors;
import com.hyprmx.android.sdk.banner.HyprMXBannerListener;
import com.hyprmx.android.sdk.banner.HyprMXBannerSize;
import com.hyprmx.android.sdk.banner.HyprMXBannerView;
import com.hyprmx.android.sdk.core.HyprMXErrors;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;
import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;
import com.v.ad.vadenhancerLibrary.SessionManager;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;
import com.vungle.warren.AdConfig;
import com.vungle.warren.BannerAdConfig;
import com.vungle.warren.Banners;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.VungleBanner;
import com.vungle.warren.error.VungleException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class VAdEnhancerMrecModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private static volatile VAdEnhancerMrecModule INSTANCE = null;
    private VAdEnhancer vAdEnhancer;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private CountDownTimer mrecLoadedWaitingTimer;
    private CountDownTimer loopThroughLoadedMrecsTimer;
    private CountDownTimer noMrecLoadedWaitingTimer;
    private String loopThroughLoadedMrecsActivity = "null";

    //ironsource
    private IronSourceBannerLayout MREC_AD_IRONSOURCE;
    private BannerListener MREC_LISTENER_IRONSOURCE;
    //Adcolony
    private AdColonyAdView MREC_AD_ADCOLONY;
    private AdColonyAdViewListener MREC_LISTENER_ADCOLONY;
    //meta
    private com.facebook.ads.AdView MREC_AD_META;
    private com.facebook.ads.AdListener MREC_LISTENER_META;
    //pangle
    private PAGBannerAd MREC_AD_PANGLE;
    private PAGBannerAdLoadListener MREC_LISTENER_PANGLE;
    //mytarget
    private MyTargetView MREC_AD_MYTARGET;
    private MyTargetView.MyTargetViewListener MREC_LISTENER_MYTARGET;
    //vungle
    private VungleBanner MREC_AD_VUNGLE;
    private LoadAdCallback MREC_LISTENER_VUNGLE;
    private PlayAdCallback MREC_LISTENER_VUNGLE_2;
    //appodeal
    private MrecView MREC_AD_APPODEAL;
    private MrecCallbacks MREC_LISTENER_APPODEAL;
    //hyprmx
    private HyprMXBannerView MREC_AD_HYPRMX;
    private HyprMXBannerListener MREC_LISTENER_HYPRMX;
    //inmobi
    private InMobiBanner MREC_AD_INMOBI;
    private BannerAdEventListener MREC_LISTENER_INMOBI;
    //admob
    private AdView MREC_AD_ADMOB;
    private AdListener MREC_LISTENER_ADMOB;
    //applovin
    private MaxAdView MREC_AD_APPLOVIN;
    private MaxAdViewAdListener MREC_LISTENER_APPLOVIN;
    //chartboost
    private Banner MREC_AD_CHARTBOOST;
    private BannerCallback MREC_LISTENER_CHARTBOOST;
    //appnext
    private com.appnext.banners.BannerView MREC_AD_APPNEXT;
    private com.appnext.banners.BannerListener MREC_LISTENER_APPNEXT;
    //greedygames
    private GGAdview MREC_AD_GREEDYGAMES;
    private AdLoadCallback MREC_LISTENER_GREEDYGAMES;
    //googleadmanager
    private AdManagerAdView MREC_AD_GOOGLEADMANAGER;
    private AdListener MREC_LISTENER_GOOGLEADMANAGER;


    private boolean MREC_AD_IRONSOURCE_LOADED = false;
    private boolean MREC_AD_ADCOLONY_LOADED = false;
    private boolean MREC_AD_META_LOADED = false;
    private boolean MREC_AD_PANGLE_LOADED = false;
    private boolean MREC_AD_VUNGLE_LOADED = false;
    private boolean MREC_AD_APPODEAL_LOADED = false;
    private boolean MREC_AD_CHARTBOOST_LOADED = false;
    private boolean MREC_AD_APPNEXT_LOADED = false;
    private boolean MREC_AD_HYPRMX_LOADED = false;
    private boolean MREC_AD_INMOBI_LOADED = false;
    private boolean MREC_AD_MYTARGET_LOADED = false;
    private boolean MREC_AD_ADMOB_LOADED = false;
    private boolean MREC_AD_APPLOVIN_LOADED = false;
    private boolean MREC_AD_GREEDYGAMES_LOADED = false;
    private boolean MREC_AD_GOOGLEADMANAGER_LOADED = false;


    private VAdEnhancerMrecModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAdEnhancerMrecModule", "VAdEnhancerMrecModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;
        vAdEnhancerRegister = registerx;

    }

    public static VAdEnhancerMrecModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAdEnhancerMrecModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancerMrecModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadMrecAds(String LOAD_AD) {
        Log.e("VAdEnhancerMrecModule", "loadMrecAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD);


        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAdEnhancerMrecModule", "loadMrecAds() register.keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAdEnhancerMrecModule", "loadMrecAds() register.keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAdEnhancerMrecModule", "loadMrecAds() register.keyValueTableHashMap.get(activated) false");
        } else if (Objects.equals(LOAD_AD, "all")) {
            Log.e("VAdEnhancerMrecModule", "loadMrecAds() loading...");


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("ironsource_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("ironsource_activated"), "true")) {
                    getIronsourceMREC(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("adcolony_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("adcolony_activated"), "true")) {
                    getAdcolonyMREC(currentActivity);
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("meta_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("meta_activated"), "true")) {
                    getMetaMREC(currentActivity);//not tested //native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    getPangleMREC(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("vungle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("vungle_activated"), "true")) {
                    getVungleMREC(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appodeal_activated"), "true")) {
                    getAdcolonyMREC(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("chartboost_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("chartboost_activated"), "true")) {
                    getChartBoostMREC(currentActivity);
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appnext_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appnext_activated"), "true")) {
                    getAppNextMREC(currentActivity);//native
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("hyprmx_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("hyprmx_activated"), "true")) {
                    getHyprmxMREC(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("inmobi_activated"), "true")) {
                    getInmobiMREC(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mytarget_activated"), "true")) {
                    getMyTargetMREC(currentActivity);//not tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    getAdMobMREC(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("applovin_activated"), "true")) {
                    getApplovinMREC(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("greedygames_activated"), "true")) {
                    getGreedyGamesMREC(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    getGoogleAdManagerMREC(currentActivity);
                }
            }


        } else if (Objects.equals(LOAD_AD, "ironsource")) {
            new CountDownTimer(20000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getIronsourceMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "adcolony")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAdcolonyMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "meta")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getMetaMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "pangle")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getPangleMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "mytarget")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getMyTargetMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "vungle")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getVungleMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appodeal")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAppodealMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "chartboost")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getChartBoostMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "hyprmx")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getHyprmxMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "inmobi")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getInmobiMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "admob")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAdMobMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "applovin")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getApplovinMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appnext")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAppNextMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "greedygames")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getGreedyGamesMREC(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "googleadmanager")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getGoogleAdManagerMREC(currentActivity);
                }
            }.start();
        }


    }

    public void getMultipleMrecAds(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds() VAdEnhancerRegister.adNetworkOrderTableMrecList " + vAdEnhancerRegister.adNetworkOrderTableMrecList);

        currentActivity = activity;


        if (loopThroughLoadedMrecsTimer != null) {
            loopThroughLoadedMrecsTimer.cancel();
        }

        if (mrecLoadedWaitingTimer != null) {
            mrecLoadedWaitingTimer.cancel();
        }

        if (vAdEnhancerRegister != null) {
            if (!vAdEnhancerRegister.USERONLINE) {
                Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds user not online");
                return;
            }
        }


        loopThroughLoadedMrecsActivity = currentActivity.getLocalClassName();

        loopThroughLoadedMrecsTimer = new CountDownTimer(600000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds " + millisUntilFinished + " activity " + currentActivity.getLocalClassName());

                if (vAdEnhancerRegister.keyValueTableHashMap != null) {
                    if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mrec"), "false")) {
                        Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds mrec not enabled");
                        loopThroughLoadedMrecsTimer.cancel();
                    }
                }

                if (vAdEnhancerRegister.adNetworkOrderTableMrecList != null) {
                    for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableMrecList.size(); i++) {
                        Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds  " + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getPreference());

                        if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "gam") && MREC_AD_GOOGLEADMANAGER != null && MREC_AD_GOOGLEADMANAGER_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_GOOGLEADMANAGER");
                            MREC_AD_GOOGLEADMANAGER_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_GOOGLEADMANAGER);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("googleadmanager");
                            logFirebase("true", "mrec", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "greedygames") && MREC_AD_GREEDYGAMES != null && MREC_AD_GREEDYGAMES_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_GREEDYGAMES");
                            MREC_AD_GREEDYGAMES_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_GREEDYGAMES);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("greedygames");
                            logFirebase("true", "mrec", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "ironsource") && MREC_AD_IRONSOURCE != null && MREC_AD_IRONSOURCE_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_IRONSOURCE");
                            MREC_AD_IRONSOURCE_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_IRONSOURCE);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("ironsource");
                            logFirebase("true", "mrec", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "adcolony") && MREC_AD_ADCOLONY != null && MREC_AD_ADCOLONY_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_ADCOLONY");
                            MREC_AD_ADCOLONY_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_ADCOLONY);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("adcolony");
                            logFirebase("true", "mrec", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "meta") && MREC_AD_META != null && MREC_AD_META_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_META");
                            MREC_AD_META_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_META);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("meta");
                            logFirebase("true", "mrec", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "vungle") && MREC_AD_VUNGLE != null && MREC_AD_VUNGLE_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_VUNGLE");
                            MREC_AD_VUNGLE_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_VUNGLE);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("vungle");
                            logFirebase("true", "mrec", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "pangle") && MREC_AD_PANGLE != null && MREC_AD_PANGLE_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_PANGLE");
                            MREC_AD_PANGLE_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_PANGLE.getBannerView());
                            mrecLoadedWaitingTimer();
                            loadMrecAds("pangle");
                            logFirebase("true", "mrec", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "appodeal") && MREC_AD_APPODEAL != null && MREC_AD_APPODEAL_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_APPODEAL");
                            MREC_AD_APPODEAL_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_APPODEAL);
                            if (Appodeal.isLoaded(Appodeal.MREC)) {
                                Appodeal.show(currentActivity, Appodeal.MREC, vAdEnhancerRegister.getPlacementId("appodeal", "mrec", activity));
                            }
                            mrecLoadedWaitingTimer();
                            loadMrecAds("appodeal");
                            logFirebase("true", "mrec", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "chartboost") && MREC_AD_CHARTBOOST != null && MREC_AD_CHARTBOOST_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_CHARTBOOST");
                            MREC_AD_CHARTBOOST_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_CHARTBOOST);
                            MREC_AD_CHARTBOOST.show();
                            mrecLoadedWaitingTimer();
                            loadMrecAds("chartboost");
                            logFirebase("true", "mrec", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "appnext") && MREC_AD_APPNEXT != null && MREC_AD_APPNEXT_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_APPNEXT");
                            MREC_AD_APPNEXT_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_APPNEXT);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("appnext");
                            logFirebase("true", "mrec", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "hyprmx") && MREC_AD_HYPRMX != null && MREC_AD_HYPRMX_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_HYPRMX");
                            MREC_AD_HYPRMX_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_HYPRMX);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("hyprmx");
                            logFirebase("true", "mrec", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "inmobi") && MREC_AD_INMOBI != null && MREC_AD_INMOBI_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_INMOBI");
                            MREC_AD_INMOBI_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_INMOBI);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("inmobi");
                            logFirebase("true", "mrec", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "mytarget") && MREC_AD_MYTARGET != null && MREC_AD_MYTARGET_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_MYTARGET");
                            MREC_AD_MYTARGET_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_MYTARGET);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("mytarget");
                            logFirebase("true", "mrec", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "admob") && MREC_AD_ADMOB != null && MREC_AD_ADMOB_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_ADMOB");
                            MREC_AD_ADMOB_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_ADMOB);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("admob");
                            logFirebase("true", "mrec", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "applovin") && MREC_AD_APPLOVIN != null && MREC_AD_APPLOVIN_LOADED) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found MREC_AD_APPLOVIN");
                            MREC_AD_APPLOVIN_LOADED = false;
                            if (loopThroughLoadedMrecsTimer != null) {
                                loopThroughLoadedMrecsTimer.cancel();
                            }
                            if (noMrecLoadedWaitingTimer != null) {
                                noMrecLoadedWaitingTimer.cancel();
                            }
                            sendMrec(MREC_AD_APPLOVIN);
                            mrecLoadedWaitingTimer();
                            loadMrecAds("applovin");
                            logFirebase("true", "mrec", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                            break;
                        } else {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found non");
                        }

                        if (i == (vAdEnhancerRegister.adNetworkOrderTableMrecList.size() - 1)) {
                            Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds found non in loop");
                            if (noMrecLoadedWaitingTimer == null) {
                                noMrecLoadedWaitingTimer();
                            }
                        }

                    }
                }

            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerMrecModule", "getMultipleMrecAds onFinish() ");
            }
        }.start();
    }

    public void noMrecLoadedWaitingTimer() {
        Log.e("VAdEnhancerMrecModule", "noMrecLoadedWaitingTimer()");

        if (noMrecLoadedWaitingTimer != null) {
            noMrecLoadedWaitingTimer.cancel();
        }

        noMrecLoadedWaitingTimer = new CountDownTimer(30000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerMrecModule", "noMrecLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerMrecModule", "noMrecLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mrec"), "true")) {
                    loadMrecAds("all");
                }
                if (noMrecLoadedWaitingTimer != null) {
                    noMrecLoadedWaitingTimer.cancel();
                    noMrecLoadedWaitingTimer = null;
                }
            }
        }.start();
    }

    public void mrecLoadedWaitingTimer() {
        Log.e("VAdEnhancerMrecModule", "mrecLoadedWaitingTimer()");

        if (mrecLoadedWaitingTimer != null) {
            mrecLoadedWaitingTimer.cancel();
        }

        mrecLoadedWaitingTimer = new CountDownTimer(vAdEnhancerRegister.MRECLOADEDWAITINGTIMER, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerMrecModule", "mrecLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerMrecModule", "mrecLoadedWaitingTimer onFinish() ");
                if (Objects.equals(loopThroughLoadedMrecsActivity, currentActivity.getLocalClassName())) {
                    getMultipleMrecAds(currentActivity);
                }
            }
        }.start();
    }

    public View getSingleMrecAd(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getSingleMrecAd()");

        currentActivity = activity;

        View view = null;

        if (vAdEnhancerRegister.adNetworkOrderTableMrecList != null) {
            Log.e("VAdEnhancerMrecModule", "getSingleMrecAd() register.adNetworkOrderTableMrecListData.size() " + vAdEnhancerRegister.adNetworkOrderTableMrecList.size());
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableMrecList.size(); i++) {
                Log.e("VAdEnhancerMrecModule", "getSingleMrecAd " + i + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "gam") && MREC_AD_GOOGLEADMANAGER != null && MREC_AD_GOOGLEADMANAGER_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_GOOGLEADMANAGER");
                    MREC_AD_GOOGLEADMANAGER_LOADED = false;
                    loadMrecAds("googleadmanager");
                    logFirebase("true", "mrec", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_GOOGLEADMANAGER;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "greedygames") && MREC_AD_GREEDYGAMES != null && MREC_AD_GREEDYGAMES_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_GREEDYGAMES");
                    MREC_AD_GREEDYGAMES_LOADED = false;
                    loadMrecAds("greedygames");
                    logFirebase("true", "mrec", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_GREEDYGAMES;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "ironsource") && MREC_AD_IRONSOURCE != null && MREC_AD_IRONSOURCE_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_IRONSOURCE");
                    MREC_AD_IRONSOURCE_LOADED = false;
                    loadMrecAds("ironsource");
                    logFirebase("true", "mrec", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_IRONSOURCE;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "adcolony") && MREC_AD_ADCOLONY != null && MREC_AD_ADCOLONY_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_ADCOLONY");
                    MREC_AD_ADCOLONY_LOADED = false;
                    loadMrecAds("adcolony");
                    logFirebase("true", "mrec", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_ADCOLONY;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "meta") && MREC_AD_META != null && MREC_AD_META_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_META");
                    MREC_AD_META_LOADED = false;
                    loadMrecAds("meta");
                    logFirebase("true", "mrec", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_META;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "vungle") && MREC_AD_VUNGLE != null && MREC_AD_VUNGLE_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_VUNGLE");
                    MREC_AD_VUNGLE_LOADED = false;
                    loadMrecAds("vungle");
                    logFirebase("true", "mrec", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_VUNGLE;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "pangle") && MREC_AD_PANGLE != null && MREC_AD_PANGLE_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_PANGLE");
                    MREC_AD_PANGLE_LOADED = false;
                    loadMrecAds("pangle");
                    logFirebase("true", "mrec", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_PANGLE.getBannerView();
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "appodeal") && MREC_AD_APPODEAL != null && MREC_AD_APPODEAL_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_APPODEAL");
                    MREC_AD_APPODEAL_LOADED = false;
                    if (Appodeal.isLoaded(Appodeal.MREC)) {
                        Appodeal.show(currentActivity, Appodeal.MREC, vAdEnhancerRegister.getPlacementId("appodeal", "mrec", activity));
                    }
                    loadMrecAds("appodeal");
                    logFirebase("true", "mrec", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_APPODEAL;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "chartboost") && MREC_AD_CHARTBOOST != null && MREC_AD_CHARTBOOST_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_CHARTBOOST");
                    MREC_AD_CHARTBOOST_LOADED = false;
                    loadMrecAds("chartboost");
                    MREC_AD_CHARTBOOST.show();
                    logFirebase("true", "mrec", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_CHARTBOOST;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "appnext") && MREC_AD_APPNEXT != null && MREC_AD_APPNEXT_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_APPNEXT");
                    MREC_AD_APPNEXT_LOADED = false;
                    loadMrecAds("appnext");
                    logFirebase("true", "mrec", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_APPNEXT;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "hyprmx") && MREC_AD_HYPRMX != null && MREC_AD_HYPRMX_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_HYPRMX");
                    MREC_AD_HYPRMX_LOADED = false;
                    loadMrecAds("hyprmx");
                    logFirebase("true", "mrec", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_HYPRMX;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "inmobi") && MREC_AD_INMOBI != null && MREC_AD_INMOBI_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_INMOBI");
                    MREC_AD_INMOBI_LOADED = false;
                    loadMrecAds("inmobi");
                    logFirebase("true", "mrec", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_INMOBI;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "mytarget") && MREC_AD_MYTARGET != null && MREC_AD_MYTARGET_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_MYTARGET");
                    MREC_AD_MYTARGET_LOADED = false;
                    loadMrecAds("mytarget");
                    logFirebase("true", "mrec", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_MYTARGET;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "admob") && MREC_AD_ADMOB != null && MREC_AD_ADMOB_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_ADMOB");
                    MREC_AD_ADMOB_LOADED = false;
                    loadMrecAds("admob");
                    logFirebase("true", "mrec", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_ADMOB;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableMrecList.get(i).getAdnetwork(), "applovin") && MREC_AD_APPLOVIN != null && MREC_AD_APPLOVIN_LOADED) {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found MREC_AD_APPLOVIN");
                    MREC_AD_APPLOVIN_LOADED = false;
                    loadMrecAds("applovin");
                    logFirebase("true", "mrec", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                    if (noMrecLoadedWaitingTimer != null) {
                        noMrecLoadedWaitingTimer.cancel();
                    }
                    view = MREC_AD_APPLOVIN;
                    break;
                } else {
                    Log.e("VAdEnhancerMrecModule", "getSingleMrecAd found none");
                }

            }
        }

        return view;
    }

    private void sendMrec(View view) {
        Log.e("VAdEnhancerMrecModule", "sendMrec() ");

        if (view.getParent() != null) {
            Log.e("VAdEnhancerMrecModule", "sendBanner has parent " + view);
        }

        try {
            Method method = currentActivity.getClass().getMethod("placeMrecAd", View.class);
            method.invoke(currentActivity, view);
        } catch (Exception e) {
            Log.e("VAdEnhancerMrecModule", "exception error " + e);
        }

    }


    public void getIronsourceMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getIronsourceMREC() " + activity.getLocalClassName());

        logFirebase("true", "mrec", "requested", "ironsource", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.IronSourceInit(activity);

        if (MREC_AD_IRONSOURCE != null) {
            IronSource.destroyBanner(MREC_AD_IRONSOURCE);
            MREC_AD_IRONSOURCE = null;
        }
        if (MREC_LISTENER_IRONSOURCE != null) {
            MREC_LISTENER_IRONSOURCE = null;
        }

        MREC_LISTENER_IRONSOURCE = new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdLoaded()");
                logFirebase("true", "mrec", "loaded", "ironsource", String.valueOf(""), String.valueOf(""));
                MREC_AD_IRONSOURCE_LOADED = true;
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdLoadFailed() " + ironSourceError.getErrorMessage());
                logFirebase("true", "mrec", "failed", "ironsource", String.valueOf(ironSourceError.getErrorCode()), String.valueOf(ironSourceError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getIronsourceMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onBannerAdScreenPresented() {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdScreenPresented()");
                logFirebase("true", "mrec", "impression", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerAdClicked() {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdClicked()");
                logFirebase("true", "mrec", "clicked", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerAdScreenDismissed() {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdScreenDismissed()");
            }

            @Override
            public void onBannerAdLeftApplication() {
                Log.e("VAdEnhancerMrecModule", "getIronsourceMREC onBannerAdLeftApplication()");
            }
        };


        MREC_AD_IRONSOURCE = IronSource.createBanner(activity, ISBannerSize.RECTANGLE);
        MREC_AD_IRONSOURCE.setBannerListener(MREC_LISTENER_IRONSOURCE);
        IronSource.loadBanner(MREC_AD_IRONSOURCE, vAdEnhancerRegister.getPlacementId("ironsource", "mrec", activity));


    }

    public void getAdcolonyMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getAdcolonyMREC()");

        logFirebase("true", "mrec", "requested", "adcolony", "", "");

        vAdEnhancerRegister.AdcolonyInit(activity);

        MREC_LISTENER_ADCOLONY = new AdColonyAdViewListener() {
            @Override
            public void onRequestFilled(AdColonyAdView ad) {
                Log.e("VAdEnhancerMrecModule", "adcolony onRequestFilled()");
                logFirebase("true", "mrec", "loaded", "adcolony", "", "");
                MREC_AD_ADCOLONY = ad;
                MREC_AD_ADCOLONY_LOADED = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
                Log.e("VAdEnhancerMrecModule", "adcolony onRequestNotFilled()");
                logFirebase("true", "mrec", "failed", "adcolony", "", "");
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAdcolonyMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onShow(AdColonyAdView ad) {
                super.onShow(ad);
                Log.e("VAdEnhancerMrecModule", "onadcolony Show()");
                logFirebase("true", "mrec", "impression", "adcolony", "", "");
            }

            @Override
            public void onClicked(AdColonyAdView ad) {
                super.onClicked(ad);
                Log.e("VAdEnhancerMrecModule", "adcolony onClicked()");
                logFirebase("true", "mrec", "clicked", "adcolony", "", "");
            }

            @Override
            public void onOpened(AdColonyAdView ad) {
                super.onOpened(ad);
                Log.e("VAdEnhancerMrecModule", "adcolony onOpened()");
            }

            @Override
            public void onClosed(AdColonyAdView ad) {
                super.onClosed(ad);
                Log.e("VAdEnhancerMrecModule", "adcolony onClosed()");
            }

            @Override
            public void onLeftApplication(AdColonyAdView ad) {
                super.onLeftApplication(ad);
                Log.e("VAdEnhancerMrecModule", "adcolony onLeftApplication()");
            }

        };

        AdColony.requestAdView(vAdEnhancerRegister.getPlacementId("adcolony", "mrec", activity), MREC_LISTENER_ADCOLONY, AdColonyAdSize.MEDIUM_RECTANGLE);

    }

    public void getMetaMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getMetaMREC()");

        logFirebase("true", "mrec", "requested", "meta", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MetaInit(activity);

        MREC_LISTENER_META = new com.facebook.ads.AdListener() {

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("VAdEnhancerMrecModule", "meta onAdLoaded()");
                logFirebase("true", "mrec", "loaded", "meta", String.valueOf(""), String.valueOf(""));
                MREC_AD_META_LOADED = true;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("VAdEnhancerMrecModule", "meta onError()" + adError.getErrorMessage());
                logFirebase("true", "mrec", "failed", "adcolony", String.valueOf(adError.getErrorCode()), String.valueOf(adError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getMetaMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("VAdEnhancerMrecModule", "meta onLoggingImpression()");
                logFirebase("true", "mrec", "impression", "meta", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("VAdEnhancerMrecModule", "meta onAdClicked()");
                logFirebase("true", "mrec", "clicked", "meta", String.valueOf(""), String.valueOf(""));
            }


        };

        MREC_AD_META = new com.facebook.ads.AdView(activity, vAdEnhancerRegister.getPlacementId("meta", "mrec", activity), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        MREC_AD_META.loadAd(MREC_AD_META.buildLoadAdConfig().withAdListener(MREC_LISTENER_META).build());

    }

    public void getVungleMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getVungleMREC() ");

        logFirebase("true", "mrec", "requested", "vungle", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.VungleInit(activity);

        MREC_LISTENER_VUNGLE = new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdLoad() ");
                logFirebase("true", "mrec", "loaded", "vungle", String.valueOf(""), String.valueOf(""));
                BannerAdConfig bannerAdConfig = new BannerAdConfig();
                bannerAdConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
                MREC_AD_VUNGLE = Banners.getBanner(vAdEnhancerRegister.getPlacementId("vungle", "mrec", activity), bannerAdConfig, MREC_LISTENER_VUNGLE_2);
                MREC_AD_VUNGLE_LOADED = true;
            }

            @Override
            public void onError(String id, VungleException exception) {
                Log.e("VAdEnhancerMrecModule", "vungle onError() " + exception.getLocalizedMessage());
                logFirebase("true", "mrec", "failed", "vungle", String.valueOf(exception.getExceptionCode()), String.valueOf(exception.getLocalizedMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getVungleMREC(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        MREC_LISTENER_VUNGLE_2 = new PlayAdCallback() {

            @Override
            public void onAdStart(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdStart() ");
                logFirebase("true", "mrec", "impression", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClick(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdClick() ");
                logFirebase("true", "mrec", "clicked", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void creativeId(String creativeId) {
                Log.e("VAdEnhancerMrecModule", "vungle creativeId() ");
            }

            @Override
            public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdEnd() ");
            }

            @Override
            public void onAdEnd(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdEnd() ");
            }

            @Override
            public void onAdRewarded(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdRewarded() ");
            }

            @Override
            public void onAdLeftApplication(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdLeftApplication() ");
            }

            @Override
            public void onError(String placementId, VungleException exception) {
                Log.e("VAdEnhancerMrecModule", "vungle onError() " + exception.getLocalizedMessage());
            }

            @Override
            public void onAdViewed(String placementId) {
                Log.e("VAdEnhancerMrecModule", "vungle onAdViewed() ");
            }
        };

        BannerAdConfig bannerAdConfig = new BannerAdConfig();
        bannerAdConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
        Banners.loadBanner(vAdEnhancerRegister.getPlacementId("vungle", "mrec", activity), bannerAdConfig, MREC_LISTENER_VUNGLE);


    }

    public void getAppodealMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getAppodealMREC() ");

        logFirebase("true", "mrec", "requested", "appodeal", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppODealInit(activity);

        MREC_LISTENER_APPODEAL = new MrecCallbacks() {
            @Override
            public void onMrecLoaded(boolean b) {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecLoaded() ");
                logFirebase("true", "mrec", "loaded", "appodeal", String.valueOf(""), String.valueOf(""));
                MREC_AD_APPODEAL_LOADED = true;
            }

            @Override
            public void onMrecFailedToLoad() {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecFailedToLoad() ");
                logFirebase("true", "mrec", "failed", "appodeal", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAppodealMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onMrecShown() {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecShown() ");
                logFirebase("true", "mrec", "impression", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onMrecClicked() {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecClicked() ");
                logFirebase("true", "mrec", "clicked", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onMrecShowFailed() {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecShowFailed() ");
            }

            @Override
            public void onMrecExpired() {
                Log.e("VAdEnhancerMrecModule", "appodeal onMrecExpired() ");
            }
        };

        MREC_AD_APPODEAL = Appodeal.getMrecView(activity);
        Appodeal.setMrecCallbacks(MREC_LISTENER_APPODEAL);
    }

    public void getPangleMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getPangleMREC()");

        logFirebase("true", "mrec", "requested", "pangle", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.PangleInit(activity);

        MREC_LISTENER_PANGLE = new PAGBannerAdLoadListener() {

            @Override
            public void onAdLoaded(PAGBannerAd ad) {
                Log.e("VAdEnhancerMrecModule", "pangle onAdLoaded()");
                logFirebase("true", "mrec", "loaded", "pangle", String.valueOf(""), String.valueOf(""));
                MREC_AD_PANGLE = ad;
                MREC_AD_PANGLE_LOADED = true;
            }

            @Override
            public void onError(int i, String s) {
                Log.e("VAdEnhancerMrecModule", "pangle onError()" + s);
                logFirebase("true", "mrec", "failed", "pangle", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getPangleMREC(activity);
                            }
                        }
                    }
                }.start();
            }

        };

        PAGBannerAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "mrec", activity), new PAGBannerRequest(PAGBannerSize.BANNER_W_300_H_250), MREC_LISTENER_PANGLE);

    }

    public void getHyprmxMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getHyprmxMREC() ");

        logFirebase("true", "mrec", "requested", "hyprmx", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.HyprMxInit(activity);

        MREC_LISTENER_HYPRMX = new HyprMXBannerListener() {

            @Override
            public void onAdLoaded(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdLoaded() ");
                logFirebase("true", "mrec", "loaded", "hyprmx", String.valueOf(""), String.valueOf(""));
                MREC_AD_HYPRMX = hyprMXBannerView;
                MREC_AD_HYPRMX_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull HyprMXBannerView hyprMXBannerView, @NonNull HyprMXErrors hyprMXErrors) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdFailedToLoad() " + hyprMXErrors.toString());
                logFirebase("true", "mrec", "failed", "hyprmx", String.valueOf(hyprMXErrors.toString()), String.valueOf(hyprMXErrors.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getHyprmxMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdOpened(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdOpened() ");
                logFirebase("true", "mrec", "impression", "hyprmx", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdClicked() ");
                logFirebase("true", "mrec", "clicked", "hyprmx", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdClosed() ");
            }

            @Override
            public void onAdLeftApplication(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerMrecModule", "hyprmx onAdLeftApplication() ");
            }
        };

        MREC_AD_HYPRMX = new HyprMXBannerView(activity, null, vAdEnhancerRegister.getPlacementId("hyprmx", "mrec", activity), HyprMXBannerSize.HyprMXAdSizeMediumRectangle.INSTANCE);
        MREC_AD_HYPRMX.setListener(MREC_LISTENER_HYPRMX);
        MREC_AD_HYPRMX.loadAd();
    }

    public void getChartBoostMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getChartBoostMREC() ");

        logFirebase("true", "mrec", "requested", "chartboost", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.ChartBoostInit(activity);

        MREC_LISTENER_CHARTBOOST = new BannerCallback() {

            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                Log.e("VAdEnhancerMrecModule", "chartboost onAdLoaded() ");
                logFirebase("true", "mrec", "loaded", "chartboost", String.valueOf(""), String.valueOf(""));
                MREC_AD_CHARTBOOST_LOADED = true;
            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {
                Log.e("VAdEnhancerMrecModule", "chartboost onImpressionRecorded() ");
                logFirebase("true", "mrec", "impression", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {
                Log.e("VAdEnhancerMrecModule", "chartboost onAdClicked() ");
                logFirebase("true", "mrec", "clicked", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {
                Log.e("VAdEnhancerMrecModule", "chartboost onAdRequestedToShow() ");
            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                Log.e("VAdEnhancerMrecModule", "chartboost onAdShown() ");
            }

        };

        MREC_AD_CHARTBOOST = new Banner(activity, vAdEnhancerRegister.getPlacementId("chartboost", "mrec", activity), Banner.BannerSize.MEDIUM, MREC_LISTENER_CHARTBOOST, null);
        MREC_AD_CHARTBOOST.cache();

    }

    public void getMyTargetMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getMyTargetMREC() ");

        logFirebase("true", "mrec", "requested", "mytarget", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MyTargetInit(activity);

        MREC_LISTENER_MYTARGET = new MyTargetView.MyTargetViewListener() {
            @Override
            public void onLoad(MyTargetView myTargetView) {
                Log.e("VAdEnhancerMrecModule", "mytarget onLoad() ");
                logFirebase("true", "mrec", "loaded", "mytarget", String.valueOf(""), String.valueOf(""));
                MREC_AD_MYTARGET = myTargetView;
                MREC_AD_MYTARGET_LOADED = true;
            }

            @Override
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull MyTargetView myTargetView) {
                Log.e("VAdEnhancerMrecModule", "mytarget onNoAd() " + iAdLoadingError.getMessage());
                logFirebase("true", "mrec", "failed", "mytarget", String.valueOf(iAdLoadingError.getCode()), String.valueOf(iAdLoadingError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getMyTargetMREC(activity);
                            }
                        }
                    }
                }.start();
            }



            @Override
            public void onShow(MyTargetView myTargetView) {
                Log.e("VAdEnhancerMrecModule", "mytarget onShow() ");
                logFirebase("true", "mrec", "impression", "mytarget", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClick(MyTargetView myTargetView) {
                Log.e("VAdEnhancerMrecModule", "mytarget onClick() ");
                logFirebase("true", "mrec", "clicked", "mytarget", String.valueOf(""), String.valueOf(""));
            }
        };

        MREC_AD_MYTARGET = new MyTargetView(activity);
        MREC_AD_MYTARGET.setSlotId(vAdEnhancerRegister.getPlacementIdInt("mytarget", "mrec", activity));
        MREC_AD_MYTARGET.setAdSize(MyTargetView.AdSize.ADSIZE_300x250);
        MREC_AD_MYTARGET.setListener(MREC_LISTENER_MYTARGET);
        MREC_AD_MYTARGET.load();
    }

    public void getInmobiMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getInmobiMREC() ");

        logFirebase("true", "mrec", "requested", "inmobi", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.InMobiInit(activity);

        MREC_LISTENER_INMOBI = new BannerAdEventListener() {

            @Override
            public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                super.onAdLoadSucceeded(inMobiBanner, adMetaInfo);
                Log.e("VAdEnhancerMrecModule", "inmobi onAdLoadSucceeded() ");
                logFirebase("true", "mrec", "loadeed", "inmobi", String.valueOf(""), String.valueOf(""));
                MREC_AD_INMOBI_LOADED = true;
            }

            @Override
            public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);
                Log.e("VAdEnhancerMrecModule", "inmobi onAdFetchFailed() ");
                logFirebase("true", "mrec", "failed", "inmobi", String.valueOf(inMobiAdRequestStatus.getStatusCode()), String.valueOf(inMobiAdRequestStatus.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getInmobiMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression(@NonNull InMobiBanner inMobiBanner) {
                super.onAdImpression(inMobiBanner);
                logFirebase("true", "mrec", "impression", "inmobi", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDisplayed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDisplayed(inMobiBanner);
                Log.e("VAdEnhancerMrecModule", "inmobi onAdDisplayed() ");
            }

            @Override
            public void onAdDismissed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDismissed(inMobiBanner);
                Log.e("VAdEnhancerMrecModule", "inmobi onAdDismissed() ");
            }

            @Override
            public void onUserLeftApplication(@NonNull InMobiBanner inMobiBanner) {
                super.onUserLeftApplication(inMobiBanner);
                Log.e("VAdEnhancerMrecModule", "inmobi onUserLeftApplication() ");
            }

            @Override
            public void onRewardsUnlocked(@NonNull InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onRewardsUnlocked(inMobiBanner, map);
                Log.e("VAdEnhancerMrecModule", "inmobi onRewardsUnlocked() ");
            }
        };

        MREC_AD_INMOBI = new InMobiBanner(activity, vAdEnhancerRegister.getPlacementIdInt("inmobi", "mrec", activity));
        MREC_AD_INMOBI.setListener(MREC_LISTENER_INMOBI);
        MREC_AD_INMOBI.load();

    }

    public void getAdMobMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getAdMobMREC()");

        logFirebase("true", "mrec", "requested", "admob", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AdmobInit(activity);

        MREC_LISTENER_ADMOB = new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerMrecModule", "admob onAdLoaded()");
                logFirebase("true", "mrec", "loaded", "admob", String.valueOf(""), String.valueOf(""));
                MREC_AD_ADMOB_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VAdEnhancerMrecModule", "admob onAdFailedToLoad() " + adError.getMessage());
                logFirebase("true", "mrec", "failed", "admob", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAdMobMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerMrecModule", "admob onAdImpression()");
                logFirebase("true", "mrec", "impression", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerMrecModule", "admob onAdClicked()");
                logFirebase("true", "mrec", "clicked", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerMrecModule", "admob onAdClosed()");
            }

            @Override
            public void onAdOpened() {
                Log.e("VAdEnhancerMrecModule", "admob onAdOpened()");
            }
        };

        MREC_AD_ADMOB = new AdView(activity);
        MREC_AD_ADMOB.setAdSize(AdSize.MEDIUM_RECTANGLE);
        MREC_AD_ADMOB.setAdUnitId(vAdEnhancerRegister.getPlacementId("admob", "mrec", activity));
        MREC_AD_ADMOB.setAdListener(MREC_LISTENER_ADMOB);
        MREC_AD_ADMOB.loadAd(new AdRequest.Builder().build());


    }

    public void getApplovinMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getApplovinMREC() " + activity.getLocalClassName());

        logFirebase("true", "mrec", "requested", "applovin", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MaxInit(activity);

        MREC_LISTENER_APPLOVIN = new MaxAdViewAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdLoaded() ");
                logFirebase("true", "mrec", "loaded", "applovin", String.valueOf(""), String.valueOf(""));
                MREC_AD_APPLOVIN_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdLoadFailed() " + error.getMessage());
                logFirebase("true", "mrec", "failed", "applovin", String.valueOf(error.getCode()), String.valueOf(error.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getApplovinMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdDisplayed() ");
                logFirebase("true", "mrec", "impression", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdClicked() ");
                logFirebase("true", "mrec", "clicked", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdExpanded(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdExpanded() ");
            }

            @Override
            public void onAdCollapsed(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdCollapsed() ");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdHidden() ");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.e("VAdEnhancerMrecModule", "applovin onAdDisplayFailed() " + error.getMessage());
            }
        };

        MREC_AD_APPLOVIN = new MaxAdView(vAdEnhancerRegister.getPlacementId("applovin", "mrec", activity), activity);
        MREC_AD_APPLOVIN.setListener(MREC_LISTENER_APPLOVIN);
        MREC_AD_APPLOVIN.loadAd();

    }

    public void getAppNextMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getAppNextMREC() " + activity.getLocalClassName());

        logFirebase("true", "mrec", "requested", "appnext", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppNextInit(activity);

        MREC_LISTENER_APPNEXT = new com.appnext.banners.BannerListener() {

            @Override
            public void onAdLoaded(String s, AppnextAdCreativeType creativeType) {
                super.onAdLoaded(s, creativeType);
                Log.e("VAdEnhancerMrecModule", "getAppNextMREC onAdLoaded() ");
                logFirebase("true", "mrec", "loaded", "appnext", String.valueOf(""), String.valueOf(""));
                MREC_AD_APPNEXT_LOADED = true;
            }

            @Override
            public void onError(AppnextError error) {
                super.onError(error);
                Log.e("VAdEnhancerMrecModule", "getAppNextMREC onError() ");
                logFirebase("true", "mrec", "error", "appnext", String.valueOf(error.toString()), String.valueOf(error.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAppNextMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void adImpression() {
                super.adImpression();
                Log.e("VAdEnhancerMrecModule", "getAppNextMREC adImpression() ");
                logFirebase("true", "mrec", "impression", "appnext", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.e("VAdEnhancerMrecModule", "getAppNextMREC onAdClicked() ");
                logFirebase("true", "mrec", "clicked", "appnext", String.valueOf(""), String.valueOf(""));
            }
        };

        MREC_AD_APPNEXT = new com.appnext.banners.BannerView(activity);
        MREC_AD_APPNEXT.setPlacementId(vAdEnhancerRegister.getPlacementId("appnext", "mrec", activity));
        MREC_AD_APPNEXT.setBannerSize(BannerSize.MEDIUM_RECTANGLE);
        MREC_AD_APPNEXT.setBannerListener(MREC_LISTENER_APPNEXT);
        MREC_AD_APPNEXT.loadAd(new BannerAdRequest());

    }

    public void getGreedyGamesMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC() " + activity.getLocalClassName());

        logFirebase("true", "mrec", "requested", "greedygames", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GreedyGamesInit(activity);

        MREC_LISTENER_GREEDYGAMES = new AdLoadCallback() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC onAdLoaded");
                logFirebase("true", "mrec", "loaded", "greedygames", String.valueOf(""), String.valueOf(""));
                MREC_AD_GREEDYGAMES_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(AdErrors cause) {
                Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC onAdLoadFailed " + cause);
                logFirebase("true", "mrec", "error", "greedygames", String.valueOf(cause.toString()), String.valueOf(cause.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getGreedyGamesMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onUiiOpened() {
                Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC onUiiOpened");
                logFirebase("true", "mrec", "impression", "greedygames", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onReadyForRefresh() {
                Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC onReadyForRefresh");
            }

            /*@Override
            public void onPlacementId(@NonNull String s) {

            }*/

            @Override
            public void onUiiClosed() {
                Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC onUiiClosed");
            }

        };


        float width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                300f,
                activity.getResources().getDisplayMetrics()
        );
        float height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                250f,
                activity.getResources().getDisplayMetrics()
        );
        Log.e("VAdEnhancerMrecModule", "getGreedyGamesMREC width=" + width + "height=" + height);

        MREC_AD_GREEDYGAMES = new GGAdview(activity);
        MREC_AD_GREEDYGAMES.setUnitId(vAdEnhancerRegister.getPlacementId("greedygames", "mrec", activity));
        MREC_AD_GREEDYGAMES.setAdsMaxWidth((int) width);
        MREC_AD_GREEDYGAMES.setAdsMaxHeight((int) height);
        MREC_AD_GREEDYGAMES.loadAd(MREC_LISTENER_GREEDYGAMES);

    }

    public void getGoogleAdManagerMREC(Activity activity) {
        Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC()");

        logFirebase("true", "mrec", "requested", "googleadmanager", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GoogleAdManagerInit(activity);

        MREC_LISTENER_GOOGLEADMANAGER = new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdLoaded()");
                logFirebase("true", "mrec", "loaded", "googleadmanager", String.valueOf(""), String.valueOf(""));
                MREC_AD_GOOGLEADMANAGER_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdFailedToLoad() " + adError.getMessage());
                logFirebase("true", "mrec", "failed", "googleadmanager", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getGoogleAdManagerMREC(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdImpression()");
                logFirebase("true", "mrec", "impression", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdClicked()");
                logFirebase("true", "mrec", "clicked", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdClosed()");
            }

            @Override
            public void onAdOpened() {
                Log.e("VAdEnhancerMrecModule", "getGoogleAdManagerMREC onAdOpened()");
            }
        };

        MREC_AD_GOOGLEADMANAGER = new AdManagerAdView(activity);
        MREC_AD_GOOGLEADMANAGER.setAdSize(AdSize.MEDIUM_RECTANGLE);
        MREC_AD_GOOGLEADMANAGER.setAdUnitId(vAdEnhancerRegister.getPlacementId("gam", "mrec", activity));
        MREC_AD_GOOGLEADMANAGER.setAdListener(MREC_LISTENER_GOOGLEADMANAGER);
        MREC_AD_GOOGLEADMANAGER.loadAd(new AdManagerAdRequest.Builder().build());


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
