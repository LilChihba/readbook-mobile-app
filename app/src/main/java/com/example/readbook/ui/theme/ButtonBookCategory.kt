package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import java.io.BufferedReader
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonBookCategory(
    book: Book,
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 15.dp)
            .width(110.dp)
    ) {
        Button(
            onClick = {  navController.navigate("bookPage/" + book.uid) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .height(175.dp)
        ) {
//            Image(
//                bitmap = book.cover.asImageBitmap(),
//                contentDescription = "Book",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://f466-85-95-178-202.ngrok-free.app/books/${book.uid}/cover")
                    .crossfade(true)
                    .diskCacheKey("books_images_${Instant.now()}_${book.uid}")
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
                softWrap = false
            )
            Text(
                text = book.getAuthorsString(),
                color = Color.Black,
                fontSize = 10.sp,
                softWrap = true
            )
        }
    }
}