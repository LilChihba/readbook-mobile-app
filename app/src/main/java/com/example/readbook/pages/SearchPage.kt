package com.example.readbook.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.ui.theme.GenreCard
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.SearchBook
import com.example.readbook.ui.theme.SearchBox
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.concurrent.thread

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    apiClient: ApiClient,
    navController: NavHostController,
    genreBooks: MutableList<Book>,
    listGenres: List<Genre>,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
) {
    val books = remember { mutableListOf<Book>() }
    val textSearch = remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val bool = remember { mutableStateOf(false) }
    val isSearch = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { keyboardController?.hide() }
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(start = 25.dp, top = 25.dp, end = 25.dp),
            containerColor = Milk
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
            ) {
                TopAppBar(
                    title = {},
                    actions = {
                        SearchBox(
                            text = textSearch,
                            onSearchClick = {
                                bool.value = false
                                thread {
                                    try {
                                        val booksList = (apiClient.searchBooks(textSearch.value) as List<*>).filterIsInstance<Book>()
                                        books.clear()
                                        books.addAll(booksList)
                                    } catch (_: IOException) {
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Произошла ошибка! Повторите попытку!",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }.join()
                                bool.value = true
                                isSearch.value = true
                            }
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Milk
                    ),
                    scrollBehavior = scrollBehavior
                )
                if(textSearch.value == "")
                    isSearch.value = false
                LazyColumn {
                    if (!isSearch.value) {
                        items(
                            count = listGenres.size,
                            key = {
                                listGenres[it].id
                            },
                            itemContent = { index ->
                                val genreItemData = listGenres[index]
                                GenreCard(
                                    genre = genreItemData,
                                    navController = navController,
                                    apiClient = apiClient,
                                    books = genreBooks,
                                    snackbarHostState = snackbarHostState,
                                    colorSnackBar = colorSnackBar
                                )
                            }
                        )
                        books.clear()
                        bool.value = false
                    }
                    if(bool.value) {
                        items(
                            count = books.size,
                            key = {
                                books[it].uuid
                            },
                            itemContent = { index ->
                                val bookItemData = books[index]
                                SearchBook(book = bookItemData, navController = navController)
                            }
                        )
                    }
                }
            }
        }
    }
}