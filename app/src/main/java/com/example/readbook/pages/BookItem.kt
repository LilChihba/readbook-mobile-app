package com.example.readbook.pages

import com.example.readbook.R

sealed class BookItem(var title: String, var author: String, var image: Int, var rate: Int) {
    object Book1: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 5)
    object Book2: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 4)
    object Book3: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 3)
    object Book4: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 2)
    object Book5: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 1)
}

val listNewBooks = listOf(
    BookItem.Book1,
    BookItem.Book2,
    BookItem.Book3,
    BookItem.Book4,
    BookItem.Book5,
)