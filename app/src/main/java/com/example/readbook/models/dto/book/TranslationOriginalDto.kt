package com.example.readbook.models.dto.book

import java.util.Date
import java.util.UUID

data class TranslationOriginalDto (
    val uid: UUID,
    val language: String,
    val isbn: String,
    val title: String,
    val publicationDate: Date,
    val publicationPlace: String,
    val publisher: String
)