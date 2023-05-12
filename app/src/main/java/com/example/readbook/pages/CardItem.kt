package com.example.readbook.pages

import com.example.readbook.R

sealed class CardItem(val id: Int,val title: String, val background: Int, val books: List<BookItem>) {
    object NewBooks: CardItem(1, "Недавно добавленные", R.drawable.new_books, listNewBooks)
    object PopularBooks: CardItem(2, "Популярные книги", R.drawable.popular_books, listNewBooks)
    object BestBooks: CardItem(3, "С наилучшей оценкой", R.drawable.best_books, listNewBooks)
}