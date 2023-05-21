package com.example.readbook.models

data class User(
    val id: Int,
    var nickname: String,
    var first_name: String,
    var middle_name: String,
    var last_name: String,
    var photo: Int,
    var mail: String,
    var password: String
)