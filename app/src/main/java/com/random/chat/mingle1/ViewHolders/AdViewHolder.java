package com.random.chat.mingle1.ViewHolders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

/*import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdkUtils;*/
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.random.chat.mingle1.R;
import com.v.ad.vadenhancerLibrary.VAdEnhancer;

import org.checkerframework.checker.nullness.qual.NonNull;


public class AdViewHolder extends RecyclerView.ViewHolder {
    private AdViewHolder bannerAdViewHolder;
    private LinearLayout banner_linear_layout;
    private View mrecView;
    private View nativeView;
    private VAdEnhancer vAdEnhancer;
    public AdViewHolder(@NonNull View itemView) {
        super(itemView);
        banner_linear_layout =itemView.findViewById(R.id.banner_ad_layout2);
    }

    public void showAd(AdViewHolder holder, int position, Activity context) {

        vAdEnhancer = VAdEnhancer.getInstance(context);
        vAdEnhancer.getMultipleBannerAds(context);
        vAdEnhancer.getMultipleMrecAds(context);
        bannerAdViewHolder = holder;
        if (holder.banner_linear_layout.getChildCount() == 0) {
            Log.e("BannerAdViewHolder", "child 0");

            if (mrecView == null) {
                Log.e("BannerAdViewHolder", "getting mrec");
                mrecView = vAdEnhancer.getSingleMrecAd(context);
            }


            if (nativeView == null) {
                Log.e("BannerAdViewHolder", "getting native");
                nativeView = vAdEnhancer.getSingleNativeAd(context);
            }

            if (mrecView != null) {
                Log.e("BannerAdViewHolder", "mrec available " + mrecView);
                try {
                    bannerAdViewHolder.banner_linear_layout.setVisibility(View.VISIBLE);
                    bannerAdViewHolder.banner_linear_layout.removeAllViews();
                    bannerAdViewHolder.banner_linear_layout.addView(mrecView);
                } catch (IllegalStateException e) {
                    Log.e("BannerAdViewHolder", "crashed " + mrecView + " error " + e);
                }
            } else if (nativeView != null) {
                Log.e("BannerAdViewHolder", "native available " + nativeView);
                bannerAdViewHolder.banner_linear_layout.setVisibility(View.VISIBLE);
                bannerAdViewHolder.banner_linear_layout.removeAllViews();
                bannerAdViewHolder.banner_linear_layout.addView(nativeView);
            }
        }
        else
        {
            Log.e("BannerAdViewHolder", "child not 0");
        }

    }
}
