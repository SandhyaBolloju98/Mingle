package com.v.ad.vadenhancerLibrary.Classes.Modules;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.appbrain.AdId;
import com.appbrain.InterstitialBuilder;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.appnext.core.AppnextAdCreativeType;
import com.appnext.core.callbacks.OnAdClicked;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnAdOpened;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;
import com.chartboost.sdk.ads.Interstitial;
import com.chartboost.sdk.callbacks.InterstitialCallback;
import com.chartboost.sdk.events.CacheError;
import com.chartboost.sdk.events.CacheEvent;
import com.chartboost.sdk.events.ClickError;
import com.chartboost.sdk.events.ClickEvent;
import com.chartboost.sdk.events.DismissEvent;
import com.chartboost.sdk.events.ImpressionEvent;
import com.chartboost.sdk.events.ShowError;
import com.chartboost.sdk.events.ShowEvent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;
import com.greedygame.core.models.general.AdErrors;
import com.hyprmx.android.sdk.core.HyprMX;
import com.hyprmx.android.sdk.core.HyprMXErrors;
import com.hyprmx.android.sdk.placement.PlacementListener;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.my.target.common.models.IAdLoadingError;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;
import com.v.ad.vadenhancerLibrary.SessionManager;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class VAdEnhancerInterstitialModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private static volatile VAdEnhancerInterstitialModule INSTANCE = null;
    private VAdEnhancer vAdEnhancer;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private CountDownTimer noInterstitialAdLoadedWaitingTimer;

    //ironsource
    private InterstitialListener INTERSTITIAL_AD_IRONSOURCE_LISTENER;
    //Adcolony
    private AdColonyInterstitial INTERSTITIAL_AD_COLONY;
    private AdColonyInterstitialListener INTERSTITIAL_LISTENER_ADCOLONY;
    //unity
    private IUnityAdsLoadListener INTERSTITIAL_LISTENER_UNITY;
    private IUnityAdsShowListener INTERSTITIAL_LISTENER_UNITY_2;
    //meta
    private com.facebook.ads.InterstitialAd INTERSTITIAL_AD_META;
    private InterstitialAdListener INTERSTITIAL_LISTENER_META;
    //pangle
    private PAGInterstitialAd INTERSTITIAL_AD_PANGLE;
    private PAGInterstitialAdLoadListener INTERSTITIAL_LISTENER_PANGLE;
    private PAGInterstitialAdInteractionListener INTERSTITIAL_LISTENER_PANGLE_2;
    //mytarget
    private com.my.target.ads.InterstitialAd INTERSTITIAL_AD_MYTARGET;
    private com.my.target.ads.InterstitialAd.InterstitialAdListener INTERSTITIAL_LISTENER_MYTARGET;
    //vungle
    private LoadAdCallback INTERSTITIAL_LISTENER_VUNGLE;
    private PlayAdCallback INTERSTITIAL_LISTENER_VUNGLE_2;
    //appodeal
    private InterstitialCallbacks INTERSTITIAL_LISTENER_APPODEAL;
    //hyprmx
    private com.hyprmx.android.sdk.placement.Placement INTERSTITIAL_AD_HYPRMX;
    private PlacementListener INTERSTITIAL_LISTENER_HYPRMX;
    //inmobi
    private InMobiInterstitial INTERSTITIAL_AD_INMOBI;
    private InterstitialAdEventListener INTERSTITIAL_LISTENER_INMOBI;
    //admob
    private InterstitialAd INTERSTITIAL_AD_ADMOB;
    private InterstitialAdLoadCallback INTERSTITIAL_LISTENER_ADMOB;
    private FullScreenContentCallback INTERSTITIAL_LISTENER_ADMOB_2;
    //applovin
    private MaxInterstitialAd INTERSTITIAL_AD_APPLOVIN;
    private MaxAdListener INTERSTITIAL_LISTENER_APPLOVIN;
    //chartboost
    private Interstitial INTERSTITIAL_AD_CHARTBOOST;
    private InterstitialCallback INTERSTITIAL_LISTENER_CHARTBOOST;
    //appbrain
    private InterstitialBuilder INTERSTITIAL_AD_APPBRAIN;
    private com.appbrain.InterstitialListener INTERSTITIAL_LISTENER_APPBRAIN;
    //kidoz
    private KidozInterstitial INTERSTITIAL_AD_KIDOZ;
    private BaseInterstitial.IOnInterstitialEventListener INTERSTITIAL_LISTENER_KIDOZ;
    //appnext
    private com.appnext.ads.interstitial.Interstitial INTERSTITIAL_AD_APPNEXT;
    //greedygames
    private GGInterstitialAd INTERSTITIAL_AD_GREEDYGAMES;
    private GGInterstitialEventsListener INTERSTITIAL_LISTENER_GREEDYGAMES;
    //googleadmanager
    private AdManagerInterstitialAd INTERSTITIAL_AD_GOOGLEADMANAGER;
    private AdManagerInterstitialAdLoadCallback INTERSTITIAL_LISTENER_GOOGLEADMANAGER;
    private FullScreenContentCallback INTERSTITIAL_LISTENER_GOOGLEADMANAGER_2;

    private boolean INTERSTITIAL_AD_IRONSOURCE_LOADED = false;
    private boolean INTERSTITIAL_AD_ADCOLONY_LOADED = false;
    private boolean INTERSTITIAL_AD_UNITY_LOADED = false;
    private boolean INTERSTITIAL_AD_META_LOADED = false;
    private boolean INTERSTITIAL_AD_PANGLE_LOADED = false;
    private boolean INTERSTITIAL_AD_VUNGLE_LOADED = false;
    private boolean INTERSTITIAL_AD_APPODEAL_LOADED = false;
    private boolean INTERSTITIAL_AD_CHARTBOOST_LOADED = false;
    private boolean INTERSTITIAL_AD_APPBRAIN_LOADED = false;
    private boolean INTERSTITIAL_AD_KIDOZ_LOADED = false;
    private boolean INTERSTITIAL_AD_APPNEXT_LOADED = false;
    private boolean INTERSTITIAL_AD_HYPRMX_LOADED = false;
    private boolean INTERSTITIAL_AD_INMOBI_LOADED = false;
    private boolean INTERSTITIAL_AD_MYTARGET_LOADED = false;
    private boolean INTERSTITIAL_AD_ADMOB_LOADED = false;
    private boolean INTERSTITIAL_AD_APPLOVIN_LOADED = false;
    private boolean INTERSTITIAL_AD_GREEDYGAMES_LOADED = false;
    private boolean INTERSTITIAL_AD_GOOGLEADMANAGER_LOADED = false;

    private VAdEnhancerInterstitialModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAdEnhancerInterstAd", "VAdEnhancerInterstitialModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;
        vAdEnhancerRegister = registerx;

    }

    public static VAdEnhancerInterstitialModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAdEnhancerInterstitialModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancerInterstitialModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadInterstitialAds() {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAds()  ");

        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAdEnhancerInterstAd", "loadInterstitialAds() register.keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAdEnhancerInterstAd", "loadInterstitialAds() register.keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAdEnhancerInterstAd", "loadInterstitialAds() register.keyValueTableHashMap.get(activated) false");
        } else {
            Log.e("VAdEnhancerInterstAd", "loadBannerAds() loading...");

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("ironsource_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("ironsource_activated"), "true")) {
                    loadInterstitialIronSource(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("adcolony_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("adcolony_activated"), "true")) {
                    loadInterstitialAdColony(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("unity_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("unity_activated"), "true")) {
                    loadInterstitialUnity(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("meta_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("meta_activated"), "true")) {
                    loadInterstitialMeta(currentActivity);//not tested //native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    loadInterstitialPangle(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("vungle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("vungle_activated"), "true")) {
                    loadInterstitialVungle(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appodeal_activated"), "true")) {
                    loadInterstitialAppodeal(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("chartboost_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("chartboost_activated"), "true")) {
                    loadInterstitialChartBoost(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appbrain_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appbrain_activated"), "true")) {
                    loadInterstitialAppBrain(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("kidoz_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("kidoz_activated"), "true")) {
                    loadInterstitialKidoz(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appnext_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appnext_activated"), "true")) {
                    loadInterstitialAppNext(currentActivity);//native
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("hyprmx_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("hyprmx_activated"), "true")) {
                    loadInterstitialHyprmx(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("inmobi_activated"), "true")) {
                    loadInterstitialInmobi(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mytarget_activated"), "true")) {
                    loadInterstitialMyTarget(currentActivity);//not tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    loadInterstitialAdmob(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("applovin_activated"), "true")) {
                    loadInterstitialApplovin(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("greedygames_activated"), "true")) {
                    loadInterstitialGreedyGames(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    loadInterstitialGoogleAdManager(currentActivity);
                }
            }

        }

    }

    public boolean isInterstitialAdReady(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() " + currentActivity.getLocalClassName());

        currentActivity = activity;


        boolean ready = false;

        if (vAdEnhancerRegister.adNetworkOrderTableInterstitialList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableInterstitialList.size(); i++) {
                Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady  " + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "gam") && INTERSTITIAL_AD_GOOGLEADMANAGER_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready googleadmanager");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "greedygames") && INTERSTITIAL_AD_GREEDYGAMES_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready greedygames");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "ironsource") && INTERSTITIAL_AD_IRONSOURCE_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready ironsource");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "adcolony") && INTERSTITIAL_AD_ADCOLONY_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready adcolony");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "unity") && INTERSTITIAL_AD_UNITY_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready unity");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "meta") && INTERSTITIAL_AD_META_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready meta");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "vungle") && INTERSTITIAL_AD_VUNGLE_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready vungle");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "pangle") && INTERSTITIAL_AD_PANGLE_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready pangle");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appodeal") && INTERSTITIAL_AD_APPODEAL_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready appodeal");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "chartboost") && INTERSTITIAL_AD_CHARTBOOST_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready chartboost");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appbrain") && INTERSTITIAL_AD_APPBRAIN_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready appbrain");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "kidoz") && INTERSTITIAL_AD_KIDOZ_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready kidoz");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appnext") && INTERSTITIAL_AD_APPNEXT_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready appnext");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "hyprmx") && INTERSTITIAL_AD_HYPRMX_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready hyprmx");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "inmobi") && INTERSTITIAL_AD_INMOBI_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready inmobi");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "mytarget") && INTERSTITIAL_AD_MYTARGET_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready mytarget");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "admob") && INTERSTITIAL_AD_ADMOB_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready admob");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "applovin") && INTERSTITIAL_AD_APPLOVIN_LOADED) {//not tested
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready applovin");
                    ready = true;
                    break;
                } else {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady() ready none");
                }


               /* if (i == (adNetworkOrderTableNativeList.size() - 1)) {
                    Log.e("VAdEnhancerInterstAd", "isInterstitialAdReady found non in loop");
                    if (ready) {
                        if (noInterstitialAdLoadedWaitingTimer != null) {
                            noInterstitialAdLoadedWaitingTimer.cancel();
                        }
                    } else {
                        if (noInterstitialAdLoadedWaitingTimer == null) {
                            noInterstitialAdLoadedWaitingTimer();
                        }
                    }
                }*/

            }
        }

        if (ready) {
            if (noInterstitialAdLoadedWaitingTimer != null) {
                noInterstitialAdLoadedWaitingTimer.cancel();
            }
        } else {
            if (noInterstitialAdLoadedWaitingTimer == null) {
                noInterstitialAdLoadedWaitingTimer();
            }
        }

        return ready;
    }

    public void showInterstitialAd(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "showInterstitialAd()");

        currentActivity = activity;

        if (vAdEnhancerRegister.adNetworkOrderTableInterstitialList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableInterstitialList.size(); i++) {
                Log.e("VAdEnhancerInterstAd", "showInterstitialAd  " + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getPreference());

                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "gam") && INTERSTITIAL_AD_GOOGLEADMANAGER_LOADED) {
                    if (INTERSTITIAL_AD_GOOGLEADMANAGER != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() googleadmanager");
                        INTERSTITIAL_AD_GOOGLEADMANAGER.show(currentActivity);
                        INTERSTITIAL_AD_GOOGLEADMANAGER_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: G-Ad", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "greedygames") && INTERSTITIAL_AD_GREEDYGAMES_LOADED) {
                    if (INTERSTITIAL_AD_GREEDYGAMES != null) {
                        if (INTERSTITIAL_AD_GREEDYGAMES.isAdLoaded()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() greedygames");
                            INTERSTITIAL_AD_GREEDYGAMES.show();
                            INTERSTITIAL_AD_GREEDYGAMES_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: GreedyGames", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "ironsource") && INTERSTITIAL_AD_IRONSOURCE_LOADED) {
                    if (IronSource.isInterstitialReady()) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() ironsource");
                        IronSource.showInterstitial(vAdEnhancerRegister.getPlacementId("ironsource", "interstitial", activity));
                        INTERSTITIAL_AD_IRONSOURCE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: IronSource", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "adcolony") && INTERSTITIAL_AD_ADCOLONY_LOADED) {
                    if (INTERSTITIAL_AD_COLONY != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() adcolony");
                        INTERSTITIAL_AD_COLONY.show();
                        INTERSTITIAL_AD_ADCOLONY_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Adcolony", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "unity") && INTERSTITIAL_AD_UNITY_LOADED) {
                    Log.e("VAdEnhancerInterstAd", "showInterstitialAd() unity");
                    UnityAds.show(currentActivity, vAdEnhancerRegister.getPlacementId("unity", "interstitial", activity), INTERSTITIAL_LISTENER_UNITY_2);
                    INTERSTITIAL_AD_UNITY_LOADED = false;
                    Toast.makeText(currentActivity, "Ad: Unity", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "unity", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "meta") && INTERSTITIAL_AD_META_LOADED) {
                    if (INTERSTITIAL_AD_META != null) {
                        if (INTERSTITIAL_AD_META.isAdLoaded()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() meta");
                            INTERSTITIAL_AD_META.show();
                            INTERSTITIAL_AD_META_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Meta", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "meta", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "vungle") && INTERSTITIAL_AD_VUNGLE_LOADED) {
                    if (Vungle.canPlayAd(vAdEnhancerRegister.getPlacementId("vungle", "interstitial", activity))) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() vungle");
                        Vungle.playAd(vAdEnhancerRegister.getPlacementId("vungle", "interstitial", activity), null, INTERSTITIAL_LISTENER_VUNGLE_2);
                        INTERSTITIAL_AD_VUNGLE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Vungle", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "pangle") && INTERSTITIAL_AD_PANGLE_LOADED) {
                    if (INTERSTITIAL_AD_PANGLE != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() pangle");
                        INTERSTITIAL_AD_PANGLE.show(currentActivity);
                        INTERSTITIAL_AD_PANGLE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Pangle", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "pangle", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appodeal") && INTERSTITIAL_AD_APPODEAL_LOADED) {
                    if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() appodeal");
                        Appodeal.show(currentActivity, Appodeal.INTERSTITIAL, vAdEnhancerRegister.getPlacementId("appodeal", "interstitial", activity));
                        INTERSTITIAL_AD_APPODEAL_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Appodeal", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "chartboost") && INTERSTITIAL_AD_CHARTBOOST_LOADED) {
                    if (INTERSTITIAL_AD_CHARTBOOST != null) {
                        if (INTERSTITIAL_AD_CHARTBOOST.isCached()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() chartboost");
                            INTERSTITIAL_AD_CHARTBOOST.show();
                            INTERSTITIAL_AD_CHARTBOOST_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Chartboost", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appbrain") && INTERSTITIAL_AD_APPBRAIN_LOADED) {
                    if (INTERSTITIAL_AD_APPBRAIN != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() appbrain");
                        INTERSTITIAL_AD_APPBRAIN.show(currentActivity);
                        INTERSTITIAL_AD_APPBRAIN_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: AppBrain", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "appbrain", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "kidoz") && INTERSTITIAL_AD_KIDOZ_LOADED) {
                    if (INTERSTITIAL_AD_KIDOZ != null) {
                        if (INTERSTITIAL_AD_KIDOZ.isLoaded()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() kidoz");
                            INTERSTITIAL_AD_KIDOZ.show();
                            INTERSTITIAL_AD_KIDOZ_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Kidoz", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "kidoz", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "appnext") && INTERSTITIAL_AD_APPNEXT_LOADED) {
                    if (INTERSTITIAL_AD_APPNEXT != null) {
                        if (INTERSTITIAL_AD_APPNEXT.isAdLoaded()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() appnext");
                            INTERSTITIAL_AD_APPNEXT.showAd();
                            INTERSTITIAL_AD_APPNEXT_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: AppNext", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "hyprmx") && INTERSTITIAL_AD_HYPRMX_LOADED) {
                    if (INTERSTITIAL_AD_HYPRMX != null) {
                        if (INTERSTITIAL_AD_HYPRMX.isAdAvailable()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() hyprmx");
                            INTERSTITIAL_AD_HYPRMX.showAd();
                            INTERSTITIAL_AD_HYPRMX_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: HyprMx", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "inmobi") && INTERSTITIAL_AD_INMOBI_LOADED) {
                    if (INTERSTITIAL_AD_INMOBI != null) {
                        if (INTERSTITIAL_AD_INMOBI.isReady()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() inmobi");
                            INTERSTITIAL_AD_INMOBI.show();
                            INTERSTITIAL_AD_INMOBI_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: InMobi", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "mytarget") && INTERSTITIAL_AD_MYTARGET_LOADED) {
                    if (INTERSTITIAL_AD_MYTARGET != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() mytarget");
                        INTERSTITIAL_AD_MYTARGET.show();
                        INTERSTITIAL_AD_MYTARGET_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: MyTarget", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "admob") && INTERSTITIAL_AD_ADMOB_LOADED) {
                    if (INTERSTITIAL_AD_ADMOB != null) {
                        Log.e("VAdEnhancerInterstAd", "showInterstitialAd() admob");
                        INTERSTITIAL_AD_ADMOB.show(currentActivity);
                        INTERSTITIAL_AD_ADMOB_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: AdMob", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableInterstitialList.get(i).getAdnetwork(), "applovin") && INTERSTITIAL_AD_APPLOVIN_LOADED) {
                    if (INTERSTITIAL_AD_APPLOVIN != null) {
                        if (INTERSTITIAL_AD_APPLOVIN.isReady()) {
                            Log.e("VAdEnhancerInterstAd", "showInterstitialAd() applovin");
                            INTERSTITIAL_AD_APPLOVIN.showAd();
                            INTERSTITIAL_AD_APPLOVIN_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: AppLovin", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "interstitial", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                    break;
                }

            }
        }

    }

    public void noInterstitialAdLoadedWaitingTimer() {
        Log.e("VAdEnhancerInterstAd", "noInterstitialAdLoadedWaitingTimer()");

        if (noInterstitialAdLoadedWaitingTimer != null) {
            noInterstitialAdLoadedWaitingTimer.cancel();
        }

        noInterstitialAdLoadedWaitingTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerInterstAd", "noInterstitialAdLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerInterstAd", "noInterstitialAdLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("interstitial"), "true")) {
                    loadInterstitialAds();
                }
                if (noInterstitialAdLoadedWaitingTimer != null) {
                    noInterstitialAdLoadedWaitingTimer.cancel();
                    noInterstitialAdLoadedWaitingTimer = null;
                }
            }
        }.start();
    }

    private void onInterstitialAdRewarded() {
        Log.e("VAdEnhancerInterstAd", "onInterstitialAdRewarded() ");


        try {
            Method method = currentActivity.getClass().getMethod("interstitialAdRewarded");
            method.invoke(currentActivity);
        } catch (Exception e) {
            Log.e("VAdEnhancerInterstAd", "exception error " + e);
        }
    }


    //ironsource
    public void loadInterstitialIronSource(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource()");

        logFirebase("true", "interstitial", "requested", "ironsource", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.IronSourceInit(activity);

        INTERSTITIAL_AD_IRONSOURCE_LISTENER = new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdReady()");
                logFirebase("true", "interstitial", "loaded", "ironsource", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_IRONSOURCE_LOADED = true;
            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdLoadFailed() " + ironSourceError.getErrorMessage());
                logFirebase("true", "interstitial", "failed", "ironsource", String.valueOf(ironSourceError.getErrorCode()), String.valueOf(ironSourceError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialIronSource(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onInterstitialAdClosed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdClosed()");
                onInterstitialAdRewarded();
                loadInterstitialIronSource(activity);
            }

            @Override
            public void onInterstitialAdShowSucceeded() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdShowSucceeded()");
                logFirebase("true", "interstitial", "impression", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onInterstitialAdClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdClicked()");
                logFirebase("true", "interstitial", "clicked", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onInterstitialAdOpened() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdOpened()");
            }


            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialIronSource onInterstitialAdShowFailed() " + ironSourceError.getErrorMessage());
            }

        };

        IronSource.setInterstitialListener(INTERSTITIAL_AD_IRONSOURCE_LISTENER);
        IronSource.loadInterstitial();

    }

    //adcolony
    public void loadInterstitialAdColony(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony()");

        logFirebase("true", "interstitial", "requested", "adcolony", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.AdcolonyInit(activity);

        INTERSTITIAL_LISTENER_ADCOLONY = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onRequestFilled()");
                logFirebase("true", "interstitial", "loaded", "adcolony", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_COLONY = ad;
                INTERSTITIAL_AD_ADCOLONY_LOADED = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onRequestNotFilled()");
                logFirebase("true", "interstitial", "failed", "adcolony", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialAdColony(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onClosed()");
                onInterstitialAdRewarded();
                loadInterstitialAdColony(activity);
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onOpened()");
                logFirebase("true", "interstitial", "impression", "adcolony", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onClicked()");
                logFirebase("true", "interstitial", "clicked", "adcolony", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onIAPEvent(AdColonyInterstitial ad, String product_id, int engagement_type) {
                super.onIAPEvent(ad, product_id, engagement_type);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onIAPEvent()");
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onExpiring()");
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdColony onLeftApplication()");
            }


        };

        AdColony.requestInterstitial(vAdEnhancerRegister.getPlacementId("adcolony", "interstitial", activity), INTERSTITIAL_LISTENER_ADCOLONY);
    }

    //unity
    private void loadInterstitialUnity(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity() " + activity.getLocalClassName());

        logFirebase("true", "interstitial", "requested", "unity", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.UnityInit(activity);

        INTERSTITIAL_LISTENER_UNITY = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "unity", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_UNITY_LOADED = true;
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsFailedToLoad() " + message);
                logFirebase("true", "interstitial", "failed", "unity", String.valueOf(error.toString()), String.valueOf(message));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialUnity(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        UnityAds.load(vAdEnhancerRegister.getPlacementId("unity", "interstitial", activity), INTERSTITIAL_LISTENER_UNITY);

        INTERSTITIAL_LISTENER_UNITY_2 = new IUnityAdsShowListener() {

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsShowComplete()");
                onInterstitialAdRewarded();
                loadInterstitialUnity(activity);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsShowStart()");
                logFirebase("true", "interstitial", "impression", "unity", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsShowClick()");
                logFirebase("true", "interstitial", "clicked", "unity", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialUnity onUnityAdsShowFailure()" + message);
            }

        };
    }

    //meta
    private void loadInterstitialMeta(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta()");

        logFirebase("true", "interstitial", "requested", "meta", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.MetaInit(activity);

        INTERSTITIAL_LISTENER_META = new InterstitialAdListener() {

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "meta", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_META_LOADED = true;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta loadInterstitialMeta() onError() " + adError.getErrorMessage());
                logFirebase("true", "interstitial", "failed", "meta", String.valueOf(adError.getErrorCode()), String.valueOf(adError.getErrorMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialMeta(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta onInterstitialDismissed()");
                onInterstitialAdRewarded();
                loadInterstitialMeta(activity);
            }


            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta onLoggingImpression()");
                logFirebase("true", "interstitial", "impression", "meta", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "meta", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMeta onInterstitialDisplayed()");
            }

        };

        INTERSTITIAL_AD_META = new com.facebook.ads.InterstitialAd(activity, vAdEnhancerRegister.getPlacementId("meta", "interstitial", activity));
        INTERSTITIAL_AD_META.loadAd(INTERSTITIAL_AD_META.buildLoadAdConfig()
                .withAdListener(INTERSTITIAL_LISTENER_META)
                .build());
    }

    //pangle
    private void loadInterstitialPangle(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialPangle()");

        logFirebase("true", "interstitial", "requested", "pangle", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.PangleInit(activity);

        PAGInterstitialRequest request = new PAGInterstitialRequest();

        INTERSTITIAL_LISTENER_PANGLE = new PAGInterstitialAdLoadListener() {
            @Override
            public void onAdLoaded(PAGInterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialPangle onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "pangle", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_PANGLE = interstitialAd;
                INTERSTITIAL_AD_PANGLE.setAdInteractionListener(INTERSTITIAL_LISTENER_PANGLE_2);
                INTERSTITIAL_AD_PANGLE_LOADED = true;
            }

            @Override
            public void onError(int code, String message) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialPangle onError() " + message);
                logFirebase("true", "interstitial", "failed", "pangle", String.valueOf(code), String.valueOf(message));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialPangle(activity);
                            }
                        }
                    }
                }.start();
            }
        };


        PAGInterstitialAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "interstitial", activity), request, INTERSTITIAL_LISTENER_PANGLE);

        INTERSTITIAL_LISTENER_PANGLE_2 = new PAGInterstitialAdInteractionListener() {

            @Override
            public void onAdDismissed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialListenerPangle onAdDismissed()");
                onInterstitialAdRewarded();
                loadInterstitialPangle(activity);
            }

            @Override
            public void onAdShowed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialListenerPangle onAdShowed()");
                logFirebase("true", "interstitial", "impression", "pangle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialListenerPangle onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "pangle", String.valueOf(""), String.valueOf(""));
            }

        };

    }

    //mytarget
    private void loadInterstitialMyTarget(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget()");

        logFirebase("true", "interstitial", "requested", "mytarget", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.MyTargetInit(activity);

        INTERSTITIAL_AD_MYTARGET = new com.my.target.ads.InterstitialAd(vAdEnhancerRegister.getPlacementIdInt("mytarget", "interstitial", activity), activity);

        INTERSTITIAL_LISTENER_MYTARGET = new com.my.target.ads.InterstitialAd.InterstitialAdListener() {
            @Override
            public void onLoad(@NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onLoad()");
                logFirebase("true", "interstitial", "loaded", "mytarget", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_MYTARGET_LOADED = true;
            }

            @Override
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onNoAd() " + iAdLoadingError.getMessage());
                logFirebase("true", "interstitial", "failed", "mytarget", String.valueOf(iAdLoadingError.getCode()), String.valueOf(iAdLoadingError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialMyTarget(activity);
                            }
                        }
                    }
                }.start();
            }



            @Override
            public void onDismiss(@NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onDismiss()");
                onInterstitialAdRewarded();
                loadInterstitialMyTarget(activity);
            }

            @Override
            public void onDisplay(@NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onDisplay()");
                logFirebase("true", "interstitial", "impression", "mytarget", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClick(@NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onClick()");
                logFirebase("true", "interstitial", "clicked", "mytarget", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onVideoCompleted(@NonNull com.my.target.ads.InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMyTarget onVideoCompleted()");
            }
        };

        INTERSTITIAL_AD_MYTARGET.setListener(INTERSTITIAL_LISTENER_MYTARGET);
        INTERSTITIAL_AD_MYTARGET.load();
    }

    //vungle
    private void loadInterstitialVungle(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle()");

        logFirebase("true", "interstitial", "requested", "vungle", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.VungleInit(activity);

        INTERSTITIAL_LISTENER_VUNGLE = new LoadAdCallback() {
            @Override
            public void onAdLoad(String placementReferenceId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdLoad()");
                logFirebase("true", "interstitial", "loaded", "vungle", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_VUNGLE_LOADED = true;
            }

            @Override
            public void onError(String placementReferenceId, VungleException exception) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onError() " + exception.getLocalizedMessage());
                logFirebase("true", "interstitial", "failed", "vungle", String.valueOf(exception.getExceptionCode()), String.valueOf(exception.getLocalizedMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialVungle(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        Vungle.loadAd(vAdEnhancerRegister.getPlacementId("vungle", "interstitial", activity), INTERSTITIAL_LISTENER_VUNGLE);

        INTERSTITIAL_LISTENER_VUNGLE_2 = new PlayAdCallback() {

            @Override
            public void onAdEnd(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdEnd()");
                onInterstitialAdRewarded();
                loadInterstitialVungle(activity);
            }

            @Override
            public void onAdViewed(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdViewed()");
                logFirebase("true", "interstitial", "impression", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClick(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdClick()");
                logFirebase("true", "interstitial", "clicked", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onError(String placementId, VungleException exception) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onError()");
            }

            @Override
            public void creativeId(String creativeId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle creativeId()");
            }

            @Override
            public void onAdStart(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdStart()");
            }

            @Override
            public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdEnd()");
            }

            @Override
            public void onAdRewarded(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdRewarded()");
            }

            @Override
            public void onAdLeftApplication(String placementId) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialVungle onAdLeftApplication()");
            }

        };


    }

    //appodeal
    private void loadInterstitialAppodeal(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal()");

        logFirebase("true", "interstitial", "requested", "appodeal", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppODealInit(activity);

        INTERSTITIAL_LISTENER_APPODEAL = new InterstitialCallbacks() {

            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialLoaded()");
                logFirebase("true", "interstitial", "loaded", "appodeal", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_APPODEAL_LOADED = true;
            }

            @Override
            public void onInterstitialFailedToLoad() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialFailedToLoad()");
                logFirebase("true", "interstitial", "failed", "appodeal", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialAppodeal(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onInterstitialClosed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal  onInterstitialClosed()");
                onInterstitialAdRewarded();
                loadInterstitialAppodeal(activity);
            }

            @Override
            public void onInterstitialShown() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialShown()");
                logFirebase("true", "interstitial", "impression", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onInterstitialClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialClicked()");
                logFirebase("true", "interstitial", "clicked", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onInterstitialShowFailed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialShowFailed()");
            }


            @Override
            public void onInterstitialExpired() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppodeal onInterstitialExpired()");
            }
        };

        Appodeal.setInterstitialCallbacks(INTERSTITIAL_LISTENER_APPODEAL);
    }

    //chartboost
    private void loadInterstitialChartBoost(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost()");

        logFirebase("true", "interstitial", "requested", "chartboost", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.ChartBoostInit(activity);

        INTERSTITIAL_LISTENER_CHARTBOOST = new InterstitialCallback() {

            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "chartboost", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_CHARTBOOST_LOADED = true;
            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onAdShown()");
                onInterstitialAdRewarded();
            }

            @Override
            public void onAdDismiss(@NonNull DismissEvent dismissEvent) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onAdDismiss()");
                loadInterstitialChartBoost(activity);
            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onImpressionRecorded()");
                logFirebase("true", "interstitial", "impression", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialChartBoost onAdRequestedToShow()");
            }


        };

        INTERSTITIAL_AD_CHARTBOOST = new Interstitial(vAdEnhancerRegister.getPlacementId("chartboost", "interstitial", activity), INTERSTITIAL_LISTENER_CHARTBOOST, null);
        INTERSTITIAL_AD_CHARTBOOST.cache();
    }

    //hyprmx
    private void loadInterstitialHyprmx(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx()");

        logFirebase("true", "interstitial", "requested", "hyprmx", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.HyprMxInit(activity);

        INTERSTITIAL_AD_HYPRMX = HyprMX.INSTANCE.getPlacement(vAdEnhancerRegister.getPlacementId("hyprmx", "interstitial", activity));


        INTERSTITIAL_LISTENER_HYPRMX = new PlacementListener() {

            @Override
            public void onAdAvailable(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx onAdAvailable()");
                logFirebase("true", "interstitial", "loaded", "hyprmx", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_HYPRMX_LOADED = true;
            }

            @Override
            public void onAdNotAvailable(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx onAdNotAvailable()");
                logFirebase("true", "interstitial", "failed", "hyprmx", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialHyprmx(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdClosed(com.hyprmx.android.sdk.placement.Placement placement, boolean b) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx onAdClosed()");
                onInterstitialAdRewarded();
                loadInterstitialHyprmx(activity);
            }

            @Override
            public void onAdStarted(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx onAdStarted()");
                logFirebase("true", "interstitial", "impression", "hyprmx", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onAdDisplayError(com.hyprmx.android.sdk.placement.Placement placement, HyprMXErrors hyprMXErrors) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialHyprmx onAdDisplayError()");
            }

        };

        INTERSTITIAL_AD_HYPRMX.setPlacementListener(INTERSTITIAL_LISTENER_HYPRMX);
        INTERSTITIAL_AD_HYPRMX.loadAd();
    }

    //inmobi
    private void loadInterstitialInmobi(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi()");

        logFirebase("true", "interstitial", "requested", "inmobi", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.InMobiInit(activity);

        INTERSTITIAL_LISTENER_INMOBI = new InterstitialAdEventListener() {

            public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi onAdLoadSucceeded()");
                logFirebase("true", "interstitial", "loaded", "inmobi", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_INMOBI_LOADED = true;
            }

            public void onAdLoadFailed(@NonNull InMobiInterstitial ad, @NonNull InMobiAdRequestStatus status) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi onAdLoadFailed()");
                logFirebase("true", "interstitial", "failed", "inmobi", String.valueOf(status.getStatusCode()), String.valueOf(status.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialInmobi(activity);
                            }
                        }
                    }
                }.start();
            }

            public void onAdDismissed(@NonNull InMobiInterstitial ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi onAdDismissed()");
                onInterstitialAdRewarded();
                loadInterstitialInmobi(activity);
            }

            public void onAdImpression(@NonNull InMobiInterstitial ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi onAdImpression()");
                logFirebase("true", "interstitial", "impression", "inmobi", String.valueOf(""), String.valueOf(""));
            }

            public void onAdClicked(@NonNull InMobiInterstitial ad, Map<Object, Object> params) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialInmobi onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "inmobi", String.valueOf(""), String.valueOf(""));
            }

        };

        INTERSTITIAL_AD_INMOBI = new InMobiInterstitial(activity, vAdEnhancerRegister.getPlacementIdInt("inmobi", "interstitial", activity),
                INTERSTITIAL_LISTENER_INMOBI);

        INTERSTITIAL_AD_INMOBI.load();
    }

    //admob
    public void loadInterstitialAdmob(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob()");

        logFirebase("true", "interstitial", "requested", "admob", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AdmobInit(activity);

        AdRequest adRequest = new AdRequest.Builder().build();

        INTERSTITIAL_LISTENER_ADMOB = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "admob", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_ADMOB = interstitialAd;
                INTERSTITIAL_AD_ADMOB.setFullScreenContentCallback(INTERSTITIAL_LISTENER_ADMOB_2);
                INTERSTITIAL_AD_ADMOB_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdFailedToLoad()" + loadAdError.getMessage());
                logFirebase("true", "interstitial", "failed", "admob", String.valueOf(loadAdError.getCode()), String.valueOf(loadAdError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialAdmob(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        InterstitialAd.load(activity, vAdEnhancerRegister.getPlacementId("admob", "interstitial", activity), adRequest, INTERSTITIAL_LISTENER_ADMOB);

        INTERSTITIAL_LISTENER_ADMOB_2 = new FullScreenContentCallback() {

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdDismissedFullScreenContent()");
                onInterstitialAdRewarded();
                loadInterstitialAdmob(activity);
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdImpression()");
                logFirebase("true", "interstitial", "impression", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "admob", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdFailedToShowFullScreenContent()" + adError.getMessage());
            }


            @Override
            public void onAdShowedFullScreenContent() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAdmob onAdShowedFullScreenContent()");
            }
        };

    }

    //max
    private void loadInterstitialApplovin(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialMax()");

        logFirebase("true", "interstitial", "requested", "applovin", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.MaxInit(activity);

        INTERSTITIAL_AD_APPLOVIN = new MaxInterstitialAd(vAdEnhancerRegister.getPlacementId("applovin", "interstitial", activity), activity);

        INTERSTITIAL_LISTENER_APPLOVIN = new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "applovin", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_APPLOVIN_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdLoadFailed() " + error.getMessage());
                logFirebase("true", "interstitial", "failed", "applovin", String.valueOf(error.getCode()), String.valueOf(error.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialApplovin(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdDisplayed()");
                onInterstitialAdRewarded();
                loadInterstitialApplovin(activity);
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdHidden()");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialMax onAdDisplayFailed() " + error.getMessage());
            }
        };

        INTERSTITIAL_AD_APPLOVIN.setListener(INTERSTITIAL_LISTENER_APPLOVIN);
        INTERSTITIAL_AD_APPLOVIN.loadAd();
    }

    //appbrain
    private void loadInterstitialAppBrain(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain()");

        logFirebase("true", "interstitial", "requested", "appbrain", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.AppBrainInit(activity);

        INTERSTITIAL_LISTENER_APPBRAIN = new com.appbrain.InterstitialListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "appbrain", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_APPBRAIN_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(InterstitialError interstitialError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain onAdFailedToLoad()");
                logFirebase("true", "interstitial", "failed", "appbrain", String.valueOf(interstitialError.toString()), String.valueOf(interstitialError.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialAppBrain(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onDismissed(boolean b) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain onDismissed()");
                onInterstitialAdRewarded();
                loadInterstitialAppBrain(activity);
            }

            @Override
            public void onPresented() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain onPresented()");
                logFirebase("true", "interstitial", "impression", "appbrain", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClick() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppBrain onClick()");
                logFirebase("true", "interstitial", "clicked", "appbrain", String.valueOf(""), String.valueOf(""));
            }

        };


        INTERSTITIAL_AD_APPBRAIN = InterstitialBuilder.create()
                .setAdId(AdId.custom(vAdEnhancerRegister.getPlacementId("appbrain", "interstitial", activity)))
                .setListener(INTERSTITIAL_LISTENER_APPBRAIN)
                .preload(activity);


    }

    //kidoz
    private void loadInterstitialKidoz(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz()");

        logFirebase("true", "interstitial", "requested", "kidoz", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.KidozInit(activity);


        INTERSTITIAL_LISTENER_KIDOZ = new BaseInterstitial.IOnInterstitialEventListener() {

            @Override
            public void onReady() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz onReady()");
                logFirebase("true", "interstitial", "loaded", "kidoz", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_KIDOZ_LOADED = true;
            }

            @Override
            public void onLoadFailed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz onLoadFailed()");
                logFirebase("true", "interstitial", "failed", "kidoz", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialKidoz(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onClosed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz onClosed()");
                onInterstitialAdRewarded();
                loadInterstitialKidoz(activity);
            }

            @Override
            public void onOpened() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz onOpened()");
                logFirebase("true", "interstitial", "impression", "kidoz", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onNoOffers() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialKidoz onNoOffers()");
            }
        };


        INTERSTITIAL_AD_KIDOZ = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.INTERSTITIAL);
        INTERSTITIAL_AD_KIDOZ.setOnInterstitialEventListener(INTERSTITIAL_LISTENER_KIDOZ);
        INTERSTITIAL_AD_KIDOZ.loadAd();

    }

    //appnext
    private void loadInterstitialAppNext(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext()");

        logFirebase("true", "interstitial", "requested", "appnext", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.AppNextInit(activity);

        INTERSTITIAL_AD_APPNEXT = new com.appnext.ads.interstitial.Interstitial(activity, vAdEnhancerRegister.getPlacementId("appnext", "interstitial", activity));


        INTERSTITIAL_AD_APPNEXT.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String bannerId, AppnextAdCreativeType creativeType) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext adLoaded()");
                logFirebase("true", "interstitial", "loaded", "appnext", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_APPNEXT_LOADED = true;
            }
        });
        INTERSTITIAL_AD_APPNEXT.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String error) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext adError()");
                logFirebase("true", "interstitial", "failed", "appnext", String.valueOf(error.toString()), String.valueOf(error.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialAppNext(activity);
                            }
                        }
                    }
                }.start();
            }
        });
        INTERSTITIAL_AD_APPNEXT.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext onAdClosed()");
                onInterstitialAdRewarded();
                loadInterstitialAppNext(activity);
            }
        });
        INTERSTITIAL_AD_APPNEXT.setOnAdOpenedCallback(new OnAdOpened() {
            @Override
            public void adOpened() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext adOpened()");
                logFirebase("true", "interstitial", "impression", "appnext", String.valueOf(""), String.valueOf(""));
            }
        });
        INTERSTITIAL_AD_APPNEXT.setOnAdClickedCallback(new OnAdClicked() {
            @Override
            public void adClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialAppNext adClicked()");
                logFirebase("true", "interstitial", "clicked", "appnext", String.valueOf(""), String.valueOf(""));
            }
        });

        INTERSTITIAL_AD_APPNEXT.loadAd();

    }

    //greedygames
    private void loadInterstitialGreedyGames(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames()");

        logFirebase("true", "interstitial", "requested", "greedygames", String.valueOf(""), String.valueOf(""));


        vAdEnhancerRegister.GreedyGamesInit(activity);

        INTERSTITIAL_LISTENER_GREEDYGAMES = new GGInterstitialEventsListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "greedygames", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_GREEDYGAMES_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames onAdLoadFailed()");
                logFirebase("true", "interstitial", "failed", "greedygames", String.valueOf(adErrors.toString()), String.valueOf(adErrors.toString()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialGreedyGames(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdClosed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames onAdClosed()");
                onInterstitialAdRewarded();
                loadInterstitialGreedyGames(activity);
            }

            @Override
            public void onAdOpened() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames onAdOpened()");
                logFirebase("true", "interstitial", "impression", "greedygames", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdShowFailed() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGreedyGames onAdShowFailed()");
            }


        };

        INTERSTITIAL_AD_GREEDYGAMES = new GGInterstitialAd(activity, vAdEnhancerRegister.getPlacementId("greedygames", "interstitial", activity));
        INTERSTITIAL_AD_GREEDYGAMES.setListener(INTERSTITIAL_LISTENER_GREEDYGAMES);
        INTERSTITIAL_AD_GREEDYGAMES.loadAd();

    }

    //googleadmanager
    public void loadInterstitialGoogleAdManager(Activity activity) {
        Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager()");

        logFirebase("true", "interstitial", "requested", "admob", String.valueOf(""), String.valueOf(""));

        vAdEnhancerRegister.GoogleAdManagerInit(activity);

        INTERSTITIAL_LISTENER_GOOGLEADMANAGER = new AdManagerInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdLoaded()");
                logFirebase("true", "interstitial", "loaded", "googleadmanager", String.valueOf(""), String.valueOf(""));
                INTERSTITIAL_AD_GOOGLEADMANAGER = interstitialAd;
                INTERSTITIAL_AD_GOOGLEADMANAGER.setFullScreenContentCallback(INTERSTITIAL_LISTENER_GOOGLEADMANAGER_2);
                INTERSTITIAL_AD_GOOGLEADMANAGER_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdFailedToLoad()" + loadAdError.getMessage());
                logFirebase("true", "interstitial", "failed", "googleadmanager", String.valueOf(loadAdError.getCode()), String.valueOf(loadAdError.getMessage()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadInterstitialGoogleAdManager(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        AdManagerInterstitialAd.load(activity, vAdEnhancerRegister.getPlacementId("gam", "interstitial", activity), new AdManagerAdRequest.Builder().build(), INTERSTITIAL_LISTENER_GOOGLEADMANAGER);

        INTERSTITIAL_LISTENER_GOOGLEADMANAGER_2 = new FullScreenContentCallback() {

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdDismissedFullScreenContent()");
                onInterstitialAdRewarded();
            }

            @Override
            public void onAdImpression() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdImpression()");
                logFirebase("true", "interstitial", "impression", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdClicked()");
                logFirebase("true", "interstitial", "clicked", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }


            @Override
            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdFailedToShowFullScreenContent()" + adError.getMessage());
            }


            @Override
            public void onAdShowedFullScreenContent() {
                Log.e("VAdEnhancerInterstAd", "loadInterstitialGoogleAdManager onAdShowedFullScreenContent()");
                loadInterstitialGoogleAdManager(activity);
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
