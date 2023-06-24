package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.CardItem
import com.example.readbook.models.Token
import com.example.readbook.ui.theme.CategoryCard
import com.example.readbook.ui.theme.Milk

@Composable
fun HomePage(
    navController: NavHostController,
    mutableListBooksAddedOn: MutableList<Book>?,
    apiClient: ApiClient,
    token: Token,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>
) {
    val listCard = listOf(
        CardItem.NewBooks,
        CardItem.PopularBooks,
        CardItem.BestBooks
    )

    val listBooks = listOf(
        mutableListBooksAddedOn,
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
        .padding(start = 25.dp, top = 30.dp, end = 25.dp)
    ) {
        Text(
            text = "Главная",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        LazyColumn {
            items(
                count = listCard.size,
                key = {
                    listCard[it].id
                },
                itemContent = { index ->
                    val cardItemData = listCard[index]
                    CategoryCard(
                        card = cardItemData,
                        navController = navController,
                        listBooks = listBooks[index],
                        apiClient = apiClient,
                        token = token,
                        snackbarHostState = snackbarHostState,
                        colorSnackBar = colorSnackBar,
                    )
                }
            )
        }
    }
}