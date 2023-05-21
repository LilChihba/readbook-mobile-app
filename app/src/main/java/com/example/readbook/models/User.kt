package com.example.readbook.models

data class User(
    val id: Int,
    var nickname: String,
    var firstName: String,
    var middleName: String,
    var lastName: String,
    var photo: Int,
    var mail: String,
    var password: String
)