package com.example.readbook.ui.theme

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.readbook.R

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier
) {
    Row() {
        for(i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "star",
                modifier = modifier
                    .width(16.dp)
                    .height(16.dp),
                tint = if(rating < i) Color.LightGray else Blue
            )
        }

    }
}