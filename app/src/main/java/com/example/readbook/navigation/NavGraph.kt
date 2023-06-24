package com.example.readbook.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.models.fromJson
import com.example.readbook.pages.AuthPage
import com.example.readbook.pages.BookPage
import com.example.readbook.pages.CodePage
import com.example.readbook.pages.ForgotPassPage
import com.example.readbook.pages.ForgotPassPage_Code
import com.example.readbook.pages.GenresPage
import com.example.readbook.pages.HomePage
import com.example.readbook.pages.LibraryPage
import com.example.readbook.pages.ProfileEditPage
import com.example.readbook.pages.ProfilePage
import com.example.readbook.pages.ReadPage
import com.example.readbook.pages.RegPage
import com.example.readbook.pages.ReviewPage
import com.example.readbook.pages.SearchPage
import com.example.readbook.pages.SettingsPage
import com.example.readbook.ui.theme.SnackbarCustom

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavGraph(
    navController: NavHostController,
    pref: SharedPreferences?,
    mutableListBooksAddedOn: MutableList<Book>?,
    token: Token,
    snackbarHostState: SnackbarHostState,
    apiClient: ApiClient,
    user: User,
    colorSnackBar: MutableState<Color>,
    mutableListLibraryBooks: MutableList<Book>,
    listGenres: List<Genre>,
    genreBooks: MutableList<Book>,
    context: Context
) {
    BoxWithConstraints {
        NavHost(navController = navController, startDestination = Route.homePage) {
            composable(Route.homePage){
                HomePage(
                    navController = navController,
                    mutableListBooksAddedOn = mutableListBooksAddedOn,
                    apiClient = apiClient,
                    token = token,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar
                )
            }

            composable(Route.libraryPage){
                LibraryPage(
                    listLibraryBooks = mutableListLibraryBooks,
                    navController = navController,
                    token = token,
                    apiClient = apiClient,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar
                )
            }

            composable(Route.searchPage){
                SearchPage(
                    apiClient = apiClient,
                    navController = navController,
                    listGenres = listGenres,
                    genreBooks = genreBooks,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar
                )
            }

            composable(
                route = Route.genrePage,
                arguments = listOf(navArgument("genre") {
                    type = NavType.StringType
                })
            ){
                it.arguments?.getString("genre")?.let { jsonString ->
                    val genre = jsonString.fromJson(Genre::class.java)
                    GenresPage(
                        genre = genre,
                        navigateBack = { navController.popBackStack() },
                        books = genreBooks,
                        navController = navController
                    )
                }
            }

            composable(route = Route.profilePage) {
                ProfilePage(
                    user = user,
                    token = token,
                    navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                    navigateToAuthPage = { navController.navigate(Route.authPage) },
                    apiClient = apiClient,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar
                )
            }

            composable(route = Route.settingsPage) {
                SettingsPage(
                    user = user,
                    token = token,
                    pref = pref,
                    mutableListLibraryBooks = mutableListLibraryBooks,
                    navigateToAuthPage = { navController.navigate(Route.authPage) },
                    navigateBack = { navController.popBackStack() },
                    navigateToProfileEdit = { navController.navigate(Route.profileEditPage) }
                )
            }

            composable(route = Route.authPage) {
                AuthPage(
                    user = user,
                    token = token,
                    pref = pref,
                    apiClient = apiClient,
                    mutableListLibraryBooks = mutableListLibraryBooks,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
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
                    navController = navController,
                    apiClient = apiClient,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
                    navigateBack = { navController.popBackStack() },
                    navigateBackToProfile = { navController.popBackStack(
                        route = Route.profilePage,
                        inclusive = false
                    ) }
                )
            }

            composable(
                route = Route.codeRegPage
            ) {
                CodePage(
                    apiClient = apiClient,
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
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar,
                    navigateBack = { navController.popBackStack() },
                    navigateBackToProfile = { navController.popBackStack(
                        route = Route.profilePage,
                        inclusive = false
                    ) },
                    navController = navController,
                    apiClient = apiClient
                )
            }

            composable(route = Route.forgotPassPage_Code) {
                ForgotPassPage_Code(
                    apiClient = apiClient,
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
                arguments = listOf(
                    navArgument("bookString") {
                        type = NavType.StringType
                    }
                )
            ) {
                it.arguments?.getString("bookString")?.let { bookString ->
                    val book = bookString.fromJson(Book::class.java)
                    BookPage(
                        book = book,
                        apiClient = apiClient,
                        token = token,
                        snackbarHostState = snackbarHostState,
                        colorSnackBar = colorSnackBar,
                        navController = navController,
                        listLibraryBooks = mutableListLibraryBooks,
                        context = context,
                        user = user,
                    )
                }
            }

            composable(
                route = Route.profileEditPage
            ) {
                ProfileEditPage(
                    navigateBack = { navController.popBackStack() },
                    navigateBackToProfile = { navController.popBackStack(
                        route = Route.profilePage,
                        inclusive = false
                    ) },
                    user = user,
                    apiClient = apiClient,
                    token = token,
                    snackbarHostState = snackbarHostState,
                    colorSnackBar = colorSnackBar
                )
            }

            composable(
                route = Route.reviewPage,
                arguments = listOf(
                    navArgument("book") {
                        type = NavType.StringType
                    },
                    navArgument("rating") {
                        type = NavType.IntType
                    }
                )
            ) {
                it.arguments?.getString("book")?.let { jsonString ->
                    val book = jsonString.fromJson(Book::class.java)
                    ReviewPage(
                        navigateBack = { navController.popBackStack() },
                        token = token,
                        apiClient = apiClient,
                        book = book,
                        rating = it.arguments?.getInt("rating"),
                        snackbarHostState = snackbarHostState,
                        colorSnackBar = colorSnackBar,
                        navController = navController
                    )
                }
            }
            composable(
                route = Route.readPage,
                arguments = listOf(
                    navArgument("uri") {
                        type = NavType.StringType
                    }
                )
            ) {
                ReadPage(
                    uri = it.arguments?.getString("uri"),
                    navigateToBack = { navController.popBackStack() },
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