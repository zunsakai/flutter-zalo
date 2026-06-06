package com.zunsakai.flutter_zalo.flutter_zalo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

open class Storage (context: Context){
    private var localPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    protected fun clearKeys(vararg keys: String) {
        val edit = localPref.edit()
        keys.forEach { key -> edit.remove(key) }
        edit.apply()
    }

    protected fun getString(key: String?): String? {
        return localPref.getString(key, "")
    }

    protected fun setString(key: String?, value: String?) {
        val edit = localPref.edit()
        edit.putString(key, value)
        edit.apply()
    }

    protected fun getLong(key: String?): Long {
        return localPref.getLong(key, 0)
    }

    protected fun setLong(key: String?, value: Long) {
        val edit = localPref.edit()
        edit.putLong(key, value)
        edit.apply()
    }
}
