package com.example.readbook.pages

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.GenreItem
import com.example.readbook.ui.theme.GenreCard
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.SearchBook
import com.example.readbook.ui.theme.SearchBox
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    apiClient: ApiClient
) {
    val books = remember { mutableListOf<Book>() }
    val textSearch = remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val listGenres = listOf(
        GenreItem.Genre1,
        GenreItem.Genre2,
        GenreItem.Genre3,
        GenreItem.Genre4,
        GenreItem.Genre5,
        GenreItem.Genre6,
        GenreItem.Genre7,
        GenreItem.Genre8,
    )
    val bool = remember { mutableStateOf(false) }
    val isSearch = remember { mutableStateOf(false) }
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
                                    val booksList = (apiClient.searchBooks(textSearch.value) as List<*>).filterIsInstance<Book>()
                                    books.clear()
                                    books.addAll(booksList)
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
                                listGenres[it].title
                            },
                            itemContent = { index ->
                                val genreItemData = listGenres[index]
                                GenreCard(genre = genreItemData)
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
                                SearchBook(book = bookItemData)
                            }
                        )
                    }
                }
            }
        }
    }
}