package com.zunsakai.flutter_zalo.flutter_zalo.data

import android.content.Context

class AppStorage(context: Context) : Storage(context) {
    private val APP_ACCESS_TOKEN = "APP_ACCESS_TOKEN"
    private val APP_EXPIRES_IN = "APP_EXPIRES_IN"
    private val APP_REFRESH_TOKEN = "APP_REFRESH_TOKEN"
    private val APP_REFRESH_TOKEN_EXPIRES_IN = "APP_REFRESH_TOKEN_EXPIRES_IN"

    companion object {
        @Volatile
        private var INSTANCE: AppStorage? = null

        fun getInstance(context: Context): AppStorage = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppStorage(context).also { INSTANCE = it }
        }
    }

    fun getAccessToken(): String? {
        return getString(APP_ACCESS_TOKEN)
    }

    fun setAccessToken(token: String?) {
        setString(APP_ACCESS_TOKEN, token)
    }

    fun getExpiresIn(): Long {
        return getLong(APP_EXPIRES_IN)
    }

    fun setExpiresIn(expiresIn: Long) {
        setLong(APP_EXPIRES_IN, expiresIn)
    }

    fun getRefreshToken(): String? {
        return getString(APP_REFRESH_TOKEN)
    }

    fun setRefreshToken(token: String?) {
        setString(APP_REFRESH_TOKEN, token)
    }

    fun getRefreshTokenExpiresIn(): Long {
        return getLong(APP_REFRESH_TOKEN_EXPIRES_IN)
    }

    fun setRefreshTokenExpiresIn(expiresIn: Long) {
        setLong(APP_REFRESH_TOKEN_EXPIRES_IN, expiresIn)
    }

    fun clearZaloData() {
        clearKeys(
            APP_ACCESS_TOKEN,
            APP_EXPIRES_IN,
            APP_REFRESH_TOKEN,
            APP_REFRESH_TOKEN_EXPIRES_IN
        )
    }
}
