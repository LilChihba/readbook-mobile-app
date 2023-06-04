package com.example.readbook.navigation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.pages.AuthPage
import com.example.readbook.pages.BookPage
import com.example.readbook.pages.ForgotPassPage
import com.example.readbook.pages.ForgotPassPage_ChangePass
import com.example.readbook.pages.ForgotPassPage_Code
import com.example.readbook.pages.HomePage
import com.example.readbook.pages.LibraryPage
import com.example.readbook.pages.ProfileEditPage
import com.example.readbook.pages.ProfilePage
import com.example.readbook.pages.RegPage
import com.example.readbook.pages.SearchPage
import com.example.readbook.pages.SettingsPage
import com.example.readbook.ui.theme.DarkGray
import com.example.readbook.ui.theme.SnackbarCustom
import kotlin.concurrent.thread

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavGraph(
    navController: NavHostController,
    pref: SharedPreferences?
) {
    val apiClient = ApiClient()
    var getBooks: Any?
    var getListBooks: List<Book>?
    val mutableListBooks: MutableList<Book> = remember { mutableListOf() }

    val th = thread {
        getBooks = apiClient.getBooks()
        if(getBooks is List<*>){
            getListBooks = (getBooks as List<*>).filterIsInstance<Book>()
            for(i in getListBooks!!) {
                i.cover = apiClient.getCoverBook(i.uid)!!
                mutableListBooks.add(i)
            }
        }
    }
//    val mutableListBooks = getListBooks?.toMutableList()
//    val listUsers = UserRepository().getAllData()
//    val listLibraryBooks = BookLibraryRepository().getAllData()
//    val mail = pref!!.getString("mail", "")
//    val password = pref.getString("password", "")
//    val authUser = AuthUser().auth(listUsers, mail.toString(), password.toString())
    val snackbarHostState = SnackbarHostState()
    val colorSnackBar = remember { mutableStateOf(DarkGray) }

    th.join()
    BoxWithConstraints {
        NavHost(navController = navController, startDestination = Route.homePage) {
            composable(Route.homePage){
                HomePage(
                    navController = navController,
                    listBooks = mutableListBooks
                )
            }

            composable(Route.libraryPage){
                LibraryPage(
//                    authUser = authUser,
//                    listLibraryBooks = listLibraryBooks,
                    navController = navController
                )
            }

            composable(Route.searchPage){
                SearchPage()
            }

            composable(route = Route.profilePage) {
                ProfilePage(
//                    authUser = authUser,
                    navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                    navigateToAuthPage = { navController.navigate(Route.authPage) }
                )
            }

            composable(route = Route.settingsPage) {
                SettingsPage(
//                    authUser = authUser,
                    pref = pref,
                    navigateToAuthPage = { navController.navigate(Route.authPage) },
                    navigateBack = { navController.popBackStack() },
                    navigateToProfileEdit = { navController.navigate(Route.profileEditPage) }
                )
            }

            composable(route = Route.authPage) {
                AuthPage(
                    pref = pref,
                    snackbarHostState = snackbarHostState,
//                    authUser = authUser,
                    colorSnackBar = colorSnackBar,
//                    listUsers = listUsers,
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
                    pref = pref,
//                    authUser = authUser,
//                    listUsers = listUsers,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
                    navigateBack = { navController.popBackStack() },
                    navigateBackToProfile = { navController.popBackStack(
                        route = Route.profilePage,
                        inclusive = false
                    ) }
                )
            }

            composable(route = Route.forgotPassPage) {
                ForgotPassPage(
//                    listUsers = listUsers,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
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
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
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
//                    listUsers = listUsers,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
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
//                    authUser = authUser,
//                    listBooks = listLibraryBooks,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
                    navigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Route.profileEditPage
            ) {
                ProfileEditPage(
//                    authUser = authUser,
                    navigateBack = { navController.popBackStack() },
                    navigateBackToProfile = { navController.popBackStack(
                        route = Route.profilePage,
                        inclusive = false
                    ) }
                )
            }
        }
        SnackbarCustom(
            state = snackbarHostState,
            text = snackbarHostState.currentSnackbarData?.visuals?.message?: "",
            color = colorSnackBar.value
        )
    }
}