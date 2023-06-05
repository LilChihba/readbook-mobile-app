package com.example.readbook.pages

import android.content.SharedPreferences
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.ui.theme.AdditionalButton
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.concurrent.thread


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthPage(
    user: User,
    token: Token,
    pref: SharedPreferences?,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateToRegPage: () -> Unit,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToForgotPassPage: () -> Unit
) {
    val textEmail = remember{ mutableStateOf("") }
    val textPassword = remember{ mutableStateOf("") }
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
                    .padding(start = 45.dp, top = 58.dp, end = 45.dp)
            ) {
                Row {
                    Text(
                        text = "Вход",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextForField(text = "Почта")
                    TextBox(text = textEmail)

                    TextForField(text = "Пароль")
                    PassBox(textPass = textPassword)

                    ButtonApp(
                        text = "Вход",
                        navigate = {
                            if(Patterns.EMAIL_ADDRESS.matcher(textEmail.value).matches()) {
                                val th = thread {
                                    val tokenJSON = ApiClient().auth(textEmail.value, textPassword.value) as Token
                                    with(token) {
                                        accessToken = tokenJSON.accessToken
                                        accessTokenExpiresIn = tokenJSON.accessTokenExpiresIn
                                        refreshToken = tokenJSON.refreshToken
                                        refreshTokenExpiresIn = tokenJSON.refreshTokenExpiresIn
                                        date = Instant.now()
                                    }
                                    val userJSON = ApiClient().getMe(token) as User
                                    with(user) {
                                        username = userJSON.username
                                        firstName = userJSON.firstName
                                        secondName = userJSON.secondName
                                        lastName = userJSON.lastName
                                        avatar = ApiClient().getMeAvatar(user.username)
                                        email = ApiClient().getMeEmail(user.username, token)!!.email
                                    }
                                    with(pref!!.edit()) {
                                        putString("accessToken", token.accessToken)
                                        putInt("accessTokenExpiresIn", token.accessTokenExpiresIn)
                                        putString("refreshToken", token.refreshToken)
                                        putInt("refreshTokenExpiresIn", token.refreshTokenExpiresIn)
                                        putLong("date", token.date.toEpochMilli())
                                        apply()
                                    }

                                    colorSnackBar.value = Color.Green
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Вы успешно авторизовались!",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                th.join()
                                navigateBackToProfile()
                            }
                            else {
                                colorSnackBar.value = Color.Red
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Введите корректную почту!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        snackbarHostState = snackbarHostState,
                        colorSnackBar = colorSnackBar,
                        mail = textEmail.value,
                        password = textPassword.value,
                        pref = pref
                    )
                    AdditionalButton(
                        text = "Забыл пароль",
                        navigate = navigateToForgotPassPage,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(35.dp)
                    )
                    AdditionalButton(
                        text = "Зарегистрироваться",
                        navigate = navigateToRegPage,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(35.dp)
                    )
                }
            }
        }
    }
}