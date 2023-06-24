package com.example.readbook.models.dto.book

data class UserDto(
    val username: String,
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = ""
)