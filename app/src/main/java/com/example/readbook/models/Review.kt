package com.example.readbook.models

data class Review(
    val id: Int,
    val nickname: String,
    val rating: Int,
    val date: String,
    val content: String
)