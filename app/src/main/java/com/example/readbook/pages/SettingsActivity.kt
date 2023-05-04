package com.example.readbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk

@Composable
fun SettingsPage(
    navigateToAuthPage: () -> Unit,
    navigateBack: () -> Unit
) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 10.dp)
            ) {
                Text(
                    text = "Настройки",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            }

            Column(modifier = Modifier
                .padding(start = 25.dp, top = 10.dp)
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 25.dp)
                ) {
                    Text(
                        text = "Учётная запись",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                    )
                    Button(
                        onClick = { navigateToAuthPage() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Вход или регистрация",
                            color = Blue,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 20.dp, bottom = 5.dp)
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, end = 25.dp)
                ) {
                    Text(
                        text = "О приложении",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                    )

                    Text(
                        text = "Версия 0.1",
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 15.dp)
                    )
                }
            }
        }
    }
}