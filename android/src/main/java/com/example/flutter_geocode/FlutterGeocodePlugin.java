package com.example.flutter_geocode;

import androidx.annotation.NonNull;


import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.os.AsyncTask;
import java.lang.Object;
import java.util.Locale;
import java.io.IOException;
import java.util.List;

import android.location.Geocoder;
import android.location.Address;
import android.content.Context;

import android.app.Fragment;
import android.app.Activity;
import java.lang.reflect.Method;
/** FlutterGeocodePlugin */
public class FlutterGeocodePlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_geocode");
    channel.setMethodCallHandler(this);
  }

  private static Context CONTEXT;

  public static Context getApplicationContext() {
    if (CONTEXT != null) {
      return CONTEXT;
    } else {
      try {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Method method = activityThreadClass.getMethod("currentApplication");
        CONTEXT = (Context) method.invoke(null);
        return CONTEXT;
      } catch (Exception e) {
        return null;
      }
    }
  }

  @Override
  public void onMethodCall(final MethodCall call, final Result result) {
    if (call.method.equals("latlng")) {
      final double lat = call.argument("lat");
      final double lng = call.argument("lng");
      final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
      new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... params) {
          try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
//            add = add + " " + obj.getCountryName();
//            add = add + " " + obj.getCountryCode();
//            add = add + " " + obj.getAdminArea();
//            add = add + " " + obj.getPostalCode();
//            add = add + " " + obj.getSubAdminArea();
//            add = add + " " + obj.getLocality();
//            add = add + " " + obj.getSubThoroughfare();
            return add;

          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
          }
        }

        @Override
        protected void onPostExecute(String result1) {
          result.success(result1);
        }
      }.execute();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
