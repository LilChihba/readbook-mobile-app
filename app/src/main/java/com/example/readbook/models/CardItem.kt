package com.example.readbook.models

import com.example.readbook.R

sealed class CardItem(val id: Int,val title: String, val background: Int) {
    object NewBooks: CardItem(1, "Недавно добавленные", R.drawable.new_books)
    object PopularBooks: CardItem(2, "Самые обсуждаемые", R.drawable.popular_books)
    object BestBooks: CardItem(3, "С наилучшей оценкой", R.drawable.best_books)
}