package com.example.readbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.readbook.pages.AuthPage
import com.example.readbook.pages.BookPage
import com.example.readbook.pages.ForgotPassPage
import com.example.readbook.pages.ForgotPassPage_ChangePass
import com.example.readbook.pages.ForgotPassPage_Code
import com.example.readbook.pages.HomePage
import com.example.readbook.pages.LibraryPage
import com.example.readbook.pages.ProfilePage
import com.example.readbook.pages.RegPage
import com.example.readbook.pages.SearchPage
import com.example.readbook.pages.SettingsPage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.homePage) {
        composable(Route.homePage){
            HomePage(
                navController = navController
            )
        }

        composable(Route.libraryPage){
            LibraryPage(
                navController = navController
            )
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
                navController = navController
            )
        }

        composable(
            route = Route.forgotPassPage_Code,
            arguments = listOf(navArgument("mail") {
                type = NavType.StringType
            })
        ) {
            ForgotPassPage_Code(
                mail = it.arguments?.getString("mail"),
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navController = navController
            )
        }

        composable(
            route = Route.forgotPassPage_ChangePass,
            arguments = listOf(navArgument("mail") {
                type = NavType.StringType
            })
        ) {
            ForgotPassPage_ChangePass(
                mail = it.arguments?.getString("mail"),
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) },
                navigateToAuthPage = { navController.navigate(Route.authPage)}
            )
        }

        composable(
            route = Route.bookPage,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            BookPage(
                bookId = it.arguments?.getInt("id"),
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}