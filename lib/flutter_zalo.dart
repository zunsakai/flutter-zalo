/// A Flutter plugin providing integration with the Zalo SDK.
///
/// This library provides developers with the necessary tools to implement Zalo Login,
/// retrieve user profile data, and handle token lifecycles within Flutter applications.
library flutter_zalo;

import 'flutter_zalo_platform_interface.dart';

/// The main entry point for the Zalo SDK plugin.
/// 
/// Provides methods to interact with Zalo SDK functionalities
/// such as authentication, retrieving user profiles, and managing tokens.
class FlutterZalo {
  /// Creates a new instance of [FlutterZalo].
  FlutterZalo();

  /// Initializes the Zalo SDK. Must be called before any other methods.
  Future<void> init() {
    return FlutterZaloAPI.instance.init();
  }

  /// Initiates the Zalo login process.
  /// 
  /// Returns `true` if the login is successful, `false` otherwise.
  Future<bool?> logIn() {
    return FlutterZaloAPI.instance.logIn();
  }

  /// Checks if the current Zalo access token is still valid.
  /// 
  /// Returns `true` if valid, `false` otherwise.
  Future<bool?> isAccessTokenValid() {
    return FlutterZaloAPI.instance.isAccessTokenValid();
  }

  /// Retrieves the current Zalo access token.
  /// 
  /// Returns the token as a [String], or `null` if none exists.
  Future<String?> getAccessToken() {
    return FlutterZaloAPI.instance.getAccessToken();
  }

  /// Refreshes the Zalo access token.
  /// 
  /// Returns `true` if successfully refreshed, `false` otherwise.
  Future<bool?> refreshAccessToken() {
    return FlutterZaloAPI.instance.refreshAccessToken();
  }

  /// Retrieves the current user's profile information.
  /// 
  /// Returns a [Map] containing profile details, or `null` if the request fails.
  Future<Map<String, dynamic>?> getProfile() {
    return FlutterZaloAPI.instance.getProfile();
  }

  /// Logs the user out of Zalo.
  /// 
  /// Returns `true` if successful, `false` otherwise.
  Future<bool?> logOut() {
    return FlutterZaloAPI.instance.logOut();
  }
}
