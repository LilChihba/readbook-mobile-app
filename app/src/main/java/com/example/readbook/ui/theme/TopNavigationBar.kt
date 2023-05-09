package com.example.readbook.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.readbook.R

@Composable
fun TopNavigationBar(
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
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

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateBackToProfile() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "close",
                tint = Blue
            )
        }
    }
}