package com.example.readbook.models

import com.example.readbook.models.dto.book.AuthorDto
import com.example.readbook.models.dto.book.GenreDto
import java.math.BigDecimal
import java.util.UUID

data class Book (
    val uuid: UUID,
    val language: String,
    val isbn: String,
    val title: String,
    val about: String,
    val authors: Set<AuthorDto>,
    val edition: String,
    val genres: Set<GenreDto>,
    val publicationDate: String,
    val publicationPlace: String,
    val publisher: String,
    val numberOfPages: Int,
    val score: Double,
    val priceRub: BigDecimal,
    var isBuyed: Boolean = false,
    var reviews: List<Review>? = null
) {
    fun getAuthorsString(): String {
        val authorNames: MutableList<String?> = ArrayList()
        for ((name) in authors) {
            authorNames.add(name)
        }
        return java.lang.String.join(", ", authorNames)
    }
}