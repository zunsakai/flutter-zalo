import 'flutter_zalo_method_channel.dart';

/// The interface that platform-specific implementations of `flutter_zalo` must extend.
abstract class FlutterZaloAPI {
  /// Constructs a FlutterZaloAPI.
  FlutterZaloAPI();

  /// The default instance of [FlutterZaloAPI] to use.
  static FlutterZaloAPI instance = MethodChannelFlutterZalo();

  /// Initializes the plugin platform implementation.
  Future<void> init() {
    throw UnimplementedError('init() has not been implemented.');
  }

  /// Initiates the login flow on the native platform.
  Future<bool?> logIn() {
    throw UnimplementedError('logIn() has not been implemented.');
  }

  /// Validates the current access token on the native platform.
  Future<bool?> isAccessTokenValid() {
    throw UnimplementedError('isAccessTokenValid() has not been implemented.');
  }

  /// Retrieves the access token from the native platform.
  Future<String?> getAccessToken() {
    throw UnimplementedError('getAccessToken() has not been implemented.');
  }

  /// Requests the native platform to refresh the access token.
  Future<bool?> refreshAccessToken() {
    throw UnimplementedError('refreshAccessToken() has not been implemented.');
  }

  /// Retrieves the user profile from the native platform.
  Future<Map<String, dynamic>?> getProfile() {
    throw UnimplementedError('getProfile() has not been implemented.');
  }

  /// Logs out the user on the native platform.
  Future<bool?> logOut() {
    throw UnimplementedError('logOut() has not been implemented.');
  }
}
