package com.example.readbook

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.graphics.toColor
import com.example.readbook.bottom_navigation.BottomBarNavigation
import com.example.readbook.ui.theme.DarkGray
import com.example.readbook.ui.theme.ReadbookTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.navigationBarColor = resources.getColor(R.color.navigationBarColorMenu)
        setContent {
            ReadbookTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BottomBarNavigation()
                }
            }
        }
    }
}

