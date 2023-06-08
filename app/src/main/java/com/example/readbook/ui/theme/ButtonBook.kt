package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.models.URL
import com.example.readbook.models.toJson
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonBook(
    book: Book,
    apiClient: ApiClient,
    token: Token,
    navController: NavHostController
) {
    val bookString = book.toJson()
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
            .width(110.dp)
            .height(160.dp)
            .padding(bottom = 10.dp, end = 10.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${URL.BASE_URL}/books/${book.uuid}/cover")
                .crossfade(true)
                .diskCacheKey("books_library_images_${Instant.now()}_${book.uuid}")
                .build(),
            contentDescription = "Book",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.default_cover),
            error = painterResource(id = R.drawable.default_cover),
            filterQuality = FilterQuality.High,
            modifier = Modifier.fillMaxSize()
        )
    }
}