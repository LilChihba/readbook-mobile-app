package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Review
import com.example.readbook.models.Token
import com.example.readbook.models.toJson
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Milk
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewPage(
    navigateBack: () -> Unit,
    navController: NavHostController,
    book: Book,
    token: Token,
    apiClient: ApiClient,
    rating: Int?,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
) {
    val textPreview = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        Color.Gray,
                        Offset.Zero.copy(y = size.height),
                        Offset(size.width, size.height),
                        1f
                    )
                }
        ) {
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

            Text(
                text = "Отзыв",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 9.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    var isSuccess = false
                    thread {
                        try {
                            apiClient.postReview(book.uuid.toString(), rating!!, textPreview.value, token.accessToken)
                            book.reviews = (apiClient.getReviews(book.uuid) as List<*>).filterIsInstance<Review>()
                            isSuccess = true
                            colorSnackBar.value = Color.Green
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Отзыв успешно добавлен! Благодарим за оценку",
                                    duration = SnackbarDuration.Short
                                )
                            }
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
                        val bookString = book.toJson()
                        navController.navigate("bookPage/$bookString")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = "ОПУБЛИКОВАТЬ",
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        OutlinedTextField(
            value = textPreview.value,
            onValueChange = { textPreview.value = it },
            placeholder = {
                Text(
                    text = "Напишите ваш отзыв об этой книге",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}