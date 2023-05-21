package com.example.readbook.models

import com.example.readbook.R
import com.example.readbook.repository.UserRepository

class AuthUser() {
    var id = 0
    var nickname: String = "Гость"
    var firstName: String = ""
    var middleName: String = ""
    var lastName: String = ""
    var photo: Int = R.drawable.avatar_female
    var mail: String = ""
    var password: String = ""

    fun auth(mail: String, password: String): AuthUser {
        val user = UserRepository().getUserByMail(mail = mail)
        if(user != null) {
            return if (user.password == password) {
                this.id = user.id
                this.nickname = user.nickname
                this.firstName = user.firstName
                this.middleName = user.middleName
                this.lastName = user.lastName
                this.photo = user.photo
                this.mail = user.mail
                this.password = user.password
                this
            } else
                AuthUser()
        }
        else
            return AuthUser()
    }
}