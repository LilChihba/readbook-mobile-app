package com.example.readbook.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonApp(
    text: String,
    navigate: () -> Unit,
    modifier: Modifier = Modifier,
    fontsize: TextUnit = 16.sp
) {
    Button(
        onClick = { navigate() },
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        shape = RoundedCornerShape(7.dp),
        modifier = modifier
            .padding(top = 10.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = fontsize
        )
    }
}