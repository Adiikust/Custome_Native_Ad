import 'dart:io';
import 'package:flutter/material.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

class NativeAdMediumCard extends StatefulWidget {
  const NativeAdMediumCard({super.key});

  @override
  State<NativeAdMediumCard> createState() => _NativeAdMediumCardState();
}

class _NativeAdMediumCardState extends State<NativeAdMediumCard> {
  NativeAd? _nativeAd;
  bool _isLoaded = false;

  @override
  void initState() {
    super.initState();
    _loadAd();
  }

  void _loadAd() {
    _nativeAd = NativeAd(
      adUnitId:
          Platform.isAndroid
              ? 'ca-app-pub-3940256099942544/2247696110' // Test Native Advanced (Android)
              : '', // iOS not covered here
      factoryId: 'nativeMedium', // <-- must match Kotlin registration
      request: const AdRequest(),
      listener: NativeAdListener(
        onAdLoaded: (ad) => setState(() => _isLoaded = true),
        onAdFailedToLoad: (ad, error) {
          ad.dispose();
          debugPrint('NativeAd failed: $error');
        },
      ),
      // Optional: pass style options that Kotlin can read
      customOptions: const {'ctaColor': '#2962FF'},
    )..load();
  }

  @override
  void dispose() {
    _nativeAd?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body:
          !_isLoaded
              ? Center(
                child: SizedBox(
                  height: 300,
                  child: Center(child: CircularProgressIndicator()),
                ),
              )
              : Center(child: Text("load native add")),

      bottomNavigationBar:
          _isLoaded
              ? Container(
                height: 170,
                color: Colors.grey,
                child: AdWidget(ad: _nativeAd!),
              )
              : SizedBox.shrink(),
    );
  }
}
