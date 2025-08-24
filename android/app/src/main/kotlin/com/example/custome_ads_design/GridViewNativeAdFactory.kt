package com.example.custome_ads_design

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.flutter.plugins.googlemobileads.GoogleMobileAdsPlugin

class GridViewNativeAdFactory(private val context: Context) :
    GoogleMobileAdsPlugin.NativeAdFactory {

    override fun createNativeAd(
        nativeAd: NativeAd,
        customOptions: MutableMap<String, Any>?
    ): NativeAdView {
        val adView = LayoutInflater.from(context)
            .inflate(R.layout.girdview_native_ad, null) as NativeAdView

        // Bind views from XML
        val headlineView = adView.findViewById<TextView>(R.id.adHeadline)
        val bodyView = adView.findViewById<TextView>(R.id.adBody)
        val ctaView = adView.findViewById<Button>(R.id.adCallToAction)
        val iconView = adView.findViewById<ImageView>(R.id.adAppIcon)
        val mediaView = adView.findViewById<MediaView>(R.id.adMediaView)
        val ratingBar = adView.findViewById<RatingBar>(R.id.appRating)
        val adAttributeView = adView.findViewById<TextView>(R.id.adAttribute)

        // Assign views to NativeAdView
        adView.headlineView = headlineView
        adView.bodyView = bodyView
        adView.callToActionView = ctaView
        adView.iconView = iconView
        adView.mediaView = mediaView

        // Fill ad content
        headlineView.text = nativeAd.headline
        bodyView.text = nativeAd.body ?: ""
        ctaView.text = nativeAd.callToAction ?: "Learn more"

        // App icon (optional)
        nativeAd.icon?.let {
            iconView.setImageDrawable(it.drawable)
            iconView.visibility = View.GONE
        } ?: run {
            iconView.visibility = View.GONE
        }

        // Star rating (optional)
        if (nativeAd.starRating != null && nativeAd.starRating!! > 0) {
            ratingBar.rating = nativeAd.starRating!!.toFloat()
            ratingBar.visibility = View.GONE
        } else {
            ratingBar.visibility = View.GONE
        }

        // Always show "Ad" tag
        adAttributeView.visibility = View.GONE

        // Finalize binding
        adView.setNativeAd(nativeAd)
        return adView
    }
}
