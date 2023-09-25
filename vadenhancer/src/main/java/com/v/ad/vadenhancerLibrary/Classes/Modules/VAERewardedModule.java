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
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.AppnextAdCreativeType;
import com.appnext.core.callbacks.OnAdClicked;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnAdOpened;
import com.appnext.core.callbacks.OnVideoEnded;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardItem;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAd;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdLoadListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedRequest;
import com.chartboost.sdk.ads.Rewarded;
import com.chartboost.sdk.callbacks.RewardedCallback;
import com.chartboost.sdk.events.CacheError;
import com.chartboost.sdk.events.CacheEvent;
import com.chartboost.sdk.events.ClickError;
import com.chartboost.sdk.events.ClickEvent;
import com.chartboost.sdk.events.DismissEvent;
import com.chartboost.sdk.events.ImpressionEvent;
import com.chartboost.sdk.events.RewardEvent;
import com.chartboost.sdk.events.ShowError;
import com.chartboost.sdk.events.ShowEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.greedygame.core.models.general.AdErrors;
import com.greedygame.core.rewarded_ad.general.GGRewardedAd;
import com.greedygame.core.rewarded_ad.general.GGRewardedAdsEventListener;
import com.hyprmx.android.sdk.core.HyprMX;
import com.hyprmx.android.sdk.core.HyprMXErrors;
import com.hyprmx.android.sdk.placement.RewardedPlacementListener;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.my.target.ads.Reward;
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

public class VAERewardedModule {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private static volatile VAERewardedModule INSTANCE = null;
    private VAdEnhancer vAdEnhancer;
    private VAdEnhancerRegister vAdEnhancerRegister;

    private CountDownTimer noRewardedVideoAdLoadedWaitingTimer;

    //ironsource
    private RewardedVideoListener REWARDED_AD_IRONSOURCE_LISTENER;
    //Adcolony
    private AdColonyInterstitial REWARDED_AD_ADCOLONY;
    private AdColonyInterstitialListener REWARDED_LISTENER_ADCOLONY;
    private AdColonyRewardListener REWARDED_LISTENER_ADCOLONY_2;
    //unity
    private IUnityAdsLoadListener REWARDED_LISTENER_UNITY;
    private IUnityAdsShowListener REWARDED_LISTENER_UNITY_2;
    //pangle
    private PAGRewardedAd REWARDED_AD_PANGLE;
    private PAGRewardedAdLoadListener REWARDED_LISTENER_PANGLE;
    private PAGRewardedAdInteractionListener REWARDED_LISTENER_PANGLE_2;
    //mytarget
    private com.my.target.ads.RewardedAd REWARDED_AD_MY_TARGET;
    private com.my.target.ads.RewardedAd.RewardedAdListener REWARDED_LISTENER_MYTARGET;
    //vungle
    private LoadAdCallback REWARDED_LISTENER_VUNGLE;
    private PlayAdCallback REWARDED_LISTENER_VUNGLE_2;
    //appodeal
    private RewardedVideoCallbacks REWARDED_LISTENER_APPODEAL;
    //hyprmx
    private com.hyprmx.android.sdk.placement.Placement REWARDED_AD_HYPRMX;
    private RewardedPlacementListener REWARDED_LISTENER_HYPRMX;
    //inmobi
    private InMobiInterstitial REWARDED_AD_INMOBI;
    private InterstitialAdEventListener REWARDED_LISTENER_INMOBI;
    //admob
    private RewardedAd REWARDED_AD_ADMOB;
    private RewardedAdLoadCallback REWARDED_LISTENER_ADMOB;
    private FullScreenContentCallback REWARDED_LISTENER_ADMOB_2;
    private OnUserEarnedRewardListener REWARDED_LISTENER_ADMOB_3;
    //applovin
    private MaxRewardedAd REWARDED_AD_APPLOVIN;
    private MaxRewardedAdListener REWARDED_LISTENER_APPLOVIN;
    //chartboost
    private Rewarded REWARDED_AD_CHARTBOOST;
    private RewardedCallback REWARDED_LISTENER_CHARTBOOST;
    //kidoz
    private KidozInterstitial REWARDED_AD_KIDOZ;
    private BaseInterstitial.IOnInterstitialEventListener REWARDED_LISTENER_KIDOZ;
    private BaseInterstitial.IOnInterstitialRewardedEventListener REWARDED_LISTENER_KIDOZ_2;
    //appnext
    private RewardedVideo REWARDED_AD_APPNEXT;
    //greedygames
    private GGRewardedAd REWARDED_AD_GREEDYGAMES;
    private GGRewardedAdsEventListener REWARDED_LISTENER_GREEDYGAMES;
    //googleadmanager
    private RewardedAd REWARDED_AD_GOOGLEADMANAGER;
    private RewardedAdLoadCallback REWARDED_LISTENER_GOOGLEADMANAGER;
    private FullScreenContentCallback REWARDED_LISTENER_GOOGLEADMANAGER_2;
    private OnUserEarnedRewardListener REWARDED_LISTENER_GOOGLEADMANAGER_3;

    private boolean REWARDED_AD_IRONSOURCE_LOADED = false;
    private boolean REWARDED_AD_ADCOLONY_LOADED = false;
    private boolean REWARDED_AD_UNITY_LOADED = false;
    private boolean REWARDED_AD_PANGLE_LOADED = false;
    private boolean REWARDED_AD_VUNGLE_LOADED = false;
    private boolean REWARDED_AD_APPODEAL_LOADED = false;
    private boolean REWARDED_AD_CHARTBOOST_LOADED = false;
    private boolean REWARDED_AD_KIDOZ_LOADED = false;
    private boolean REWARDED_AD_APPNEXT_LOADED = false;
    private boolean REWARDED_AD_HYPRMX_LOADED = false;
    private boolean REWARDED_AD_INMOBI_LOADED = false;
    private boolean REWARDED_AD_MYTARGET_LOADED = false;
    private boolean REWARDED_AD_ADMOB_LOADED = false;
    private boolean REWARDED_AD_APPLOVIN_LOADED = false;
    private boolean REWARDED_AD_GREEDYGAMES_LOADED = false;
    private boolean REWARDED_AD_GOOGLEADMANAGER_LOADED = false;

    private VAERewardedModule(Activity activityx, VAdEnhancer vAdEnhancerxHandler, VAdEnhancerRegister registerx) {
        Log.e("VAERewardedModule", "VAERewardedModule() " + activityx.getLocalClassName() + " vAdEnhancerxHandler " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;
        vAdEnhancerRegister = registerx;

    }

    public static VAERewardedModule getInstance(Activity activity, VAdEnhancer vAdEnhancer, VAdEnhancerRegister VAdEnhancerRegister) {
        if (INSTANCE == null) {
            synchronized (VAERewardedModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAERewardedModule(activity, vAdEnhancer, VAdEnhancerRegister);
                }
            }
        }
        return INSTANCE;
    }


    public void loadRewardedVideoAds() {
        Log.e("VAERewardedModule", "loadRewardedVideoAds()  ");


        if (vAdEnhancerRegister.keyValueTableHashMap.isEmpty()) {
            Log.e("VAERewardedModule", "loadRewardedVideoAds() register.keyValueTableHashMap.isEmpty()");
        } else if (!vAdEnhancerRegister.keyValueTableHashMap.containsKey("activated")) {
            Log.e("VAERewardedModule", "loadRewardedVideoAds() register.keyValueTableHashMap.containsKey(activated) false");
        } else if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("activated"), "false")) {
            Log.e("VAERewardedModule", "loadRewardedVideoAds() register.keyValueTableHashMap.get(activated) false");
        } else {
            Log.e("VAERewardedModule", "loadBannerAds() loading...");


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("ironsource_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("ironsource_activated"), "true")) {
                    loadRewardedAdIronSource(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("adcolony_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("adcolony_activated"), "true")) {
                    loadRewardedAdAdcolony(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("unity_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("unity_activated"), "true")) {
                    loadRewardedAdUnity(currentActivity);
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("pangle_activated"), "true")) {
                    loadRewardedAdPangle(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("vungle_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("vungle_activated"), "true")) {
                    loadRewardedVungle(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appodeal_activated"), "true")) {
                    loadRewardedAdAppodeal(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("chartboost_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("chartboost_activated"), "true")) {
                    loadRewardedAdChartBoost(currentActivity);
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("kidoz_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("kidoz_activated"), "true")) {
                    loadRewardedAdKidoz(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("appnext_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("appnext_activated"), "true")) {
                    loadRewardedAdAppNext(currentActivity);//native
                }
            }


            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("hyprmx_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("hyprmx_activated"), "true")) {
                    loadRewardedHyprmx(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("inmobi_activated"), "true")) {
                    loadRewardedInmobi(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("mytarget_activated"), "true")) {
                    loadRewardedAdMyTarget(currentActivity);//not tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("admob_activated"), "true")) {
                    loadRewardedAdAdmob(currentActivity);
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("applovin_activated"), "true")) {
                    loadRewardedAdApplovin(currentActivity);//no tested
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("greedygames_activated"), "true")) {
                    loadRewardedAdGreedyGames(currentActivity);//native
                }
            }

            if (vAdEnhancerRegister.keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("gam_activated"), "true")) {
                    loadRewardedAdGoogleAdManager(currentActivity);
                }
            }


        }

    }

    public boolean isRewardedVideoAdReady(Activity activity) {
        Log.e("VAERewardedModule", "isRewardedVideoAdReady() " + currentActivity.getLocalClassName());

        currentActivity = activity;


        boolean ready = false;

        if (vAdEnhancerRegister.adNetworkOrderTableRewardedList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableRewardedList.size(); i++) {
                Log.e("VAERewardedModule", "isRewardedVideoAdReady  " + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getPreference());


                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "gam") && REWARDED_AD_GOOGLEADMANAGER_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready googleadmanager rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "greedygames") && REWARDED_AD_GREEDYGAMES_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready greedygames rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "ironsource") && REWARDED_AD_IRONSOURCE_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready ironsource rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "adcolony") && REWARDED_AD_ADCOLONY_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready adcolony rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "unity") && REWARDED_AD_UNITY_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready unity rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "vungle") && REWARDED_AD_VUNGLE_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready vungle rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "pangle") && REWARDED_AD_PANGLE_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready pangle rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "appodeal") && REWARDED_AD_APPODEAL_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready appodeal rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "chartboost") && REWARDED_AD_CHARTBOOST_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready chartboost rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "kidoz") && REWARDED_AD_KIDOZ_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready kidoz rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "appnext") && REWARDED_AD_APPNEXT_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready appnext rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "hyprmx") && REWARDED_AD_HYPRMX_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready hyprmx rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "inmobi") && REWARDED_AD_INMOBI_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready inmobi rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "mytarget") && REWARDED_AD_MYTARGET_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready mytarget rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "admob") && REWARDED_AD_ADMOB_LOADED) {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready admob rewarded ");
                    ready = true;
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "applovin") && REWARDED_AD_APPLOVIN_LOADED) {//not tested
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready applovin rewarded ");
                    ready = true;
                    break;
                } else {
                    Log.e("VAERewardedModule", "isRewardedVideoAdReady() ready none");
                }

            }
        }

        if (ready) {
            if (noRewardedVideoAdLoadedWaitingTimer != null) {
                noRewardedVideoAdLoadedWaitingTimer.cancel();
            }
        } else {
            if (noRewardedVideoAdLoadedWaitingTimer == null) {
                noRewardedVideoAdLoadedWaitingTimer();
            }
        }

        return ready;
    }

    public void showRewardedVideoAd(Activity activity) {
        Log.e("VAERewardedModule", "showRewardedVideoAd()");

        currentActivity = activity;

        if (vAdEnhancerRegister.adNetworkOrderTableRewardedList != null) {
            for (int i = 0; i < vAdEnhancerRegister.adNetworkOrderTableRewardedList.size(); i++) {
                Log.e("VAERewardedModule", "isRewardedVideoAdReady  " + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork() + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdtype() + vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getPreference());


                if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "gam") && REWARDED_AD_GOOGLEADMANAGER_LOADED) {
                    if (REWARDED_AD_GOOGLEADMANAGER != null) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() googleadmanager rewarded ");
                        REWARDED_AD_GOOGLEADMANAGER.show(currentActivity, REWARDED_LISTENER_GOOGLEADMANAGER_3);
                        REWARDED_AD_GOOGLEADMANAGER_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: G-Ad-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "greedygames") && REWARDED_AD_GREEDYGAMES_LOADED) {
                    if (REWARDED_AD_GREEDYGAMES != null) {
                        if (REWARDED_AD_GREEDYGAMES.isAdLoaded()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() greedygames rewarded");
                            REWARDED_AD_GREEDYGAMES.show();
                            REWARDED_AD_GREEDYGAMES_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: GreedyGames-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "greedygames", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "ironsource") && REWARDED_AD_IRONSOURCE_LOADED) {
                    if (IronSource.isRewardedVideoAvailable()) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() ironsource rewarded ");
                        IronSource.showRewardedVideo(vAdEnhancerRegister.getPlacementId("ironsource", "rewarded", activity));
                        REWARDED_AD_IRONSOURCE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: IronSource-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "ironsource", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "adcolony") && REWARDED_AD_ADCOLONY_LOADED) {
                    if (REWARDED_AD_ADCOLONY != null) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() adcolony rewarded ");
                        REWARDED_AD_ADCOLONY.show();
                        REWARDED_AD_ADCOLONY_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Adcolony-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "adcolony", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "unity") && REWARDED_AD_UNITY_LOADED) {
                    Log.e("VAERewardedModule", "showRewardedVideoAd() unity rewarded ");
                    UnityAds.show(currentActivity, vAdEnhancerRegister.getPlacementId("unity", "rewarded", activity), REWARDED_LISTENER_UNITY_2);
                    REWARDED_AD_UNITY_LOADED = false;
                    Toast.makeText(currentActivity, "Ad: Unity-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "unity", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "vungle") && REWARDED_AD_VUNGLE_LOADED) {
                    if (Vungle.canPlayAd(vAdEnhancerRegister.getPlacementId("vungle", "rewarded", activity))) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() vungle rewarded ");
                        Vungle.playAd(vAdEnhancerRegister.getPlacementId("vungle", "rewarded", activity), null, REWARDED_LISTENER_VUNGLE_2);
                        REWARDED_AD_VUNGLE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Vungle-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "vungle", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "pangle") && REWARDED_AD_PANGLE_LOADED) {
                    if (REWARDED_AD_PANGLE != null) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() pangle rewarded ");
                        REWARDED_AD_PANGLE.show(currentActivity);
                        REWARDED_AD_PANGLE_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Pangle-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "pangele", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "appodeal") && REWARDED_AD_APPODEAL_LOADED) {
                    if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() appodeal rewarded ");
                        Appodeal.show(currentActivity, Appodeal.REWARDED_VIDEO, vAdEnhancerRegister.getPlacementId("appodeal", "rewarded", activity));
                        REWARDED_AD_APPODEAL_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: Appodeal-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "appodeal", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "chartboost") && REWARDED_AD_CHARTBOOST_LOADED) {
                    if (REWARDED_AD_CHARTBOOST != null) {
                        if (REWARDED_AD_CHARTBOOST.isCached()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() chartboost rewarded ");
                            REWARDED_AD_CHARTBOOST.show();
                            REWARDED_AD_CHARTBOOST_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Chartboost-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "chartboost", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "kidoz") && REWARDED_AD_KIDOZ_LOADED) {
                    if (REWARDED_AD_KIDOZ != null) {
                        if (REWARDED_AD_KIDOZ.isLoaded()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() kidoz rewarded ");
                            REWARDED_AD_KIDOZ.show();
                            REWARDED_AD_KIDOZ_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Kidoz-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "kidoz", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "appnext") && REWARDED_AD_APPNEXT_LOADED) {
                    if (REWARDED_AD_APPNEXT != null) {
                        if (REWARDED_AD_APPNEXT.isAdLoaded()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() appnext rewarded ");
                            REWARDED_AD_APPNEXT.showAd();
                            REWARDED_AD_APPNEXT_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Appnext-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "appnext", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "hyprmx") && REWARDED_AD_HYPRMX_LOADED) {
                    if (REWARDED_AD_HYPRMX != null) {
                        if (REWARDED_AD_HYPRMX.isAdAvailable()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() hyprmx rewarded ");
                            REWARDED_AD_HYPRMX.showAd();
                            REWARDED_AD_HYPRMX_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: Hyprmx-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "hyprmx", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "inmobi") && REWARDED_AD_INMOBI_LOADED) {
                    if (REWARDED_AD_INMOBI != null) {
                        if (REWARDED_AD_INMOBI.isReady()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() inmobi rewarded ");
                            REWARDED_AD_INMOBI.show();
                            REWARDED_AD_INMOBI_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: InMobi-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "inmobi", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "mytarget") && REWARDED_AD_MYTARGET_LOADED) {
                    if (REWARDED_AD_MY_TARGET != null) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() mytarget rewarded ");
                        REWARDED_AD_MY_TARGET.show();
                        REWARDED_AD_MYTARGET_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: MyTarget-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "mytarget", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "admob") && REWARDED_AD_ADMOB_LOADED) {
                    if (REWARDED_AD_ADMOB != null) {
                        Log.e("VAERewardedModule", "showRewardedVideoAd() admob rewarded ");
                        REWARDED_AD_ADMOB.show(currentActivity, REWARDED_LISTENER_ADMOB_3);
                        REWARDED_AD_ADMOB_LOADED = false;
                    }
                    Toast.makeText(currentActivity, "Ad: AdMob-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "admob", String.valueOf(""), String.valueOf(""));
                    break;
                } else if (Objects.equals(vAdEnhancerRegister.adNetworkOrderTableRewardedList.get(i).getAdnetwork(), "applovin") && REWARDED_AD_APPLOVIN_LOADED) {
                    if (REWARDED_AD_APPLOVIN != null) {
                        if (REWARDED_AD_APPLOVIN.isReady()) {
                            Log.e("VAERewardedModule", "showRewardedVideoAd() applovin rewarded ");
                            REWARDED_AD_APPLOVIN.showAd();
                            REWARDED_AD_APPLOVIN_LOADED = false;
                        }
                    }
                    Toast.makeText(currentActivity, "Ad: AppLovin-R", Toast.LENGTH_SHORT).show();
                    logFirebase("true", "rewarded", "displayed", "applovin", String.valueOf(""), String.valueOf(""));
                    break;
                }
            }
        }


    }

    public void noRewardedVideoAdLoadedWaitingTimer() {
        Log.e("VAERewardedModule", "noRewardedVideoAdLoadedWaitingTimer()");

        if (noRewardedVideoAdLoadedWaitingTimer != null) {
            noRewardedVideoAdLoadedWaitingTimer.cancel();
        }

        noRewardedVideoAdLoadedWaitingTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAERewardedModule", "noRewardedVideoAdLoadedWaitingTimer " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAERewardedModule", "noRewardedVideoAdLoadedWaitingTimer onFinish() ");
                if (Objects.equals(vAdEnhancerRegister.keyValueTableHashMap.get("rewarded"), "true")) {
                    loadRewardedVideoAds();
                }
                if (noRewardedVideoAdLoadedWaitingTimer != null) {
                    noRewardedVideoAdLoadedWaitingTimer.cancel();
                    noRewardedVideoAdLoadedWaitingTimer = null;
                }
            }
        }.start();
    }

    private void onRewardedVideoRewarded() {
        Log.e("VAERewardedModule", "onRewardedVideoRewarded() ");
        try {
            Method method = currentActivity.getClass().getMethod("rewardedAdRewarded");
            method.invoke(currentActivity);
        } catch (Exception e) {
            Log.e("VAERewardedModule", "exception error " + e);
        }
    }


    //ironsource
    public void loadRewardedAdIronSource(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdIronSource() ");

        vAdEnhancerRegister.IronSourceInit(activity);


        REWARDED_AD_IRONSOURCE_LISTENER = new RewardedVideoListener() {

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAvailabilityChanged() " + b);
                if (IronSource.isRewardedVideoAvailable()) {
                    Log.e("VAERewardedModule", "loadRewardedAdIronSource IronSource.isRewardedVideoAvailable()");
                    REWARDED_AD_IRONSOURCE_LOADED = true;
                }
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdRewarded()");
                logFirebase("true", "rewarded", "rewarded", "ironsource", String.valueOf(""), String.valueOf(""));
                onRewardedVideoRewarded();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdOpened()");
                logFirebase("true", "rewarded", "impression", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdClicked()");
                logFirebase("true", "rewarded", "clicked", "ironsource", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onRewardedVideoAdEnded() {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdEnded()");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdClosed()");
            }

            @Override
            public void onRewardedVideoAdStarted() {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdStarted()");
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
                Log.e("VAERewardedModule", "loadRewardedAdIronSource onRewardedVideoAdShowFailed() " + ironSourceError.getErrorMessage());
            }


        };

        IronSource.setRewardedVideoListener(REWARDED_AD_IRONSOURCE_LISTENER);
    }

    //adcolony
    private void loadRewardedAdAdcolony(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdAdcolony()");

        vAdEnhancerRegister.AdcolonyInit(activity);

        REWARDED_LISTENER_ADCOLONY_2 = new AdColonyRewardListener() {
            @Override
            public void onReward(AdColonyReward reward) {
                if (reward.success()) {
                    Log.e("VAERewardedModule", "loadRewardedAdAdcolony onReward()");
                    logFirebase("true", "rewarded", "rewarded", "adcolony", String.valueOf(""), String.valueOf(""));
                    onRewardedVideoRewarded();
                    loadRewardedAdAdcolony(activity);
                }
            }
        };

        REWARDED_LISTENER_ADCOLONY = new AdColonyInterstitialListener() {

            @Override
            public void onRequestFilled(AdColonyInterstitial adReward) {
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onRequestFilled()");
                REWARDED_AD_ADCOLONY = adReward;
                REWARDED_AD_ADCOLONY_LOADED = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onRequestNotFilled()");
                logFirebase("true", "rewarded", "failed", "adcolony", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdAdcolony(activity);
                            }
                        }
                    }
                }.start();
            }


            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onClosed()");
                logFirebase("true", "rewarded", "impression", "adcolony", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onClicked()");
                logFirebase("true", "rewarded", "clicked", "adcolony", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onOpened()");
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onLeftApplication()");
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
                Log.e("VAERewardedModule", "loadRewardedAdAdcolony onExpiring()");
            }
        };


        AdColony.requestInterstitial(vAdEnhancerRegister.getPlacementId("adcolony", "rewarded", activity), REWARDED_LISTENER_ADCOLONY);
        AdColony.setRewardListener(REWARDED_LISTENER_ADCOLONY_2);

    }

    //unity
    private void loadRewardedAdUnity(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdUnity()");

        vAdEnhancerRegister.UnityInit(activity);

        REWARDED_LISTENER_UNITY = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsAdLoaded()");
                REWARDED_AD_UNITY_LOADED = true;
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsFailedToLoad() " + message);
                logFirebase("true", "rewarded", "failed", "unity", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdUnity(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        UnityAds.load(vAdEnhancerRegister.getPlacementId("unity", "rewarded", activity), REWARDED_LISTENER_UNITY);

        REWARDED_LISTENER_UNITY_2 = new IUnityAdsShowListener() {

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsShowComplete()");
                if (state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED)) {
                    onRewardedVideoRewarded();
                    loadRewardedAdUnity(activity);
                }
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                logFirebase("true", "rewarded", "impression", "unity", String.valueOf(""), String.valueOf(""));
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsShowStart()");
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                logFirebase("true", "rewarded", "click", "unity", String.valueOf(""), String.valueOf(""));
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsShowClick()");
            }

            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                Log.e("VAERewardedModule", "loadRewardedAdUnity onUnityAdsShowFailure()");
            }

        };

    }

    //pangle
    private void loadRewardedAdPangle(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdPangle()");

        vAdEnhancerRegister.PangleInit(activity);

        PAGRewardedRequest request = new PAGRewardedRequest();

        REWARDED_LISTENER_PANGLE = new PAGRewardedAdLoadListener() {

            @Override
            public void onAdLoaded(PAGRewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onAdLoaded()");
                REWARDED_AD_PANGLE = rewardedAd;
                REWARDED_AD_PANGLE.setAdInteractionListener(REWARDED_LISTENER_PANGLE_2);
                REWARDED_AD_PANGLE_LOADED = true;
            }

            @Override
            public void onError(int code, String message) {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onError() " + message);
                logFirebase("true", "rewarded", "failed", "pangle", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdPangle(activity);
                            }
                        }
                    }
                }.start();
            }


        };

        PAGRewardedAd.loadAd(vAdEnhancerRegister.getPlacementId("pangle", "rewarded", activity), request, REWARDED_LISTENER_PANGLE);

        REWARDED_LISTENER_PANGLE_2 = new PAGRewardedAdInteractionListener() {

            @Override
            public void onUserEarnedReward(PAGRewardItem item) {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onUserEarnedReward()");
                onRewardedVideoRewarded();
                loadRewardedAdPangle(activity);
            }

            @Override
            public void onAdShowed() {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onAdShowed()");
                logFirebase("true", "rewarded", "impression", "pangle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "pangle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDismissed() {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onAdDismissed()");
            }

            @Override
            public void onUserEarnedRewardFail(int errorCode, String errorMsg) {
                Log.e("VAERewardedModule", "loadRewardedAdPangle onUserEarnedRewardFail() " + errorMsg);
            }
        };


    }

    //mytarget
    private void loadRewardedAdMyTarget(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdMyTarget()");

        vAdEnhancerRegister.MyTargetInit(activity);

        REWARDED_AD_MY_TARGET = new com.my.target.ads.RewardedAd(vAdEnhancerRegister.getPlacementIdInt("mytarget", "rewarded", activity), activity);

        REWARDED_LISTENER_MYTARGET = new com.my.target.ads.RewardedAd.RewardedAdListener() {
            @Override
            public void onLoad(@NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onLoad()");
                REWARDED_AD_MY_TARGET = rewardedAd;
                REWARDED_AD_MYTARGET_LOADED = true;
            }

            @Override
            public void onNoAd(@NonNull IAdLoadingError iAdLoadingError, @NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onNoAd() " + iAdLoadingError.getCode());
                logFirebase("true", "rewarded", "failed", "mytarget", String.valueOf(iAdLoadingError.getMessage()), String.valueOf(iAdLoadingError.getCode()));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdMyTarget(activity);
                            }
                        }
                    }
                }.start();
            }



            @Override
            public void onReward(@NonNull Reward reward, @NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onReward()");
                onRewardedVideoRewarded();
                loadRewardedAdMyTarget(activity);
            }

            @Override
            public void onDisplay(@NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onDisplay()");
                logFirebase("true", "rewarded", "impression", "mytarget", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onClick(@NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onClick()");
                logFirebase("true", "rewarded", "clicked", "mytarget", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onDismiss(@NonNull com.my.target.ads.RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdMyTarget onDismiss()");
            }

        };

        REWARDED_AD_MY_TARGET.setListener(REWARDED_LISTENER_MYTARGET);
        REWARDED_AD_MY_TARGET.load();

    }

    //appodeal
    private void loadRewardedAdAppodeal(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdAppodeal()");

        vAdEnhancerRegister.AppODealInit(activity);

        REWARDED_LISTENER_APPODEAL = new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean isPrecache) {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoLoaded()");
                REWARDED_AD_APPODEAL_LOADED = true;
            }

            @Override
            public void onRewardedVideoFailedToLoad() {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoFailedToLoad()");
                logFirebase("true", "rewarded", "failed", "appodeal", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdAppodeal(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onRewardedVideoFinished(double amount, String name) {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoFinished()");
                onRewardedVideoRewarded();
                loadRewardedAdAppodeal(activity);
            }

            @Override
            public void onRewardedVideoShown() {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoShown()");
                logFirebase("true", "rewarded", "impression", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onRewardedVideoClicked() {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoClicked()");
                logFirebase("true", "rewarded", "clicked", "appodeal", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onRewardedVideoShowFailed() {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoShowFailed()");
            }

            @Override
            public void onRewardedVideoClosed(boolean finished) {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoClosed()");
            }

            @Override
            public void onRewardedVideoExpired() {
                Log.e("VAERewardedModule", "loadRewardedAdAppodeal onRewardedVideoExpired()");
            }
        };

        Appodeal.setRewardedVideoCallbacks(REWARDED_LISTENER_APPODEAL);
    }

    //vungle
    private void loadRewardedVungle(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedVungle()");

        vAdEnhancerRegister.VungleInit(activity);

        REWARDED_LISTENER_VUNGLE = new LoadAdCallback() {
            @Override
            public void onAdLoad(String placementReferenceId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdLoad()");
                REWARDED_AD_VUNGLE_LOADED = true;
            }

            @Override
            public void onError(String placementReferenceId, VungleException exception) {
                Log.e("VAERewardedModule", "loadRewardedVungle onError() " + exception.getLocalizedMessage());
                logFirebase("true", "rewarded", "failed", "vungle", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedVungle(activity);
                            }
                        }
                    }
                }.start();
            }
        };

        Vungle.loadAd(vAdEnhancerRegister.getPlacementId("vungle", "rewarded", activity), REWARDED_LISTENER_VUNGLE);


        REWARDED_LISTENER_VUNGLE_2 = new PlayAdCallback() {

            @Override
            public void onAdRewarded(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdRewarded()");
                onRewardedVideoRewarded();
                loadRewardedVungle(activity);
            }

            @Override
            public void onAdStart(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdStart()");
                logFirebase("true", "rewarded", "impression", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClick(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdClick()");
                logFirebase("true", "rewarded", "clicked", "vungle", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void creativeId(String creativeId) {
                Log.e("VAERewardedModule", "loadRewardedVungle creativeId()");
            }

            @Override
            public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdEnd()");
            }

            @Override
            public void onAdEnd(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdEnd()");
            }

            @Override
            public void onAdLeftApplication(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdLeftApplication()");
            }

            @Override
            public void onError(String placementId, VungleException exception) {
                Log.e("VAERewardedModule", "loadRewardedVungle onError() " + exception.getLocalizedMessage());
            }

            @Override
            public void onAdViewed(String placementId) {
                Log.e("VAERewardedModule", "loadRewardedVungle onAdViewed()");
            }
        };


    }

    //chartboost
    private void loadRewardedAdChartBoost(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdChartBoost()");

        vAdEnhancerRegister.ChartBoostInit(activity);

        REWARDED_LISTENER_CHARTBOOST = new RewardedCallback() {

            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onAdLoaded()");
                REWARDED_AD_CHARTBOOST_LOADED = true;
            }

            @Override
            public void onRewardEarned(@NonNull RewardEvent rewardEvent) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onRewardEarned()");
                onRewardedVideoRewarded();
                loadRewardedAdChartBoost(activity);
            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onImpressionRecorded()");
                logFirebase("true", "rewarded", "impression", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "chartboost", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDismiss(@NonNull DismissEvent dismissEvent) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onAdDismiss()");
            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onAdRequestedToShow()");
            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                Log.e("VAERewardedModule", "loadRewardedAdChartBoost onAdShown()");
            }

        };

        REWARDED_AD_CHARTBOOST = new Rewarded(vAdEnhancerRegister.getPlacementId("chartboost", "rewarded", activity), REWARDED_LISTENER_CHARTBOOST, null);
        REWARDED_AD_CHARTBOOST.cache();

    }

    //inmobi
    private void loadRewardedInmobi(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedInmobi()");


        vAdEnhancerRegister.InMobiInit(activity);

        REWARDED_LISTENER_INMOBI = new InterstitialAdEventListener() {

            public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdLoadSucceeded()");
                REWARDED_AD_INMOBI_LOADED = true;
            }

            public void onAdLoadFailed(@NonNull InMobiInterstitial ad, InMobiAdRequestStatus status) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdLoadFailed()");
                logFirebase("true", "rewarded", "failed", "inmobi", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedInmobi(activity);
                            }
                        }
                    }
                }.start();
            }

            public void onRewardsUnlocked(@NonNull InMobiInterstitial ad, Map<Object, Object> rewards) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onRewardsUnlocked()");
                onRewardedVideoRewarded();
                loadRewardedInmobi(activity);
            }

            public void onAdDisplayed(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdDisplayed()");
                logFirebase("true", "rewarded", "impression", "inmobi", String.valueOf(""), String.valueOf(""));
            }

            public void onAdClicked(@NonNull InMobiInterstitial ad, Map<Object, Object> params) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "inmobi", String.valueOf(""), String.valueOf(""));
            }

            public void onAdFetchSuccessful(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdFetchSuccessful()");
            }


            public void onAdWillDisplay(@NonNull InMobiInterstitial ad) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdWillDisplay()");
            }

            public void onAdDisplayFailed(@NonNull InMobiInterstitial ad) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdDisplayFailed()");
            }

            public void onAdDismissed(@NonNull InMobiInterstitial ad) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onAdDismissed()");
            }

            public void onUserLeftApplication(@NonNull InMobiInterstitial ad) {
                Log.e("VAERewardedModule", "loadRewardedInmobi onUserLeftApplication()");
            }
        };

        REWARDED_AD_INMOBI = new InMobiInterstitial(activity, vAdEnhancerRegister.getPlacementIdInt("inmobi", "rewarded", activity),
                REWARDED_LISTENER_INMOBI);
        REWARDED_AD_INMOBI.load();


    }

    //hyprmx
    private void loadRewardedHyprmx(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedHyprmx()");

        vAdEnhancerRegister.HyprMxInit(activity);

        REWARDED_LISTENER_HYPRMX = new RewardedPlacementListener() {

            @Override
            public void onAdAvailable(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdAvailable");
                REWARDED_AD_HYPRMX_LOADED = true;
            }

            @Override
            public void onAdNotAvailable(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdNotAvailable");
                logFirebase("true", "rewarded", "failed", "hyprmx", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedHyprmx(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onAdRewarded(@NonNull com.hyprmx.android.sdk.placement.Placement placement, @NonNull String s, int i) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdRewarded");
                onRewardedVideoRewarded();
                loadRewardedHyprmx(activity);
            }

            @Override
            public void onAdStarted(com.hyprmx.android.sdk.placement.Placement placement) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdStarted");
                logFirebase("true", "rewarded", "impression", "hyprmx", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClosed(com.hyprmx.android.sdk.placement.Placement placement, boolean b) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdClosed");
            }

            @Override
            public void onAdDisplayError(com.hyprmx.android.sdk.placement.Placement placement, HyprMXErrors hyprMXErrors) {
                Log.e("VAERewardedModule", "loadRewardedHyprmx onAdDisplayError" + hyprMXErrors.toString());
            }

        };


        REWARDED_AD_HYPRMX = HyprMX.INSTANCE.getPlacement(vAdEnhancerRegister.getPlacementId("hyprmx", "rewarded", activity));
        REWARDED_AD_HYPRMX.setPlacementListener(REWARDED_LISTENER_HYPRMX);
        REWARDED_AD_HYPRMX.loadAd();


    }

    //admob
    private void loadRewardedAdAdmob(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdAdmob()");

        vAdEnhancerRegister.AdmobInit(activity);

        REWARDED_LISTENER_ADMOB = new RewardedAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdLoaded()");
                REWARDED_AD_ADMOB = rewardedAd;
                REWARDED_AD_ADMOB.setFullScreenContentCallback(REWARDED_LISTENER_ADMOB_2);
                REWARDED_AD_ADMOB_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdFailedToLoad() " + loadAdError.getMessage());
                logFirebase("true", "rewarded", "failed", "admob", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdAdmob(activity);
                            }
                        }
                    }
                }.start();
            }


        };

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, vAdEnhancerRegister.getPlacementId("admob", "rewarded", activity), adRequest, REWARDED_LISTENER_ADMOB);


        REWARDED_LISTENER_ADMOB_2 = new FullScreenContentCallback() {

            @Override
            public void onAdImpression() {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdImpression()");
                logFirebase("true", "rewarded", "impression", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "admob", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdDismissedFullScreenContent()");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdFailedToShowFullScreenContent()");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onAdShowedFullScreenContent()");
            }
        };


        REWARDED_LISTENER_ADMOB_3 = new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                Log.e("VAERewardedModule", "loadRewardedAdAdmob onUserEarnedReward()");
                onRewardedVideoRewarded();
                loadRewardedAdAdmob(activity);
            }
        };

    }

    //max
    private void loadRewardedAdApplovin(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdMax()");

        vAdEnhancerRegister.MaxInit(activity);

        REWARDED_LISTENER_APPLOVIN = new MaxRewardedAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdLoaded()");
                REWARDED_AD_APPLOVIN_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdLoadFailed() " + error.getMessage());
                logFirebase("true", "rewarded", "failed", "applovin", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdApplovin(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onUserRewarded(MaxAd ad, MaxReward reward) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onUserRewarded()");
                onRewardedVideoRewarded();
                loadRewardedAdApplovin(activity);
            }


            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onRewardedVideoCompleted()");
                logFirebase("true", "rewarded", "impression", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "applovin", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onRewardedVideoStarted(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onRewardedVideoStarted()");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdDisplayed()");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdHidden()");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.e("VAERewardedModule", "loadRewardedAdMax onAdDisplayFailed() " + error.getMessage());

            }
        };


        REWARDED_AD_APPLOVIN = MaxRewardedAd.getInstance(vAdEnhancerRegister.getPlacementId("applovin", "rewarded", activity), activity);
        REWARDED_AD_APPLOVIN.setListener(REWARDED_LISTENER_APPLOVIN);
        REWARDED_AD_APPLOVIN.loadAd();

    }

    //kidoz
    private void loadRewardedAdKidoz(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdKidoz()");

        vAdEnhancerRegister.KidozInit(activity);

        REWARDED_LISTENER_KIDOZ = new BaseInterstitial.IOnInterstitialEventListener() {

            @Override
            public void onReady() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
                REWARDED_AD_KIDOZ_LOADED = true;
            }

            @Override
            public void onLoadFailed() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdKidoz(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onOpened() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
            }

            @Override
            public void onClosed() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
            }

            @Override
            public void onNoOffers() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
            }
        };


        REWARDED_LISTENER_KIDOZ_2 = new BaseInterstitial.IOnInterstitialRewardedEventListener() {
            @Override
            public void onRewardReceived() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardReceived()");
                onRewardedVideoRewarded();
                loadRewardedAdKidoz(activity);
            }

            @Override
            public void onRewardedStarted() {
                Log.e("VAERewardedModule", "loadRewardedAdKidoz onRewardedStarted()");
                logFirebase("true", "rewarded", "impression", "kidoz", String.valueOf(""), String.valueOf(""));
            }
        };


        REWARDED_AD_KIDOZ = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.REWARDED_VIDEO);
        REWARDED_AD_KIDOZ.setOnInterstitialEventListener(REWARDED_LISTENER_KIDOZ);
        REWARDED_AD_KIDOZ.setOnInterstitialRewardedEventListener(REWARDED_LISTENER_KIDOZ_2);
        REWARDED_AD_KIDOZ.loadAd();

    }

    //appnext
    private void loadRewardedAdAppNext(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdAppNext()");

        vAdEnhancerRegister.AppNextInit(activity);

        REWARDED_AD_APPNEXT = new RewardedVideo(activity, vAdEnhancerRegister.getPlacementId("appnext", "rewarded", activity));
        REWARDED_AD_APPNEXT.loadAd();


        REWARDED_AD_APPNEXT.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s, AppnextAdCreativeType appnextAdCreativeType) {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext adLoaded()");
                REWARDED_AD_APPNEXT_LOADED = true;
            }
        });
        REWARDED_AD_APPNEXT.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String error) {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext adError()");
                logFirebase("true", "rewarded", "failed", "appnext", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdAppNext(activity);
                            }
                        }
                    }
                }.start();
            }
        });
        REWARDED_AD_APPNEXT.setOnVideoEndedCallback(new OnVideoEnded() {
            @Override
            public void videoEnded() {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext videoEnded()");
                onRewardedVideoRewarded();
                loadRewardedAdAppNext(activity);
            }
        });
        REWARDED_AD_APPNEXT.setOnAdOpenedCallback(new OnAdOpened() {
            @Override
            public void adOpened() {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext adOpened()");
                logFirebase("true", "rewarded", "impression", "appnext", String.valueOf(""), String.valueOf(""));
            }
        });
        REWARDED_AD_APPNEXT.setOnAdClickedCallback(new OnAdClicked() {
            @Override
            public void adClicked() {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext adClicked()");
                logFirebase("true", "rewarded", "clicked", "appnext", String.valueOf(""), String.valueOf(""));
            }
        });
        REWARDED_AD_APPNEXT.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                Log.e("VAERewardedModule", "loadRewardedAdAppNext onAdClosed()");
            }
        });

    }

    //greedygames
    private void loadRewardedAdGreedyGames(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdGreedyGames()");

        vAdEnhancerRegister.GreedyGamesInit(activity);


        REWARDED_LISTENER_GREEDYGAMES = new GGRewardedAdsEventListener() {

            @Override
            public void onAdLoaded() {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onAdLoaded()");
                REWARDED_AD_GREEDYGAMES_LOADED = true;
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onAdLoadFailed()");
                logFirebase("true", "rewarded", "failed", "greedygames", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdGreedyGames(activity);
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void onReward() {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onReward()");
                onRewardedVideoRewarded();
                loadRewardedAdGreedyGames(activity);
            }

            @Override
            public void onAdOpened() {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onAdOpened()");
                logFirebase("true", "rewarded", "impression", "greedygames", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdShowFailed() {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onAdShowFailed()");
            }

            @Override
            public void onAdClosed() {
                Log.e("VAERewardedModule", "loadRewardedAdGreedyGames onAdClosed()");
            }

        };

        REWARDED_AD_GREEDYGAMES = new GGRewardedAd(activity, vAdEnhancerRegister.getPlacementId("greedygames", "rewarded", activity));
        REWARDED_AD_GREEDYGAMES.setListener(REWARDED_LISTENER_GREEDYGAMES);
        REWARDED_AD_GREEDYGAMES.loadAd();

    }

    //googleadmanager
    private void loadRewardedAdGoogleAdManager(Activity activity) {
        Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager()");

        vAdEnhancerRegister.GoogleAdManagerInit(activity);

        REWARDED_LISTENER_GOOGLEADMANAGER = new RewardedAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdLoaded()");
                REWARDED_AD_GOOGLEADMANAGER = rewardedAd;
                REWARDED_AD_GOOGLEADMANAGER.setFullScreenContentCallback(REWARDED_LISTENER_GOOGLEADMANAGER_2);
                REWARDED_AD_GOOGLEADMANAGER_LOADED = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdFailedToLoad() " + loadAdError.getMessage());
                logFirebase("true", "rewarded", "failed", "googleadmanager", String.valueOf(""), String.valueOf(""));
                new CountDownTimer(60000, 5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (vAdEnhancerRegister != null) {
                            if (vAdEnhancerRegister.USERONLINE) {
                                loadRewardedAdGoogleAdManager(activity);
                            }
                        }
                    }
                }.start();
            }


        };

        RewardedAd.load(activity, vAdEnhancerRegister.getPlacementId("gam", "rewarded", activity), new AdManagerAdRequest.Builder().build(), REWARDED_LISTENER_GOOGLEADMANAGER);


        REWARDED_LISTENER_GOOGLEADMANAGER_2 = new FullScreenContentCallback() {

            @Override
            public void onAdImpression() {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdImpression()");
                logFirebase("true", "rewarded", "impression", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdClicked() {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdClicked()");
                logFirebase("true", "rewarded", "clicked", "googleadmanager", String.valueOf(""), String.valueOf(""));
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdDismissedFullScreenContent()");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdFailedToShowFullScreenContent()");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onAdShowedFullScreenContent()");
            }
        };


        REWARDED_LISTENER_GOOGLEADMANAGER_3 = new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                Log.e("VAERewardedModule", "loadRewardedAdGoogleAdManager onUserEarnedReward()");
                onRewardedVideoRewarded();
                loadRewardedAdGoogleAdManager(activity);
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
