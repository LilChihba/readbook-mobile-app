package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.models.toJson
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GenreCard(
    books: MutableList<Book>,
    apiClient: ApiClient,
    genre: Genre,
    navController: NavHostController
) {
    val genreString = genre.toJson()
    Button(
        onClick = {
            books.clear()
            thread {
                books.addAll((apiClient.getGenreBooks(genre.id) as MutableList<*>).filterIsInstance<Book>())
            }.join()
            navController.navigate("genrePage/${genreString}")
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = ShapeDefaults.Small,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Gray
            ),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = genre.title,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 35.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(genre.iconId)
                        .crossfade(true)
                        .diskCacheKey("genre_images_${Instant.now()}_${genre.iconId}")
                        .build(),
                    contentDescription = genre.title,
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.High,
                    modifier = Modifier
                        .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                        .height(80.dp)
                        .width(55.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
            }
        }
    }
}