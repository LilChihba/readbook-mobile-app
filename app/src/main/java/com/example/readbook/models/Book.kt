package com.example.readbook.models

import com.example.readbook.models.dto.book.AuthorDto
import com.example.readbook.models.dto.book.DiscountDto
import com.example.readbook.models.dto.book.GenreDto
import com.example.readbook.models.dto.book.TranslationOriginalDto
import com.example.readbook.models.dto.book.TranslatorDto
import java.math.BigDecimal
import java.util.UUID


data class Book (
    val uid: UUID,
    val language: String,
    val isbn: String,
    val title: String,
    val about: String,
    val authors: Set<AuthorDto>,
    val translators: Set<TranslatorDto>,
    val translationOf: TranslationOriginalDto,
    val edition: String,
    val genres: Set<GenreDto>,
    val publicationDate: String,
    val publicationPlace: String,
    val publisher: String,
    val numberOfPages: Int,
    val score: Double,
    val originalPriceRub: BigDecimal,
    val salePrice: BigDecimal,
    val discounts: Set<DiscountDto>
) {
    fun getAuthorsString(): String {
        val authorNames: MutableList<String?> = ArrayList()
        for ((name) in authors) {
            authorNames.add(name)
        }
        return java.lang.String.join(", ", authorNames)
    }
}