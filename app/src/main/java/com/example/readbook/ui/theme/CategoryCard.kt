package com.example.readbook.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.models.CardItem
import com.example.readbook.repository.BookRepository

@Composable
fun CategoryCard(
    card: CardItem,
    navController: NavHostController
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier.background(Color.Black)
            ) {
                Image(
                    painter = painterResource(id = card.background),
                    contentDescription = "background",
                    alpha = 0.6f
                )
                Text(
                    text = card.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                )
            }

            val bookRepository = BookRepository()
            val getAllData = bookRepository.getAllData()

            Box(
                modifier = Modifier
                    .padding(top = 150.dp, bottom = 8.dp)
            ) {
                LazyRow() {
                    items(
                        count = getAllData.size,
                        key = {
                            getAllData[it].id
                        },
                        itemContent = { index ->
                            val bookItemData = getAllData[index]
                            ButtonBookCategory(book = bookItemData, navController = navController)
                        }
                    )
                }
            }
        }
    }
}