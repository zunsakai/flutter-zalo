package com.zunsakai.flutter_zalo.flutter_zalo

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import com.zing.zalo.zalosdk.ZaloOAuthResultCode
import com.zing.zalo.zalosdk.oauth.LoginVia
import com.zing.zalo.zalosdk.oauth.OAuthCompleteListener
import com.zing.zalo.zalosdk.oauth.OauthResponse
import com.zing.zalo.zalosdk.oauth.ZaloOpenAPICallback
import com.zing.zalo.zalosdk.oauth.ZaloSDK
import com.zing.zalo.zalosdk.oauth.model.ErrorResponse
import com.zunsakai.flutter_zalo.flutter_zalo.data.AppStorage
import com.zunsakai.flutter_zalo.flutter_zalo.data.UserData
import org.json.JSONObject
import io.flutter.plugin.common.MethodChannel.Result

class ZaloAPI {
    private val LOG_TAG = ZaloAPI::class.java.simpleName
    private var mUserData: UserData? = null
    private lateinit var context: Context
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())

    fun setContext(context: Context) {
        this.context = context
    }

    fun logIn(result: Result, activity: Activity) {
        logout()
        Utilities.genNewCode()

        ZaloSDK.Instance.authenticateZaloWithAuthenType(
            activity,
            LoginVia.APP_OR_WEB,
            Utilities.code_challenge,
            object : OAuthCompleteListener() {
                override fun onAuthenError(response: ErrorResponse) {
                    super.onAuthenError(response)
                    result.success(false)
                    Log.e(LOG_TAG, "Login failed: ${response.errorCode} - ${response.errorMsg}")
                }

                override fun onGetOAuthComplete(response: OauthResponse) {
                    super.onGetOAuthComplete(response)
                    // Execute the SDK call on a background thread
                    executor.execute {
                        try {
                            ZaloSDK.Instance.getAccessTokenByOAuthCode(
                                context, response.oauthCode, Utilities.code_verifier,
                                ZaloOpenAPICallback { data ->
                                    // Post the result back to main thread
                                    mainHandler.post {
                                        val err = data.optInt("extCode", ZaloOAuthResultCode.ERR_UNKNOWN_ERROR)
                                        if (err != 0) {
                                            val msg = data.optString("errorMsg", "")
                                            Log.e(LOG_TAG, "Login failed: $msg")
                                            result.success(false)
                                        } else {
                                            saveTokenData(data)
                                            result.success(true)
                                        }
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            Log.e(LOG_TAG, "Error in getAccessTokenByOAuthCode: ${e.message}")
                            // Post error result back to main thread
                            mainHandler.post {
                                result.success(false)
                            }
                        }
                    }
                }
            }
        )
    }

    private fun saveTokenData(data: JSONObject) {
        try {
            val accessToken = data.optString("access_token")
            val expiresIn = data.optString("expires_in").toLong()
            val refreshToken = data.optString("refresh_token")
            val refreshTokenExpiresIn = data.optString("refresh_token_expires_in").toLong()

            if (TextUtils.isEmpty(accessToken)) {
                Log.e(LOG_TAG, "Access token is empty")
                return
            }

            val timeExpire = System.currentTimeMillis() + expiresIn * 1000
            val timeRefreshExpire = System.currentTimeMillis() + refreshTokenExpiresIn * 1000

            AppStorage.getInstance(context).setAccessToken(accessToken)
            AppStorage.getInstance(context).setExpiresIn(timeExpire)
            AppStorage.getInstance(context).setRefreshToken(refreshToken)
            AppStorage.getInstance(context).setRefreshTokenExpiresIn(timeRefreshExpire)

            Log.d(LOG_TAG, "Login successfull!")
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error while saving token data: ${e.message}")
        }
    }

    fun isAccessTokenValid(): Boolean {
        val accessToken = AppStorage.getInstance(context).getAccessToken()
        val timeExpire = AppStorage.getInstance(context).getExpiresIn()
        return !TextUtils.isEmpty(accessToken) && timeExpire > System.currentTimeMillis()
    }

    fun getAccessToken(): String? {
        val accessToken = AppStorage.getInstance(context).getAccessToken()
        return if (isAccessTokenValid()) accessToken else null
    }

    fun isRefreshAccessTokenValid(): Boolean {
        val refreshToken = AppStorage.getInstance(context).getRefreshToken()
        val timeExpire = AppStorage.getInstance(context).getRefreshTokenExpiresIn()
        return !TextUtils.isEmpty(refreshToken) && timeExpire > System.currentTimeMillis()
    }

    fun refreshAccessToken(result: Result) {
        try {
            if (!isRefreshAccessTokenValid()) {
                result.success(false)
                return
            }
            // Execute the SDK call on a background thread
            executor.execute {
                try {
                    ZaloSDK.Instance.getAccessTokenByRefreshToken(
                        context,
                        AppStorage.getInstance(context).getRefreshToken(),
                        ZaloOpenAPICallback { data ->
                            // Post the result back to main thread
                            mainHandler.post {
                                val err = data.optInt("extCode", ZaloOAuthResultCode.ERR_UNKNOWN_ERROR)
                                if (err != 0) {
                                    val msg = data.optString("errorMsg", "")
                                    Log.e(LOG_TAG, "Refresh token failed: $msg")
                                    result.success(false)
                                } else {
                                    saveTokenData(data)
                                    result.success(true)
                                }
                            }
                        }
                    )
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error in getAccessTokenByRefreshToken: ${e.message}")
                    // Post error result back to main thread
                    mainHandler.post {
                        result.success(false)
                    }
                }
            }
        } catch (_: Exception) {
            result.success(false)
        }
    }

    fun getProfile(callback: (UserData?) -> Unit) {
        val accessToken = getAccessToken()
        if (accessToken == null) {
            Log.e(LOG_TAG, "Access token is empty")
            callback(null)
            return
        }
        
        // Execute the SDK call on a background thread
        executor.execute {
            try {
                val fields = arrayOf("id", "picture.type(large)", "name")
                ZaloSDK.Instance.getProfile(
                    context, accessToken,
                    { data ->
                        // Post the result back to main thread
                        mainHandler.post {
                            if (data == null) {
                                callback(null)
                            } else {
                                mUserData = UserData()
                                mUserData?.fromJson(data)
                                callback(mUserData!!)
                            }
                        }
                    }, fields
                )
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error in getProfile: ${e.message}")
                // Post error result back to main thread
                mainHandler.post {
                    callback(null)
                }
            }
        }
    }

    fun logout(): Boolean {
        try {
            AppStorage.getInstance(context).clear()
            ZaloSDK.Instance.unauthenticate()
            mUserData = UserData()
            return true
        } catch (_: Exception) {
            return false
        }
    }

    fun cleanup() {
        try {
            executor.shutdown()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error shutting down executor: ${e.message}")
        }
    }
}
