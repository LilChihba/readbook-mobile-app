package com.example.readbook.pages

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegPage(
    navController: NavHostController,
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit
) {
    val textEmail = remember{ mutableStateOf("") }
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
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            TopNavigationBar(
                navigateBack = navigateBack,
                navigateBackToProfile = navigateBackToProfile
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, top = 58.dp, end = 45.dp)
            ) {
                Text(
                    text = "Регистрация",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = "Введите вашу почту, чтобы мы отправили письмо с дальнейшими инструкциями",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextForField(text = "Почта")
                    TextBox(text = textEmail, keyboardType = KeyboardType.Email)

                    ButtonApp(
                        text = "Зарегистрироваться",
                        onClick = {
                            var error = false
                            if(Patterns.EMAIL_ADDRESS.matcher(textEmail.value).matches()) {
                                thread {
                                    try {
                                        apiClient.getRegToken(textEmail.value)
                                    } catch (e: IOException) {
                                        error = true
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Произошла ошибка на сервере",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                        return@thread
                                    }
                                }.join()
                            } else {
                                colorSnackBar.value = Color.Red
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Введите корректную почту!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                            if(!error) {
                                navController.navigate("codeRegPage")
                            }
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodePage(
    apiClient: ApiClient,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateBackToAuth: () -> Unit
) {
    val textCode = remember{ mutableStateOf("") }
    val textPassword = remember{ mutableStateOf("") }
    val textRepeatPassword = remember{ mutableStateOf("") }
    val textUsername = remember{ mutableStateOf("") }
    val checkedState = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"№;%:?*()_+-=@#\$^&<>,./{}\\[\\]])(?=\\S+\$).{8,}\$"
    val usernamePattern = "^[A-Za-z]+([A-Za-z0-9._-])*.{1,20}\$"

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
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            TopNavigationBar(
                navigateBack = navigateBack,
                navigateBackToProfile = navigateBackToProfile
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, top = 58.dp, end = 45.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Регистрация",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = "Пароль должен быть без пробелов и содержать: \n" +
                            "🔸 8 и более символов\n" +
                            "🔸 прописные латинские буквы\n" +
                            "🔸 строчные латинские буквы\n" +
                            "🔸 цифры\n" +
                            "🔸 специальные символы (!\"№;%:?*()_+-=@#$^&<>,./{}[])\n" +
                            "Никнейм должен содержать:\n" +
                            "🔸 не больше 20 символов\n" +
                            "🔸 начинаться с латинской буквы\n" +
                            "🔸 допустимые специальные символы (. - _)",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextForField(text = "Код")
                    TextBox(text = textCode, keyboardType = KeyboardType.Number)

                    TextForField(text = "Никнейм")
                    TextBox(text = textUsername, keyboardType = KeyboardType.Email)

                    TextForField(text = "Пароль")
                    PassBox(textPass = textPassword)

                    TextForField(text = "Повторите пароль")
                    PassBox(textPass = textRepeatPassword)

                    Row {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it }
                        )
                        Text(
                            text = "Согласен на обработку персональных данных",
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                        )
                    }

                    ButtonApp(
                        text = "Зарегистрироваться",
                        modifier = Modifier.padding(bottom = 15.dp),
                        onClick = {
                            var isSuccess = false
                            thread {
                                if(textPassword.value != "" && textCode.value != "" && textRepeatPassword.value != "" && textUsername.value != "") {
                                    if(Pattern.compile(usernamePattern).matcher(textUsername.value).matches()) {
                                        if(Pattern.compile(passwordPattern).matcher(textPassword.value).matches()) {
                                            if(textPassword.value == textRepeatPassword.value) {
                                                if(checkedState.value) {
                                                    try {
                                                        apiClient.registration(textCode.value, textUsername.value, textPassword.value)
                                                        isSuccess = true
                                                    } catch (e: IOException) {
                                                        isSuccess = false
                                                        colorSnackBar.value = Color.Red
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Произошла ошибка при регистрации!",
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
                                                            message = "Для регистрации необходимо соглашение на обработку персональных данных",
                                                            duration = SnackbarDuration.Short
                                                        )
                                                    }
                                                }
                                            } else {
                                                isSuccess = false
                                                colorSnackBar.value = Color.Red
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Пароли не совпадают",
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
                                    } else {
                                        isSuccess = false
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Никнейм не соответствует требованиям!",
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
                            if(isSuccess)
                            {
                                colorSnackBar.value = Color.Green
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Вы успешно зарегистрировались!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                navigateBackToAuth()
                            }
                        },
                    )
                }
            }
        }
    }
}