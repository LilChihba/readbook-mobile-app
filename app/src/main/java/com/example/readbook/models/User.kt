package com.example.readbook.models

import android.graphics.Bitmap

data class User(
    var username: String = "Гость",
    var firstName: String = "",
    var secondName: String = "",
    var lastName: String = "",
    var email: String = "",
    var avatar: Bitmap? = null,
    var moneyRub: Int = 0
) {
    fun delete() {
        username = "Гость"
        firstName = ""
        secondName = ""
        lastName = ""
        email = ""
        avatar = null
        moneyRub = 0
    }

    fun copy(userJSON: User, token: Token, apiClient: ApiClient) {
        username = userJSON.username
        firstName = userJSON.firstName
        secondName = userJSON.secondName
        lastName = userJSON.lastName
        avatar = apiClient.getMeAvatar(this.username)
        email = apiClient.getMeEmail(token)!!.email
        moneyRub = userJSON.moneyRub
    }
}