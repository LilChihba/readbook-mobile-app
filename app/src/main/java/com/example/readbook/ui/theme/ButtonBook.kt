package com.example.readbook.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.readbook.R

@Preview
@Composable
fun ButtonBook() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .width(110.dp)
            .height(150.dp)
            .padding(start = 10.dp, top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.book),
            contentDescription = "Book",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
        )
    }
}