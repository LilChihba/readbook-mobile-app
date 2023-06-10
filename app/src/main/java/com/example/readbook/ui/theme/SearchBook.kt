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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.models.Book
import com.example.readbook.models.URL.BASE_URL
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchBook(
    book: Book
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 2.dp)
                )
                Text(
                    text = book.getAuthorsString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                )
                Row {
                    RatingBar(
                        rating = book.score.toInt(),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                            .padding(start = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "${book.score}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }
            }
        }
    }
}