package com.example.readbook.pages

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.R
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.SearchBook

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GenresPage(
    genre: Genre,
    books: MutableList<Book>,
    navController: NavHostController,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
    ) {
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

            LazyColumn(
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 5.dp)
            ) {
                items(
                    count = books.size,
                    itemContent = { index ->
                        val bookItemData = books[index]
                        SearchBook(book = bookItemData, navController = navController)
                    }
                )
            }
        }
    }
}