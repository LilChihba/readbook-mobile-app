package com.example.readbook.pages

import com.example.readbook.R

sealed class GenreItem(val title: String, val iconId: Int, val route: String) {
    object Genre1: GenreItem("Жанр 1", R.drawable.genre, "genre1")
    object Genre2: GenreItem("Жанр 2", R.drawable.genre, "genre2")
    object Genre3: GenreItem("Жанр 3", R.drawable.genre, "genre3")
    object Genre4: GenreItem("Жанр 4", R.drawable.genre, "genre4")
    object Genre5: GenreItem("Жанр 5", R.drawable.genre, "genre5")
    object Genre6: GenreItem("Жанр 6", R.drawable.genre, "genre6")
    object Genre7: GenreItem("Жанр 7", R.drawable.genre, "genre7")
    object Genre8: GenreItem("Жанр 8", R.drawable.genre, "genre8")
    object Genre9: GenreItem("Жанр 9", R.drawable.genre, "genre9")
    object Genre10: GenreItem("Жанр 10", R.drawable.genre, "genre10")
}