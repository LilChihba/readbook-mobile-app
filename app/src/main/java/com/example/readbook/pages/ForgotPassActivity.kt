package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar

var textEmail: MutableState<String> = mutableStateOf("")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassPage(
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToCodePage: () -> Unit
) {
    textEmail = remember { mutableStateOf("") }
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
        TopNavigationBar(
            navigateBack = navigateBack,
            navigateBackToProfile = navigateBackToProfile
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 45.dp, top = 10.dp, end = 45.dp)
        ) {
            Column() {
                Text(
                    text = "Восстановление доступа к аккаунту",
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

                TextForField(text = "Почта")
                TextBox(text = textEmail)

                ButtonApp(text = "Восстановить пароль", navigate = navigateToCodePage)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassPage_Code(
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToChangePassPage: () -> Unit
) {
    val textCode = remember { mutableStateOf("") }
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
        TopNavigationBar(
            navigateBack = navigateBack,
            navigateBackToProfile = navigateBackToProfile
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 45.dp, top = 10.dp, end = 45.dp)
        ) {
            Column() {
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
            TextBox(text = textCode)

            ButtonApp(text = "Восстановить пароль", navigate = navigateToChangePassPage)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassPage_ChangePass(
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToAuthPage: () -> Unit
) {
    val textPass = rememberSaveable { mutableStateOf("") }
    val repeatTextPass = rememberSaveable { mutableStateOf("") }
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
        TopNavigationBar(
            navigateBack = navigateBack,
            navigateBackToProfile = navigateBackToProfile
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 45.dp, top = 10.dp, end = 45.dp)
        ) {
            Column() {
                Text(
                    text = "Восстановление доступа к аккаунту",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Введите новый пароль и повторите его ещё раз",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
            }

            TextForField(text = "Введите пароль")
            PassBox(textPass = textPass)

            TextForField(text = "Повторите пароль")
            PassBox(textPass = repeatTextPass)

            ButtonApp(text = "Восстановить пароль", navigate = navigateToAuthPage)
        }
    }
}