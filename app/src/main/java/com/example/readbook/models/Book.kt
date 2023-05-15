package com.example.readbook.models

data class Book (
    val id: Int,
    val isbn: String,
    val title: String,
    val author: String,
    val publisher_date: String,
    val publisher: String,
    val pages: Int,
    val rating: Float,
    val price: Int,
    val description: String,
    val image: Int
)