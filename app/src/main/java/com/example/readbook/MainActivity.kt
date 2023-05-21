package com.example.readbook

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.readbook.navigation.MainScreen
import com.example.readbook.ui.theme.ReadbookTheme

@RequiresApi(Build.VERSION_CODES.P)
class MainActivity : ComponentActivity() {
    var pref : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Readbook)
        super.onCreate(savedInstanceState)
        setContent {
            ReadbookTheme {
                pref = getSharedPreferences("User", Context.MODE_PRIVATE)
                MainScreen(pref)
            }
        }
    }
}
