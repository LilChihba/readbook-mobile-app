package com.example.readbook.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SnackbarCustom(
    state: SnackbarHostState,
    text: String?,
    color: Color = Blue
) {
    SnackbarHost(state) {
        Snackbar(
            containerColor = Milk,
            contentColor = DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                    state.currentSnackbarData?.dismiss()
                }
        ) {
            Text(
                text = text.toString(),
                color = color
            )
        }
    }
}