package com.example.readbook.pages

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.readbook.R
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.Milk
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberHorizontalPdfReaderState

@Composable
fun ReadPage(
    uri: String?,
    navigateToBack: () -> Unit,
) {
    val pdfState = rememberHorizontalPdfReaderState(
        resource = ResourceType.Local(Uri.parse(uri)),
        isZoomEnable = true
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    navigateToBack()
                },
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
        Box(
            contentAlignment = Alignment.TopStart
        ) {
            HorizontalPDFReader(
                state = pdfState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.medium
                            )
                    ) {
                        Text(
                            text = "${pdfState.currentPage}/${pdfState.pdfPageCount}",
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 4.dp,
                                top = 8.dp
                            )
                        )
                    }
                }
            }
        }
    }
}