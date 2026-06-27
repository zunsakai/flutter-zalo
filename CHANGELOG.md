## 1.0.2

* Migrated Android Gradle configuration toward Flutter built-in Kotlin while keeping compatibility with AGP versions earlier than 9 (`e142a58`).

## 1.0.1

* Added fallback to WebView login flow when the Zalo app is not installed for both Android and iOS (`b5dec4e`).
* Updated README with Android Maven repository setup instructions for the Zalo SDK (`b5dec4e`).

## 1.0.0

* Pinned Android Zalo SDK dependencies to version `4.24.1101` and updated the Android build toolchain to AGP `9.0.1`, Kotlin `2.3.20`, and Java 17 (`8bc5111`, `764810b`).
* Improved Android lifecycle handling by clearing detached activities and requiring an activity only for `logIn` (`2a276f8`, `90b05a0`).
* Included Android main-thread safety fixes contributed by `laofun` (`60aedf1`). Thank you, laofun!
* Made Android token persistence failures visible to callers and limited logout cleanup to Zalo authentication data (`68f49e5`, `ce3a809`).
* Made Android profile parsing tolerant of missing optional fields (`4833b7d`).
* Raised the iOS deployment target to 13.0 and updated the example iOS project configuration (`8579994`).
* Added iOS validation for missing `ZaloAppID`, safer root view controller lookup during login, and guarded refresh token calls (`3e154aa`, `2a93cd8`, `d2549aa`).
* Namespaced iOS keychain entries and surfaced token save failures through login and refresh results (`d6647f3`, `2e3cb35`).
* Normalized iOS profile responses so missing fields return empty strings instead of optional values (`f034020`).
* Updated the Android unit test to cover profile parsing with missing picture data (`baa6149`).
* Added project guidance and local configuration files (`8bc5111`).

## 0.0.1

* Initial release.
