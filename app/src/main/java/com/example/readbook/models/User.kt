package com.example.readbook.models

import android.graphics.Bitmap
import java.io.IOException

data class User(
    var username: String = "Гость",
    var firstName: String = "",
    var secondName: String = "",
    var lastName: String = "",
    var email: String = "",
    var avatar: Bitmap? = null
) {
    fun delete() {
        username = "Гость"
        firstName = ""
        secondName = ""
        lastName = ""
        email = ""
        avatar = null
    }

    fun collectData(token: Token): User? {
        return try {
            val userJSON = ApiClient().getMe(token) as User
            val user = User()
            with(user) {
                username = userJSON.username
                firstName = userJSON.firstName
                secondName = userJSON.secondName
                lastName = userJSON.lastName
                avatar = ApiClient().getMeAvatar(user.username)
                email = ApiClient().getMeEmail(user.username, token)!!.email
            }
            user
        } catch (e: IOException) {
            null
        }
    }

    fun copy(userJSON: User, token: Token, apiClient: ApiClient) {
        username = userJSON.username
        firstName = userJSON.firstName
        secondName = userJSON.secondName
        lastName = userJSON.lastName
        avatar = apiClient.getMeAvatar(this.username)
        email = apiClient.getMeEmail(this.username, token)!!.email
    }
}