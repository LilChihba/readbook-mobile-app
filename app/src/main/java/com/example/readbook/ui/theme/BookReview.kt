package com.example.readbook.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.readbook.R
import com.example.readbook.models.Review
import com.example.readbook.models.URL.BASE_URL
import java.time.Instant


@Composable
fun BookReview(
    review: Review
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$BASE_URL/users/${review.author.username}/avatar")
                    .crossfade(true)
                    .diskCacheKey("avatar_user_${Instant.now()}")
                    .build(),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.default_avatar),
                error = painterResource(id = R.drawable.default_avatar),
                filterQuality = FilterQuality.High,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "${if(!review.author.lastName.isNullOrEmpty()) "${review.author.lastName} " else ""}${if(!review.author.firstName.isNullOrEmpty()) "${review.author.firstName} " else ""}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
            )
            Text(
                text = review.author.username,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 5.dp)
        ) {
            RatingBar(
                rating = review.score.toInt(),
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Text(
                text = review.created,
                fontSize = 12.sp,
                color = Blue,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = review.message,
                textAlign = TextAlign.Left,
                color = DarkGray,
                fontSize = 14.sp
            )
        }
        Row(
            modifier = Modifier.width(100.dp)
        ) {
            Divider(
                color = Color.Gray,
                thickness = (0.25).dp
            )
        }
    }
}