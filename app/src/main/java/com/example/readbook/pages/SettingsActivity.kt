package com.example.readbook.pages

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.Book
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.ui.theme.AdditionalButton
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsPage(
    user: User,
    token: Token,
    pref: SharedPreferences?,
    mutableListLibraryBooks: MutableList<Book>,
    navigateToAuthPage: () -> Unit,
    navigateBack: () -> Unit,
    navigateToProfileEdit: () -> Unit
) {
    Column(
        modifier = Modifier
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
            Row(
                modifier = Modifier
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

            Column(
                modifier = Modifier
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
                    if (token.accessToken == "")
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
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(if(user.avatar?.asImageBitmap() != null) user.avatar else R.drawable.default_avatar)
                                            .crossfade(true)
                                            .diskCacheKey("avatar_user_${Instant.now()}")
                                            .build(),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        placeholder = painterResource(id = R.drawable.default_avatar),
                                        error = painterResource(id = R.drawable.default_avatar),
                                        filterQuality = FilterQuality.High,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                    )
                                    Column(
                                        modifier = Modifier.padding(start = 10.dp)
                                    ) {
                                        Text(
                                            text = "${user.firstName} ${user.lastName}",
                                            fontSize = 12.sp,
                                            color = Blue
                                        )
                                        Text(
                                            text = user.email,
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
                                    thread {
                                        user.delete()
                                        token.delete()
                                        token.save(pref)
                                        mutableListLibraryBooks.clear()
                                    }.join()
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