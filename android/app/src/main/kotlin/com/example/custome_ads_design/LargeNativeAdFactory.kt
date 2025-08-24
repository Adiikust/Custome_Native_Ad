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

class LargeNativeAdFactory(private val context: Context) :
    GoogleMobileAdsPlugin.NativeAdFactory {

    override fun createNativeAd(
        nativeAd: NativeAd,
        customOptions: MutableMap<String, Any>?
    ): NativeAdView {
        val adView = LayoutInflater.from(context)
            .inflate(R.layout.native_large_ad, null) as NativeAdView

        // Bind views
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

        // Set text content
        headlineView.text = nativeAd.headline
        bodyView.text = nativeAd.body ?: ""
        ctaView.text = nativeAd.callToAction ?: "Learn more"

        // Set app icon if available
        nativeAd.icon?.let {
            iconView.setImageDrawable(it.drawable)
            iconView.visibility = View.VISIBLE
        } ?: run { iconView.visibility = View.GONE }

        // Handle rating if available
        if (nativeAd.starRating != null && nativeAd.starRating!! > 0) {
            ratingBar.rating = nativeAd.starRating!!.toFloat()
            ratingBar.visibility = View.VISIBLE
        } else {
            ratingBar.visibility = View.GONE
        }

        // Always show the "Ad" tag
        adAttributeView.visibility = View.VISIBLE

        // Finalize
        adView.setNativeAd(nativeAd)
        return adView
    }
}
