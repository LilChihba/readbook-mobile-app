package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.TextBox
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.concurrent.thread

@Composable
fun ProfilePage(
    user: User,
    token: Token?,
    navigateToSettingsPage: () -> Unit,
    navigateToAuthPage: () -> Unit,
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>
) {
    val openDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navigateToSettingsPage() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    tint = Blue
                )

                Text(
                    text = "Настройки",
                    textAlign = TextAlign.Center,
                    color = Blue,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(top = 15.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, top = 10.dp, end = 45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if(user.avatar?.asImageBitmap() != null) user.avatar else R.drawable.default_avatar)
                        .crossfade(true)
                        .diskCacheKey("avatar_user_${user.username}")
                        .build(),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.default_avatar),
                    error = painterResource(id = R.drawable.default_avatar),
                    filterQuality = FilterQuality.High,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(250.dp)
                )

                Text(
                    text = user.username,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.padding(top = 15.dp)
                )

                if(token?.accessToken == "") {
                    ButtonApp(text = "Вход или регистрация", onClick = navigateToAuthPage)
                }
                else {
                    Row {
                        Text(
                            text = "Ваш баланс: ${user.moneyRub} руб.",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 15.dp)
                        )
                        Button(
                            onClick = { openDialog.value = true },
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Blue),
                            modifier = Modifier
                                .padding(top = 5.dp, start = 10.dp)
                                .size(45.dp)
                        ) {
                            Text(
                                text = "+",
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(0.dp)
                            )
                        }

                        if(openDialog.value) {
                            val text = remember { mutableStateOf("") }
                            AlertDialog(
                                onDismissRequest = { openDialog.value = false },
                                title = { Text(text = "Реквизиты для пополнения") },
                                text = {
                                    val sdf = SimpleDateFormat("dd.MM.yyyy")
                                    Column {
                                        Text(
                                            text = "Переведите денежные средства на карту (Сбербанк):\n" +
                                                    "🔸 4279 3806 2197 7414 (Данила Алексеевич Ю.)\n" +
                                                    "🔸 Укажите в комментариях:\n" +
                                                    " 🔹 Никнейм: ${user.username}\n" +
                                                    " 🔹 Дата: ${sdf.format(Date())}",
                                        )
                                        TextBox(text = text, placeholder = "Сумма", keyboardType = KeyboardType.Number)
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            thread {
                                                try {
                                                    if(text.value != "") {
                                                        apiClient.deposit(text.value.toInt(), user.username)
                                                        val newBalance = apiClient.getMeMoney(token) as User
                                                        user.moneyRub = newBalance.moneyRub
                                                        colorSnackBar.value = Color.Green
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Ваш баланс пополнен!",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                        }
                                                    }
                                                } catch (_: Throwable) {
                                                    colorSnackBar.value = Color.Red
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Произошла ошибка",
                                                            duration = SnackbarDuration.Short
                                                        )
                                                    }
                                                } finally {
                                                    openDialog.value = false
                                                }
                                            }.join()
                                        }
                                    ) {
                                        Text(text = "Проверить")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}