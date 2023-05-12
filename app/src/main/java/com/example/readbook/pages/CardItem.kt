package com.example.readbook.pages

import com.example.readbook.R

sealed class CardItem(val title: String, val background: Int, val books: List<BookItem>) {
    object NewBooks: CardItem("Недавно добавленные", R.drawable.new_books, listNewBooks)
    object PopularBooks: CardItem("Популярные книги", R.drawable.popular_books, listNewBooks)
    object BestBooks: CardItem("С наилучшей оценкой", R.drawable.best_books, listNewBooks)
}