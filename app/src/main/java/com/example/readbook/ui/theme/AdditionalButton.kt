package com.example.readbook.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdditionalButton(
    text: String,
    fontSize: TextUnit = 14.sp,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navigate() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = Blue),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            color = Blue,
            fontSize = fontSize
        )
    }
}