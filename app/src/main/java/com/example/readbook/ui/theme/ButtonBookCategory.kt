package com.example.readbook.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.pages.BookItem

@Composable
fun ButtonBookCategory(
    book: BookItem
) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 15.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .width(106.5.dp)
                .height(150.dp)

        ) {
            Image(
                painter = painterResource(id = book.image),
                contentDescription = "Book",
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.padding(top = 7.dp)
        ) {
            RatingBar(rating = book.rate)
            Text(
                text = book.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 3.dp)
            )
            Text(
                text = book.author,
                color = Color.Black,
                fontSize = 10.sp,
            )
        }
    }
}