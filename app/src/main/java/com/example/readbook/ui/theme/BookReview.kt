package com.example.readbook.ui.theme

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R


//@Composable
//fun BookReview(
//    review: Review
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 20.dp)
//    ) {
//        Row {
//            Image(
//                painter = painterResource(id = R.drawable.avatar_male),
//                contentDescription = "avatar",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//            )
//
//            Text(
//                text = review.nickname,
//                color = Color.Black,
//                fontWeight = FontWeight.Normal,
//                fontSize = 14.sp,
//                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
//            )
//        }
//        Row(
//            modifier = Modifier
//                .padding(top = 8.dp, bottom = 5.dp)
//        ) {
//            RatingBar(
//                rating = review.rating,
//                modifier = Modifier
//                    .width(18.dp)
//                    .height(18.dp)
//            )
//            Text(
//                text = review.date,
//                fontSize = 10.sp,
//                color = Blue,
//                modifier = Modifier
//                    .padding(start = 5.dp, top = 2.dp)
//            )
//        }
//        Row(
//            modifier = Modifier
//                .padding(bottom = 8.dp)
//        ) {
//            Text(
//                text = review.content,
//                textAlign = TextAlign.Left,
//                color = DarkGray,
//                fontSize = 14.sp
//            )
//        }
//        Row(
//            modifier = Modifier.width(100.dp)
//        ) {
//            Divider(
//                color = Color.Gray,
//                thickness = (0.25).dp
//            )
//        }
//    }
//}