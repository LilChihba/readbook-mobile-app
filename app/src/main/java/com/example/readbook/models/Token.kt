package com.example.readbook.models

data class Token (
    var accessToken: String = "",
    var accessTokenExpiresIn: Int = 0,
    var refreshToken: String = "",
    var refreshTokenExpiresIn: Int = 0
)