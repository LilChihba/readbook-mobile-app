package com.example.readbook.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navController: NavHostController,
    listBooks: MutableList<Book>?,
    apiClient: ApiClient,
    token: Token,
) {
    val listCard = listOf(
        CardItem.NewBooks,
        CardItem.PopularBooks,
        CardItem.BestBooks
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
                        listBooks = listBooks,
                        apiClient = apiClient,
                        token = token
                    )
                }
            )
        }
    }
}