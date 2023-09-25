package com.v.ad.vadenhancerLibrary;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    static SharedPreferences pref;
    static SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "3S24r$#434a223P^4*P@$T&*dftvadenhancer";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String CURRENT_ACTIVITY = "CURRENT_ACTIVITY";
    private static final String USER_ONLINE = "USER_ONLINE";
    private static final String RECHECK_TIME = "RECHECK_TIME";


    public SessionManager(Context ctx) {
        if (ctx == null) {
            return;
        }
        pref = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setUserId(String userid) {
        editor.putString(KEY_USER_ID, userid);
        editor.commit();
    }

    public String getUserID() {
        return pref.getString(KEY_USER_ID, "12345");
    }

    public void setCurrentActivity(String CurrentActivity) {
        editor.putString(CURRENT_ACTIVITY, CurrentActivity);
        editor.commit();
    }

    public String getCurrentActivity() {
        return pref.getString(CURRENT_ACTIVITY, "null");
    }

    public void setReCheckTime(long ReCheckTime) {
        editor.putLong(RECHECK_TIME, ReCheckTime);
        editor.commit();
    }

    public long getReCheckTime() {
        return pref.getLong(RECHECK_TIME, 0);
    }


    private static final String IRONSOURCEINIT = "IRONSOURCEINIT";
    private static final String GREEDYGAMESINIT = "GREEDYGAMESINIT";
    private static final String GOOGLEADMANAGERINIT = "GOOGLEADMANAGERINIT";
    private static final String UNITYINIT = "UNITYINIT";
    private static final String ADCOLONYINIT = "ADCOLONYINIT";
    private static final String METAINIT = "METAINIT";
    private static final String VUNGLEINIT = "VUNGLEINIT";
    private static final String MYTARGETINIT = "MYTARGETINIT";
    private static final String HYPRMXINIT = "HYPRMXINIT";
    private static final String PANGLEINIT = "PANGLEINIT";
    private static final String ADMOBINIT = "ADMOBINIT";
    private static final String CHARTBOOSTINIT = "CHARTBOOSTINIT";
    private static final String STARTAPPINIT = "STARTAPPINIT";
    private static final String APPODEALINIT = "APPODEALINIT";
    private static final String MAXINIT = "MAXINIT";
    private static final String INMOBIINIT = "INMOBIINIT";
    private static final String OGURYINIT = "OGURYINIT";
    private static final String APPBRAININIT = "APPBRAININIT";
    private static final String KIDOZINIT = "KIDOZINIT";
    private static final String APPNEXTINIT = "APPNEXTINIT";

    public void setIronSourceInit(boolean IronSourceInit) {
        editor.putBoolean(IRONSOURCEINIT, IronSourceInit);
        editor.commit();
    }

    public boolean getIronSourceInit() {
        return pref.getBoolean(IRONSOURCEINIT, false);
    }

    public void setGreedyGamesInit(boolean GreedyGamesInit) {
        editor.putBoolean(GREEDYGAMESINIT, GreedyGamesInit);
        editor.commit();
    }

    public boolean getGreedyGamesInit() {
        return pref.getBoolean(GREEDYGAMESINIT, false);
    }

    public void setGoogleAdManagerInit(boolean GoogleAdManagerInit) {
        editor.putBoolean(GOOGLEADMANAGERINIT, GoogleAdManagerInit);
        editor.commit();
    }

    public boolean getGoogleAdManagerInit() {
        return pref.getBoolean(GOOGLEADMANAGERINIT, false);
    }

    public void setUnityInit(boolean UnityInit) {
        editor.putBoolean(UNITYINIT, UnityInit);
        editor.commit();
    }

    public boolean getUnityInit() {
        return pref.getBoolean(UNITYINIT, false);
    }

    public void setAdcolonyInit(boolean AdcolonyInit) {
        editor.putBoolean(ADCOLONYINIT, AdcolonyInit);
        editor.commit();
    }

    public boolean getAdcolonyInit() {
        return pref.getBoolean(ADCOLONYINIT, false);
    }

    public void setMetaInit(boolean MetaInit) {
        editor.putBoolean(METAINIT, MetaInit);
        editor.commit();
    }

    public boolean getMetaInit() {
        return pref.getBoolean(METAINIT, false);
    }

    public void setVungleInit(boolean VungleInit) {
        editor.putBoolean(VUNGLEINIT, VungleInit);
        editor.commit();
    }

    public boolean getVungleInit() {
        return pref.getBoolean(VUNGLEINIT, false);
    }

    public void setMyTargetInit(boolean MyTargetInit) {
        editor.putBoolean(MYTARGETINIT, MyTargetInit);
        editor.commit();
    }

    public boolean getMyTargetInit() {
        return pref.getBoolean(MYTARGETINIT, false);
    }

    public void setHYPRMXInit(boolean HYPRMXInit) {
        editor.putBoolean(HYPRMXINIT, HYPRMXInit);
        editor.commit();
    }

    public boolean getHYPRMXInit() {
        return pref.getBoolean(HYPRMXINIT, false);
    }

    public void setPangleInit(boolean PangleInit) {
        editor.putBoolean(PANGLEINIT, PangleInit);
        editor.commit();
    }

    public boolean getPangleInit() {
        return pref.getBoolean(PANGLEINIT, false);
    }

    public void setAdmobInit(boolean AdmobInit) {
        editor.putBoolean(ADMOBINIT, AdmobInit);
        editor.commit();
    }

    public boolean getAdmobInit() {
        return pref.getBoolean(ADMOBINIT, false);
    }

    public void setChartBoostInit(boolean ChartBoostInit) {
        editor.putBoolean(CHARTBOOSTINIT, ChartBoostInit);
        editor.commit();
    }

    public boolean getChartBoostInit() {
        return pref.getBoolean(CHARTBOOSTINIT, false);
    }

    public void setStartAppInit(boolean StartAppInit) {
        editor.putBoolean(STARTAPPINIT, StartAppInit);
        editor.commit();
    }

    public boolean getStartAppInit() {
        return pref.getBoolean(STARTAPPINIT, false);
    }

    public void setAppODealInit(boolean AppODealInit) {
        editor.putBoolean(APPODEALINIT, AppODealInit);
        editor.commit();
    }

    public boolean getAppODealInit() {
        return pref.getBoolean(APPODEALINIT, false);
    }

    public void setMaxInit(boolean MaxInit) {
        editor.putBoolean(MAXINIT, MaxInit);
        editor.commit();
    }

    public boolean getMaxInit() {
        return pref.getBoolean(MAXINIT, false);
    }

    public void setInMobiInit(boolean InMobiInit) {
        editor.putBoolean(INMOBIINIT, InMobiInit);
        editor.commit();
    }

    public boolean getInMobiInit() {
        return pref.getBoolean(INMOBIINIT, false);
    }

    public void setOguryInit(boolean OguryInit) {
        editor.putBoolean(OGURYINIT, OguryInit);
        editor.commit();
    }

    public boolean getOguryInit() {
        return pref.getBoolean(OGURYINIT, false);
    }

    public void setAppBrainInit(boolean AppBrainInit) {
        editor.putBoolean(APPBRAININIT, AppBrainInit);
        editor.commit();
    }

    public boolean getAppBrainInit() {
        return pref.getBoolean(APPBRAININIT, false);
    }

    public void setKidozInit(boolean KidozInit) {
        editor.putBoolean(KIDOZINIT, KidozInit);
        editor.commit();
    }

    public boolean getKidozInit() {
        return pref.getBoolean(KIDOZINIT, false);
    }

    public void setAppNextInit(boolean AppNextInit) {
        editor.putBoolean(APPNEXTINIT, AppNextInit);
        editor.commit();
    }

    public boolean getAppNextInit() {
        return pref.getBoolean(APPNEXTINIT, false);
    }


}
