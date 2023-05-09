package com.example.readbook.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextForField(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 14.sp
    )
}