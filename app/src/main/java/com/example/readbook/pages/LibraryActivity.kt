package com.example.readbook.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.ui.theme.ButtonBook
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LibraryPage(
    token: Token,
    apiClient: ApiClient,
    navController: NavHostController,
    listLibraryBooks: MutableList<Book>,
) {
    val count = remember{ mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .padding(start = 25.dp, top = 30.dp, end = 25.dp)
    ) {
        Text(
            text = "Мои книги",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .align(Alignment.Start)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .alpha(if(count.value == 0) 0F else 1F)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 110.dp),
                contentPadding = PaddingValues(start = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                items(
                    count = listLibraryBooks.size,
                    key = {
                        listLibraryBooks[it].uuid
                    },
                    itemContent = { index ->
                        val bookItemData = listLibraryBooks[index]
                        ButtonBook(
                            bookItemData,
                            navController = navController,
                            token = token,
                            apiClient = apiClient
                        )
                        count.value += 1
                    }
                )
            }
        }
        if(count.value == 0)
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.empty_library)
                    .crossfade(true)
                    .diskCacheKey("empty_library_${Instant.now()}")
                    .build(),
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                filterQuality = FilterQuality.High,
                modifier = Modifier
                    .width(300.dp)
                    .height(135.dp)
            )
            Text(
                text = "У вас нет купленных книг",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}