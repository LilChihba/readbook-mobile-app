package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.models.URL.BASE_URL
import com.example.readbook.models.toJson
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonBookCategory(
    book: Book,
    apiClient: ApiClient,
    token: Token,
    navController: NavHostController,
) {
    val bookString = book.toJson()
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 15.dp)
            .width(110.dp)
    ) {
        Button(
            onClick = {
                thread {
                    if(book.isBuyed == null)
                        book.isBuyed = apiClient.checkBuy(book.uuid, accessToken = token.accessToken) == 200
                }.join()
                navController.navigate("bookPage/$bookString")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .height(175.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$BASE_URL/books/${book.uuid}/cover")
                    .crossfade(true)
                    .diskCacheKey("books_images_${Instant.now()}_${book.uuid}")
                    .build(),
                contentDescription = "ButtonBook",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.default_cover),
                error = painterResource(id = R.drawable.default_cover),
                filterQuality = FilterQuality.High,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.padding(top = 7.dp)
        ) {
            RatingBar(rating = book.score.toInt())
            Text(
                text = book.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 3.dp),
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.getAuthorsString(),
                color = Color.Black,
                fontSize = 10.sp,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}