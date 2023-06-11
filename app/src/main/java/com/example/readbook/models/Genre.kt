package com.example.readbook.models

import com.example.readbook.R

class Genre(
    val id: Int = 0,
    val title: String = "",
    val iconId: Int = 0
) {
    fun getAll(): List<Genre> {
        return listOf(
            Genre(
                id = 1,
                title = "Боевики",
                iconId = R.drawable.genre1
            ),
            Genre(
                id = 2,
                title = "Зарубежная литература",
                iconId = R.drawable.genre2
            ),
            Genre(
                id = 3,
                title = "Классика",
                iconId = R.drawable.book1
            ),
            Genre(
                id = 4,
                title = "Приключения",
                iconId = R.drawable.genre
            ),
            Genre(
                id = 5,
                title = "Ужасы",
                iconId = R.drawable.genre3
            ),
            Genre(
                id = 6,
                title = "Фантастика",
                iconId = R.drawable.genre4
            ),
            Genre(
                id = 7,
                title = "Фэнтези",
                iconId = R.drawable.genre5
            ),
            Genre(
                id = 8,
                title = "Юмор",
                iconId = R.drawable.genre6
            )
        )
    }
}