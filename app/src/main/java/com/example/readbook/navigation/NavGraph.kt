package com.example.readbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.readbook.pages.AuthPage
import com.example.readbook.pages.BookPage
import com.example.readbook.pages.FavoritePage
import com.example.readbook.pages.ForgotPassPage
import com.example.readbook.pages.ForgotPassPage_ChangePass
import com.example.readbook.pages.ForgotPassPage_Code
import com.example.readbook.pages.HomePage
import com.example.readbook.pages.ProfilePage
import com.example.readbook.pages.RegPage
import com.example.readbook.pages.SearchPage
import com.example.readbook.pages.SettingsPage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.homePage) {
        composable(Route.homePage){
            HomePage(
                navigateToBook = { navController.navigate(Route.bookPage) }
            )
        }

        composable(Route.mybookPage){
            FavoritePage()
        }

        composable(Route.searchPage){
            SearchPage()
        }

        composable(route = Route.profilePage) {
            ProfilePage(
                navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                navigateToAuthPage = { navController.navigate(Route.authPage) }
            )
        }

        composable(route = Route.settingsPage) {
            SettingsPage(
                navigateToAuthPage = { navController.navigate(Route.authPage) },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Route.authPage) {
            AuthPage(
                navigateToRegPage = { navController.navigate(Route.regPage) },
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navigateToForgotPassPage = { navController.navigate(Route.forgotPassPage) }
            )
        }

        composable(route = Route.regPage) {
            RegPage(
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) }
            )
        }

        composable(route = Route.forgotPassPage) {
            ForgotPassPage(
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navigateToCodePage = { navController.navigate(Route.forgotPassPage_Code)}
            )
        }

        composable(route = Route.forgotPassPage_Code) {
            ForgotPassPage_Code(
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navigateToChangePassPage = { navController.navigate(Route.forgotPassPage_ChangePass)}
            )
        }

        composable(route = Route.forgotPassPage_ChangePass) {
            ForgotPassPage_ChangePass(
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navigateToAuthPage = { navController.navigate(Route.authPage)}
            )
        }

        composable(route = Route.bookPage) {
            BookPage(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}