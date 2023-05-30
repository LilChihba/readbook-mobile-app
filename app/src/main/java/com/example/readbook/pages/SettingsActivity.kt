package com.example.readbook.pages

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R
import com.example.readbook.models.AuthUser
import com.example.readbook.ui.theme.AdditionalButton
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk

@Composable
fun SettingsPage(
    authUser: AuthUser,
    pref: SharedPreferences?,
    navigateToAuthPage: () -> Unit,
    navigateBack: () -> Unit,
    navigateToProfileEdit: () -> Unit
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
                .padding(start = 25.dp, top = 15.dp, end = 25.dp)
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Учётная запись",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                    )
                    if(!authUser.auth)
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
                    else {
                        Column {
                            Button(
                                onClick = { navigateToProfileEdit() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(0.dp),
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(id = authUser.photo),
                                        contentDescription = "Avatar",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                    )
                                    Column(
                                        modifier = Modifier.padding(start = 10.dp)
                                    ) {
                                        Text(
                                            text = "${authUser.firstName} ${authUser.lastName}",
                                            fontSize = 12.sp,
                                            color = Blue
                                        )
                                        Text(
                                            text = authUser.mail,
                                            fontSize = 12.sp,
                                            color = Blue,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier.weight(1.0f)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_right),
                                        contentDescription = "arrow",
                                        tint = Blue,
                                        modifier = Modifier
                                            .size(35.dp)
                                    )
                                }
                            }
                            Divider(
                                color = Color.Gray,
                                thickness = (0.25).dp
                            )
                            AdditionalButton(
                                text = "Выйти",
                                fontSize = 12.sp,
                                navigate = {
                                    authUser.exit()
                                    with(pref!!.edit()) {
                                        putString("mail", "")
                                        putString("password", "")
                                        apply()
                                    }

                                    navigateBack()
                                },
                                modifier = Modifier
                                    .padding(15.dp)
                                    .height(25.dp)
                            )
                        }
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp)
                ) {
                    Text(
                        text = "О приложении",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 20.dp)
                    )

                    Text(
                        text = "Версия 0.1",
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 12.dp, bottom = 15.dp)
                    )
                }
            }
        }
    }
}