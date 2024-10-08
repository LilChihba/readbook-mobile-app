package com.example.readbook.ui.theme

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Review
import com.example.readbook.models.Token
import com.example.readbook.models.URL.BASE_URL
import com.example.readbook.models.toJson
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import kotlin.concurrent.thread

@Composable
fun SearchBook(
    book: Book,
    navController: NavHostController,
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    token: Token
) {
    var bookString: String?
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            var isSuccess = false
            thread {
                try {
                    if(!book.isBuyed)
                        book.isBuyed = apiClient.checkBuy(book.uuid, accessToken = token.accessToken)
                    book.reviews = (apiClient.getReviews(book.uuid) as List<*>).filterIsInstance<Review>()
                    isSuccess = true
                } catch (e: IOException) {
                    isSuccess = false
                    colorSnackBar.value = Color.Red
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Произошла ошибка! Повторите попытку",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }.join()
            if(isSuccess) {
                bookString = book.toJson()
                navController.navigate("bookPage/$bookString")
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
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
                    .shadow(
                        2.dp,
                        RoundedCornerShape(5.dp),
                        ambientColor = Color.LightGray,
                        spotColor = Color.LightGray
                    )
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