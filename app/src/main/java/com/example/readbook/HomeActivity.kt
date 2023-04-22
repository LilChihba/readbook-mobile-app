package com.example.readbook

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.readbook.bottom_navigation.BottomBarNavigation
import com.example.readbook.ui.theme.ReadbookTheme

class HomeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadbookTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BottomBarNavigation()
                }
            }
        }
    }
}
