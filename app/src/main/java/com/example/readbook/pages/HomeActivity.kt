package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.ui.theme.CategoryCard
import com.example.readbook.ui.theme.Milk

@Preview
@Composable
fun HomePage() {
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

        LazyColumn(
            modifier = Modifier.height(625.dp)
        ) {
            items(listCard) { card ->
                CategoryCard(card = card)
            }
        }
    }
}