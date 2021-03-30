
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterGeocode {

  static Future<String> latlng(double a, double b) async {
    try {
      final String result = await const MethodChannel('flutter_geocode').invokeMethod(
          'latlng', <String, dynamic>{"lat": a, "lng": b});
      return result;
    } on PlatformException catch (e) {
      return "Failed : '${e.message}'.";
    }
  }


  static Future<String> apikey(String a) async {
    try {
      final String result = await const MethodChannel('flutter_geocode').invokeMethod(
          'apikey', a);
      return result;
    } on PlatformException catch (e) {
      return "Failed : '${e.message}'.";
    }
  }
}
