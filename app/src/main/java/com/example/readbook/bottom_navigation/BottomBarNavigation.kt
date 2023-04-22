package com.example.readbook.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.readbook.R
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.DarkGray

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarNavigation() {
    /* Style bottomBarNavigation */
    val fontSize = 14.sp
    val fontFamily = FontFamily(
        Font(R.font.arial, weight = FontWeight.Normal)
    )
    val iconSize = 32.dp
    val unselectedColor = Color.White
    val selectedColor = Blue
    val containerColor = DarkGray
    /*****************************/

    val tabItems = listOf(
        "Главная",
        "Избранное",
        "Поиск",
        "Профиль"
    )

    var selectedItem = remember { mutableStateOf(0)}
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = containerColor,
        ) {
            tabItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem.value == index,
                    onClick = {
                        selectedItem.value = index
                    },
                    icon = {
                        when(item) {
                           "Главная" -> Icon(painter = painterResource(id = R.drawable.home), contentDescription = null, modifier = Modifier.size(iconSize))
                           "Избранное" -> Icon(painter = painterResource(id = R.drawable.favorite), contentDescription = null, modifier = Modifier.size(iconSize))
                           "Поиск" -> Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, modifier = Modifier.size(iconSize))
                           "Профиль" -> Icon(painter = painterResource(id = R.drawable.profile), contentDescription = null, modifier = Modifier.size(iconSize))
                        }
                    },
                    label = { Text(text = item, fontSize = fontSize, fontFamily = fontFamily)},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        selectedTextColor = selectedColor,
                        indicatorColor = DarkGray,
                        unselectedIconColor = unselectedColor,
                        unselectedTextColor = unselectedColor
                    )
                )
            }
        }
    }) {
    }
}