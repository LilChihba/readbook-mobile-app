package com.example.readbook.models

import com.example.readbook.R
import com.example.readbook.navigation.Route

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object Home: BottomItem("Главная", R.drawable.home, Route.homePage)
    object Favorite: BottomItem("Мои книги", R.drawable.library_books, Route.mybookPage)
    object Search: BottomItem("Поиск", R.drawable.search, Route.searchPage)
    object Profile: BottomItem("Профиль", R.drawable.profile, Route.profilePage)
}
