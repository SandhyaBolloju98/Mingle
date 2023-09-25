package com.v.ad.vadenhancerLibrary;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.v.ad.vadenhancerLibrary.Classes.Modules.VAEAppOpenModule;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAERewardedModule;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAdEnhancerBannerModule;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAdEnhancerInterstitialModule;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAdEnhancerMrecModule;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAdEnhancerNativeModule;
import com.v.ad.vadenhancerLibrary.Classes.VAdEnhancerRegister;

public class VAdEnhancer {

    private final Activity currentActivity;

    private VAdEnhancerRegister vAdEnhancerRegister;
    private final VAdEnhancerBannerModule vAdEnhancerBannerModule;
    private final VAdEnhancerMrecModule vAdEnhancerMrecModule;
    private final VAdEnhancerNativeModule vAdEnhancerNativeModule;
    private final VAdEnhancerInterstitialModule vAdEnhancerInterstitialModule;
    private final VAERewardedModule VAERewardedModule;
    private final VAEAppOpenModule VAEAppOpenModule;

    private static volatile VAdEnhancer INSTANCE = null;


    private VAdEnhancer(Activity activity) {
        Log.e("VAdEnhancer", "VAdEnhancer() " + activity.getLocalClassName() + " activity.getPackageName() = " + activity.getPackageName());

        currentActivity = activity;

        vAdEnhancerRegister = VAdEnhancerRegister.getInstance(activity, this);
        vAdEnhancerBannerModule = VAdEnhancerBannerModule.getInstance(activity, this, vAdEnhancerRegister);
        vAdEnhancerMrecModule = VAdEnhancerMrecModule.getInstance(activity, this, vAdEnhancerRegister);
        vAdEnhancerNativeModule = VAdEnhancerNativeModule.getInstance(activity, this, vAdEnhancerRegister);
        vAdEnhancerInterstitialModule = VAdEnhancerInterstitialModule.getInstance(activity, this, vAdEnhancerRegister);
        VAERewardedModule = com.v.ad.vadenhancerLibrary.Classes.Modules.VAERewardedModule.getInstance(activity, this, vAdEnhancerRegister);
        VAEAppOpenModule = com.v.ad.vadenhancerLibrary.Classes.Modules.VAEAppOpenModule.getInstance(activity, this, vAdEnhancerRegister);

        AppLifecycleTracker appLifecycleTracker = AppLifecycleTracker.getInstance();
        appLifecycleTracker.setEnableLifeCycleTracker(activity.getApplication(), vAdEnhancerRegister);

        vAdEnhancerRegister.login(activity);

    }

    public static VAdEnhancer getInstance(Activity activity) {
        if (INSTANCE == null) {
            synchronized (VAdEnhancer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancer(activity);
                }
            }
        }
        return INSTANCE;
    }


    /**
     * banner ad
     */
    public void loadBannerAds(String LOAD_AD) {
        Log.e("VAdEnhancer", "loadBannerAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD);

        vAdEnhancerBannerModule.loadBannerAds(LOAD_AD);
    }

    public void getMultipleBannerAds(Activity activity) {
        Log.e("VAdEnhancer", "getMultipleBannerAds() " + currentActivity.getLocalClassName());

        vAdEnhancerBannerModule.getMultipleBannerAds(activity);
        vAdEnhancerRegister.reCheck(activity);
    }

    public View getSingleBannerAd(Activity activity) {
        Log.e("VAdEnhancer", "getSingleBannerAd()" + currentActivity.getLocalClassName());

        vAdEnhancerRegister.reCheck(activity);
        return vAdEnhancerBannerModule.getSingleBannerAd(activity);
    }


    /**
     * mrec ad
     */
    public void loadMrecAds(String LOAD_AD) {
        Log.e("VAdEnhancer", "loadMrecAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD);

        vAdEnhancerMrecModule.loadMrecAds(LOAD_AD);
    }

    public void getMultipleMrecAds(Activity activity) {
        Log.e("VAdEnhancer", "getMultipleMrecAds() " + currentActivity.getLocalClassName());

        vAdEnhancerMrecModule.getMultipleMrecAds(activity);
        vAdEnhancerRegister.reCheck(activity);
    }

    public View getSingleMrecAd(Activity activity) {
        Log.e("VAdEnhancer", "getSingleMrecAd()");

        vAdEnhancerRegister.reCheck(activity);
        return vAdEnhancerMrecModule.getSingleMrecAd(activity);
    }


    /**
     * native ad
     */
    public void loadNativeAds(String LOAD_AD) {
        Log.e("VAdEnhancer", "loadNativeAds() " + currentActivity.getLocalClassName() + " LOAD_AD " + LOAD_AD);

        vAdEnhancerNativeModule.loadNativeAds(LOAD_AD);
    }

    public void getMultipleNativeAds(Activity activity) {
        Log.e("VAdEnhancer", "getMultipleNativeAds()");

        vAdEnhancerNativeModule.getMultipleNativeAds(activity);
        vAdEnhancerRegister.reCheck(activity);
    }

    public View getSingleNativeAd(Activity activity) {
        Log.e("VAdEnhancer", "getSingleNativeAd()");

        vAdEnhancerRegister.reCheck(activity);
        return vAdEnhancerNativeModule.getSingleNativeAd(activity);
    }


    /**
     * interstitial ad
     */
    public void loadInterstitialAds() {
        Log.e("VAdEnhancer", "loadInterstitialAds()  ");

        vAdEnhancerInterstitialModule.loadInterstitialAds();
    }

    public boolean isInterstitialAdReady(Activity activity) {
        Log.e("VAdEnhancer", "isInterstitialAdReady() " + currentActivity.getLocalClassName());

        vAdEnhancerRegister.reCheck(activity);
        return vAdEnhancerInterstitialModule.isInterstitialAdReady(activity);
    }

    public void showInterstitialAd(Activity activity) {
        Log.e("VAdEnhancer", "showInterstitialAd()");

        vAdEnhancerInterstitialModule.showInterstitialAd(activity);
    }


    /**
     * rewarded ad
     */
    public void loadRewardedVideoAds() {
        Log.e("VAdEnhancer", "loadRewardedVideoAds()  ");

        VAERewardedModule.loadRewardedVideoAds();
    }

    public boolean isRewardedVideoAdReady(Activity activity) {
        Log.e("VAdEnhancer", "isRewardedVideoAdReady() " + currentActivity.getLocalClassName());

        vAdEnhancerRegister.reCheck(activity);
        return VAERewardedModule.isRewardedVideoAdReady(activity);
    }

    public void showRewardedVideoAd(Activity activityx) {
        Log.e("VAdEnhancer", "showRewardedVideoAd()");

        VAERewardedModule.showRewardedVideoAd(activityx);
    }


    /**
     * appopen ad
     */
    public void loadAppOpenAds(String LOAD, Activity activity) {
        Log.e("VAdEnhancer", "loadAppOpenAds() ");

        VAEAppOpenModule.loadAppOpenAds(activity);
    }

    public boolean isAppOpenAdReady(Activity activity) {
        Log.e("VAdEnhancer", "isAppOpenAdReady() ");

        vAdEnhancerRegister.reCheck(activity);
        return VAEAppOpenModule.isAppOpenAdReady(activity);
    }

    public void showAppOpenAd() {
        Log.e("VAdEnhancer", "showAppOpenAd() ");

        VAEAppOpenModule.showAppOpenAd();
    }


    /**
     * destroy methods
     */
/*    public void destroyAllAds() {
        Log.e("VAdEnhancer", "destroyAllAds() " + currentActivity.getLocalClassName());

        //destroyAllBanners();
        //destroyAllMrec();
        //destroyAllFullScreenAds();

    }

    public void destroyAllBanners() {

        //ironsource
        if (BANNER_AD_IRONSOURCE != null) {
            IronSource.destroyBanner(BANNER_AD_IRONSOURCE);
        }
        if (BANNER_LISTENER_IRONSOURCE != null) {
            BANNER_LISTENER_IRONSOURCE = null;
        }


        //adcolony
        if (BANNER_AD_ADCOLONY != null) {
            BANNER_AD_ADCOLONY.destroy();
        }
        if (BANNER_LISTENER_ADCOLONY != null) {
            BANNER_LISTENER_ADCOLONY = null;
        }


        //admob
        if (BANNER_AD_ADMOB != null) {
            BANNER_AD_ADMOB.destroy();
        }
        if (BANNER_LISTENER_ADMOB != null) {
            BANNER_LISTENER_ADMOB = null;
        }


        //unity
        if (BANNER_AD_UNITY != null) {
            BANNER_AD_UNITY.destroy();
        }
        if (BANNER_LISTENER_UNITY != null) {
            BANNER_LISTENER_UNITY = null;
        }


        //meta
        if (BANNER_AD_META != null) {
            BANNER_AD_META.destroy();
        }
        if (BANNER_LISTENER_META != null) {
            BANNER_LISTENER_META = null;
        }


        //pangle
        if (BANNER_AD_PANGLE != null) {
            BANNER_AD_PANGLE.destroy();
        }
        if (BANNER_LISTENER_PANGLE != null) {
            BANNER_LISTENER_PANGLE = null;
        }


        //max
        if (BANNER_AD_APPLOVIN != null) {
            BANNER_AD_APPLOVIN.destroy();
        }
        if (BANNER_LISTENER_APPLOVIN != null) {
            BANNER_LISTENER_APPLOVIN = null;
        }


        //chartboost
        if (BANNER_AD_CHARTBOOST != null) {
            BANNER_AD_CHARTBOOST.detach();
        }
        if (BANNER_LISTENER_CHARTBOOST != null) {
            BANNER_LISTENER_CHARTBOOST = null;
        }


        //appodeal
        if (BANNER_AD_APPODEAL != null) {
            Appodeal.destroy(Appodeal.BANNER);
        }
        if (BANNER_LISTENER_APPODEAL != null) {
            BANNER_LISTENER_APPODEAL = null;
        }


        //startapp
*//*        if (BANNER_AD_STARTAPP != null) {
            BANNER_AD_STARTAPP.removeAllViews();
        }
        if (BANNER_LISTENER_STARTAPP != null) {
            BANNER_LISTENER_STARTAPP = null;
        }*//*


        //mytarget
        if (BANNER_AD_MYTARGET != null) {
            BANNER_AD_MYTARGET.destroy();
        }
        if (BANNER_LISTENER_MYTARGET != null) {
            BANNER_LISTENER_MYTARGET = null;
        }


        //vungle
        if (BANNER_AD_VUNGLE != null) {
            BANNER_AD_VUNGLE.destroyAd();
        }
        if (BANNER_LISTENER_VUNGLE != null) {
            BANNER_LISTENER_VUNGLE = null;
        }


        //hyprmx
        if (BANNER_AD_HYPRMX != null) {
            BANNER_AD_HYPRMX.destroy();
        }
        if (BANNER_LISTENER_HYPRMX != null) {
            BANNER_LISTENER_HYPRMX = null;
        }


        //inmobi
        if (BANNER_AD_INMOBI != null) {
            BANNER_AD_INMOBI.destroy();
        }
        if (BANNER_LISTENER_INMOBI != null) {
            BANNER_LISTENER_INMOBI = null;
        }
    }

    public void destroyAllMrec() {

        //ironsource
        if (MREC_AD_IRONSOURCE != null) {
            IronSource.destroyBanner(MREC_AD_IRONSOURCE);
        }
        if (MREC_LISTENER_IRONSOURCE != null) {
            MREC_LISTENER_IRONSOURCE = null;
        }


        //adcolony
        if (MREC_AD_ADCOLONY != null) {
            MREC_AD_ADCOLONY.destroy();
        }
        if (MREC_LISTENER_ADCOLONY != null) {
            MREC_LISTENER_ADCOLONY = null;
        }


        //admob
        if (MREC_AD_ADMOB != null) {
            MREC_AD_ADMOB.destroy();
        }
        if (MREC_LISTENER_ADMOB != null) {
            MREC_LISTENER_ADMOB = null;
        }


        //meta
        if (MREC_AD_META != null) {
            MREC_AD_META.destroy();
        }
        if (MREC_LISTENER_META != null) {
            MREC_LISTENER_META = null;
        }


        //pangle
        if (MREC_AD_PANGLE != null) {
            MREC_AD_PANGLE.destroy();
        }
        if (MREC_LISTENER_PANGLE != null) {
            MREC_LISTENER_PANGLE = null;
        }


        //max
        if (MREC_AD_APPLOVIN != null) {
            MREC_AD_APPLOVIN.destroy();
        }
        if (MREC_LISTENER_APPLOVIN != null) {
            MREC_LISTENER_APPLOVIN = null;
        }


        //chartboost
        if (MREC_AD_CHARTBOOST != null) {
            MREC_AD_CHARTBOOST.detach();
        }
        if (MREC_LISTENER_CHARTBOOST != null) {
            MREC_LISTENER_CHARTBOOST = null;
        }


        //appodeal
        if (MREC_AD_APPODEAL != null) {
            Appodeal.destroy(Appodeal.MREC);
        }
        if (MREC_LISTENER_APPODEAL != null) {
            MREC_LISTENER_APPODEAL = null;
        }


        //startapp
*//*        if (MREC_AD_STARTAPP != null) {
            MREC_AD_STARTAPP.removeAllViews();
        }
        if (MREC_LISTENER_STARTAPP != null) {
            MREC_LISTENER_STARTAPP = null;
        }*//*


        //mytarget
        if (MREC_AD_MYTARGET != null) {
            MREC_AD_MYTARGET.destroy();
        }
        if (MREC_LISTENER_MYTARGET != null) {
            MREC_LISTENER_MYTARGET = null;
        }


        //vungle
        if (MREC_AD_VUNGLE != null) {
            MREC_AD_VUNGLE.destroyAd();
        }
        if (MREC_LISTENER_VUNGLE != null) {
            MREC_LISTENER_VUNGLE = null;
        }


        //hyprmx
        if (MREC_AD_HYPRMX != null) {
            MREC_AD_HYPRMX.destroy();
        }
        if (MREC_LISTENER_HYPRMX != null) {
            MREC_LISTENER_HYPRMX = null;
        }


        //inmobi
        if (MREC_AD_INMOBI != null) {
            MREC_AD_INMOBI.destroy();
        }
        if (MREC_LISTENER_INMOBI != null) {
            MREC_LISTENER_INMOBI = null;
        }
    }

    public void destroyAllFullScreenAds() {

        //ironsource


        //adcolony


        //admob


        //unity


        //meta


        //pangle


        //max


        //chartboost


        //appodeal


        //startapp


        //mytarget


        //vungle


        //hyprmx


        //inmobi

    }*/


}
