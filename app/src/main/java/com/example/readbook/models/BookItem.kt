package com.example.readbook.models

import com.example.readbook.R

sealed class BookItem(var title: String, var author: String, var image: Int, var rate: Float) {
    object Book1: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 5.0f)
    object Book2: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 4.0f)
    object Book3: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 3.0f)
    object Book4: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 2.0f)
    object Book5: BookItem("Ведьмак", "Анджей Сапковский", R.drawable.book, 1.0f)
}

val listNewBooks = listOf(
    BookItem.Book1,
    BookItem.Book2,
    BookItem.Book3,
    BookItem.Book4,
    BookItem.Book5,
)