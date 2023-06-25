package com.example.readbook.pages

import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Review
import com.example.readbook.models.Token
import com.example.readbook.models.URL.BASE_URL
import com.example.readbook.models.User
import com.example.readbook.models.toJson
import com.example.readbook.navigation.Route
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.BookReview
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.RatingBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

@Composable
fun BookPage(
    book: Book,
    user: User,
    apiClient: ApiClient,
    token: Token,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navController: NavHostController,
    listLibraryBooks: MutableList<Book>,
    context: Context
) {
    var bookPage = remember { mutableStateOf(book) }
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        thread {
            bookPage.value = apiClient.getBook(bookPage.value.uuid) as Book
            bookPage.value.reviews = (apiClient.getReviews(bookPage.value.uuid) as List<*>).filterIsInstance<Review>()
            bookPage.value.isBuyed = apiClient.checkBuy(bookPage.value.uuid, accessToken = token.accessToken)
        }.join()
    }) {
        val review = bookPage.value.reviews?.findLast { it.author.username == user.username }
        var isFound = false
        for (i in context.fileList()) {
            if(i == "${bookPage.value.uuid}.pdf")
                isFound = true
        }
        val scope = rememberCoroutineScope()
        val text = if(bookPage.value.isBuyed)
            if(isFound)
                remember { mutableStateOf("Читать") }
            else
                remember { mutableStateOf("Скачать книгу") }
        else
            remember { mutableStateOf("Купить за ${bookPage.value.priceRub} руб.") }

        val rating = if(review != null)
            remember { mutableStateOf(review.score.toInt()) }
        else
            remember { mutableStateOf(0) }
        val openDialog = remember { mutableStateOf(false) }
        var bookString: String?

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Milk)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { navController.navigate(Route.homePage) },
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
                                    .data("$BASE_URL/books/${bookPage.value.uuid}/cover")
                                    .crossfade(true)
                                    .diskCacheKey("books_images_${bookPage.value.uuid}")
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
                                    text = bookPage.value.title,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                )
                                Text(
                                    text = "Автор: ${bookPage.value.getAuthorsString()}",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 3.dp)
                                )
                                Row {
                                    RatingBar(
                                        rating = bookPage.value.score.toInt(),
                                        modifier = Modifier
                                            .width(26.dp)
                                            .height(26.dp)
                                    )
                                    Text(
                                        text = "${bookPage.value.score}",
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
                                    if(text.value.startsWith("Купить", true)) {
                                        if(token.accessToken != "") {
                                            openDialog.value = true
                                        } else {
                                            colorSnackBar.value = Color.Red
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Вы не авторизованы!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    }
                                    if(text.value.startsWith("Скачать", true)) {
                                        var baos: ByteArrayOutputStream? = null
                                        var isSuccess = false
                                        thread {
                                            try {
                                                baos = apiClient.getContent(bookPage.value.uuid, accessToken = token.accessToken)
                                                isSuccess = true
                                            } catch (_: IOException) {
                                                isSuccess = false
                                                colorSnackBar.value = Color.Red
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Произошла ошибка при скачивании",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                }
                                            }
                                        }.join()
                                        if(isSuccess) {
                                            colorSnackBar.value = Color.Green
                                            scope.launch {
                                                apiClient.saveFile(context, bookPage.value.uuid, baos)
                                                Log.d("SaveFile", "${bookPage.value.uuid}.pdf success saved")
                                                text.value = "Читать"
                                                snackbarHostState.showSnackbar(
                                                    message = "Книга успешно скачана!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    }
                                    if(text.value.startsWith("Читать")) {
                                        val file = File(context.filesDir, "${bookPage.value.uuid}.pdf")
                                        val uri = Uri.fromFile(file).toString()
                                        val encode = URLEncoder.encode(uri, StandardCharsets.UTF_8.toString())
                                        navController.navigate("readPage/${encode}")
                                    }
                                },
                                modifier = Modifier.height(45.dp),
                                fontsize = 14.sp
                            )
                            if(openDialog.value)
                                AlertDialog(
                                    onDismissRequest = {
                                        openDialog.value = false
                                    },
                                    title = { Text(text = "Подтверждение") },
                                    text = {
                                        Text(text = "Вы уверены что хотите купить книгу?")
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                thread {
                                                    try {
                                                        val response = apiClient.buyBook(bookPage.value.uuid, token)
                                                        if(response == 201) {
                                                            listLibraryBooks.add(bookPage.value)
                                                            val newBalance = apiClient.getMeMoney(token) as User
                                                            user.moneyRub = newBalance.moneyRub
                                                            bookPage.value.isBuyed = apiClient.checkBuy(bookPage.value.uuid, accessToken = token.accessToken)
                                                            colorSnackBar.value = Color.Green
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Книга успешно приобретена!",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                        if(response == 401) {
                                                            colorSnackBar.value = Color.Red
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Пользователь не авторизован",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                        if(response == 409) {
                                                            colorSnackBar.value = Color.Red
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Книга уже приобретена!",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                        if(response == 500) {
                                                            colorSnackBar.value = Color.Red
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Недостаточно средств для совершения покупки!",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                    } catch (e: IOException) {
                                                        colorSnackBar.value = Color.Red
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Произошла ошибка, повторите попытку!",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                        }
                                                    }
                                                }.join()
                                                openDialog.value = false
                                            }
                                        ) {
                                            Text(text = "Да")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = {
                                                openDialog.value = false
                                            }
                                        ) {
                                            Text(text = "Нет")
                                        }
                                    }
                                )
                        }
                        if(bookPage.value.isBuyed) {
                            Card (
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                            ) {
                                Row {
                                    Column (
                                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                                    ) {
                                        Text(
                                            text = "Ваша",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = "оценка",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Row (
                                        modifier = Modifier.padding(top = 3.dp)
                                    ) {
                                        for(i in 1..5) {
                                            Button (
                                                onClick = {
                                                    if(review == null) {
                                                        if(user.firstName != "" && user.lastName != "" && user.secondName != "") {
                                                            rating.value = i
                                                            bookString = bookPage.value.toJson()
                                                            navController.navigate("reviewPage/${bookString}/${rating.value}")
                                                        } else {
                                                            colorSnackBar.value = Color.Red
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Заполните пожалуйста свой профиль и повторите попытку!",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        colorSnackBar.value = Color.Red
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Вы уже оставляли отзыв!",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                        }
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                                contentPadding = PaddingValues(0.dp)
                                            ) {
                                                if(i <= rating.value) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.star),
                                                        contentDescription = "star",
                                                        modifier = Modifier
                                                            .padding(start = 23.dp)
                                                            .width(35.dp)
                                                            .height(35.dp),
                                                        tint = Blue
                                                    )
                                                } else {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.star_border),
                                                        contentDescription = "star",
                                                        modifier = Modifier
                                                            .padding(start = 23.dp)
                                                            .width(35.dp)
                                                            .height(35.dp),
                                                        tint = Blue
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 5.dp)
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
                                text = bookPage.value.about,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify
                            )
                        }
                        DescriptionText(title = "Издатель", text = bookPage.value.publisher)
                        DescriptionText(title = "Год издание", text = bookPage.value.publicationDate)
                        DescriptionText(title = "Количество страниц", text = "${bookPage.value.numberOfPages}")
                        DescriptionText(title = "ISBN", text = bookPage.value.isbn)

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
                    if(bookPage.value.reviews?.isEmpty() == false) {
                        items(
                            count = bookPage.value.reviews?.size ?: 0,
                            key = {
                                bookPage.value.reviews!![it].id
                            },
                            itemContent = { index ->
                                val reviewData = bookPage.value.reviews!![index]
                                BookReview(review = reviewData)
                            }
                        )
                    } else {
                        item {
                            Text(
                                text = "Отзывы на данный момент отсутствуют. Будьте первым, кто оставит отзыв!",
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }
                }
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