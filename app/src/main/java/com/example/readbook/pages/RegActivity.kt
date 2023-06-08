package com.example.readbook.pages

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
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
                    TextBox(text = textEmail)

                    ButtonApp(
                        text = "Зарегистрироваться",
                        onClick = {
                            thread {
                                apiClient.getRegToken(textEmail.value)
                            }.join()
                            navController.navigate("codeRegPage/${textEmail.value}")
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
    mail: String?,
    apiClient: ApiClient,
    pref: SharedPreferences?,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit
) {
    val textCode = remember{ mutableStateOf("") }
    val textPassword = remember{ mutableStateOf("") }
    val textRepeatPassword = remember{ mutableStateOf("") }
    val textUsername = remember{ mutableStateOf("") }
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextForField(text = "Код")
                    TextBox(text = textCode)

                    TextForField(text = "Никнейм")
                    TextBox(text = textUsername)

                    TextForField(text = "Пароль")
                    PassBox(textPass = textPassword)

                    TextForField(text = "Повторите пароль")
                    PassBox(textPass = textRepeatPassword)

                    ButtonApp(
                        text = "Зарегистрироваться",
                        onClick = {
                            thread {
                                if(textPassword.value == textRepeatPassword.value)
                                    apiClient.registration(textCode.value, textUsername.value, textPassword.value)
                            }.join()
                            navigateBackToProfile()
                        },
                    )
                }
            }
        }
    }
}