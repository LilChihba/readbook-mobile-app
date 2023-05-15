package com.example.readbook.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readbook.models.BookLibrary
import com.example.readbook.repository.BookRepository

@Composable
fun ButtonBook(
    bookLibrary: BookLibrary,
    navController: NavHostController
) {
    val book = BookRepository().getBookByID(id = bookLibrary.id_book)

    if (book != null)
        Button(
            onClick = { navController.navigate("bookPage/" + book.id) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .width(110.dp)
                .height(160.dp)
                .padding(bottom = 10.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(id = book.image),
                contentDescription = "Book",
                contentScale = ContentScale.Crop
            )
        }
}