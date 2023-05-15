package com.example.readbook.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.readbook.models.GenreItem
import com.example.readbook.ui.theme.GenreCard
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.SearchBox

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchPage() {
    val textSearch = remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val listGenres = listOf(
        GenreItem.Genre1,
        GenreItem.Genre2,
        GenreItem.Genre3,
        GenreItem.Genre4,
        GenreItem.Genre5,
        GenreItem.Genre6,
        GenreItem.Genre7,
        GenreItem.Genre8,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { keyboardController?.hide() }
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(start = 25.dp, top = 25.dp, end = 25.dp),
            containerColor = Milk
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
            ) {
                TopAppBar(
                    title = {},
                    actions = { SearchBox(text = textSearch) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Milk
                    ),
                    scrollBehavior = scrollBehavior
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    if (textSearch.value == "")
                        items(listGenres) { genre ->
                            GenreCard(genre = genre)
                        }
                    else
                        item {
                            Text(text = "Поиск")
                        }
                }
            }
        }
    }
}