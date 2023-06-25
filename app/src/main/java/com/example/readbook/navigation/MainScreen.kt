package com.example.readbook.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.models.Token
import com.example.readbook.models.User

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    pref: SharedPreferences?,
    mutableListBooksAddedOn: MutableList<Book>?,
    token: Token,
    snackbarHostState: SnackbarHostState,
    apiClient: ApiClient,
    user: User,
    colorSnackBar: MutableState<Color>,
    mutableListLibraryBooks: MutableList<Book>,
    mutableListBooksPopular: MutableList<Book>,
    mutableListBooksScore: MutableList<Book>,
    listGenres: List<Genre>,
    genreBooks: MutableList<Book>,
    context: Context
) {
    val navController = rememberNavController()

    val navBarState = rememberSaveable { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Route.genrePage -> {
            navBarState.value = false
        }
        Route.readPage -> {
            navBarState.value = false
        }
        Route.profileEditPage -> {
            navBarState.value = false
        }
        Route.libraryPage -> {
            navBarState.value = true
        }
        Route.codeRegPage -> {
            navBarState.value = false
        }
        Route.authPage -> {
            navBarState.value = false
        }
        Route.bookPage -> {
            navBarState.value = false
        }
        Route.forgotPassPage -> {
            navBarState.value = false
        }
        Route.forgotPassPage_Code -> {
            navBarState.value = false
        }
        Route.homePage -> {
            navBarState.value = true
        }
        Route.profilePage -> {
            navBarState.value = true
        }
        Route.regPage -> {
            navBarState.value = false
        }
        Route.settingsPage -> {
            navBarState.value = false
        }
        Route.reviewPage -> {
            navBarState.value = false
        }
        Route.searchPage -> {
            navBarState.value = true
        }
    }

    Scaffold(
        bottomBar = { BottomBarNavigation(navController = navController, navBarState = navBarState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavGraph(
                navController = navController,
                pref = pref,
                mutableListBooksAddedOn = mutableListBooksAddedOn,
                token = token,
                snackbarHostState = snackbarHostState,
                apiClient = apiClient,
                user = user,
                colorSnackBar = colorSnackBar,
                mutableListLibraryBooks = mutableListLibraryBooks,
                mutableListBooksPopular = mutableListBooksPopular,
                mutableListBooksScore = mutableListBooksScore,
                listGenres = listGenres,
                genreBooks = genreBooks,
                context = context
            )
        }
    }
}