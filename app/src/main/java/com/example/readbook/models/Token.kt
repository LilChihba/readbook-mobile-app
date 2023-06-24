package com.example.readbook.models

import android.content.SharedPreferences
import android.util.Log
import java.time.Instant

data class Token (
    var accessToken: String = "",
    var accessTokenExpiresIn: Int = 0,
    var refreshToken: String = "",
    var refreshTokenExpiresIn: Int = 0,
    var date: Long = 0
) {
    fun delete() {
        accessToken = ""
        accessTokenExpiresIn = 0
        refreshToken = ""
        refreshTokenExpiresIn = 0
        date = 0
    }

    fun isAccessTokenExpired(time: Instant): Boolean {
        val result = Instant.ofEpochSecond(date).plusSeconds(accessTokenExpiresIn.toLong()) < time
        if(result)
            Log.d("Token", "Access Token expired")
        return result
    }

    fun isRefreshTokenExpired(time: Instant): Boolean {
        val result = Instant.ofEpochSecond(date).plusSeconds(refreshTokenExpiresIn.toLong()) < time
        if(result)
            Log.d("Token", "Refresh Token expired")
        return result
    }

    fun copy(tokenJSON: Token) {
        accessToken = tokenJSON.accessToken
        accessTokenExpiresIn = tokenJSON.accessTokenExpiresIn
        refreshToken = tokenJSON.refreshToken
        refreshTokenExpiresIn = tokenJSON.refreshTokenExpiresIn
        date = Instant.now().epochSecond
    }

    fun save(preferences: SharedPreferences?) {
        with(preferences!!.edit()) {
            putString("accessToken", accessToken)
            putInt("accessTokenExpiresIn", accessTokenExpiresIn)
            putString("refreshToken", refreshToken)
            putInt("refreshTokenExpiresIn", refreshTokenExpiresIn)
            putLong("date", date)
            apply()
        }
    }
}