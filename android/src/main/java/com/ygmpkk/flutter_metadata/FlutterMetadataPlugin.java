package com.ygmpkk.flutter_metadata;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterMetadataPlugin */
public class FlutterMetadataPlugin implements MethodCallHandler {
  private Activity activity;

  private FlutterMetadataPlugin(Activity activity) { this.activity = activity; }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel =
        new MethodChannel(registrar.messenger(), "flutter_metadata");
    channel.setMethodCallHandler(
        new FlutterMetadataPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("getMetadata")) {
      String name = (String)call.argument("name");
      Log.d("getMetadata", name);

      result.success(getPackageMetadata(activity, name));
    } else {
      result.notImplemented();
    }
  }

  private static String getPackageMetadata(Context context, String name) {
    try {
      ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
          context.getPackageName(), PackageManager.GET_META_DATA);
      if (appInfo.metaData != null) {
        return appInfo.metaData.getString(name);
      }
    } catch (PackageManager.NameNotFoundException e) {
    }

    return null;
  }
}
