package com.example.custome_ads_design


import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.googlemobileads.GoogleMobileAdsPlugin

class MainActivity: FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        GoogleMobileAdsPlugin.registerNativeAdFactory(
            flutterEngine,
            "nativeLarge",
            LargeNativeAdFactory(this)
        )

        // Register Medium Native Ad
        GoogleMobileAdsPlugin.registerNativeAdFactory(
            flutterEngine,
            "nativeMedium",
            MediumNativeAdFactory(this)
        )

        GoogleMobileAdsPlugin.registerNativeAdFactory(
            flutterEngine,
            "gridViewNative",
            GridViewNativeAdFactory(this)
        )
    }
    override fun cleanUpFlutterEngine(flutterEngine: FlutterEngine) {
        GoogleMobileAdsPlugin.unregisterNativeAdFactory(flutterEngine, "nativeLarge")
        GoogleMobileAdsPlugin.unregisterNativeAdFactory(flutterEngine, "nativeMedium")
        GoogleMobileAdsPlugin.unregisterNativeAdFactory(flutterEngine, "gridViewNative")
        super.cleanUpFlutterEngine(flutterEngine)
    }
}

