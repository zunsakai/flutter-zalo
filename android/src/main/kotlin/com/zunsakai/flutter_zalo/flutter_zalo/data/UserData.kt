package com.zunsakai.flutter_zalo.flutter_zalo.data

import org.json.JSONObject

class UserData {
    private var id: String = ""
    private var name: String = ""
    private var pictureUrl: String = ""

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getPictureUrl(): String {
        return pictureUrl
    }

    fun fromJson(json: JSONObject) {
        id = json.optString("id")
        name = json.optString("name")
        pictureUrl = json.optJSONObject("picture")
            ?.optJSONObject("data")
            ?.optString("url")
            ?: ""
    }
}
