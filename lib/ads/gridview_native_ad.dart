import 'dart:io';
import 'package:flutter/material.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

class GridviewNativeAd extends StatefulWidget {
  const GridviewNativeAd({super.key});

  @override
  State<GridviewNativeAd> createState() => _GridviewNativeAdState();
}

class _GridviewNativeAdState extends State<GridviewNativeAd> {
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
              ? 'ca-app-pub-3940256099942544/1044960115' // Test Native Advanced (Android)
              : '', // iOS not covered here
      factoryId: 'gridViewNative',
      request: const AdRequest(),
      listener: NativeAdListener(
        onAdLoaded: (ad) => setState(() => _isLoaded = true),
        onAdFailedToLoad: (ad, error) {
          ad.dispose();
          debugPrint('NativeAd failed: $error');
        },
      ),
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
      backgroundColor: Colors.grey[200],
      appBar: AppBar(title: const Text("GridView Example")),
      body: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 2,
          crossAxisSpacing: 0,
          mainAxisSpacing: 0,
          childAspectRatio: 174.4 / 248,
        ),
        itemCount: 10,
        itemBuilder: (context, index) {
          // Show Native Ad at index 2
          if (index == 2) {
            return _isLoaded
                ? Card(
                  color: Colors.red,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: AdWidget(ad: _nativeAd!),
                  ),
                )
                : const Center(child: CircularProgressIndicator());
          }

          // Regular Grid Card
          return Card(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
            child: Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
              ),
              child: Center(
                child: Text(
                  "Card $index",
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
