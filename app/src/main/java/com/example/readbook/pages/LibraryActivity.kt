package com.example.readbook.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.R
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk

@Composable
fun LibraryPage(
    navController: NavHostController,
//    listLibraryBooks: MutableList<BookLibrary>,
//    authUser: AuthUser
) {
    val count = remember{ mutableStateOf(0) }
//    val listBooks: MutableList<BookLibrary> = mutableListOf()
//    for(i in listLibraryBooks)
//        if(i.id_user == authUser.id)
//            listBooks.add(i)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .padding(start = 25.dp, top = 30.dp, end = 25.dp)
    ) {
        Text(
            text = "Мои книги",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .align(Alignment.Start)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .alpha(if(count.value == 0) 0F else 1F)
        ) {
//            LazyVerticalGrid(
//                columns = GridCells.Adaptive(minSize = 110.dp),
//                contentPadding = PaddingValues(start = 10.dp, top = 10.dp),
//                horizontalArrangement = Arrangement.Start
//            ) {
//                items(
//                    count = listBooks.size,
//                    key = {
//                        listBooks[it].id
//                    },
//                    itemContent = { index ->
//                        val bookItemData = listBooks[index]
//                        ButtonBook(bookItemData, navController = navController)
//                        count.value += 1
//                    }
//                )
//            }
        }
        if(count.value == 0)
        {
            Image(
                painter = painterResource(id = R.drawable.empty_library),
                contentDescription = "img",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Text(
                text = "У вас нет купленных книг",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}