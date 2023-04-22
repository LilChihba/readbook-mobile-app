package com.example.readbook.bottom_navigation

import com.example.readbook.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object Home: BottomItem("Главная", R.drawable.home, "home_page")
    object Favorite: BottomItem("Избранное", R.drawable.favorite, "favorite_page")
    object Search: BottomItem("Поиск", R.drawable.search, "search_page")
    object Profile: BottomItem("Профиль", R.drawable.profile, "profile_page")
}
