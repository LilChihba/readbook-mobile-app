package com.example.readbook.pages

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.models.ApiClient
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.regex.Pattern
import kotlin.concurrent.thread

private var textEmail: MutableState<String> = mutableStateOf("")
private var textUsername: MutableState<String> = mutableStateOf("")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassPage(
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navController: NavHostController,
    apiClient: ApiClient
) {
    textEmail = remember { mutableStateOf("") }
    textUsername = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { keyboardController?.hide() }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopNavigationBar(
                navigateBack = navigateBack,
                navigateBackToProfile = navigateBackToProfile
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, end = 45.dp, top = 58.dp)
            ) {
                Column {
                    Text(
                        text = "Восстановление доступа к аккаунту",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Введите ваше имя аккаунта и почту, чтобы мы отправили письмо с дальнейшими инструкциями",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )

                    TextForField(text = "Почта")
                    TextBox(text = textEmail, keyboardType = KeyboardType.Email)

                    TextForField(text = "Имя аккаунта")
                    TextBox(text = textUsername, keyboardType = KeyboardType.Text)

                    ButtonApp(
                        text = "Отправить письмо",
                        onClick = {
                            var isSuccess = false
                            thread {
                                if(textEmail.value != "" || textUsername.value != "") {
                                    if(Patterns.EMAIL_ADDRESS.matcher(textEmail.value).matches()) {
                                        if(apiClient.checkUser(textUsername.value)) {
                                            try {
                                                apiClient.getCodeInEmail(textEmail.value, textUsername.value)
                                                isSuccess = true
                                            } catch (e: IOException) {
                                                isSuccess = false
                                                colorSnackBar.value = Color.Red
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Произошла ошибка при смене пароля!",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                }
                                                return@thread
                                            }
                                        } else {
                                            isSuccess = false
                                            colorSnackBar.value = Color.Red
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Пользователь с данным никнеймом не найден",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    } else {
                                        isSuccess = false
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Введите корректную почту!",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                } else {
                                    isSuccess = false
                                    colorSnackBar.value = Color.Red
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Все поля должны быть заполнены!",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }.join()
                            if(isSuccess) {
                                navController.navigate("codePage/${textEmail.value}")
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassPage_Code(
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToAuthPage: () -> Unit,
) {
    val textCode = remember { mutableStateOf("") }
    val textPass = remember { mutableStateOf("") }
    val textRepeatPass = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { keyboardController?.hide() }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopNavigationBar(
                navigateBack = navigateBack,
                navigateBackToProfile = navigateBackToProfile
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, top = 58.dp, end = 45.dp)
            ) {
                Column {
                    Text(
                        text = "Восстановление доступа к аккаунту",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Введите код, который мы отправили вам письмом на почту ${textEmail.value}",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                }

                TextForField(text = "Код")
                TextBox(text = textCode, keyboardType = KeyboardType.Number)

                TextForField(text = "Введите пароль")
                PassBox(textPass = textPass)

                TextForField(text = "Повторите пароль")
                PassBox(textPass = textRepeatPass)

                val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"№;%:?*()_+-=@#\$^&<>,./{}\\[\\]])(?=\\S+\$).{8,}\$"
                val scope = rememberCoroutineScope()

                ButtonApp(
                    text = "Сменить пароль",
                    onClick = {
                        var isSuccess = false
                        if(Pattern.compile(passwordPattern).matcher(textPass.value).matches()) {
                            if(textPass.value == textRepeatPass.value) {
                                thread {
                                    try {
                                        apiClient.changePassword(textCode.value, textPass.value, textUsername.value)
                                        isSuccess = true
                                    } catch (e: IOException) {
                                        isSuccess = false
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Произошла ошибка при смене пароля!",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                        return@thread
                                    }
                                }.join()
                            } else {
                                isSuccess = false
                                colorSnackBar.value = Color.Red
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Пароли должны совпадать!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        } else {
                            isSuccess = false
                            colorSnackBar.value = Color.Red
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Пароль не соответствует требованиям!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        if(isSuccess) {
                            navigateToAuthPage()
                            colorSnackBar.value = Color.Green
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Пароль был успешно изменён!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}