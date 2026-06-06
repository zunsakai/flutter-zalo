package com.zunsakai.flutter_zalo.flutter_zalo

import com.zunsakai.flutter_zalo.flutter_zalo.data.UserData
import kotlin.test.assertEquals
import kotlin.test.Test
import org.json.JSONObject

/*
 * This demonstrates a simple unit test of the Kotlin portion of this plugin's implementation.
 *
 * Once you have built the plugin's example app, you can run these tests from the command
 * line by running `./gradlew testDebugUnitTest` in the `example/android/` directory, or
 * you can run them directly from IDEs that support JUnit such as Android Studio.
 */

internal class FlutterZaloAPIPluginTest {
  @Test
  fun userData_fromJson_handlesMissingPicture() {
    val userData = UserData()

    userData.fromJson(JSONObject("""{"id":"123","name":"Zalo User"}"""))

    assertEquals("123", userData.getId())
    assertEquals("Zalo User", userData.getName())
    assertEquals("", userData.getPictureUrl())
  }
}
