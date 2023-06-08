package com.example.readbook.models

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
data class Token (
    var accessToken: String = "",
    var accessTokenExpiresIn: Int = 0,
    var refreshToken: String = "",
    var refreshTokenExpiresIn: Int = 0,
    var date: Instant = Instant.ofEpochMilli(0)
) {
    fun delete() {
        accessToken = ""
        accessTokenExpiresIn = 0
        refreshToken = ""
        refreshTokenExpiresIn = 0
        date = Instant.ofEpochMilli(0)
    }

    fun isAccessTokenExpired(time: Instant): Boolean {
        return date.plusSeconds(accessTokenExpiresIn.toLong()) < time
    }

    fun isRefreshTokenExpired(time: Instant): Boolean {
        return date.plusSeconds(refreshTokenExpiresIn.toLong()) < time
    }

    fun copy(tokenJSON: Token) {
        accessToken = tokenJSON.accessToken
        accessTokenExpiresIn = tokenJSON.accessTokenExpiresIn
        refreshToken = tokenJSON.refreshToken
        refreshTokenExpiresIn = tokenJSON.refreshTokenExpiresIn
        date = Instant.now()
    }

    fun save(preferences: SharedPreferences?) {
        with(preferences!!.edit()) {
            putString("accessToken", accessToken)
            putInt("accessTokenExpiresIn", accessTokenExpiresIn)
            putString("refreshToken", refreshToken)
            putInt("refreshTokenExpiresIn", refreshTokenExpiresIn)
            putLong("date", date.toEpochMilli())
            apply()
        }
    }
}