package com.example.readbook.bottom_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Home",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Favorite(navController: NavHostController) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Favorite",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Search(navController: NavHostController) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Search",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Profile(navController: NavHostController) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Profile",
        textAlign = TextAlign.Center
    )
}