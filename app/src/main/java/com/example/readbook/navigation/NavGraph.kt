package com.example.readbook.navigation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.readbook.models.Token
import com.example.readbook.models.User
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
    val user = User()

    val token = Token(
        pref!!.getString("accessToken", "").toString(),
        pref.getInt("accessTokenExpiresIn", 0),
        pref.getString("refreshToken", "").toString(),
        pref.getInt("refreshTokenExpiresIn", 0),
    )

    val th = thread {
        getBooks = apiClient.getBooks()
        if(getBooks is List<*>){
            getListBooks = (getBooks as List<*>).filterIsInstance<Book>()
            for(i in getListBooks!!) {
//                i.cover = apiClient.getCoverBook(i.uid)!!
                mutableListBooks.add(i)
            }
        }

        try {
            val userJSON = ApiClient().getMe(token) as User
            with(user) {
                username = userJSON.username
                firstName = userJSON.firstName
                secondName = userJSON.secondName
                lastName = userJSON.lastName
                avatar = ApiClient().getMeAvatar(user.username)
                email = ApiClient().getMeEmail(user.username, token)!!.email
            }
        } catch (e: IOException) {
            val nowDate = Instant.now()
            if(token.date.plusSeconds(token.accessTokenExpiresIn.toLong()) < nowDate)
                try {
                    if(token.date.plusSeconds(token.refreshTokenExpiresIn.toLong()) < nowDate) {
                        val newToken = ApiClient().updateToken(token) as Token
                        with(token) {
                            accessToken = newToken.accessToken
                            accessTokenExpiresIn = newToken.accessTokenExpiresIn
                            refreshToken = newToken.refreshToken
                            refreshTokenExpiresIn = newToken.refreshTokenExpiresIn
                            date = Instant.now()
                        }

                        with(pref.edit()) {
                            putString("accessToken", token.accessToken)
                            putInt("accessTokenExpiresIn", token.accessTokenExpiresIn)
                            putString("refreshToken", token.refreshToken)
                            putInt("refreshTokenExpiresIn", token.refreshTokenExpiresIn)
                            putLong("date", token.date.toEpochMilli())
                            apply()
                        }

                        val userJSON = ApiClient().getMe(token) as User
                        with(user) {
                            username = userJSON.username
                            firstName = userJSON.firstName
                            secondName = userJSON.secondName
                            lastName = userJSON.lastName
                            avatar = ApiClient().getMeAvatar(user.username)
                            email = ApiClient().getMeEmail(user.username, token)!!.email
                        }
                    } else {
                        with(token) {
                            accessToken = ""
                            accessTokenExpiresIn = 0
                            refreshToken = ""
                            refreshTokenExpiresIn = 0
                            date = Instant.ofEpochMilli(0)
                        }

                        with(pref.edit()) {
                            putString("accessToken", token.accessToken)
                            putInt("accessTokenExpiresIn", token.accessTokenExpiresIn)
                            putString("refreshToken", token.refreshToken)
                            putInt("refreshTokenExpiresIn", token.refreshTokenExpiresIn)
                            putLong("date", token.date.toEpochMilli())
                            apply()
                        }
                    }
                } catch (e: IOException) {
                    with(pref.edit()) {
                        putString("accessToken", "")
                        putInt("accessTokenExpiresIn", 0)
                        putString("refreshToken", "")
                        putInt("refreshTokenExpiresIn", 0)
                        putLong("date", Instant.ofEpochMilli(0).toEpochMilli())
                        apply()
                    }
                }
            return@thread
        }
    }

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
                    user = user,
                    token = token,
                    navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                    navigateToAuthPage = { navController.navigate(Route.authPage) }
                )
            }

            composable(route = Route.settingsPage) {
                SettingsPage(
                    user = user,
                    pref = pref,
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