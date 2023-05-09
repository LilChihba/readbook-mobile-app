package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar

@Composable
fun RegPage(
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit
) {
    val textEmail = remember{ mutableStateOf("") }
    val textPassword = remember{ mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
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
            Row() {
                Text(
                    text = "Регистрация",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextForField(text = "Почта")
                TextBox(text = textEmail)

                TextForField(text = "Пароль")
                PassBox(textPass = textPassword)

                ButtonApp(text = "Зарегистрироваться", navigate = { /* TODO */ })
            }
        }
    }
}