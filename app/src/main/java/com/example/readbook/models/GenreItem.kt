package com.example.readbook.models

import com.example.readbook.R

sealed class GenreItem(val title: String, val iconId: Int, val route: String) {
    object Genre1: GenreItem("Боевики", R.drawable.genre1, "genre1")
    object Genre2: GenreItem("Зарубежная литература", R.drawable.genre2, "genre2")
    object Genre3: GenreItem("Классика", R.drawable.book1, "genre3")
    object Genre4: GenreItem("Приключения", R.drawable.genre, "genre4")
    object Genre5: GenreItem("Ужасы", R.drawable.genre3, "genre5")
    object Genre6: GenreItem("Фантастика", R.drawable.genre4, "genre6")
    object Genre7: GenreItem("Фэнтези", R.drawable.genre5, "genre7")
    object Genre8: GenreItem("Юмор", R.drawable.genre6, "genre8")
}