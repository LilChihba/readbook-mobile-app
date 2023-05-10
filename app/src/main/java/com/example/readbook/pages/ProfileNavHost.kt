package com.example.readbook.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.profilePage
    ) {
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
    }
}