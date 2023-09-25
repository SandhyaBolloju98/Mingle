package com.v.ad.vadenhancerLibrary.Classes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adcolony.sdk.AdColony;
import com.appbrain.AppBrain;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.appnext.base.Appnext;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.initializing.ApdInitializationCallback;
import com.appodeal.ads.initializing.ApdInitializationError;
import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.bytedance.sdk.openadsdk.api.init.PAGSdk;
import com.chartboost.sdk.Chartboost;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.greedygame.core.AppConfig;
import com.greedygame.core.GreedyGameAds;
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener;
import com.greedygame.core.models.general.InitErrors;
import com.hyprmx.android.sdk.core.HyprMX;
import com.hyprmx.android.sdk.core.HyprMXIf;
import com.hyprmx.android.sdk.utility.HyprMXLog;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;
import com.ironsource.adqualitysdk.sdk.IronSourceAdQuality;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.sdk.InitializationListener;
import com.kidoz.sdk.api.KidozSDK;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.my.target.common.MyTargetConfig;
import com.my.target.common.MyTargetManager;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.v.ad.vadenhancerLibrary.Classes.Modules.VAEAppOpenModule;
import com.v.ad.vadenhancerLibrary.LocalDb.VAELD;
import com.v.ad.vadenhancerLibrary.LocalTables.AdNetworksOrderTable;
import com.v.ad.vadenhancerLibrary.LocalTables.KeyValueTable;
import com.v.ad.vadenhancerLibrary.SessionManager;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;
import com.vungle.warren.InitCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VAdEnhancerRegister {

    private Activity currentActivity;
    private SessionManager sessionManager;

    private FirebaseApp firebaseApp;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private static volatile VAdEnhancerRegister INSTANCE = null;
    private VAdEnhancer vAdEnhancer;

    public HashMap<String, String> keyValueTableHashMap = new HashMap<>();
    public List<AdNetworksOrderTable> adTypesOrderList;
    public List<AdNetworksOrderTable> adNetworkOrderTableBannerList;
    public List<AdNetworksOrderTable> adNetworkOrderTableMrecList;
    public List<AdNetworksOrderTable> adNetworkOrderTableNativeList;
    public List<AdNetworksOrderTable> adNetworkOrderTableInterstitialList;
    public List<AdNetworksOrderTable> adNetworkOrderTableRewardedList;
    public List<AdNetworksOrderTable> adNetworkOrderTableAppOpenList;

    public long BANNERLOADEDWAITINGTIMER = 15000;
    public long MRECLOADEDWAITINGTIMER = 15000;
    public long NATIVELOADEDWAITINGTIMER = 15000;

    public boolean USERONLINE = true;

    private CountDownTimer keyValueListener;
    private CountDownTimer adNetworkOrderListener;

    private boolean loggedIn = false;


    private VAdEnhancerRegister(Activity activityx, VAdEnhancer vAdEnhancerxHandler) {
        Log.e("VAdEnhancerRegister", "VAdEnhancerRegister() = " + activityx.getLocalClassName() + " vAdEnhancerxHandler = " + vAdEnhancerxHandler);

        currentActivity = activityx;
        sessionManager = new SessionManager(activityx);
        vAdEnhancer = vAdEnhancerxHandler;

    }

    public static VAdEnhancerRegister getInstance(Activity activity, VAdEnhancer vAdEnhancer) {
        if (INSTANCE == null) {
            synchronized (VAEAppOpenModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VAdEnhancerRegister(activity, vAdEnhancer);
                }
            }
        }
        return INSTANCE;
    }

    public void reCheck(Activity activity) {
        if (loggedIn) {
            if ((sessionManager.getReCheckTime() + 3600) < (System.currentTimeMillis() / 1000L)) {
                Log.e("VAdEnhancerRegister", "reCheck " + activity.getLocalClassName() + " ses time " + sessionManager.getReCheckTime() + " cur time " + (System.currentTimeMillis() / 1000L));
                sessionManager.setReCheckTime(System.currentTimeMillis() / 1000L);
                login(activity);
            }
        }
    }

    public void login(Activity activity) {
        Log.e("VAdEnhancerRegister", "login " + activity.getLocalClassName());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("vadenhancer")
                .setApplicationId("1:649281499736:android:7827b0a07f82277b11d02d")
                .setApiKey("AIzaSyAFOcgPHh74yS3eF0alFAhdcY9rONvqUNw")
                .setStorageBucket("vadenhancer.appspot.com")
                .build();


        try {
            Log.e("VAdEnhancerRegister", "initializeFirebase() before exception");
            FirebaseApp.initializeApp(activity, options, "vadenhancer");
        } catch (IllegalStateException e) {
            Log.e("VAdEnhancerRegister", "session vadenhancer initialization is false exception = " + e);
        }

        firebaseApp = FirebaseApp.getInstance("vadenhancer");

        firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        firebaseFirestore = FirebaseFirestore.getInstance(firebaseApp);

        if (firebaseAuth.getCurrentUser() == null) {
            Log.e("VAdEnhancerRegister", "getCurrentUser() null " + activity.getLocalClassName());
            firebaseAuth.signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.e("VAdEnhancerRegister", "signInAnonymously:addOnSuccessListener " + authResult.toString());
                            firebaseUser = firebaseAuth.getCurrentUser();
                            checkFirebaseDetails(activity);
                            sessionManager.setReCheckTime(System.currentTimeMillis() / 1000L);
                            loggedIn = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("VAdEnhancerRegister", "signInAnonymously:addOnFailureListener " + e.getMessage());
                        }
                    });
        } else {
            Log.e("VAdEnhancerRegister", "login exists " + firebaseAuth.getCurrentUser().getUid());
            firebaseUser = firebaseAuth.getCurrentUser();
            checkFirebaseDetails(activity);
            sessionManager.setReCheckTime(System.currentTimeMillis() / 1000L);
            loggedIn = true;
        }

    }

    private void checkFirebaseDetails(Activity activity) {
        Log.e("VAdEnhancerRegister", "checkFirebaseDetails() " + activity.getLocalClassName() + " activity.getPackageName() = " + activity.getPackageName());

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("VAdEnhancerRegister", "checkFirebaseDetails() success");
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Log.e("VAdEnhancerRegister", "checkFirebaseDetails() documentSnapshot.exists()");
                                getDetails(activity);
                            } else {
                                Log.e("VAdEnhancerRegister", "checkFirebaseDetails() documentSnapshot not exists()");
                                setFirebaseDetails(activity);
                            }
                        } else {
                            Log.e("VAdEnhancerRegister", "checkFirebaseDetails() fail");
                        }
                    }
                });
    }

    private void setFirebaseDetails(Activity activity) {
        Log.e("VAdEnhancerRegister", "setFirebaseDetails() " + activity.getLocalClassName());


        HashMap<String, Object> mainDetails = new HashMap<>();
        mainDetails.put("firebase_user_id", firebaseUser.getUid());
        mainDetails.put("package", activity.getPackageName());
        mainDetails.put("activated", true);
        mainDetails.put("test_mode", true);
        mainDetails.put("banner_timer", 15000);
        mainDetails.put("mrec_timer", 15000);
        mainDetails.put("native_timer", 15000);
        mainDetails.put("tm", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .set(mainDetails, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("VAdEnhancerRegister", "setFirebaseDetails() onSuccess() " + activity.getLocalClassName());

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.e("VAdEnhancerRegister", "setFirebaseDetails " + millisUntilFinished);
                            }

                            @Override
                            public void onFinish() {
                                Log.e("VAdEnhancerRegister", "setFirebaseDetails onFinish() ");
                                getDetails(activity);
                            }
                        }.start();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("VAdEnhancerRegister", "setFirebaseDetails() onFailure() " + e.getLocalizedMessage());
                    }
                });

        HashMap<String, Object> adTypesDetails = new HashMap<>();
        adTypesDetails.put("banner", true);
        adTypesDetails.put("mrec", true);
        adTypesDetails.put("native", true);
        adTypesDetails.put("interstitial", true);
        adTypesDetails.put("rewarded", true);
        adTypesDetails.put("appopen", true);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdTypes")
                .document("AdTypes")
                .set(adTypesDetails, SetOptions.merge());

        HashMap<String, Object> adTypesOrderDetails = new HashMap<>();
        adTypesOrderDetails.put("banner", 1);
        adTypesOrderDetails.put("mrec", 2);
        adTypesOrderDetails.put("native", 3);
        adTypesOrderDetails.put("interstitial", 4);
        adTypesOrderDetails.put("rewarded", 5);
        adTypesOrderDetails.put("appopen", 6);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdTypesOrder")
                .document("AdTypesOrder")
                .set(adTypesOrderDetails, SetOptions.merge());

        HashMap<String, Object> bannerOrderDetails = new HashMap<>();
        bannerOrderDetails.put("admob", 1);
        bannerOrderDetails.put("gam", 2);
        bannerOrderDetails.put("meta", 3);
        bannerOrderDetails.put("unity", 4);
        bannerOrderDetails.put("adcolony", 5);
        bannerOrderDetails.put("pangle", 6);
        bannerOrderDetails.put("vungle", 7);
        bannerOrderDetails.put("applovin", 8);
        bannerOrderDetails.put("ironsource", 10);
        bannerOrderDetails.put("chartboost", 9);
        bannerOrderDetails.put("mytarget", 11);
        bannerOrderDetails.put("appodeal", 12);
        bannerOrderDetails.put("greedygames", 13);
        bannerOrderDetails.put("appbrain", 14);
        bannerOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("banner")
                .set(bannerOrderDetails, SetOptions.merge());

        HashMap<String, Object> mrecOrderDetails = new HashMap<>();
        mrecOrderDetails.put("admob", 1);
        mrecOrderDetails.put("gam", 2);
        mrecOrderDetails.put("meta", 3);
        mrecOrderDetails.put("unity", 4);
        mrecOrderDetails.put("adcolony", 5);
        mrecOrderDetails.put("pangle", 6);
        mrecOrderDetails.put("vungle", 7);
        mrecOrderDetails.put("applovin", 8);
        mrecOrderDetails.put("ironsource", 10);
        mrecOrderDetails.put("chartboost", 9);
        mrecOrderDetails.put("mytarget", 11);
        mrecOrderDetails.put("appodeal", 12);
        mrecOrderDetails.put("greedygames", 13);
        mrecOrderDetails.put("appbrain", 14);
        mrecOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("mrec")
                .set(mrecOrderDetails, SetOptions.merge());

        HashMap<String, Object> nativeOrderDetails = new HashMap<>();
        nativeOrderDetails.put("admob", 1);
        nativeOrderDetails.put("gam", 2);
        nativeOrderDetails.put("meta", 3);
        nativeOrderDetails.put("unity", 4);
        nativeOrderDetails.put("adcolony", 5);
        nativeOrderDetails.put("pangle", 6);
        nativeOrderDetails.put("vungle", 7);
        nativeOrderDetails.put("applovin", 8);
        nativeOrderDetails.put("ironsource", 10);
        nativeOrderDetails.put("chartboost", 9);
        nativeOrderDetails.put("mytarget", 11);
        nativeOrderDetails.put("appodeal", 12);
        nativeOrderDetails.put("greedygames", 13);
        nativeOrderDetails.put("appbrain", 14);
        nativeOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("native")
                .set(nativeOrderDetails, SetOptions.merge());

        HashMap<String, Object> interstitialOrderDetails = new HashMap<>();
        interstitialOrderDetails.put("admob", 1);
        interstitialOrderDetails.put("gam", 2);
        interstitialOrderDetails.put("meta", 3);
        interstitialOrderDetails.put("unity", 4);
        interstitialOrderDetails.put("adcolony", 5);
        interstitialOrderDetails.put("pangle", 6);
        interstitialOrderDetails.put("vungle", 7);
        interstitialOrderDetails.put("applovin", 8);
        interstitialOrderDetails.put("ironsource", 10);
        interstitialOrderDetails.put("chartboost", 9);
        interstitialOrderDetails.put("mytarget", 11);
        interstitialOrderDetails.put("appodeal", 12);
        interstitialOrderDetails.put("greedygames", 13);
        interstitialOrderDetails.put("appbrain", 14);
        interstitialOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("interstitial")
                .set(interstitialOrderDetails, SetOptions.merge());

        HashMap<String, Object> rewardedOrderDetails = new HashMap<>();
        rewardedOrderDetails.put("admob", 1);
        rewardedOrderDetails.put("gam", 2);
        rewardedOrderDetails.put("meta", 3);
        rewardedOrderDetails.put("unity", 4);
        rewardedOrderDetails.put("adcolony", 5);
        rewardedOrderDetails.put("pangle", 6);
        rewardedOrderDetails.put("vungle", 7);
        rewardedOrderDetails.put("applovin", 8);
        rewardedOrderDetails.put("ironsource", 10);
        rewardedOrderDetails.put("chartboost", 9);
        rewardedOrderDetails.put("mytarget", 11);
        rewardedOrderDetails.put("appodeal", 12);
        rewardedOrderDetails.put("greedygames", 13);
        rewardedOrderDetails.put("appbrain", 14);
        rewardedOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("rewarded")
                .set(rewardedOrderDetails, SetOptions.merge());

        HashMap<String, Object> appopenOrderDetails = new HashMap<>();
        appopenOrderDetails.put("admob", 1);
        appopenOrderDetails.put("gam", 2);
        appopenOrderDetails.put("meta", 3);
        appopenOrderDetails.put("unity", 4);
        appopenOrderDetails.put("adcolony", 5);
        appopenOrderDetails.put("pangle", 6);
        appopenOrderDetails.put("vungle", 7);
        appopenOrderDetails.put("applovin", 8);
        appopenOrderDetails.put("ironsource", 10);
        appopenOrderDetails.put("chartboost", 9);
        appopenOrderDetails.put("mytarget", 11);
        appopenOrderDetails.put("appodeal", 12);
        appopenOrderDetails.put("greedygames", 13);
        appopenOrderDetails.put("appbrain", 14);
        appopenOrderDetails.put("appnext", 15);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworksOrder")
                .document("appopen")
                .set(appopenOrderDetails, SetOptions.merge());


        HashMap<String, Object> admobDetails = new HashMap<>();
        admobDetails.put("admob_activated", false);
        admobDetails.put("admob_test_mode", true);
        admobDetails.put("admob_test_app_id", "ca-app-pub-3940256099942544~3347511713");
        admobDetails.put("admob_test_banner", "ca-app-pub-3940256099942544/6300978111");
        admobDetails.put("admob_test_mrec", "ca-app-pub-3940256099942544/6300978111");
        admobDetails.put("admob_test_native", "ca-app-pub-3940256099942544/2247696110");
        admobDetails.put("admob_test_interstitial", "ca-app-pub-3940256099942544/1033173712");
        admobDetails.put("admob_test_rewarded", "ca-app-pub-3940256099942544/5224354917");
        admobDetails.put("admob_test_appopen", "ca-app-pub-3940256099942544/3419835294");
        admobDetails.put("admob_app_id", "");
        admobDetails.put("admob_banner_default", "");
        admobDetails.put("admob_mrec_default", "");
        admobDetails.put("admob_native_default", "");
        admobDetails.put("admob_interstitial_default", "");
        admobDetails.put("admob_rewarded_default", "");
        admobDetails.put("admob_appopen_default", "");
        admobDetails.put("admob_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("admob")
                .set(admobDetails, SetOptions.merge());


        HashMap<String, Object> metaDetails = new HashMap<>();
        metaDetails.put("meta_activated", false);
        metaDetails.put("meta_test_mode", true);
        metaDetails.put("meta_test_app_id", "");
        metaDetails.put("meta_test_banner", "IMG_16_9_APP_INSTALL");
        metaDetails.put("meta_test_mrec", "IMG_16_9_APP_INSTALL");
        metaDetails.put("meta_test_native", "IMG_16_9_APP_INSTALL");
        metaDetails.put("meta_test_interstitial", "VID_HD_16_9_15S_APP_INSTALL");
        metaDetails.put("meta_test_rewarded", "VID_HD_9_16_39S_APP_INSTALL");
        metaDetails.put("meta_test_appopen", "");
        metaDetails.put("meta_app_id", "");
        metaDetails.put("meta_banner_default", "");
        metaDetails.put("meta_mrec_default", "");
        metaDetails.put("meta_native_default", "");
        metaDetails.put("meta_interstitial_default", "");
        metaDetails.put("meta_rewarded_default", "");
        metaDetails.put("meta_appopen_default", "");
        metaDetails.put("meta_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("meta")
                .set(metaDetails, SetOptions.merge());


        HashMap<String, Object> adcolonyDetails = new HashMap<>();
        adcolonyDetails.put("adcolony_activated", false);
        adcolonyDetails.put("adcolony_test_mode", true);
        adcolonyDetails.put("adcolony_test_app_id", "");
        adcolonyDetails.put("adcolony_test_banner", "");
        adcolonyDetails.put("adcolony_test_mrec", "");
        adcolonyDetails.put("adcolony_test_native", "");
        adcolonyDetails.put("adcolony_test_interstitial", "");
        adcolonyDetails.put("adcolony_test_rewarded", "");
        adcolonyDetails.put("adcolony_test_appopen", "");
        adcolonyDetails.put("adcolony_app_id", "");
        adcolonyDetails.put("adcolony_banner_default", "");
        adcolonyDetails.put("adcolony_mrec_default", "");
        adcolonyDetails.put("adcolony_native_default", "");
        adcolonyDetails.put("adcolony_interstitial_default", "");
        adcolonyDetails.put("adcolony_rewarded_default", "");
        adcolonyDetails.put("adcolony_appopen_default", "");
        adcolonyDetails.put("adcolony_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("adcolony")
                .set(adcolonyDetails, SetOptions.merge());


        HashMap<String, Object> unityDetails = new HashMap<>();
        unityDetails.put("unity_activated", false);
        unityDetails.put("unity_test_mode", true);
        unityDetails.put("unity_test_app_id", "1234567");
        unityDetails.put("unity_test_banner", "banner");
        unityDetails.put("unity_test_mrec", "");
        unityDetails.put("unity_test_native", "");
        unityDetails.put("unity_test_interstitial", "interstitial");
        unityDetails.put("unity_test_rewarded", "rewarded");
        unityDetails.put("unity_test_appopen", "");
        unityDetails.put("unity_app_id", "");
        unityDetails.put("unity_banner_default", "");
        unityDetails.put("unity_mrec_default", "");
        unityDetails.put("unity_native_default", "");
        unityDetails.put("unity_interstitial_default", "");
        unityDetails.put("unity_rewarded_default", "");
        unityDetails.put("unity_appopen_default", "");
        unityDetails.put("unity_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("unity")
                .set(unityDetails, SetOptions.merge());


        HashMap<String, Object> pangleDetails = new HashMap<>();
        pangleDetails.put("pangle_activated", false);
        pangleDetails.put("pangle_test_mode", true);
        pangleDetails.put("pangle_test_app_id", "8025677");
        pangleDetails.put("pangle_test_banner", "980099802");
        pangleDetails.put("pangle_test_mrec", "980088196");
        pangleDetails.put("pangle_test_native", "980088216");
        pangleDetails.put("pangle_test_interstitial", "980088188");
        pangleDetails.put("pangle_test_rewarded", "980088192");
        pangleDetails.put("pangle_test_appopen", "890000078");
        pangleDetails.put("pangle_app_id", "");
        pangleDetails.put("pangle_banner_default", "");
        pangleDetails.put("pangle_mrec_default", "");
        pangleDetails.put("pangle_native_default", "");
        pangleDetails.put("pangle_interstitial_default", "");
        pangleDetails.put("pangle_rewarded_default", "");
        pangleDetails.put("pangle_appopen_default", "");
        pangleDetails.put("pangle_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("pangle")
                .set(pangleDetails, SetOptions.merge());


        HashMap<String, Object> vungleDetails = new HashMap<>();
        vungleDetails.put("vungle_activated", false);
        vungleDetails.put("vungle_test_mode", true);
        vungleDetails.put("vungle_test_app_id", "");
        vungleDetails.put("vungle_test_banner", "");
        vungleDetails.put("vungle_test_mrec", "");
        vungleDetails.put("vungle_test_native", "");
        vungleDetails.put("vungle_test_interstitial", "");
        vungleDetails.put("vungle_test_rewarded", "");
        vungleDetails.put("vungle_test_appopen", "");
        vungleDetails.put("vungle_app_id", "");
        vungleDetails.put("vungle_banner_default", "");
        vungleDetails.put("vungle_mrec_default", "");
        vungleDetails.put("vungle_native_default", "");
        vungleDetails.put("vungle_interstitial_default", "");
        vungleDetails.put("vungle_rewarded_default", "");
        vungleDetails.put("vungle_appopen_default", "");
        vungleDetails.put("vungle_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("vungle")
                .set(vungleDetails, SetOptions.merge());


        HashMap<String, Object> chartboostDetails = new HashMap<>();
        chartboostDetails.put("chartboost_activated", false);
        chartboostDetails.put("chartboost_test_mode", true);
        chartboostDetails.put("chartboost_test_app_id", "");
        chartboostDetails.put("chartboost_test_banner", "");
        chartboostDetails.put("chartboost_test_mrec", "");
        chartboostDetails.put("chartboost_test_native", "");
        chartboostDetails.put("chartboost_test_interstitial", "");
        chartboostDetails.put("chartboost_test_rewarded", "");
        chartboostDetails.put("chartboost_test_appopen", "");
        chartboostDetails.put("chartboost_app_id", "");
        chartboostDetails.put("chartboost_app_signature", "");
        chartboostDetails.put("chartboost_banner_default", "");
        chartboostDetails.put("chartboost_mrec_default", "");
        chartboostDetails.put("chartboost_native_default", "");
        chartboostDetails.put("chartboost_interstitial_default", "");
        chartboostDetails.put("chartboost_rewarded_default", "");
        chartboostDetails.put("chartboost_appopen_default", "");
        chartboostDetails.put("chartboost_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("chartboost")
                .set(chartboostDetails, SetOptions.merge());


        HashMap<String, Object> applovinDetails = new HashMap<>();
        applovinDetails.put("applovin_activated", false);
        applovinDetails.put("applovin_test_mode", true);
        applovinDetails.put("applovin_test_app_id", "");
        applovinDetails.put("applovin_test_banner", "");
        applovinDetails.put("applovin_test_mrec", "");
        applovinDetails.put("applovin_test_native", "");
        applovinDetails.put("applovin_test_interstitial", "");
        applovinDetails.put("applovin_test_rewarded", "");
        applovinDetails.put("applovin_test_appopen", "");
        applovinDetails.put("applovin_app_id", "");
        applovinDetails.put("applovin_banner_default", "");
        applovinDetails.put("applovin_mrec_default", "");
        applovinDetails.put("applovin_native_default", "");
        applovinDetails.put("applovin_interstitial_default", "");
        applovinDetails.put("applovin_rewarded_default", "");
        applovinDetails.put("applovin_appopen_default", "");
        applovinDetails.put("applovin_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("applovin")
                .set(applovinDetails, SetOptions.merge());


        HashMap<String, Object> ironsourceDetails = new HashMap<>();
        ironsourceDetails.put("ironsource_activated", false);
        ironsourceDetails.put("ironsource_test_mode", true);
        ironsourceDetails.put("ironsource_test_app_id", "");
        ironsourceDetails.put("ironsource_test_banner", "");
        ironsourceDetails.put("ironsource_test_mrec", "");
        ironsourceDetails.put("ironsource_test_native", "");
        ironsourceDetails.put("ironsource_test_interstitial", "");
        ironsourceDetails.put("ironsource_test_rewarded", "");
        ironsourceDetails.put("ironsource_test_appopen", "");
        ironsourceDetails.put("ironsource_app_id", "");
        ironsourceDetails.put("ironsource_banner_default", "");
        ironsourceDetails.put("ironsource_mrec_default", "");
        ironsourceDetails.put("ironsource_native_default", "");
        ironsourceDetails.put("ironsource_interstitial_default", "");
        ironsourceDetails.put("ironsource_rewarded_default", "");
        ironsourceDetails.put("ironsource_appopen_default", "");
        ironsourceDetails.put("ironsource_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("ironsource")
                .set(ironsourceDetails, SetOptions.merge());


        HashMap<String, Object> mytargetDetails = new HashMap<>();
        mytargetDetails.put("mytarget_activated", false);
        mytargetDetails.put("mytarget_test_mode", true);
        mytargetDetails.put("mytarget_test_app_id", "");
        mytargetDetails.put("mytarget_test_mac_id", "");
        mytargetDetails.put("mytarget_test_banner", "");
        mytargetDetails.put("mytarget_test_mrec", "");
        mytargetDetails.put("mytarget_test_native", "");
        mytargetDetails.put("mytarget_test_interstitial", "");
        mytargetDetails.put("mytarget_test_rewarded", "");
        mytargetDetails.put("mytarget_test_appopen", "");
        mytargetDetails.put("mytarget_app_id", "");
        mytargetDetails.put("mytarget_banner_default", "");
        mytargetDetails.put("mytarget_mrec_default", "");
        mytargetDetails.put("mytarget_native_default", "");
        mytargetDetails.put("mytarget_interstitial_default", "");
        mytargetDetails.put("mytarget_rewarded_default", "");
        mytargetDetails.put("mytarget_appopen_default", "");
        mytargetDetails.put("mytarget_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("mytarget")
                .set(mytargetDetails, SetOptions.merge());


        HashMap<String, Object> appodealDetails = new HashMap<>();
        appodealDetails.put("appodeal_activated", false);
        appodealDetails.put("appodeal_test_mode", true);
        appodealDetails.put("appodeal_test_app_id", "");
        appodealDetails.put("appodeal_test_banner", "");
        appodealDetails.put("appodeal_test_mrec", "");
        appodealDetails.put("appodeal_test_native", "");
        appodealDetails.put("appodeal_test_interstitial", "");
        appodealDetails.put("appodeal_test_rewarded", "");
        appodealDetails.put("appodeal_test_appopen", "");
        appodealDetails.put("appodeal_app_id", "");
        appodealDetails.put("appodeal_banner_default", "");
        appodealDetails.put("appodeal_mrec_default", "");
        appodealDetails.put("appodeal_native_default", "");
        appodealDetails.put("appodeal_interstitial_default", "");
        appodealDetails.put("appodeal_rewarded_default", "");
        appodealDetails.put("appodeal_appopen_default", "");
        appodealDetails.put("appodeal_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("appodeal")
                .set(appodealDetails, SetOptions.merge());


        HashMap<String, Object> gamDetails = new HashMap<>();
        gamDetails.put("gam_activated", false);
        gamDetails.put("gam_test_mode", true);
        gamDetails.put("gam_test_app_id", "ca-app-pub-3940256099942544~3347511713");
        gamDetails.put("gam_test_banner", "/6499/example/banner");
        gamDetails.put("gam_test_mrec", "/6499/example/banner");
        gamDetails.put("gam_test_native", "/6499/example/native");
        gamDetails.put("gam_test_interstitial", "/6499/example/interstitial");
        gamDetails.put("gam_test_rewarded", "/6499/example/rewarded");
        gamDetails.put("gam_test_appopen", "/6499/example/app-open");
        gamDetails.put("gam_app_id", "");
        gamDetails.put("gam_banner_default", "");
        gamDetails.put("gam_mrec_default", "");
        gamDetails.put("gam_native_default", "");
        gamDetails.put("gam_interstitial_default", "");
        gamDetails.put("gam_rewarded_default", "");
        gamDetails.put("gam_appopen_default", "");
        gamDetails.put("gam_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("gam")
                .set(gamDetails, SetOptions.merge());


        HashMap<String, Object> greedygamesDetails = new HashMap<>();
        greedygamesDetails.put("greedygames_activated", false);
        greedygamesDetails.put("greedygames_test_mode", true);
        greedygamesDetails.put("greedygames_test_app_id", "");
        greedygamesDetails.put("greedygames_test_banner", "");
        greedygamesDetails.put("greedygames_test_mrec", "");
        greedygamesDetails.put("greedygames_test_native", "");
        greedygamesDetails.put("greedygames_test_interstitial", "");
        greedygamesDetails.put("greedygames_test_rewarded", "");
        greedygamesDetails.put("greedygames_test_appopen", "");
        greedygamesDetails.put("greedygames_app_id", "");
        greedygamesDetails.put("greedygames_banner_default", "");
        greedygamesDetails.put("greedygames_mrec_default", "");
        greedygamesDetails.put("greedygames_native_default", "");
        greedygamesDetails.put("greedygames_interstitial_default", "");
        greedygamesDetails.put("greedygames_rewarded_default", "");
        greedygamesDetails.put("greedygames_appopen_default", "");
        greedygamesDetails.put("greedygames_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("greedygames")
                .set(greedygamesDetails, SetOptions.merge());


        HashMap<String, Object> appbrainDetails = new HashMap<>();
        appbrainDetails.put("appbrain_activated", false);
        appbrainDetails.put("appbrain_test_mode", true);
        appbrainDetails.put("appbrain_test_app_id", "");
        mytargetDetails.put("appbrain_test_device_id", "");
        appbrainDetails.put("appbrain_test_banner", "");
        appbrainDetails.put("appbrain_test_mrec", "");
        appbrainDetails.put("appbrain_test_native", "");
        appbrainDetails.put("appbrain_test_interstitial", "");
        appbrainDetails.put("appbrain_test_rewarded", "");
        appbrainDetails.put("appbrain_test_appopen", "");
        appbrainDetails.put("appbrain_app_id", "");
        appbrainDetails.put("appbrain_banner_default", "");
        appbrainDetails.put("appbrain_mrec_default", "");
        appbrainDetails.put("appbrain_native_default", "");
        appbrainDetails.put("appbrain_interstitial_default", "");
        appbrainDetails.put("appbrain_rewarded_default", "");
        appbrainDetails.put("appbrain_appopen_default", "");
        appbrainDetails.put("appbrain_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("appbrain")
                .set(appbrainDetails, SetOptions.merge());


        HashMap<String, Object> appnextDetails = new HashMap<>();
        appnextDetails.put("appnext_activated", false);
        appnextDetails.put("appnext_test_mode", true);
        appnextDetails.put("appnext_test_app_id", "");
        appnextDetails.put("appnext_test_banner", "");
        appnextDetails.put("appnext_test_mrec", "");
        appnextDetails.put("appnext_test_native", "");
        appnextDetails.put("appnext_test_interstitial", "");
        appnextDetails.put("appnext_test_rewarded", "");
        appnextDetails.put("appnext_test_appopen", "");
        appnextDetails.put("appnext_app_id", "");
        appnextDetails.put("appnext_banner_default", "");
        appnextDetails.put("appnext_mrec_default", "");
        appnextDetails.put("appnext_native_default", "");
        appnextDetails.put("appnext_interstitial_default", "");
        appnextDetails.put("appnext_rewarded_default", "");
        appnextDetails.put("appnext_appopen_default", "");
        appnextDetails.put("appnext_last_updated_time", System.currentTimeMillis() / 1000L);

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document("appnext")
                .set(appnextDetails, SetOptions.merge());


    }


    private void getDetails(Activity activity) {
        Log.e("VAdEnhancerRegister", "getDetails() " + activity.getLocalClassName());


        adNetworkOrderListener();
        keyValueListener();


        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot.exists()) {

                                if (documentSnapshot.contains("activated")) {

                                    new InsertKeyValueAsyncTask("activated",
                                            String.valueOf(documentSnapshot.getBoolean("activated"))).execute();

                                    if (documentSnapshot.contains("banner_timer")) {
                                        BANNERLOADEDWAITINGTIMER = documentSnapshot.getLong("banner_timer");
                                    }
                                    if (documentSnapshot.contains("mrec_timer")) {
                                        MRECLOADEDWAITINGTIMER = documentSnapshot.getLong("mrec_timer");
                                    }
                                    if (documentSnapshot.contains("native_timer")) {
                                        NATIVELOADEDWAITINGTIMER = documentSnapshot.getLong("native_timer");
                                    }


                                    getAdTypeDetails();
                                    getAdTypeOrderDetails();
                                    getAdNetworkDetails();
                                    getAdNetworkOrdersDetails();

                                }

                            }
                        }
                    }
                });


    }

    private void getAdTypeDetails() {
        Log.e("VAdEnhancerRegister", "getAdTypeDetails()");

        firebaseFirestore.collection("Apps")
                .document(currentActivity.getPackageName())
                .collection("AdTypes")
                .document("AdTypes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                if (documentSnapshot.getData() != null) {
                                    Map<String, Object> map = documentSnapshot.getData();
                                    for (Map.Entry<String, Object> field : map.entrySet()) {
                                        new InsertKeyValueAsyncTask(field.getKey(), String.valueOf(field.getValue())).execute();
                                    }
                                }
                            }
                        }
                    }
                });


    }

    private void getAdTypeOrderDetails() {
        Log.e("VAdEnhancerRegister", "getAdTypeOrderDetails()");

        firebaseFirestore.collection("Apps")
                .document(currentActivity.getPackageName())
                .collection("AdTypesOrder")
                .document("AdTypesOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                if (documentSnapshot.getData() != null) {
                                    Map<String, Object> map = documentSnapshot.getData();
                                    for (Map.Entry<String, Object> field : map.entrySet()) {
                                        new InsertAdNetworkOrderAsyncTask("whole", field.getKey(), Integer.parseInt(String.valueOf(field.getValue()))).execute();
                                    }
                                }
                            }
                        }
                    }
                });


    }

    private void getAdNetworkDetails() {
        Log.e("VAdEnhancerRegister", "getAdNetworkDetails()");

        firebaseFirestore.collection("Apps")
                .document(currentActivity.getPackageName())
                .collection("AdNetworks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {

                                firebaseFirestore.collection("Apps")
                                        .document(currentActivity.getPackageName())
                                        .collection("AdNetworks")
                                        .document(documentSnapshot.getId())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if (documentSnapshot.exists()) {
                                                        if (documentSnapshot.getData() != null) {
                                                            Map<String, Object> map = documentSnapshot.getData();
                                                            for (Map.Entry<String, Object> field : map.entrySet()) {
                                                                new InsertKeyValueAsyncTask(field.getKey(), String.valueOf(field.getValue())).execute();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });

                                if (i == (task.getResult().size() - 1)) {
                                    firstSettings(currentActivity);
                                } else {
                                    i++;
                                }

                            }
                        }
                    }
                });


    }

    private void getAdNetworkOrdersDetails() {
        Log.e("VAdEnhancerRegister", "getAdNetworkOrdersDetails()");

        firebaseFirestore.collection("Apps")
                .document(currentActivity.getPackageName())
                .collection("AdNetworksOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                firebaseFirestore.collection("Apps")
                                        .document(currentActivity.getPackageName())
                                        .collection("AdNetworksOrder")
                                        .document(documentSnapshot.getId())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if (documentSnapshot.exists()) {
                                                        if (documentSnapshot.getData() != null) {
                                                            Map<String, Object> map = documentSnapshot.getData();
                                                            for (Map.Entry<String, Object> field : map.entrySet()) {
                                                                new InsertAdNetworkOrderAsyncTask(field.getKey(), documentSnapshot.getId(),
                                                                        Integer.parseInt(String.valueOf(field.getValue()))).execute();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });


    }


    private class InsertKeyValueAsyncTask extends AsyncTask<KeyValueTable, Void, Void> {

        private final String key;
        private final String value;

        private InsertKeyValueAsyncTask(String key,
                                        String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        protected Void doInBackground(KeyValueTable... keyValueTables) {

            boolean present = VAELD.getCHLDInstance(currentActivity).keyValueDao().isKeyInKeyValueTable(key);
            int id_inTable = VAELD.getCHLDInstance(currentActivity).keyValueDao().getIdOfKeyInKeyValueTable(key);


            if (present) {
                Log.e("VAdEnhancerDb", "InsertKeyValueAsyncTask updating key = " + key + " value = " + value);

                KeyValueTable keyValueTable = new KeyValueTable(
                        key,
                        value);
                keyValueTable.setId(id_inTable);
                VAELD.getCHLDInstance(currentActivity).keyValueDao().update(keyValueTable);

            } else {
                Log.e("VAdEnhancerDb", "InsertKeyValueAsyncTask new key = " + key + " value = " + value);

                VAELD.getCHLDInstance(currentActivity).keyValueDao().insert(new KeyValueTable(
                        key,
                        value));

            }


            return null;
        }


    }

    private void keyValueListener() {
        keyValueListener = new CountDownTimer(60000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerRegister", "keyValueListener() " + millisUntilFinished);
                new GetKeyValueListAsyncTask().execute();
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerRegister", "keyValueListener() onFinish() ");
            }
        }.start();

    }

    private class GetKeyValueListAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<KeyValueTable> keyValueTable;

        private GetKeyValueListAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            keyValueTable = VAELD.getCHLDInstance(currentActivity).keyValueDao().getAllKeyValues();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!keyValueTable.isEmpty()) {

                for (int i = 0; i < keyValueTable.size(); i++) {
                    keyValueTableHashMap.put(keyValueTable.get(i).getKey(), keyValueTable.get(i).getValue());
                }

            }
        }
    }


    private class InsertAdNetworkOrderAsyncTask extends AsyncTask<KeyValueTable, Void, Void> {

        private final String adnetwork;
        private final String adtype;
        private final int preference;

        private InsertAdNetworkOrderAsyncTask(String adnetwork,
                                              String adtype,
                                              int preference) {
            this.adnetwork = adnetwork;
            this.adtype = adtype;
            this.preference = preference;
        }

        @Override
        protected Void doInBackground(KeyValueTable... keyValueTables) {

            boolean present = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().isAdNetworkOfAdTypeInAdNetworkOrderTable(adnetwork, adtype);
            int id_inTable = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getIdOfAdNetworkOfAdTypeInAdNetworkOrderTable(adnetwork, adtype);


            if (present) {
                Log.e("VAdEnhancerDb", "InsertAdNetworkOrderAsyncTask updating " + adnetwork + adtype + preference);

                AdNetworksOrderTable adNetworksOrderTable = new AdNetworksOrderTable(
                        adnetwork,
                        adtype,
                        preference);
                adNetworksOrderTable.setId(id_inTable);
                VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().update(adNetworksOrderTable);

            } else {
                Log.e("VAdEnhancerDb", "InsertAdNetworkOrderAsyncTask new " + adnetwork + adtype + preference);

                VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().insert(new AdNetworksOrderTable(
                        adnetwork,
                        adtype,
                        preference));

            }


            return null;
        }


    }

    private void adNetworkOrderListener() {
        Log.e("VAdEnhancerRegister", "adNetworkOrderListener() " + currentActivity.getLocalClassName());
        adNetworkOrderListener = new CountDownTimer(60000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerRegister", "adNetworkOrderListener() " + millisUntilFinished);
                new GetAdNetworkOrderListAsyncTask().execute();
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerRegister", "adNetworkOrderListener() onFinish() ");
            }
        }.start();
    }

    private class GetAdNetworkOrderListAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<AdNetworksOrderTable> adTypesOrder;
        private List<AdNetworksOrderTable> adNetworksOrderTablesBanner;
        private List<AdNetworksOrderTable> adNetworksOrderTablesMrec;
        private List<AdNetworksOrderTable> adNetworksOrderTablesNative;
        private List<AdNetworksOrderTable> adNetworksOrderTablesInterstitial;
        private List<AdNetworksOrderTable> adNetworksOrderTablesRewarded;
        private List<AdNetworksOrderTable> adNetworksOrderTablesAppopen;

        private GetAdNetworkOrderListAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            adTypesOrder = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdTypeOrdersOfAdNetwork("whole");
            adNetworksOrderTablesBanner = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("banner");
            adNetworksOrderTablesMrec = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("mrec");
            adNetworksOrderTablesNative = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("native");
            adNetworksOrderTablesInterstitial = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("interstitial");
            adNetworksOrderTablesRewarded = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("rewarded");
            adNetworksOrderTablesAppopen = VAELD.getCHLDInstance(currentActivity).adNetworksOrderDao().getAllAdNetworkOrdersOfAdType("appopen");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e("register", "GetAdNetworkOrderListAsyncTask  " + adNetworksOrderTablesBanner);
            if (!adTypesOrder.isEmpty()) {
                adTypesOrderList = adTypesOrder;
            }
            if (!adNetworksOrderTablesBanner.isEmpty()) {
                adNetworkOrderTableBannerList = adNetworksOrderTablesBanner;
            }
            if (!adNetworksOrderTablesMrec.isEmpty()) {
                adNetworkOrderTableMrecList = adNetworksOrderTablesMrec;
            }
            if (!adNetworksOrderTablesNative.isEmpty()) {
                adNetworkOrderTableNativeList = adNetworksOrderTablesNative;
            }
            if (!adNetworksOrderTablesInterstitial.isEmpty()) {
                adNetworkOrderTableInterstitialList = adNetworksOrderTablesInterstitial;
            }
            if (!adNetworksOrderTablesRewarded.isEmpty()) {
                adNetworkOrderTableRewardedList = adNetworksOrderTablesRewarded;
            }
            if (!adNetworksOrderTablesAppopen.isEmpty()) {
                adNetworkOrderTableAppOpenList = adNetworksOrderTablesAppopen;
            }
        }
    }


    public void setPlacementId(String adNetwork, String adType, Activity activity) {
        Log.e("VAdEnhancerRegister", "setPlacementId() " + adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase());


        HashMap<String, Object> placementDetails = new HashMap<>();
        placementDetails.put(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase(), "");

        firebaseFirestore.collection("Apps")
                .document(activity.getPackageName())
                .collection("AdNetworks")
                .document(adNetwork)
                .set(placementDetails, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("VAdEnhancerRegister", "setPlacementId() onSuccess() " + activity.getLocalClassName());


                        new InsertKeyValueAsyncTask(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase(),
                                "").execute();

                    }
                });

    }

    public String getPlacementId(String adNetwork, String adType, Activity activity) {
        Log.e("VAdEnhancerRegister", "getPlacementId() " + adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase());

        String placementId = "empty";
        boolean set = false;

        if (keyValueTableHashMap.containsKey(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase())) {
            placementId = keyValueTableHashMap.get(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase());
            if (placementId != null) {
                if (placementId.equals("")) {
                    if (keyValueTableHashMap.containsKey(adNetwork + "_" + adType + "_default")) {
                        placementId = keyValueTableHashMap.get(adNetwork + "_" + adType + "_default");
                    }
                }
            }
        } else if (keyValueTableHashMap.containsKey(adNetwork + "_" + adType + "_default")) {
            placementId = keyValueTableHashMap.get(adNetwork + "_" + adType + "_default");
            set = true;
        }

        if (keyValueTableHashMap.containsKey(adNetwork + "_test_mode")) {
            if (Objects.equals(keyValueTableHashMap.get(adNetwork + "_test_mode"), "true")) {
                placementId = keyValueTableHashMap.get(adNetwork + "_test_" + adType);
            }
        }

        if (Objects.equals(placementId, "empty") || set) {
            setPlacementId(adNetwork, adType, activity);
        }

        if (placementId.equals("")) {
            placementId = "null";
        }

        Log.e("VAdEnhancerRegister", "getPlacementId " + placementId + " set " + set);

        return placementId;
    }

    public int getPlacementIdInt(String adNetwork, String adType, Activity activity) {

        String placementId = "123";
        boolean set = false;


        if (keyValueTableHashMap.containsKey(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase())) {
            placementId = keyValueTableHashMap.get(adNetwork + "_" + adType + "_" + activity.getLocalClassName().toLowerCase());
        } else if (keyValueTableHashMap.containsKey(adNetwork + "_" + adType + "_default")) {
            placementId = keyValueTableHashMap.get(adNetwork + "_" + adType + "_default");
            set = true;
        }

        if (keyValueTableHashMap.containsKey(adNetwork + "_test_mode")) {
            if (Objects.equals(keyValueTableHashMap.get(adNetwork + "_test_mode"), "true")) {
                placementId = keyValueTableHashMap.get(adNetwork + "_test_" + adType);
            }
        }

        if (Objects.equals(placementId, "123") || set) {
            setPlacementId(adNetwork, adType, activity);
        }

        if (placementId.equals("")) {
            placementId = "0";
        }

        Log.e("VAdEnhancerRegister", "getPlacementId " + placementId + " set " + set);

        return Integer.parseInt(placementId);
    }


    public void firstSettings(Activity activity) {
        Log.e("VAdEnhancerRegister", "firstSettings() " + activity.getLocalClassName());

        sessionManager.setIronSourceInit(false);
        sessionManager.setUnityInit(false);
        sessionManager.setAdcolonyInit(false);
        sessionManager.setMetaInit(false);
        sessionManager.setVungleInit(false);
        sessionManager.setMyTargetInit(false);
        sessionManager.setHYPRMXInit(false);//
        sessionManager.setPangleInit(false);
        sessionManager.setAdmobInit(false);
        sessionManager.setChartBoostInit(false);
        sessionManager.setStartAppInit(false);//
        sessionManager.setAppODealInit(false);
        sessionManager.setMaxInit(false);
        sessionManager.setInMobiInit(false);//
        sessionManager.setAppBrainInit(false);
        sessionManager.setKidozInit(false);//
        sessionManager.setAppNextInit(false);
        sessionManager.setGreedyGamesInit(false);
        sessionManager.setGoogleAdManagerInit(false);


        new CountDownTimer(5000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("VAdEnhancerRegister", "firstSettings() inits " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("VAdEnhancerRegister", "firstSettings() inits onFinish() ");

                IronSourceInit(activity);
                UnityInit(activity);
                AdcolonyInit(activity);
                MetaInit(activity);
                VungleInit(activity);
                MyTargetInit(activity);
                HyprMxInit(activity);
                PangleInit(activity);
                AdmobInit(activity);
                ChartBoostInit(activity);
                AppODealInit(activity);
                MaxInit(activity);
                InMobiInit(activity);
                AppBrainInit(activity);
                KidozInit(activity);
                AppNextInit(activity);
                GreedyGamesInit(activity);
                GoogleAdManagerInit(activity);


                if (keyValueTableHashMap != null && adTypesOrderList != null) {
                    Log.e("VAdEnhancerRegister", "firstSettings() inits keyValueTableHashMap = " + keyValueTableHashMap + " adTypesOrderList = " + adTypesOrderList);

                    long duration = 10000;
                    for (int i = 0; i < adTypesOrderList.size(); i++) {
                        Log.e("VAdEnhancerRegister", "firstSettings()  " + adTypesOrderList.get(i).getAdnetwork() + adTypesOrderList.get(i).getAdtype() + adTypesOrderList.get(i).getPreference());

                        if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "banner")) {
                            if (keyValueTableHashMap.containsKey("banner")) {
                                if (Objects.equals(keyValueTableHashMap.get("banner"), "true")) {
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadBannerAds("all");
                                        }
                                    }.start();
                                }
                            }
                        } else if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "mrec")) {
                            if (keyValueTableHashMap.containsKey("mrec")) {
                                if (Objects.equals(keyValueTableHashMap.get("mrec"), "true")) {
                                    Log.e("VAdEnhancerRegister", "firstSettings() mrec " + keyValueTableHashMap.get("mrec"));
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadMrecAds("all");
                                        }
                                    }.start();
                                }
                            }
                        }
                        if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "interstitial")) {
                            if (keyValueTableHashMap.containsKey("interstitial")) {
                                if (Objects.equals(keyValueTableHashMap.get("interstitial"), "true")) {
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadInterstitialAds();
                                        }
                                    }.start();
                                }
                            }
                        }
                        if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "rewarded")) {
                            if (keyValueTableHashMap.containsKey("rewarded")) {
                                if (Objects.equals(keyValueTableHashMap.get("rewarded"), "true")) {
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadRewardedVideoAds();
                                        }
                                    }.start();
                                }
                            }
                        }
                        if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "native")) {
                            if (keyValueTableHashMap.containsKey("native")) {
                                if (Objects.equals(keyValueTableHashMap.get("native"), "true")) {
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadNativeAds("all");
                                        }
                                    }.start();
                                }
                            }
                        }
                        if (Objects.equals(adTypesOrderList.get(i).getAdtype(), "appopen")) {
                            if (keyValueTableHashMap.containsKey("appopen")) {
                                if (Objects.equals(keyValueTableHashMap.get("appopen"), "true")) {
                                    new CountDownTimer(duration, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            vAdEnhancer.loadAppOpenAds("all", activity);
                                        }
                                    }.start();
                                }
                            }
                        }

                        duration = duration + 5000;
                    }
                } else {
                    Log.e("VAdEnhancerRegister", "firstSettings() inits keyValueTableHashMap == null && adTypesOrderList == null");

                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadBannerAds("all");
                        }
                    }.start();

                    new CountDownTimer(15000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadMrecAds("all");
                        }
                    }.start();

                    new CountDownTimer(20000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadInterstitialAds();
                        }
                    }.start();

                    new CountDownTimer(25000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadRewardedVideoAds();
                        }
                    }.start();

                    new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadNativeAds("all");
                        }
                    }.start();

                    new CountDownTimer(35000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            vAdEnhancer.loadAppOpenAds("all", activity);
                        }
                    }.start();

                }


            }
        }.start();

    }


    //inits
    public void IronSourceInit(Activity activity) {
        if (!sessionManager.getIronSourceInit()) {
            Log.e("VAdEnhancerRegister", "init ironsource " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init ironsource keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("ironsource_test_mode") && keyValueTableHashMap.containsKey("ironsource_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("ironsource_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("ironsource_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("ironsource_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("ironsource_app_id")) {
                id = keyValueTableHashMap.get("ironsource_app_id");
            }

            if (keyValueTableHashMap.containsKey("ironsource_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("ironsource_activated"), "true")) {

                    IronSource.init(activity, id, new InitializationListener() {
                        @Override
                        public void onInitializationComplete() {
                            Log.e("VAdEnhancerRegister", "ironsource init success");

                            sessionManager.setIronSourceInit(true);
                        }
                    });
                    IronSourceAdQuality.getInstance().initialize(activity, id);

                }
            }


            if (test_mode) {
                IntegrationHelper.validateIntegration(activity);
            }

        }

    }

    public void UnityInit(Activity activity) {
        if (!sessionManager.getUnityInit()) {
            Log.e("VAdEnhancerRegister", "init unity " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init unity keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("unity_test_mode") && keyValueTableHashMap.containsKey("unity_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("unity_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("unity_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("unity_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("unity_app_id")) {
                id = keyValueTableHashMap.get("unity_app_id");
            }

            if (keyValueTableHashMap.containsKey("unity_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("unity_activated"), "true")) {

                    UnityAds.initialize(activity, id, test_mode, new IUnityAdsInitializationListener() {
                        @Override
                        public void onInitializationComplete() {
                            Log.e("VAdEnhancerRegister", "unity init success");

                            sessionManager.setUnityInit(true);
                        }

                        @Override
                        public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                            Log.e("VAdEnhancerRegister", "unity init " + message);

                        }
                    });

                }
            }

        }
    }

    public void AdcolonyInit(Activity activity) {
        if (!sessionManager.getAdcolonyInit()) {
            Log.e("VAdEnhancerRegister", "init adcolony " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init adcolony keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("adcolony_test_mode") && keyValueTableHashMap.containsKey("adcolony_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("adcolony_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("adcolony_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("adcolony_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("adcolony_app_id")) {
                id = keyValueTableHashMap.get("adcolony_app_id");
            }

            if (keyValueTableHashMap.containsKey("adcolony_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("adcolony_activated"), "true")) {

                    assert id != null;
                    AdColony.configure(activity, id);

                    sessionManager.setAdcolonyInit(true);

                }
            }
        }
    }

    public void MetaInit(Activity activity) {
        if (!sessionManager.getMetaInit()) {
            Log.e("VAdEnhancerRegister", "init meta " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init meta keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("meta_test_mode") && keyValueTableHashMap.containsKey("meta_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("meta_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("meta_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("meta_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("meta_app_id")) {
                id = keyValueTableHashMap.get("meta_app_id");
            }

            if (keyValueTableHashMap.containsKey("meta_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("meta_activated"), "true")) {



                    AudienceNetworkAds.buildInitSettings(activity)
                            .withInitListener(new AudienceNetworkAds.InitListener() {
                                @Override
                                public void onInitialized(AudienceNetworkAds.InitResult initResult) {
                                    Log.e("VAdEnhancerRegister", "init meta onInitialized");
                                    sessionManager.setMetaInit(true);
                                }
                            })
                            .initialize();


                }
            }
        }
    }

    public void VungleInit(Activity activity) {
        if (!sessionManager.getVungleInit()) {
            Log.e("VAdEnhancerRegister", "init vungle " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init vungle keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("vungle_test_mode") && keyValueTableHashMap.containsKey("vungle_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("vungle_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("vungle_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("vungle_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("vungle_app_id")) {
                id = keyValueTableHashMap.get("vungle_app_id");
            }

            if (keyValueTableHashMap.containsKey("vungle_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("vungle_activated"), "true")) {

                    assert id != null;
                    Vungle.init(id, activity.getApplicationContext(), new InitCallback() {
                        @Override
                        public void onSuccess() {
                            Log.e("VAdEnhancerRegister", "init vungle onSuccess");

                            sessionManager.setVungleInit(true);
                        }

                        @Override
                        public void onError(VungleException exception) {
                            Log.e("VAdEnhancerRegister", "init vungle onError " + exception.getLocalizedMessage());
                        }

                        @Override
                        public void onAutoCacheAdAvailable(String placementId) {
                            Log.e("VAdEnhancerRegister", "init vungle onAutoCacheAdAvailable");

                        }
                    });

                }
            }

        }
    }

    public void MyTargetInit(Activity activity) {
        if (!sessionManager.getMyTargetInit()) {
            Log.e("VAdEnhancerRegister", "init mytarget " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init mytarget keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            String mac_id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("mytarget_test_mode") && keyValueTableHashMap.containsKey("mytarget_test_app_id") && keyValueTableHashMap.containsKey("mytarget_test_mac_id")) {
                if (Objects.equals(keyValueTableHashMap.get("mytarget_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("mytarget_test_app_id");
                    test_mode = true;
                    mac_id = keyValueTableHashMap.get("mytarget_test_mac_id");
                } else {
                    id = keyValueTableHashMap.get("mytarget_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("mytarget_app_id")) {
                id = keyValueTableHashMap.get("mytarget_app_id");
            }

            if (keyValueTableHashMap.containsKey("mytarget_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("mytarget_activated"), "true")) {

                    MyTargetManager.initSdk(activity);

                    if (test_mode) {
                        MyTargetConfig myTargetConfig = new MyTargetConfig.Builder()
                                .withTestDevices(mac_id)
                                .build();
                        MyTargetManager.setSdkConfig(myTargetConfig);
                    } else {
                        MyTargetConfig myTargetConfig = new MyTargetConfig.Builder()
                                .build();
                        MyTargetManager.setSdkConfig(myTargetConfig);
                    }

                    sessionManager.setMyTargetInit(true);

                }
            }
        }
    }

    public void HyprMxInit(Activity activity) {

        if (!sessionManager.getHYPRMXInit()) {
            Log.e("VAdEnhancerRegister", "init hyprmx " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init hyprmx keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("hyprmx_test_mode") && keyValueTableHashMap.containsKey("hyprmx_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("hyprmx_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("hyprmx_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("hyprmx_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("hyprmx_app_id")) {
                id = keyValueTableHashMap.get("hyprmx_app_id");
            }

            if (keyValueTableHashMap.containsKey("hyprmx_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("hyprmx_activated"), "true")) {

                    if (sessionManager.getUserID() != null) {

                        if (test_mode) {
                            HyprMXLog.enableDebugLogs(true);
                        }

                        HyprMX.INSTANCE.initialize(activity.getApplicationContext(), id, null, new HyprMXIf.HyprMXInitializationListener() {
                            @Override
                            public void initializationComplete() {
                                Log.e("VAdEnhancerRegister", "init hyprmx initializationComplete()");
                                sessionManager.setHYPRMXInit(true);
                            }

                            @Override
                            public void initializationFailed() {
                                Log.e("VAdEnhancerRegister", "init hyprmx initializationFailed() ");
                            }
                        });

                    }

                }
            }

        }
    }

    public void PangleInit(Activity activity) {

        if (!sessionManager.getPangleInit()) {
            Log.e("VAdEnhancerRegister", "init pangle " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init pangle keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("pangle_test_mode") && keyValueTableHashMap.containsKey("pangle_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("pangle_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("pangle_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("pangle_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("pangle_app_id")) {
                id = keyValueTableHashMap.get("pangle_app_id");
            }

            if (keyValueTableHashMap.containsKey("pangle_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("pangle_activated"), "true")) {

                    PAGConfig pAGInitConfig = new PAGConfig.Builder()
                            .appId(id)
                            .appIcon(0)
                            .debugLog(test_mode)
                            .supportMultiProcess(false)
                            .build();

                    PAGSdk.init(activity, pAGInitConfig, new PAGSdk.PAGInitCallback() {
                        @Override
                        public void success() {
                            Log.e("VAdEnhancerRegister", "pangle init success: ");
                            sessionManager.setPangleInit(true);
                        }

                        @Override
                        public void fail(int code, String msg) {
                            Log.e("VAdEnhancerRegister", "pangle init fail: " + code);
                        }
                    });

                }
            }

        }
    }

    public void AdmobInit(Activity activity) {
        if (!sessionManager.getAdmobInit()) {
            Log.e("VAdEnhancerRegister", "init admob " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init admob keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("admob_test_mode") && keyValueTableHashMap.containsKey("admob_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("admob_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("admob_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("admob_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("admob_app_id")) {
                id = keyValueTableHashMap.get("admob_app_id");
            }

            if (keyValueTableHashMap.containsKey("admob_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("admob_activated"), "true")) {

                    MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                            Log.e("VAdEnhancerRegister", "admob init " + initializationStatus.toString());
                            sessionManager.setAdmobInit(true);
                        }
                    });

                }
            }
        }
    }

    public void ChartBoostInit(Activity activity) {
        if (!sessionManager.getChartBoostInit()) {
            Log.e("VAdEnhancerRegister", "init chartboost " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init chartboost keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;
            String app_signature = "null";


            if (keyValueTableHashMap.containsKey("chartboost_test_mode") && keyValueTableHashMap.containsKey("chartboost_test_app_id") && keyValueTableHashMap.containsKey("chartboost_app_signature")) {
                if (Objects.equals(keyValueTableHashMap.get("chartboost_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("chartboost_test_app_id");
                    test_mode = true;
                    app_signature = keyValueTableHashMap.get("chartboost_app_signature");
                } else {
                    id = keyValueTableHashMap.get("chartboost_app_id");
                    app_signature = keyValueTableHashMap.get("chartboost_app_signature");
                }
            } else if (keyValueTableHashMap.containsKey("chartboost_app_id") && keyValueTableHashMap.containsKey("chartboost_app_signature")) {
                id = keyValueTableHashMap.get("chartboost_app_id");
                app_signature = keyValueTableHashMap.get("chartboost_app_signature");
            }

            if (keyValueTableHashMap.containsKey("chartboost_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("chartboost_activated"), "true")) {

                    assert id != null;
                    assert app_signature != null;
                    Chartboost.startWithAppId(activity,
                            id,
                            app_signature,
                            startError -> {
                                if (startError == null) {
                                    Log.e("VAdEnhancerRegister", "init chartboost success");
                                    sessionManager.setChartBoostInit(true);
                                } else {
                                    Log.e("VAdEnhancerRegister", "init chartboost success" + startError.toString());
                                }
                            }
                    );

                }
            }
        }
    }

    public void AppODealInit(Activity activity) {
        if (!sessionManager.getAppODealInit()) {
            Log.e("VAdEnhancerRegister", "init appodeal " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init appodeal keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;


            if (keyValueTableHashMap.containsKey("appodeal_test_mode") && keyValueTableHashMap.containsKey("appodeal_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("appodeal_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("appodeal_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("appodeal_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("appodeal_app_id")) {
                id = keyValueTableHashMap.get("appodeal_app_id");
            }

            if (keyValueTableHashMap.containsKey("appodeal_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("appodeal_activated"), "true")) {

                    assert id != null;
                    Appodeal.initialize(activity, id, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER | Appodeal.MREC | Appodeal.NATIVE, new ApdInitializationCallback() {
                        @Override
                        public void onInitializationFinished(@Nullable List<ApdInitializationError> list) {
                            Log.e("VAdEnhancerRegister", "init appodeal" + list);
                            sessionManager.setAppODealInit(true);
                        }
                    });

                    Appodeal.setAutoCache(Appodeal.BANNER, true);
                    Appodeal.setAutoCache(Appodeal.MREC, true);
                    Appodeal.setAutoCache(Appodeal.NATIVE, true);
                    Appodeal.setAutoCache(Appodeal.INTERSTITIAL, true);
                    Appodeal.setAutoCache(Appodeal.REWARDED_VIDEO, true);
                    Appodeal.cache(activity, Appodeal.BANNER);
                    Appodeal.cache(activity, Appodeal.MREC);
                    Appodeal.cache(activity, Appodeal.NATIVE);
                    Appodeal.cache(activity, Appodeal.INTERSTITIAL);
                    Appodeal.cache(activity, Appodeal.REWARDED_VIDEO);

                    Appodeal.setTesting(test_mode);

                }
            }

        }
    }

    public void MaxInit(Activity activity) {
        if (!sessionManager.getMaxInit()) {
            Log.e("VAdEnhancerRegister", "init applovin " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init applovin keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("applovin_test_mode") && keyValueTableHashMap.containsKey("applovin_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("applovin_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("applovin_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("applovin_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("applovin_app_id")) {
                id = keyValueTableHashMap.get("applovin_app_id");
            }

            if (keyValueTableHashMap.containsKey("applovin_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("applovin_activated"), "true")) {

                    AppLovinSdk.getInstance(activity).setMediationProvider("max");
                    AppLovinSdk.initializeSdk(activity, new AppLovinSdk.SdkInitializationListener() {
                        @Override
                        public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                            Log.e("VAdEnhancerRegister", "init applovin success");
                            sessionManager.setMaxInit(true);
                        }
                    });

                }
            }

        }
    }

    public void InMobiInit(Activity activity) {
        if (!sessionManager.getInMobiInit()) {
            Log.e("VAdEnhancerRegister", "init inmobi " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init inmobi keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("inmobi_test_mode") && keyValueTableHashMap.containsKey("inmobi_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("inmobi_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("inmobi_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("inmobi_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("inmobi_app_id")) {
                id = keyValueTableHashMap.get("inmobi_app_id");
            }

            if (keyValueTableHashMap.containsKey("inmobi_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("inmobi_activated"), "true")) {

                    JSONObject consentObject = new JSONObject();
                    try {
                        consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
                        consentObject.put("gdpr", "0");
                        consentObject.put(InMobiSdk.IM_GDPR_CONSENT_IAB, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("VAdEnhancerRegister", "init inmobi error " + e);
                    }
                    InMobiSdk.init(activity, String.valueOf(id), null, new SdkInitializationListener() {
                        @Override
                        public void onInitializationComplete(@Nullable Error error) {
                            if (error != null) {
                                Log.e("VAdEnhancerRegister", "init inmobi" + error.getMessage());
                                sessionManager.setInMobiInit(true);
                            } else {
                                Log.e("VAdEnhancerRegister", "init inmobi success");
                            }
                        }
                    });

                }
            }

        }
    }

    public void AppBrainInit(Activity activity) {
        if (!sessionManager.getAppBrainInit()) {
            Log.e("VAdEnhancerRegister", "init appbrain " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init appbrain keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;
            String test_device_id = "null";

            if (keyValueTableHashMap.containsKey("appbrain_test_mode") && keyValueTableHashMap.containsKey("appbrain_test_app_id") && keyValueTableHashMap.containsKey("appbrain_test_device_id")) {
                if (Objects.equals(keyValueTableHashMap.get("appbrain_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("appbrain_test_app_id");
                    test_mode = true;
                    test_device_id = keyValueTableHashMap.get("appbrain_test_device_id");
                } else {
                    id = keyValueTableHashMap.get("appbrain_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("appbrain_app_id")) {
                id = keyValueTableHashMap.get("appbrain_app_id");
            }

            if (keyValueTableHashMap.containsKey("appbrain_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("appbrain_activated"), "true")) {

                    AppBrain.init(activity);
                    if (test_mode) {
                        AppBrain.addTestDevice(test_device_id);
                    }
                    sessionManager.setAppBrainInit(true);

                }
            }
        }
    }

    public void KidozInit(Activity activity) {
        if (!sessionManager.getKidozInit()) {
            Log.e("VAdEnhancerRegister", "init kidoz " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init kidoz keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;
            String security_token = "null";

            if (keyValueTableHashMap.containsKey("kidoz_test_mode") && keyValueTableHashMap.containsKey("kidoz_test_app_id") && keyValueTableHashMap.containsKey("kidoz_security_token")) {
                if (Objects.equals(keyValueTableHashMap.get("kidoz_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("kidoz_test_app_id");
                    test_mode = true;
                    security_token = keyValueTableHashMap.get("kidoz_security_token");
                } else {
                    id = keyValueTableHashMap.get("kidoz_app_id");
                    security_token = keyValueTableHashMap.get("kidoz_security_token");
                }
            } else if (keyValueTableHashMap.containsKey("kidoz_app_id") && keyValueTableHashMap.containsKey("kidoz_security_token")) {
                id = keyValueTableHashMap.get("kidoz_app_id");
                security_token = keyValueTableHashMap.get("kidoz_security_token");
            }

            if (keyValueTableHashMap.containsKey("kidoz_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("kidoz_activated"), "true")) {

                    KidozSDK.setSDKListener(new SDKEventListener() {
                        @Override
                        public void onInitSuccess() {
                            Log.e("VAdEnhancerRegister", "init kidoz success");
                            sessionManager.setKidozInit(true);
                        }

                        @Override
                        public void onInitError(String error) {
                            Log.e("VAdEnhancerRegister", "init kidoz error" + error);
                        }
                    });

                    assert id != null;
                    assert security_token != null;
                    KidozSDK.initialize(activity, id, security_token);

                }
            }

        }
    }

    public void AppNextInit(Activity activity) {
        if (!sessionManager.getAppNextInit()) {
            Log.e("VAdEnhancerRegister", "init appnext " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init appnext keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("appnext_test_mode") && keyValueTableHashMap.containsKey("appnext_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("appnext_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("appnext_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("appnext_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("appnext_app_id")) {
                id = keyValueTableHashMap.get("appnext_app_id");
            }

            if (keyValueTableHashMap.containsKey("appnext_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("appnext_activated"), "true")) {
                    Appnext.init(activity);
                    sessionManager.setAppNextInit(true);
                }
            }

        }
    }

    public void GreedyGamesInit(Activity activity) {
        if (!sessionManager.getGreedyGamesInit()) {
            Log.e("VAdEnhancerRegister", "init greedygames " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init greedygames keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("greedygames_test_mode") && keyValueTableHashMap.containsKey("greedygames_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("greedygames_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("greedygames_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("greedygames_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("greedygames_app_id")) {
                id = keyValueTableHashMap.get("greedygames_app_id");
            }

            if (keyValueTableHashMap.containsKey("greedygames_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("greedygames_activated"), "true")) {

                    assert id != null;
                    AppConfig appConfig = new AppConfig.Builder(activity)
                            .withAppId(id)
                            .enableDebug(test_mode)
                            .enableCrashReporting(true)
                            .enableDNTLocation(true)
                            .build();

                    GreedyGameAds.initWith(appConfig, new GreedyGameAdsEventsListener() {
                        @Override
                        public void onInitSuccess() {
                            Log.e("VAdEnhancerRegister", "greedygames init success");
                            sessionManager.setGreedyGamesInit(true);
                        }

                        @Override
                        public void onInitFailed(@NonNull InitErrors initErrors) {
                            Log.e("VAdEnhancerRegister", "greedygames init success");
                        }
                    });

                }
            }


        }
    }

    public void GoogleAdManagerInit(Activity activity) {
        if (!sessionManager.getGoogleAdManagerInit()) {
            Log.e("VAdEnhancerRegister", "init GoogleAdManagerInit " + activity.getLocalClassName());

            if (keyValueTableHashMap.isEmpty()) {
                Log.e("VAdEnhancerRegister", "init googleadmanager keyValueTableHashMap Empty");
                return;
            }

            String id = "null";
            boolean test_mode = false;

            if (keyValueTableHashMap.containsKey("gam_test_mode") && keyValueTableHashMap.containsKey("gam_test_app_id")) {
                if (Objects.equals(keyValueTableHashMap.get("gam_test_mode"), "true")) {
                    id = keyValueTableHashMap.get("gam_test_app_id");
                    test_mode = true;
                } else {
                    id = keyValueTableHashMap.get("gam_app_id");
                }
            } else if (keyValueTableHashMap.containsKey("gam_app_id")) {
                id = keyValueTableHashMap.get("gam_app_id");
            }

            if (keyValueTableHashMap.containsKey("gam_activated")) {
                if (Objects.equals(keyValueTableHashMap.get("gam_activated"), "true")) {

                    MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                            Log.e("VAdEnhancerRegister", "GoogleAdManagerInit init success");
                            sessionManager.setGoogleAdManagerInit(true);
                        }
                    });

                }
            }

        }
    }


    //firebase logs
    public void logFirebase(String value, String type, String action, String source, String
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
