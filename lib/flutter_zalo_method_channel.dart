import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_zalo_platform_interface.dart';

/// An implementation of [FlutterZaloAPI] that uses method channels.
class MethodChannelFlutterZalo extends FlutterZaloAPI {
  /// Constructs a [MethodChannelFlutterZalo].
  MethodChannelFlutterZalo();

  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_zalo');

  @override
  Future<void> init() async {
    await methodChannel.invokeMethod<void>('init');
  }

  @override
  Future<bool?> logIn() async {
    return await methodChannel.invokeMethod<bool?>('logIn');
  }

  @override
  Future<bool?> isAccessTokenValid() async {
    return await methodChannel.invokeMethod<bool>('isAccessTokenValid');
  }

  @override
  Future<String?> getAccessToken() async {
    return await methodChannel.invokeMethod<String>('getAccessToken');
  }

  @override
  Future<bool?> refreshAccessToken() async {
    return await methodChannel.invokeMethod<bool>('refreshAccessToken');
  }

  @override
  Future<Map<String, dynamic>?> getProfile() async {
    Map<Object?, Object?>? profile =
        await methodChannel.invokeMethod<Map<Object?, Object?>?>('getProfile');
    return profile?.cast<String, dynamic>();
  }

  @override
  Future<bool?> logOut() async {
    return await methodChannel.invokeMethod<bool>('logOut');
  }
}
