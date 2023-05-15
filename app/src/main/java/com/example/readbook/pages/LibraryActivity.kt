package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.repository.BookLibraryRepository
import com.example.readbook.ui.theme.ButtonBook
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk

@Composable
fun LibraryPage(
    navController: NavHostController
) {
    val bookLibraryRepository = BookLibraryRepository()
    val getAllData = bookLibraryRepository.getAllData()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
        .padding(start = 25.dp, top = 30.dp, end = 25.dp)
    ) {
        Text(
            text = "Мои книги",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 110.dp),
                contentPadding = PaddingValues(start = 10.dp, top = 10.dp)
            ) {
                items(
                    count = getAllData.size,
                    key = {
                        getAllData[it].id
                    },
                    itemContent = { index ->
                        val bookItemData = getAllData[index]
                        ButtonBook(bookItemData, navController = navController)
                    }
                )
            }
        }
    }
}