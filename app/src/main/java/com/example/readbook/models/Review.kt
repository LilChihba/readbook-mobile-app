package com.example.readbook.models

import com.example.readbook.models.dto.book.UserDto
import java.util.UUID

data class Review(
    val id: UUID,
    val author: UserDto,
    val score: Float,
    val message: String,
    val created: String
)