package com.example.readbook.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.models.URL.BASE_URL
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.RatingBar
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookPage(
    book: Book,
    apiClient: ApiClient,
    token: Token,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val text = if(book.isBuyed == true)
        remember { mutableStateOf("У вас уже куплена эта книга!") }
    else
        remember { mutableStateOf("Купить за ${book.priceRub} руб.") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { navigateBack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "back",
                    tint = Blue
                )
            }
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(Milk)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
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
                            modifier = Modifier
                                .width(100.dp)
                                .height(141.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(
                                text = book.title,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                            )
                            Text(
                                text = "Авторы: ${book.getAuthorsString()}",
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            Row {
                                RatingBar(
                                    rating = book.score.toInt(),
                                    modifier = Modifier
                                        .width(26.dp)
                                        .height(26.dp)
                                )
                                Text(
                                    text = "${book.score}",
                                    color = Blue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(start = 3.dp, top = (1.25).dp)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {
                        ButtonApp(
                            text = text.value,
                            onClick = {
//                                if(true)
//                                    if(authUser.auth)
//                                        if(!BookRepository().isBuyed(listBooks, book, authUser)) {
//                                            if(BookLibraryRepository().addBookInLibrary(authUser, listBooks, book)) {
//                                                colorSnackBar.value = Color.Green
//                                                scope.launch {
//                                                    snackbarHostState.showSnackbar(
//                                                        message = "Поздравляю! Вы только что приобрели книгу! Она теперь находится в вашей библиотеке",
//                                                        duration = SnackbarDuration.Short
//                                                    )
//                                                }
//                                                text.value = "У вас уже куплена эта книга!"
//                                            }
//                                            else {
//                                                colorSnackBar.value = Color.Red
//                                                scope.launch {
//                                                    snackbarHostState.showSnackbar(
//                                                        message = "Произошла ошибка!",
//                                                        duration = SnackbarDuration.Short
//                                                    )
//                                                }
//                                            }
//                                        }
//                                        else {
//                                            colorSnackBar.value = Color.Red
//                                            scope.launch {
//                                                snackbarHostState.showSnackbar(
//                                                    message = "Эта книга уже приобретена! Посмотрите свою библиотеку",
//                                                    duration = SnackbarDuration.Short
//                                                )
//                                            }
//                                        }
//                                    else {
//                                        colorSnackBar.value = Color.Red
//                                        scope.launch {
//                                            snackbarHostState.showSnackbar(
//                                                message = "Ошибка! Чтобы купить книгу, необходимо авторизоваться",
//                                                duration = SnackbarDuration.Short
//                                            )
//                                        }
//                                    }
                            },
                            modifier = Modifier.height(45.dp),
                            fontsize = 14.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 25.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = "О книге",
                            color = Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }
                    Row {
                        Text(
                            text = book.about,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                    DescriptionText(title = "Издатель", text = book.publisher)
                    DescriptionText(title = "Год издание", text = book.publicationDate)
                    DescriptionText(title = "Количество страниц", text = "${book.numberOfPages}")
                    DescriptionText(title = "ISBN", text = book.isbn)

                    Row(
                        modifier = Modifier
                            .padding(top = 25.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = "Оценки и отзывы",
                            color = Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }
                    }

//                items(
//                    count = getAllData.size,
//                    key = {
//                        getAllData[it].id
//                    },
//                    itemContent = { index ->
//                        val reviewData = getAllData[index]
//                        BookReview(review = reviewData)
//                    }
//                )
            }
        }
    }
}

@Composable
fun DescriptionText(
    title: String,
    text: String
) {
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(
            text = title,
            color = Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}