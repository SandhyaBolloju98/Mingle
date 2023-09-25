package com.v.ad.vadenhancerLibrary.Classes.Modules;

import android.app.Activity;
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
import com.appbrain.AdId;
import com.appbrain.AppBrainBanner;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.appnext.banners.BannerAdRequest;
import com.appnext.banners.BannerSize;
import com.appnext.core.AppnextAdCreativeType;
import com.appnext.core.AppnextError;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
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
import com.greedygame.core.models.general.RefreshPolicy;
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
import com.kidoz.sdk.api.KidozSDK;
import com.kidoz.sdk.api.ui_views.kidoz_banner.KidozBannerListener;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.KidozBannerView;
import com.my.target.ads.MyTargetView;
import com.my.target.common.models.IAdLoadingError;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;
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

public class VAdEnhancerBannerModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private static volatile VAdEnhancerBannerModule INSTANCE = null;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private CountDownTimer bannerLoadedWaitingTimer;
    private CountDownTimer loopThroughLoadedBannersTimer;
    private CountDownTimer noBannerLoadedWaitingTimer;


    //ironsource
    private IronSourceBannerLayout BANNER_AD_IRONSOURCE;
    private BannerListener BANNER_LISTENER_IRONSOURCE;
    //Adcolony
    private AdColonyAdView BANNER_AD_ADCOLONY;
    private AdColonyAdViewListener BANNER_LISTENER_ADCOLONY;
    //unity
    private BannerView BANNER_AD_UNITY;
    private BannerView.IListener BANNER_LISTENER_UNITY;
    //meta
    private com.facebook.ads.AdView BANNER_AD_META;
    private com.facebook.ads.AdListener BANNER_LISTENER_META;
    //pangle
    private PAGBannerAd BANNER_AD_PANGLE;
    private PAGBannerAdLoadListener BANNER_LISTENER_PANGLE;
    //mytarget
    private MyTargetView BANNER_AD_MYTARGET;
    private MyTargetView.MyTargetViewListener BANNER_LISTENER_MYTARGET;
    //vungle
    private VungleBanner BANNER_AD_VUNGLE;
    private LoadAdCallback BANNER_LISTENER_VUNGLE;
    private PlayAdCallback BANNNER_LISTENER_VUNGLE_2;
    //appodeal
    private com.appodeal.ads.BannerView BANNER_AD_APPODEAL;
    private BannerCallbacks BANNER_LISTENER_APPODEAL;
    //hyprmx
    private HyprMXBannerView BANNER_AD_HYPRMX;
    private HyprMXBannerListener BANNER_LISTENER_HYPRMX;
    //inmobi
    private InMobiBanner BANNER_AD_INMOBI;
    private BannerAdEventListener BANNER_LISTENER_INMOBI;
    //admob
    private AdView BANNER_AD_ADMOB;
    private AdListener BANNER_LISTENER_ADMOB;
    //applovin
    private MaxAdView BANNER_AD_APPLOVIN;
    private MaxAdViewAdListener BANNER_LISTENER_APPLOVIN;
    //chartboost
    private Banner BANNER_AD_CHARTBOOST;
    private BannerCallback BANNER_LISTENER_CHARTBOOST;
    //appbrain
    private AppBrainBanner BANNER_AD_APPBRAIN;
    private com.appbrain.BannerListener BANNER_LISTENER_APPBRAIN;
    //kidoz
    private KidozBannerView BANNER_AD_KIDOZ;
    private KidozBannerListener BANNER_LISTENER_KIDOZ;
    //appnext
    private com.appnext.banners.BannerView BANNER_AD_APPNEXT;
    private com.appnext.banners.BannerListener BANNER_LISTENER_APPNEXT;
    //greedygames
    private GGAdview BANNER_AD_GREEDYGAMES;
    private AdLoadCallback BANNER_LISTENER_GREEDYGAMES;
    //googleadmanager
    private AdManagerAdView BANNER_AD_GOOGLEADMANAGER;
    private AdListener BANNER_LISTENER_GOOGLEADMANAGER;


    private boolean BANNER_AD_IRONSOURCE_LOADED = false;
    private boolean BANNER_AD_ADCOLONY_LOADED = false;
    private boolean BANNER_AD_UNITY_LOADED = false;
    private boolean BANNER_AD_META_LOADED = false;
    private boolean BANNER_AD_PANGLE_LOADED = false;
    private boolean BANNER_AD_VUNGLE_LOADED = false;
    private boolean BANNER_AD_APPODEAL_LOADED = false;
    private boolean BANNER_AD_CHARTBOOST_LOADED = false;
    private boolean BANNER_AD_APPBRAIN_LOADED = false;
    private boolean BANNER_AD_KIDOZ_LOADED = false;
    private boolean BANNER_AD_APPNEXT_LOADED = false;
    private boolean BANNER_AD_HYPRMX_LOADED = false;
    private boolean BANNER_AD_INMOBI_LOADED = false;
    private boolean BANNER_AD_MYTARGET_LOADED = false;
    private boolean BANNER_AD_ADMOB_LOADED = false;
    private boolean BANNER_AD_APPLOVIN_LOADED = false;
    private boolean BANNER_AD_GREEDYGAMES_LOADED = false;
    private boolean BANNER_AD_GOOGLEADMANAGER_LOADED = false;


    private VAdEnhancerBannerModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAdEnhancerBannerModule", "VAdEnhancerBannerModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancerRegister = registerx;

    }

    public static VAdEnhancerBannerModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAdEnhancerBannerModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancerBannerModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadBannerAds(String LOAD_AD) {
        Log.e("VAdEnhancerBannerModule", "loadBannerAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD + " VAdEnhancerRegister.keyValueTableHashMap " + vAdEnhancerRegister.keyValueTableHashMap);


        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAdEnhancerBannerModule", "loadBannerAds() register.keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAdEnhancerBannerModule", "loadBannerAds() register.keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAdEnhancerBannerModule", "loadBannerAds() register.keyValueTableHashMap.get(activated) false");
        } else if (Objects.equals(LOAD_AD, "all")) {
            Log.e("VAdEnhancerBannerModule", "loadBannerAds() loading...");


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("ironsource_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("ironsource_activated"), "true")) {
                    getIronSourceBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("adcolony_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("adcolony_activated"), "true")) {
                    getAdcolonyBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("unity_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("unity_activated"), "true")) {
                    getUnityBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("meta_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("meta_activated"), "true")) {
                    getMetaBanner(currentActivity);//not tested //native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    getPangleBanner(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("vungle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("vungle_activated"), "true")) {
                    getVungleBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appodeal_activated"), "true")) {
                    getAppodealBanner(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("chartboost_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("chartboost_activated"), "true")) {
                    getChartBoostBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appbrain_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appbrain_activated"), "true")) {
                    getAppBrainBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("kidoz_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("kidoz_activated"), "true")) {
                    getKidozBanner(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appnext_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appnext_activated"), "true")) {
                    getAppNextBanner(currentActivity);//native
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("hyprmx_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("hyprmx_activated"), "true")) {
                    getHyprmxBanner(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("inmobi_activated"), "true")) {
                    getInmobiBanner(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mytarget_activated"), "true")) {
                    getMyTargetBanner(currentActivity);//not tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    getAdMobBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("applovin_activated"), "true")) {
                    getApplovinBanner(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("greedygames_activated"), "true")) {
                    getGreedyGamesBanner(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    getGoogleAdManagerBanner(currentActivity);
                }
            }


        } else if (Objects.equals(LOAD_AD, "ironsource")) {
            new CountDownTimer(20000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getIronSourceBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "adcolony")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAdcolonyBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "unity")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getUnityBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "meta")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getMetaBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "pangle")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getPangleBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "vungle")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getVungleBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appodeal")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAppodealBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "chartboost")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getChartBoostBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appbrain")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAppBrainBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "kidoz")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getKidozBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "appnext")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAppNextBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "hyprmx")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getHyprmxBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "inmobi")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getInmobiBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "mytarget")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getMyTargetBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "admob")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getAdMobBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "applovin")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getApplovinBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "greedygames")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getGreedyGamesBanner(currentActivity);
                }
            }.start();
        } else if (Objects.equals(LOAD_AD, "googleadmanager")) {
            new CountDownTimer(6000, 5000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getGoogleAdManagerBanner(currentActivity);
                }
            }.start();
        }


    }

    public void getMultipleBannerAds(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds() VAdEnhancerRegister.adNetworkOrderTableBannerList " + vAdEnhancerRegister.adNetworkOrderTableBannerList);

        currentActivity = activity;

        if (loopThroughLoadedBannersTimer != null) {
            loopThroughLoadedBannersTimer.cancel();
        }

        if (bannerLoadedWaitingTimer != null) {
            bannerLoadedWaitingTimer.cancel();
        }

        if (vAdEnhancerRegister != null) {
            if (!vAdEnhancerRegister.USERONLINE) {
                Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds user not online");
                return;
            }
        }


        loopThroughLoadedBannersTimer = new CountDownTimer(600000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds " + millisUntilFinished + " activity " + currentActivity.getLocalClassName());

                if (vAdEnhancerRegister.keyValueTableHashMap != null) {
                    if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("banner"), "false")) {
                        Log.e("VAdEnhancerMrecModule", "getMultipleBannerAds banner not enabled");
                        loopThroughLoadedBannersTimer.cancel();
                    }
                }

                if (vAdEnhancerRegister.adNetworkOrderTableBannerList != null) {
                    for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableBannerList.size(); i++) {
                        //Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds  " + register.adNetworkOrderTableBannerList.get(i).getAdnetwork() + register.adNetworkOrderTableBannerList.get(i).getAdtype() + register.adNetworkOrderTableBannerList.get(i).getPreference());

                        if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "gam") && BANNER_AD_GOOGLEADMANAGER != null && BANNER_AD_GOOGLEADMANAGER_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_GOOGLEADMANAGER");
                            BANNER_AD_GOOGLEADMANAGER_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_GOOGLEADMANAGER);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("googleadmanager");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "greedygames") && BANNER_AD_GREEDYGAMES != null && BANNER_AD_GREEDYGAMES_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_GREEDYGAMES");
                            BANNER_AD_GREEDYGAMES_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_GREEDYGAMES);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("greedygames");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "ironsource") && BANNER_AD_IRONSOURCE != null && BANNER_AD_IRONSOURCE_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_IRONSOURCE");
                            BANNER_AD_IRONSOURCE_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_IRONSOURCE);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("ironsource");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "adcolony") && BANNER_AD_ADCOLONY != null && BANNER_AD_ADCOLONY_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_ADCOLONY");
                            BANNER_AD_ADCOLONY_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_ADCOLONY);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("adcolony");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "unity") && BANNER_AD_UNITY != null && BANNER_AD_UNITY_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_UNITY");
                            BANNER_AD_UNITY_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_UNITY);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("unity");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "unity", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "meta") && BANNER_AD_META != null && BANNER_AD_META_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_META");
                            BANNER_AD_META_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_META);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("meta");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "vungle") && BANNER_AD_VUNGLE != null && BANNER_AD_VUNGLE_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_VUNGLE");
                            BANNER_AD_VUNGLE_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_VUNGLE);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("vungle");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "pangle") && BANNER_AD_PANGLE != null && BANNER_AD_PANGLE_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_PANGLE");
                            BANNER_AD_PANGLE_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_PANGLE.getBannerView());
                            bannerLoadedWaitingTimer();
                            loadBannerAds("pangle");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appodeal") && BANNER_AD_APPODEAL != null && BANNER_AD_APPODEAL_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_APPODEAL");
                            BANNER_AD_APPODEAL_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_APPODEAL);
                            if (Appodeal.isLoaded(Appodeal.BANNER_VIEW)) {
                                Appodeal.show(currentActivity, Appodeal.BANNER_VIEW, vAdEnhancerRegister.getPlacementId("appodeal", "banner", activity));
                            }
                            bannerLoadedWaitingTimer();
                            loadBannerAds("appodeal");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "chartboost") && BANNER_AD_CHARTBOOST != null && BANNER_AD_CHARTBOOST_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_CHARTBOOST");
                            BANNER_AD_CHARTBOOST_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_CHARTBOOST);
                            BANNER_AD_CHARTBOOST.show();
                            bannerLoadedWaitingTimer();
                            loadBannerAds("chartboost");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appbrain") && BANNER_AD_APPBRAIN != null && BANNER_AD_APPBRAIN_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_APPBRAIN");
                            BANNER_AD_APPBRAIN_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_APPBRAIN);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("appbrain");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appbrain", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "kidoz") && BANNER_AD_KIDOZ != null && BANNER_AD_KIDOZ_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_KIDOZ");
                            BANNER_AD_KIDOZ_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_KIDOZ);
                            BANNER_AD_KIDOZ.show();
                            bannerLoadedWaitingTimer();
                            loadBannerAds("kidoz");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "kidoz", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appnext") && BANNER_AD_APPNEXT != null && BANNER_AD_APPNEXT_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_APPNEXT");
                            BANNER_AD_APPNEXT_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_APPNEXT);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("appnext");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "hyprmx") && BANNER_AD_HYPRMX != null && BANNER_AD_HYPRMX_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_HYPRMX");
                            BANNER_AD_HYPRMX_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_HYPRMX);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("hyprmx");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "inmobi") && BANNER_AD_INMOBI != null && BANNER_AD_INMOBI_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_INMOBI");
                            BANNER_AD_INMOBI_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_INMOBI);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("inmobi");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "mytarget") && BANNER_AD_MYTARGET != null && BANNER_AD_MYTARGET_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_MYTARGET");
                            BANNER_AD_MYTARGET_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_MYTARGET);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("mytarget");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "admob") && BANNER_AD_ADMOB != null && BANNER_AD_ADMOB_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_ADMOB");
                            BANNER_AD_ADMOB_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_ADMOB);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("admob");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                            break;
                        } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "applovin") && BANNER_AD_APPLOVIN != null && BANNER_AD_APPLOVIN_LOADED) {
                            Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found BANNER_AD_APPLOVIN");
                            BANNER_AD_APPLOVIN_LOADED = false;
                            if (loopThroughLoadedBannersTimer != null) {
                                loopThroughLoadedBannersTimer.cancel();
                            }
                            if (noBannerLoadedWaitingTimer != null) {
                                noBannerLoadedWaitingTimer.cancel();
                            }
                            sendBanner(BANNER_AD_APPLOVIN);
                            bannerLoadedWaitingTimer();
                            loadBannerAds("applovin");
                            vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                            break;
                        } else {
                            //Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found non");
                        }

                        if (i == (vAdEnhancerRegister.adNetworkOrderTableBannerList.size() - 1)) {
                            //Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds found non in loop "+ i+" "+register.adNetworkOrderTableBannerList.size());
                            if (noBannerLoadedWaitingTimer == null) {
                                noBannerLoadedWaitingTimer();
                            }
                        }

                    }
                }


            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerBannerModule", "getMultipleBannerAds onFinish() ");
            }
        }.start();


    }

    public void noBannerLoadedWaitingTimer() {
        Log.e("VAdEnhancerBannerModule", "noBannerLoadedWaitingTimer()");

        if (noBannerLoadedWaitingTimer != null) {
            noBannerLoadedWaitingTimer.cancel();
        }

        noBannerLoadedWaitingTimer = new CountDownTimer(30000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerBannerModule", "noBannerLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerBannerModule", "noBannerLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mrec"), "true")) {
                    loadBannerAds("all");
                }
                if (noBannerLoadedWaitingTimer != null) {
                    noBannerLoadedWaitingTimer.cancel();
                    noBannerLoadedWaitingTimer = null;
                }
            }
        }.start();
    }

    public void bannerLoadedWaitingTimer() {
        Log.e("VAdEnhancerBannerModule", "bannerLoadedWaitingTimer()");

        if (bannerLoadedWaitingTimer != null) {
            bannerLoadedWaitingTimer.cancel();
        }

        bannerLoadedWaitingTimer = new CountDownTimer(vAdEnhancerRegister.BANNERLOADEDWAITINGTIMER, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerBannerModule", "bannerLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerBannerModule", "bannerLoadedWaitingTimer onFinish() ");
                getMultipleBannerAds(currentActivity);
            }
        }.start();
    }

    public View getSingleBannerAd(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAvailableBanner() " + vAdEnhancerRegister.adNetworkOrderTableBannerList);

        currentActivity = activity;


        View view = null;

        if (vAdEnhancerRegister.adNetworkOrderTableBannerList != null) {
            Log.e("VAdEnhancerBannerModule", "getAvailableBanner() register.adNetworkOrderTableBannerListData.size() " + vAdEnhancerRegister.adNetworkOrderTableBannerList.size());
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableBannerList.size(); i++) {
                //Log.e("VAdEnhancerBannerModule", "getAvailableBanner " + i + register.adNetworkOrderTableBannerList.get(i).getAdnetwork() + register.adNetworkOrderTableBannerList.get(i).getAdtype() + register.adNetworkOrderTableBannerList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "gam") && BANNER_AD_GOOGLEADMANAGER != null && BANNER_AD_GOOGLEADMANAGER_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found GOOGLEADMANAGER");
                    BANNER_AD_GOOGLEADMANAGER_LOADED = false;
                    loadBannerAds("googleadmanager");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_GOOGLEADMANAGER;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "greedygames") && BANNER_AD_GREEDYGAMES != null && BANNER_AD_GREEDYGAMES_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_GREEDYGAMES");
                    BANNER_AD_GREEDYGAMES_LOADED = false;
                    loadBannerAds("greedygames");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_GREEDYGAMES;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "ironsource") && BANNER_AD_IRONSOURCE != null && BANNER_AD_IRONSOURCE_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_IRONSOURCE");
                    BANNER_AD_IRONSOURCE_LOADED = false;
                    loadBannerAds("ironsource");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_IRONSOURCE;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "adcolony") && BANNER_AD_ADCOLONY != null && BANNER_AD_ADCOLONY_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_ADCOLONY");
                    BANNER_AD_ADCOLONY_LOADED = false;
                    loadBannerAds("adcolony");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_ADCOLONY;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "unity") && BANNER_AD_UNITY != null && BANNER_AD_UNITY_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_UNITY");
                    BANNER_AD_UNITY_LOADED = false;
                    loadBannerAds("unity");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "unity", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_UNITY;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "meta") && BANNER_AD_META != null && BANNER_AD_META_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_META");
                    BANNER_AD_META_LOADED = false;
                    loadBannerAds("meta");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_META;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "vungle") && BANNER_AD_VUNGLE != null && BANNER_AD_VUNGLE_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_VUNGLE");
                    BANNER_AD_VUNGLE_LOADED = false;
                    loadBannerAds("vungle");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_VUNGLE;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "pangle") && BANNER_AD_PANGLE != null && BANNER_AD_PANGLE_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_PANGLE");
                    BANNER_AD_PANGLE_LOADED = false;
                    loadBannerAds("pangle");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_PANGLE.getBannerView();
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appodeal") && BANNER_AD_APPODEAL != null && BANNER_AD_APPODEAL_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_APPODEAL");
                    BANNER_AD_APPODEAL_LOADED = false;
                    if (Appodeal.isLoaded(Appodeal.BANNER_VIEW)) {
                        Appodeal.show(currentActivity, Appodeal.BANNER_VIEW, vAdEnhancerRegister.getPlacementId("appodeal", "banner", activity));
                    }
                    loadBannerAds("appodeal");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_APPODEAL;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "chartboost") && BANNER_AD_CHARTBOOST != null && BANNER_AD_CHARTBOOST_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_CHARTBOOST");
                    BANNER_AD_CHARTBOOST_LOADED = false;
                    loadBannerAds("chartboost");
                    BANNER_AD_CHARTBOOST.show();
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_CHARTBOOST;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appbrain") && BANNER_AD_APPBRAIN != null && BANNER_AD_APPBRAIN_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_APPBRAIN");
                    BANNER_AD_APPBRAIN_LOADED = false;
                    loadBannerAds("appbrain");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appbrain", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_APPBRAIN;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "kidoz") && BANNER_AD_KIDOZ != null && BANNER_AD_KIDOZ_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_KIDOZ");
                    BANNER_AD_KIDOZ_LOADED = false;
                    loadBannerAds("kidoz");
                    BANNER_AD_KIDOZ.show();
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "kidoz", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_KIDOZ;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "appnext") && BANNER_AD_APPNEXT != null && BANNER_AD_APPNEXT_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_APPNEXT");
                    BANNER_AD_APPNEXT_LOADED = false;
                    loadBannerAds("appnext");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_APPNEXT;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "hyprmx") && BANNER_AD_HYPRMX != null && BANNER_AD_HYPRMX_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_HYPRMX");
                    BANNER_AD_HYPRMX_LOADED = false;
                    loadBannerAds("hyprmx");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_HYPRMX;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "inmobi") && BANNER_AD_INMOBI != null && BANNER_AD_INMOBI_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_INMOBI");
                    BANNER_AD_INMOBI_LOADED = false;
                    loadBannerAds("inmobi");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_INMOBI;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "mytarget") && BANNER_AD_MYTARGET != null && BANNER_AD_MYTARGET_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_MYTARGET");
                    BANNER_AD_MYTARGET_LOADED = false;
                    loadBannerAds("mytarget");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_MYTARGET;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "admob") && BANNER_AD_ADMOB != null && BANNER_AD_ADMOB_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_ADMOB");
                    BANNER_AD_ADMOB_LOADED = false;
                    loadBannerAds("admob");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_ADMOB;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableBannerList.get(i).getAdnetwork(), "applovin") && BANNER_AD_APPLOVIN != null && BANNER_AD_APPLOVIN_LOADED) {
                    Log.e("VAdEnhancerBannerModule", "getAvailableBanner found BANNER_AD_APPLOVIN");
                    BANNER_AD_APPLOVIN_LOADED = false;
                    loadBannerAds("applovin");
                    vAdEnhancerRegister.logFirebase("true", "banner", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                    if (noBannerLoadedWaitingTimer != null) {
                        noBannerLoadedWaitingTimer.cancel();
                    }
                    view = BANNER_AD_APPLOVIN;
                    break;
                } else {
                    //Log.e("VAdEnhancerBannerModule", "getAvailableBanner found none");
                }


            }
        }

        return view;
    }

    public void sendBanner(View view) {
        Log.e("VAdEnhancerBannerModule", "sendBanner() ");

        if (view.getParent() != null) {
            Log.e("VAdEnhancerBannerModule", "sendBanner has parent " + view);
        }

        try {
            Method method = currentActivity.getClass().getMethod("placeBannerAd", View.class);
            method.invoke(currentActivity, view);
        } catch (Exception e) {
            Log.e("VAdEnhancerBannerModule", "exception error " + e);
        }


    }


    private void getAdcolonyBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner()");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "adcolony", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AdcolonyInit(activity);

        BANNER_LISTENER_ADCOLONY = new AdColonyAdViewListener() {

            @Override
            public void onRequestFilled(AdColonyAdView ad) {
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onRequestFilled()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "adcolony", String.valueOf(""), String.valueOf(""));
                BANNER_AD_ADCOLONY = ad;
                BANNER_AD_ADCOLONY_LOADED = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onRequestNotFilled()");
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "adcolony", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAdcolonyBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onShow(AdColonyAdView ad) {
                super.onShow(ad);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onShow()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "adcolony", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClicked(AdColonyAdView ad) {
                super.onClicked(ad);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onClicked()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "adcolony", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onOpened(AdColonyAdView ad) {
                super.onOpened(ad);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onOpened()");
            }

            @Override
            public void onClosed(AdColonyAdView ad) {
                super.onClosed(ad);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onClosed()");
            }

            @Override
            public void onLeftApplication(AdColonyAdView ad) {
                super.onLeftApplication(ad);
                Log.e("VAdEnhancerBannerModule", "getAdcolonyBanner onLeftApplication()");
            }

        };

        AdColony.requestAdView(vAdEnhancerRegister.getPlacementId("adcolony", "banner", activity), BANNER_LISTENER_ADCOLONY, AdColonyAdSize.BANNER);


    }

    public void getUnityBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getUnityBanner()");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "unity", "", "");

        vAdEnhancerRegister.UnityInit(activity);

        BANNER_LISTENER_UNITY = new BannerView.IListener() {

            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                Log.e("VAdEnhancerBannerModule", "getUnityBanner onBannerLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "unity", "", "");
                BANNER_AD_UNITY = bannerAdView;
                BANNER_AD_UNITY_LOADED = true;
            }

            @Override
            public void onBannerShown(BannerView bannerAdView) {
                Log.e("VAdEnhancerBannerModule", "getUnityBanner onBannerShown()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "unity", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                Log.e("VAdEnhancerBannerModule", "getUnityBanner onBannerFailedToLoad() " + errorInfo.errorMessage);
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "unity", String.valueOf(errorInfo.errorCode), String.valueOf(errorInfo.errorMessage));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getUnityBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                Log.e("VAdEnhancerBannerModule", "getUnityBanner onBannerClick()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "unity", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerView) {
                Log.e("VAdEnhancerBannerModule", "getUnityBanner onBannerLeftApplication()");
            }
        };

        BANNER_AD_UNITY = new BannerView(activity, vAdEnhancerRegister.getPlacementId("unity", "banner", activity), new UnityBannerSize(320, 50));
        BANNER_AD_UNITY.setListener(BANNER_LISTENER_UNITY);
        BANNER_AD_UNITY.load();

    }

    public void getMetaBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getMetaBanner()");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "meta", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MetaInit(activity);

        BANNER_LISTENER_META = new com.facebook.ads.AdListener() {

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("VAdEnhancerBannerModule", "getMetaBanner onAdLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "meta", String.valueOf(""), String.valueOf(""));
                BANNER_AD_META_LOADED = true;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("VAdEnhancerBannerModule", "getMetaBanner onError()" + adError.getErrorMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "meta", String.valueOf(adError.getErrorCode()), String.valueOf(adError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getMetaBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("VAdEnhancerBannerModule", "getMetaBanner onLoggingImpression()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "meta", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("VAdEnhancerBannerModule", "getMetaBanner onAdClicked()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "meta", String.valueOf(""), String.valueOf(""));
            }
        };

        BANNER_AD_META = new com.facebook.ads.AdView(activity, vAdEnhancerRegister.getPlacementId("meta", "banner", activity), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        BANNER_AD_META.loadAd(BANNER_AD_META.buildLoadAdConfig().withAdListener(BANNER_LISTENER_META).build());

    }

    private void getIronSourceBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getIronSourceBanner()");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "ironsource", "", "");

        vAdEnhancerRegister.IronSourceInit(activity);

        if (BANNER_AD_IRONSOURCE != null) {
            IronSource.destroyBanner(BANNER_AD_IRONSOURCE);
            BANNER_AD_IRONSOURCE = null;
        }
        if (BANNER_LISTENER_IRONSOURCE != null) {
            BANNER_LISTENER_IRONSOURCE = null;
        }

        BANNER_LISTENER_IRONSOURCE = new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "ironsource", "", "");
                BANNER_AD_IRONSOURCE_LOADED = true;
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdLoadFailed() " + ironSourceError.getErrorMessage() + " , " + ironSourceError.getErrorCode());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "ironsource", String.valueOf(ironSourceError.getErrorCode()), String.valueOf(ironSourceError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getIronSourceBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onBannerAdScreenPresented() {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdScreenPresented()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "ironsource", "", "");
            }

            @Override
            public void onBannerAdClicked() {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdClicked()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "ironsource", "", "");
            }

            @Override
            public void onBannerAdScreenDismissed() {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdScreenDismissed()");
            }

            @Override
            public void onBannerAdLeftApplication() {
                Log.e("VAdEnhancerBannerModule", "getIronSourceBanner onBannerAdLeftApplication()");
            }
        };

        BANNER_AD_IRONSOURCE = IronSource.createBanner(activity, ISBannerSize.BANNER);
        BANNER_AD_IRONSOURCE.setBannerListener(BANNER_LISTENER_IRONSOURCE);
        IronSource.loadBanner(BANNER_AD_IRONSOURCE, vAdEnhancerRegister.getPlacementId("ironsource", "banner", activity));

    }

    private void getVungleBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getVungleBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "vungle", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.VungleInit(activity);

        BANNER_LISTENER_VUNGLE = new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdLoad() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "vungle", String.valueOf(""), String.valueOf(""));
                BannerAdConfig bannerAdConfig = new BannerAdConfig();
                bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
                BANNER_AD_VUNGLE = Banners.getBanner(vAdEnhancerRegister.getPlacementId("vungle", "banner", activity), bannerAdConfig, BANNNER_LISTENER_VUNGLE_2);
                BANNER_AD_VUNGLE_LOADED = true;
            }

            @Override
            public void onError(String id, VungleException exception) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onError() " + exception.getLocalizedMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "vungle", String.valueOf(exception.getExceptionCode()), String.valueOf(exception.getLocalizedMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getVungleBanner(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        BANNNER_LISTENER_VUNGLE_2 = new PlayAdCallback() {

            @Override
            public void onAdViewed(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdViewed() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClick(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdClick() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void creativeId(String creativeId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner creativeId() ");
            }

            @Override
            public void onAdStart(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdStart() ");
            }

            @Override
            public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdEnd() ");
            }

            @Override
            public void onAdEnd(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdEnd() ");
            }

            @Override
            public void onAdRewarded(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdRewarded() ");
            }

            @Override
            public void onAdLeftApplication(String placementId) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onAdLeftApplication() ");
            }

            @Override
            public void onError(String placementId, VungleException exception) {
                Log.e("VAdEnhancerBannerModule", "getVungleBanner onError() " + exception.getLocalizedMessage());
            }

        };

        BannerAdConfig bannerAdConfig = new BannerAdConfig();
        bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
        Banners.loadBanner(vAdEnhancerRegister.getPlacementId("vungle", "banner", activity), bannerAdConfig, BANNER_LISTENER_VUNGLE);

    }

    private void getAppodealBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAppodealBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "appodeal", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppODealInit(activity);

        BANNER_LISTENER_APPODEAL = new BannerCallbacks() {

            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerLoaded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "appodeal", String.valueOf(""), String.valueOf(""));
                BANNER_AD_APPODEAL_LOADED = true;
            }

            @Override
            public void onBannerFailedToLoad() {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerFailedToLoad() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "appodeal", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAppodealBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onBannerShown() {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerShown() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerClicked() {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerClicked() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerShowFailed() {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerShowFailed() ");
            }

            @Override
            public void onBannerExpired() {
                Log.e("VAdEnhancerBannerModule", "getAppodealBanner onBannerExpired() ");
            }
        };


        Appodeal.setBannerCallbacks(BANNER_LISTENER_APPODEAL);
        BANNER_AD_APPODEAL = Appodeal.getBannerView(activity);

    }

    private void getPangleBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getPangleBanner()");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "pangle", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.PangleInit(activity);

        BANNER_LISTENER_PANGLE = new PAGBannerAdLoadListener() {

            @Override
            public void onAdLoaded(PAGBannerAd pagBannerAd) {
                Log.e("VAdEnhancerBannerModule", "getPangleBanner onAdLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "pangle", String.valueOf(""), String.valueOf(""));
                BANNER_AD_PANGLE = pagBannerAd;
                BANNER_AD_PANGLE_LOADED = true;
            }

            @Override
            public void onError(int i, String s) {
                Log.e("VAdEnhancerBannerModule", "getPangleBanner onError()" + s);
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "pangle", String.valueOf(i), String.valueOf(s));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getPangleBanner(activity);
                            }
                        }
                    }
                }.start();
            }

        };

        PAGBannerAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "banner", activity), new PAGBannerRequest(PAGBannerSize.BANNER_W_320_H_50), BANNER_LISTENER_PANGLE);

    }

    private void getHyprmxBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getHyprmxBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "hyprmx", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.HyprMxInit(activity);

        BANNER_LISTENER_HYPRMX = new HyprMXBannerListener() {

            @Override
            public void onAdLoaded(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdLoaded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "hyprmx", String.valueOf(""), String.valueOf(""));
                BANNER_AD_HYPRMX_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull HyprMXBannerView hyprMXBannerView, @NonNull HyprMXErrors hyprMXErrors) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdFailedToLoad() " + hyprMXErrors.toString());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "hyprmx", String.valueOf(hyprMXErrors.toString()), String.valueOf(hyprMXErrors.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getHyprmxBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdClicked(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdClicked() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "hyprmx", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdOpened(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdOpened() ");
            }

            @Override
            public void onAdClosed(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdClosed() ");
            }

            @Override
            public void onAdLeftApplication(@NonNull HyprMXBannerView hyprMXBannerView) {
                Log.e("VAdEnhancerBannerModule", "getHyprmxBanner onAdLeftApplication() ");
            }
        };

        BANNER_AD_HYPRMX = new HyprMXBannerView(activity, null, vAdEnhancerRegister.getPlacementId("hyprmx", "banner", activity), HyprMXBannerSize.HyprMXAdSizeBanner.INSTANCE);
        BANNER_AD_HYPRMX.setListener(BANNER_LISTENER_HYPRMX);
        BANNER_AD_HYPRMX.loadAd();

    }

    private void getChartBoostBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getChartBoostBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "chartboost", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.ChartBoostInit(activity);

        BANNER_LISTENER_CHARTBOOST = new BannerCallback() {
            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                Log.e("VAdEnhancerBannerModule", "getChartBoostBanner onAdLoaded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "chartboost", String.valueOf(""), String.valueOf(""));
                BANNER_AD_CHARTBOOST_LOADED = true;
            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                Log.e("VAdEnhancerBannerModule", "getChartBoostBanner onAdShown() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {
                Log.e("VAdEnhancerBannerModule", "getChartBoostBanner onAdClicked() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {
                Log.e("VAdEnhancerBannerModule", "getChartBoostBanner onAdRequestedToShow() ");
            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {
                Log.e("VAdEnhancerBannerModule", "getChartBoostBanner onImpressionRecorded() ");
            }
        };

        BANNER_AD_CHARTBOOST = new Banner(activity, vAdEnhancerRegister.getPlacementId("chartboost", "banner", activity), Banner.BannerSize.STANDARD, BANNER_LISTENER_CHARTBOOST, null);
        BANNER_AD_CHARTBOOST.cache();


    }

    private void getMyTargetBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getMyTargetBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "mytarget", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MyTargetInit(activity);

        BANNER_LISTENER_MYTARGET = new MyTargetView.MyTargetViewListener() {
            @Override
            public void onLoad(MyTargetView myTargetView) {
                Log.e("VAdEnhancerBannerModule", "getMyTargetBanner onLoad() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "mytarget", String.valueOf(""), String.valueOf(""));
                BANNER_AD_MYTARGET_LOADED = true;
            }

            @Override
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull MyTargetView myTargetView) {
                Log.e("VAdEnhancerBannerModule", "getMyTargetBanner onNoAd() " + iAdLoadingError.getMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "mytarget", String.valueOf(iAdLoadingError.getCode()), String.valueOf(iAdLoadingError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getMyTargetBanner(activity);
                            }
                        }
                    }
                }.start();
            }


            @Override
            public void onShow(MyTargetView myTargetView) {
                Log.e("VAdEnhancerBannerModule", "getMyTargetBanner onShow() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "mytarget", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClick(MyTargetView myTargetView) {
                Log.e("VAdEnhancerBannerModule", "getMyTargetBanner onClick() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "mytarget", String.valueOf(""), String.valueOf(""));
            }
        };

        BANNER_AD_MYTARGET = new MyTargetView(activity);
        BANNER_AD_MYTARGET.setSlotId(vAdEnhancerRegister.getPlacementIdInt("mytarget", "banner", activity));
        BANNER_AD_MYTARGET.setAdSize(MyTargetView.AdSize.ADSIZE_320x50);
        BANNER_AD_MYTARGET.setListener(BANNER_LISTENER_MYTARGET);
        BANNER_AD_MYTARGET.load();

    }

    private void getInmobiBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getInmobiBanner() ");

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "inmobi", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.InMobiInit(activity);

        BANNER_LISTENER_INMOBI = new BannerAdEventListener() {

            @Override
            public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                super.onAdLoadSucceeded(inMobiBanner, adMetaInfo);
                vAdEnhancerRegister.logFirebase("true", "banner", "loadeed", "inmobi", String.valueOf(""), String.valueOf(""));
                BANNER_AD_INMOBI_LOADED = true;
            }

            @Override
            public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);
                Log.e("VAdEnhancerBannerModule", "getInmobiBanner onAdFetchFailed() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "inmobi", String.valueOf(inMobiAdRequestStatus.getStatusCode()), String.valueOf(inMobiAdRequestStatus.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getInmobiBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression(@NonNull InMobiBanner inMobiBanner) {
                super.onAdImpression(inMobiBanner);
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "inmobi", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDisplayed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDisplayed(inMobiBanner);
                Log.e("VAdEnhancerBannerModule", "getInmobiBanner onAdDisplayed() ");
            }

            @Override
            public void onAdDismissed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDismissed(inMobiBanner);
                Log.e("VAdEnhancerBannerModule", "getInmobiBanner onAdDismissed() ");
            }

            @Override
            public void onUserLeftApplication(@NonNull InMobiBanner inMobiBanner) {
                super.onUserLeftApplication(inMobiBanner);
                Log.e("VAdEnhancerBannerModule", "getInmobiBanner onUserLeftApplication() ");
            }

            @Override
            public void onRewardsUnlocked(@NonNull InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onRewardsUnlocked(inMobiBanner, map);
                Log.e("VAdEnhancerBannerModule", "getInmobiBanner onRewardsUnlocked() ");
            }
        };

        BANNER_AD_INMOBI = new InMobiBanner(activity.getApplicationContext(), vAdEnhancerRegister.getPlacementIdInt("inmobi", "banner", activity));
        BANNER_AD_INMOBI.setListener(BANNER_LISTENER_INMOBI);
        BANNER_AD_INMOBI.load();

    }

    public void getAdMobBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAdMobBanner()");


        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "admob", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AdmobInit(activity);

        BANNER_LISTENER_ADMOB = new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner onAdLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "admob", String.valueOf(""), String.valueOf(""));
                BANNER_AD_ADMOB_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner nAdFailedToLoad() " + adError.getMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "admob", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAdMobBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner onAdImpression()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner onAdClicked()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner onAdClosed()");
            }

            @Override
            public void onAdOpened() {
                Log.e("VAdEnhancerBannerModule", "getAdMobBanner onAdOpened()");
            }
        };

        BANNER_AD_ADMOB = new AdView(activity);
        BANNER_AD_ADMOB.setAdSize(AdSize.BANNER);
        BANNER_AD_ADMOB.setAdUnitId(vAdEnhancerRegister.getPlacementId("admob", "banner", activity));
        BANNER_AD_ADMOB.loadAd(new AdRequest.Builder().build());
        BANNER_AD_ADMOB.setAdListener(BANNER_LISTENER_ADMOB);

    }

    public void getApplovinBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getApplovinBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "applovin", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.MaxInit(activity);

        BANNER_LISTENER_APPLOVIN = new MaxAdViewAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdLoaded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "applovin", String.valueOf(""), String.valueOf(""));
                BANNER_AD_APPLOVIN_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdLoadFailed() " + error.getMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "applovin", String.valueOf(error.getCode()), String.valueOf(error.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getApplovinBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdDisplayed() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdClicked() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdExpanded(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdExpanded() ");
            }

            @Override
            public void onAdCollapsed(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdCollapsed() ");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdHidden() ");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.e("VAdEnhancerBannerModule", "getApplovinBanner onAdDisplayFailed() " + error.getMessage());
            }
        };

        BANNER_AD_APPLOVIN = new MaxAdView(vAdEnhancerRegister.getPlacementId("applovin", "banner", activity), activity);
        BANNER_AD_APPLOVIN.setListener(BANNER_LISTENER_APPLOVIN);
        BANNER_AD_APPLOVIN.loadAd();

    }

    public void getAppBrainBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAppBrainBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "appbrain", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppBrainInit(activity);

        BANNER_LISTENER_APPBRAIN = new com.appbrain.BannerListener() {

            @Override
            public void onAdRequestDone(boolean b) {
                Log.e("VAdEnhancerBannerModule", "getAppBrainBanner onAdRequestDone() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "appbrain", String.valueOf(""), String.valueOf(""));
                BANNER_AD_APPBRAIN_LOADED = true;
                if (b) {

                } else {
                    new CountDownTimer(60000, 5000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            if (vAdEnhancerRegister != null) {
                                if (vAdEnhancerRegister.USERONLINE) {
                                    getAppBrainBanner(activity);
                                }
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onClick() {
                Log.e("VAdEnhancerBannerModule", "getAppBrainBanner onClick() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "appbrain", String.valueOf(""), String.valueOf(""));
            }

        };

        BANNER_AD_APPBRAIN = new AppBrainBanner(activity);
        BANNER_AD_APPBRAIN.setBannerListener(BANNER_LISTENER_APPBRAIN);
        BANNER_AD_APPBRAIN.setAdId(AdId.custom(vAdEnhancerRegister.getPlacementId("appbrain", "banner", activity)));
        BANNER_AD_APPBRAIN.requestAd();

    }

    public void getKidozBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getKidozBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "kidoz", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.KidozInit(activity);

        BANNER_LISTENER_KIDOZ = new KidozBannerListener() {

            @Override
            public void onBannerReady() {
                Log.e("VAdEnhancerBannerModule", "getKidozBanner onBannerReady() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "kidoz", String.valueOf(""), String.valueOf(""));
                BANNER_AD_KIDOZ_LOADED = true;
            }

            @Override
            public void onBannerError(String s) {
                Log.e("VAdEnhancerBannerModule", "getKidozBanner onBannerError() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "error", "kidoz", String.valueOf(s.toString()), String.valueOf(s.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getKidozBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onBannerViewAdded() {
                Log.e("VAdEnhancerBannerModule", "getKidozBanner onBannerViewAdded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "kidoz", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onBannerClose() {
                Log.e("VAdEnhancerBannerModule", "getKidozBanner onBannerClose() ");

            }

            @Override
            public void onBannerNoOffers() {
                Log.e("VAdEnhancerBannerModule", "getKidozBanner onBannerClose() ");
            }
        };

        BANNER_AD_KIDOZ = KidozSDK.getKidozBanner(activity);
        BANNER_AD_KIDOZ.setKidozBannerListener(BANNER_LISTENER_KIDOZ);
        BANNER_AD_KIDOZ.load();

    }

    public void getAppNextBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getAppNextBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "appnext", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppNextInit(activity);

        BANNER_LISTENER_APPNEXT = new com.appnext.banners.BannerListener() {

            @Override
            public void onAdLoaded(String s, AppnextAdCreativeType creativeType) {
                super.onAdLoaded(s, creativeType);
                Log.e("VAdEnhancerBannerModule", "getAppNextBanner onAdLoaded() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "appnext", String.valueOf(""), String.valueOf(""));
                BANNER_AD_APPNEXT_LOADED = true;
            }

            @Override
            public void onError(AppnextError error) {
                super.onError(error);
                Log.e("VAdEnhancerBannerModule", "getAppNextBanner onError() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "error", "appnext", String.valueOf(error.toString()), String.valueOf(error.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getAppNextBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void adImpression() {
                super.adImpression();
                Log.e("VAdEnhancerBannerModule", "getAppNextBanner adImpression() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "appnext", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.e("VAdEnhancerBannerModule", "getAppNextBanner onAdClicked() ");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "appnext", String.valueOf(""), String.valueOf(""));
            }
        };

        BANNER_AD_APPNEXT = new com.appnext.banners.BannerView(activity);
        BANNER_AD_APPNEXT.setPlacementId(vAdEnhancerRegister.getPlacementId("appnext", "banner", activity));
        BANNER_AD_APPNEXT.setBannerSize(BannerSize.BANNER);
        BANNER_AD_APPNEXT.setBannerListener(BANNER_LISTENER_APPNEXT);
        BANNER_AD_APPNEXT.loadAd(new BannerAdRequest());

    }

    public void getGreedyGamesBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "greedygames", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GreedyGamesInit(activity);


        BANNER_LISTENER_GREEDYGAMES = new AdLoadCallback() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner onAdLoaded");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "greedygames", String.valueOf(""), String.valueOf(""));
                BANNER_AD_GREEDYGAMES_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(AdErrors cause) {
                Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner onAdLoadFailed " + cause);
                vAdEnhancerRegister.logFirebase("true", "banner", "error", "greedygames", String.valueOf(cause.toString()), String.valueOf(cause.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getGreedyGamesBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onUiiOpened() {
                Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner onUiiOpened");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "greedygames", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onReadyForRefresh() {
                Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner onReadyForRefresh");
            }

            /*@Override
            public void onPlacementId(@NonNull String s) {

            }*/

            @Override
            public void onUiiClosed() {
                Log.e("VAdEnhancerBannerModule", "getGreedyGamesBanner onUiiClosed");
            }

        };


        float width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                320f,
                activity.getResources().getDisplayMetrics()
        );
        float height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                activity.getResources().getDisplayMetrics()
        );


        BANNER_AD_GREEDYGAMES = new GGAdview(activity);
        BANNER_AD_GREEDYGAMES.setUnitId(vAdEnhancerRegister.getPlacementId("greedygames", "banner", activity));
        BANNER_AD_GREEDYGAMES.setAdsMaxWidth((int) width);
        BANNER_AD_GREEDYGAMES.setAdsMaxHeight((int) height);
        BANNER_AD_GREEDYGAMES.setRefreshPolicy(RefreshPolicy.MANUAL);
        BANNER_AD_GREEDYGAMES.loadAd(BANNER_LISTENER_GREEDYGAMES);


    }

    public void getGoogleAdManagerBanner(Activity activity) {
        Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner() " + activity.getLocalClassName());

        vAdEnhancerRegister.logFirebase("true", "banner", "requested", "googleadmanager", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GoogleAdManagerInit(activity);


        BANNER_LISTENER_GOOGLEADMANAGER = new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdLoaded()");
                vAdEnhancerRegister.logFirebase("true", "banner", "loaded", "googleadmanager", String.valueOf(""), String.valueOf(""));
                BANNER_AD_GOOGLEADMANAGER_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdFailedToLoad() " + adError.getMessage());
                vAdEnhancerRegister.logFirebase("true", "banner", "failed", "googleadmanager", String.valueOf(adError.getCode()), String.valueOf(adError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                getGoogleAdManagerBanner(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdImpression()");
                vAdEnhancerRegister.logFirebase("true", "banner", "impression", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdClicked()");
                vAdEnhancerRegister.logFirebase("true", "banner", "clicked", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdClosed()");
            }

            @Override
            public void onAdOpened() {
                Log.e("VAdEnhancerBannerModule", "getGoogleAdManagerBanner onAdOpened()");
            }
        };

        BANNER_AD_GOOGLEADMANAGER = new AdManagerAdView(activity);
        BANNER_AD_GOOGLEADMANAGER.setAdSize(AdSize.BANNER);
        BANNER_AD_GOOGLEADMANAGER.setAdUnitId(vAdEnhancerRegister.getPlacementId("gam", "banner", activity));
        BANNER_AD_GOOGLEADMANAGER.setAdListener(BANNER_LISTENER_GOOGLEADMANAGER);
        BANNER_AD_GOOGLEADMANAGER.loadAd(new AdManagerAdRequest.Builder().build());

    }


}
