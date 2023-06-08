package com.example.readbook.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.readbook.models.CardItem
import com.example.readbook.models.Token
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CategoryCard(
    apiClient: ApiClient,
    token: Token,
    card: CardItem,
    navController: NavHostController,
    listBooks: MutableList<Book>?,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Gray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier.background(Color.Black)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(card.background)
                        .crossfade(true)
                        .diskCacheKey("books_images_${Instant.now()}_${card.id}")
                        .build(),
                    contentDescription = "background",
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.High,
                    alpha = 0.6f,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = card.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 150.dp, bottom = 8.dp)
            ) {
                if(listBooks != null)
                    LazyRow {
                        items(
                            count = if(listBooks.size < 5) listBooks.size else 5,
                            key = {
                                listBooks[it].uuid
                            },
                            itemContent = { index ->
                                val bookItemData = listBooks[index]
                                ButtonBookCategory(
                                    book = bookItemData,
                                    navController = navController,
                                    apiClient = apiClient,
                                    token = token
                                )
                            }
                        )
                    }
            }
        }
    }
}