# AGENTS.md

## Repo shape
- This is a Flutter plugin package, not a full app: public Dart API lives in `lib/`, native method-channel implementations live in `android/` and `ios/`, and the runnable/manual test harness is `example/`.
- MethodChannel name is `flutter_zalo`; keep Dart method names in sync with Android `FlutterZaloPlugin.kt` and iOS `FlutterZaloPlugin.swift` (`init`, `logIn`, `isAccessTokenValid`, `getAccessToken`, `refreshAccessToken`, `getProfile`, `logOut`).
- The plugin targets Zalo SDK V4. Android uses `me.zalo:sdk-core/auth/openapi:+` from the GitLab Maven repo configured in `android/build.gradle`; iOS depends on CocoaPods `ZaloSDK`.

## Commands
- Install root package deps: `flutter pub get`
- Analyze plugin Dart code from repo root: `flutter analyze`
- Format Dart before committing Dart changes: `dart format lib example/lib`
- Example app deps are separate: `cd example && flutter pub get`
- Run the example manually: `cd example && flutter run`
- Android native unit tests, if touched: run from `example/android` with `./gradlew testDebugUnitTest` (the existing generated test is stale and references `getPlatformVersion`, which the plugin no longer implements).
- iOS pod validation hint from podspec: run `pod lib lint flutter_zalo.podspec` from `ios/` before publishing iOS pod changes.

## Platform integration gotchas
- Android consumers must use `android:name=".MainApplication"`, add `com.zing.zalo.zalosdk.appID` metadata pointing at `@string/appID`, call `ZaloSDKApplication.wrap(this)` in `MainApplication.onCreate`, and forward `MainActivity.onActivityResult` to `ZaloSDK.Instance.onActivityResult(...)`.
- iOS consumers must set `ZaloAppID`, `CFBundleURLSchemes` as `zalo-[APP-ID]`, `LSApplicationQueriesSchemes` (`zalosdk`, `zaloshareext`), and forward `application(_:open:options:)` to `ZDKApplicationDelegate.sharedInstance()`.
- The example app contains a real Zalo app ID in Android `strings.xml` and iOS `Info.plist`; do not replace it with placeholders unless intentionally changing the sample configuration.
- Android `init` logs the debug hash key only when the host app is debuggable; README setup relies on this log for dashboard configuration.

## Style / compatibility
- Dart SDK constraint is `^3.5.1`; Flutter plugin minimum is `>=3.3.0`; lints come only from `package:flutter_lints/flutter.yaml`.
- Avoid adding app-level architecture (Riverpod/features/etc.) to this plugin package; keep API changes minimal and mirrored across Dart, Android, iOS, README, and the example.
- Existing native style uses Kotlin under `android/src/main/kotlin/...` and Swift 5 under `ios/Classes`; match the current simple method-channel structure rather than introducing Pigeon/codegen without an explicit migration.
