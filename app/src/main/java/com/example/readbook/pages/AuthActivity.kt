package com.example.readbook.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AuthPage(
    navigateToRegPage: () -> Unit,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit
) {
    Text(text = "AuthPage")
}