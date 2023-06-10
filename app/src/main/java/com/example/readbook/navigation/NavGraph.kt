package com.example.readbook.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.models.fromJson
import com.example.readbook.pages.AuthPage
import com.example.readbook.pages.BookPage
import com.example.readbook.pages.CodePage
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
import java.io.IOException
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
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

    var getLibraryBooks: Any?
    var getListLibraryBooks: List<Book>?
    val mutableListLibraryBooks: MutableList<Book> = remember { mutableListOf() }

    val user = User()
    val snackbarHostState = SnackbarHostState()
    val colorSnackBar = remember { mutableStateOf(DarkGray) }
    val activity = (LocalContext.current as? Activity)

    val token = Token(
        pref!!.getString("accessToken", "").toString(),
        pref.getInt("accessTokenExpiresIn", 0),
        pref.getString("refreshToken", "").toString(),
        pref.getInt("refreshTokenExpiresIn", 0),
    )

    thread {
        getBooks = apiClient.getBooks()
        if(getBooks is List<*>){
            getListBooks = (getBooks as List<*>).filterIsInstance<Book>()
            for(i in getListBooks!!) {
                mutableListBooks.add(i)
            }
        }

        try {
            user.copy(apiClient.getMe(token) as User, token, apiClient)
            getLibraryBooks = apiClient.getLibraryBooks(token)
            if(getLibraryBooks is List<*>){
                getListLibraryBooks = (getLibraryBooks as List<*>).filterIsInstance<Book>()
                for(i in getListLibraryBooks!!) {
                    mutableListLibraryBooks.add(i)
                }
            }
        } catch (e: IOException) {
            val nowDate = Instant.now()
            if(token.isAccessTokenExpired(nowDate))
                try {
                    if(token.isRefreshTokenExpired(nowDate)) {
                        token.copy(apiClient.updateToken(token) as Token)
                        user.copy(apiClient.getMe(token) as User, token, apiClient)
                    }
                } catch (e: IOException) {
                    token.delete()
                }
        } finally {
            token.save(pref)
        }
    }.join()

    BoxWithConstraints {
        NavHost(navController = navController, startDestination = Route.homePage) {
            composable(Route.homePage){
                HomePage(
                    navController = navController,
                    listBooks = mutableListBooks,
                    apiClient = apiClient,
                    token = token
                )
            }

            composable(Route.libraryPage){
                LibraryPage(
                    listLibraryBooks = mutableListLibraryBooks,
                    navController = navController,
                    token = token,
                    apiClient = apiClient
                )
            }

            composable(Route.searchPage){
                SearchPage(
                    apiClient = apiClient
                )
            }

            composable(route = Route.profilePage) {
                ProfilePage(
                    user = user,
                    token = token,
                    navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                    navigateToAuthPage = { navController.navigate(Route.authPage) }
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
                route = Route.codeRegPage,
                arguments = listOf(navArgument("mail") {
                    type = NavType.StringType
                })
            ) {
                CodePage(
                    mail = it.arguments?.getString("mail"),
                    apiClient = apiClient,
                    pref = pref,
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
                route = Route.forgotPassPage_ChangePass,
                arguments = listOf(navArgument("mail") {
                    type = NavType.StringType
                })
            ) {
                ForgotPassPage_ChangePass(
                    mail = it.arguments?.getString("mail"),
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
                arguments = listOf(navArgument("book") {
                    type = NavType.StringType
                })
            ) {
                it.arguments?.getString("book")?.let { jsonString ->
                    val book = jsonString.fromJson(Book::class.java)
                    BookPage(
                        book = book,
                        apiClient = apiClient,
                        token = token,
                        snackbarHostState = snackbarHostState,
                        colorSnackBar = colorSnackBar,
                        navigateBack = { navController.popBackStack() }
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

//@Composable
//fun dialog(activity: Activity?) {
//    AlertDialog(
//        onDismissRequest = { activity?.finish() },
//        confirmButton = {},
//        title = {}
//    )
//}
//
//@Composable
//@Preview
//fun dialogPreview() {
//
//}