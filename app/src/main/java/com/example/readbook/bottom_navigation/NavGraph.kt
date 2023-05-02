package com.example.readbook

import FavoritePage
import SearchPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.readbook.pages.HomePage
import com.example.readbook.pages.ProfileNavHost

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home_page") {
        composable("home_page"){
            HomePage()
        }

        composable("favorite_page"){
            FavoritePage()
        }

        composable("search_page"){
            SearchPage()
        }

        composable("profile_page"){
            ProfileNavHost()
        }
    }
}