package com.example.readbook.models

import com.example.readbook.R
import com.example.readbook.repository.UserRepository

class AuthUser {
    var id = 0
    var nickname: String = "Гость"
    var firstName: String = ""
    var middleName: String = ""
    var lastName: String = ""
    var photo: Int = R.drawable.avatar_male
    var mail: String = ""
    var password: String = ""
    var auth: Boolean = false

    fun auth(listUsers: MutableList<User>, mail: String, password: String): AuthUser {
        val user = UserRepository().getUserByMail(listUsers = listUsers, mail = mail)
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
                this.auth = true
                this
            } else {
                this.auth = false
                AuthUser()
            }
        }
        else {
            this.auth = false
            return AuthUser()
        }
    }

    fun exit(): AuthUser {
        this.id = 0
        this.nickname = "Гость"
        this.firstName = ""
        this.middleName = ""
        this.lastName = ""
        this.photo = R.drawable.avatar_male
        this.mail = ""
        this.password = ""
        this.auth = false
        return this
    }
}