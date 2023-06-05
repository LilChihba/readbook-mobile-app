package com.example.readbook.models

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
)