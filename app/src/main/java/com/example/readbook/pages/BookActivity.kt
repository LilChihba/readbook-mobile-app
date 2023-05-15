package com.example.readbook.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R
import com.example.readbook.repository.BookRepository
import com.example.readbook.repository.ReviewRepository
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.BookReview
import com.example.readbook.ui.theme.ButtonApp
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.RatingBar

@Composable
fun BookPage(
    bookId: Int?,
    navigateBack: () -> Unit
) {
    val book = BookRepository().getBookByID(id = bookId)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
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
        }
        LazyColumn(
            modifier = Modifier
                .background(Milk)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            item() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                ) {
                    Image(
                        painter = painterResource(id = book!!.image),
                        contentDescription = "Book",
                        modifier = Modifier
                            .width(100.dp)
                            .height(141.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = book.title,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                        Text(
                            text = "Автор: ${book.author}",
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Row() {
                            RatingBar(
                                rating = book.rating.toInt(),
                                modifier = Modifier
                                    .width(26.dp)
                                    .height(26.dp)
                            )
                            Text(
                                text = "${book.rating}",
                                color = Blue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(start = 3.dp, top = (1.25).dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                ) {
                    ButtonApp(
                        text = "Купить за ${book!!.price} руб.",
                        navigate = { /* TODO */ },
                        modifier = Modifier.height(45.dp),
                        fontsize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "О книге",
                        color = Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
                Row() {
                    Text(
                        text = book!!.description,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Justify
                    )
                }
                DescriptionText(title = "Издатель", text = book!!.publisher)
                DescriptionText(title = "Год издание", text = book.publisher_date)
                DescriptionText(title = "Количество страниц", text = "${book.pages}")
                DescriptionText(title = "ISBN", text = book.isbn)

                Row(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Оценки и отзывы",
                        color = Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }

            val reviewRepository = ReviewRepository()
            val getAllData = reviewRepository.getAllData()

            items(
                count = getAllData.size,
                key = {
                    getAllData[it].id
                },
                itemContent = { index ->
                    val reviewData = getAllData[index]
                    BookReview(review = reviewData)
                }
            )
        }
    }
}

@Composable
fun DescriptionText(
    title: String,
    text: String
) {
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(
            text = title,
            color = Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}