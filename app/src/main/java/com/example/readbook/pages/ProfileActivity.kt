package com.example.readbook.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readbook.R
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Milk

@Composable
fun ProfileNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.profilePage
    ) {
        composable(route = Route.profilePage) {
            ProfilePage(
                navigateToSettingsPage = { navController.navigate(Route.settingsPage) },
                navigateToAuthPage = { navController.navigate(Route.authPage) }
            )
        }

        composable(route = Route.settingsPage) {
            SettingsPage(
                navigateToAuthPage = { navController.navigate(Route.authPage) },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Route.authPage) {
            AuthPage(
                navigateToRegPage = { navController.navigate(Route.regPage) },
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) }
            )
        }

        composable(route = Route.regPage) {
            RegPage(
                navigateBack = { navController.popBackStack() },
                navigateBackToProfile = { navController.popBackStack(
                    route = Route.profilePage,
                    inclusive = false
                ) }
            )
        }
    }
}

@Composable
fun ProfilePage(
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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.selfy),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(250.dp)
                )

                Text(
                    text = "Гость",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Button(
                    onClick = { navigateToAuthPage() },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(45.dp)
                        .width(275.dp)
                ) {
                    Text(
                        text = "Вход или регистрация",
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}