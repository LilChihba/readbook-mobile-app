package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.models.Token
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.SearchBook
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.concurrent.thread

@Composable
fun GenresPage(
    genre: Genre,
    books: MutableList<Book>,
    navController: NavHostController,
    navigateBack: () -> Unit,
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    token: Token
) {
    val booksPage = remember { mutableStateOf(books) }
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        thread {
            booksPage.value.clear()
            booksPage.value.addAll((apiClient.getGenreBooks(genre.id) as MutableList<*>).filterIsInstance<Book>())
        }.join()
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Milk)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { navigateBack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "back",
                            tint = Blue
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, top = 10.dp)
                    ) {
                        Text(
                            text = genre.title,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                    }
                }
            }
            items(
                count = booksPage.value.size,
                itemContent = { index ->
                    val bookItemData = booksPage.value[index]
                    Column(modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 10.dp)) {
                        SearchBook(
                            book = bookItemData,
                            navController = navController,
                            apiClient = apiClient,
                            snackbarHostState = snackbarHostState,
                            colorSnackBar = colorSnackBar,
                            token = token
                        )
                    }
                }
            )
        }
    }
}