package com.example.readbook.models

import com.example.readbook.R
import com.example.readbook.navigation.Route

sealed class BottomNavItem(val title: String, val iconId: Int, val route: String) {
    object Home: BottomNavItem("Главная", R.drawable.home, Route.homePage)
    object Favorite: BottomNavItem("Мои книги", R.drawable.library_books, Route.libraryPage)
    object Search: BottomNavItem("Поиск", R.drawable.search, Route.searchPage)
    object Profile: BottomNavItem("Профиль", R.drawable.profile, Route.profilePage)
}
