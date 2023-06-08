package com.example.readbook.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePage(
    user: User,
    token: Token?,
    navigateToSettingsPage: () -> Unit,
    navigateToAuthPage: () -> Unit
) {
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
                        .diskCacheKey("avatar_user_${Instant.now()}")
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

                if(token?.accessToken == "")
                    ButtonApp(text = "Вход или регистрация", onClick = navigateToAuthPage)
            }
        }
    }
}