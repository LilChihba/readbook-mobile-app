package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.models.Book
import com.example.readbook.models.URL.BASE_URL
import com.example.readbook.models.toJson
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchBook(
    book: Book,
    navController: NavHostController
) {
    val bookString = book.toJson()
    Button(
        onClick = { navController.navigate("bookPage/$bookString") },
        colors = ButtonDefaults.buttonColors(containerColor = Gray),
        shape = RoundedCornerShape(5.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$BASE_URL/books/${book.uuid}/cover")
                    .crossfade(true)
                    .diskCacheKey("search_book_image${Instant.now()}")
                    .build(),
                contentDescription = "Book",
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.High,
                modifier = Modifier
                    .height(140.dp)
                    .width(95.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DarkGray,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                )
                Text(
                    text = book.getAuthorsString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = DarkGray,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                )
                Row(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    RatingBar(
                        rating = book.score.toInt()
                    )
                    Text(
                        text = "${book.score}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = DarkGray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}