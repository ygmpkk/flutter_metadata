import 'dart:async';

import 'package:flutter/services.dart';

class FlutterMetadata {
  static const MethodChannel _channel = const MethodChannel('flutter_metadata');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> getMetadata(String name) async {
    final String channel = await _channel.invokeMethod('getMetadata', {
      'name': name,
    });

    return channel;
  }
}
