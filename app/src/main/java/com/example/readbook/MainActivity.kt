package com.example.readbook

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Book
import com.example.readbook.models.Genre
import com.example.readbook.models.Internet
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.navigation.MainScreen
import com.example.readbook.ui.theme.DarkGray
import com.example.readbook.ui.theme.ReadbookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.Instant
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.P)
class MainActivity : ComponentActivity() {
    var pref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Readbook)
        super.onCreate(savedInstanceState)

        setContent {
            ReadbookTheme {
                pref = getSharedPreferences("Token", Context.MODE_PRIVATE)

                val openDialogInternet = remember { mutableStateOf(false) }
                val openDialogServer = remember { mutableStateOf(false) }

                val apiClient = ApiClient()
                val mutableListBooksAddedOn: MutableList<Book> = remember { mutableListOf() }

                var getLibraryBooks: Any?
                var getListLibraryBooks: List<Book>?
                val mutableListLibraryBooks: MutableList<Book> = remember { mutableListOf() }

                val user = User()
                val snackbarHostState = SnackbarHostState()
                val colorSnackBar = remember { mutableStateOf(DarkGray) }
                val context = LocalContext.current
                val activity = (context as? Activity)

                val listGenres = Genre().getAll()
                val genreBooks: MutableList<Book> = remember { mutableListOf() }

                val token = Token(
                    pref!!.getString("accessToken", "").toString(),
                    pref!!.getInt("accessTokenExpiresIn", 0),
                    pref!!.getString("refreshToken", "").toString(),
                    pref!!.getInt("refreshTokenExpiresIn", 0),
                    pref!!.getLong("date", Instant.ofEpochMilli(0).epochSecond)
                )
                var thOne = Thread()
                var thTwo = Thread()
                var thThree = Thread()
                var thFour = Thread()
                val scope = rememberCoroutineScope()

                if(!Internet().isOnline(context)) {
                    openDialogInternet.value = true
                    AlertDialog(
                        onDismissRequest = {
                            activity?.finish()
                            openDialogInternet.value = false
                        },
                        title = { Text(text = "Ошибка") },
                        text = { Text(text = "Отсутствует соединение с интернетом!") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    openDialogInternet.value = false
                                    activity?.finish()
                                }
                            ) {
                                Text(text = "Выйти")
                            }
                        }
                    )
                } else {
                    thOne = thread {
                        val getBooksAddedOn: Any?
                        val getListBooksAddedOn: List<Book>?
                        try {
                            getBooksAddedOn = apiClient.getBooksAddedOn()
                            if (getBooksAddedOn is List<*>) {
                                getListBooksAddedOn = (getBooksAddedOn as List<*>).filterIsInstance<Book>()
                                for (i in getListBooksAddedOn) {
                                    mutableListBooksAddedOn.add(i)
                                }
                            }
                        } catch (e: IOException) {
                            scope.launch {
                                withContext(Dispatchers.Main) {
                                    openDialogServer.value = true
                                }
                            }
                        }
                    }

                    thTwo = thread {

                    }

                    thThree = thread {

                    }

                    thFour = thread {
                        val nowDate = Instant.now()
                        Log.d("DateTime", "Now: $nowDate UTC; AccessTokenExpireIn: ${Instant.ofEpochSecond(token.date).plusSeconds(token.accessTokenExpiresIn.toLong())}")
                        Log.d("DateTime", "Now: $nowDate UTC; RefreshTokenExpireIn: ${Instant.ofEpochSecond(token.date).plusSeconds(token.refreshTokenExpiresIn.toLong())}")
                        if(token.accessToken != "") {
                            if (token.isAccessTokenExpired(nowDate)) {
                                try {
                                    if (!token.isRefreshTokenExpired(nowDate)) {
                                        token.copy(apiClient.updateToken(token) as Token)
                                        token.save(pref)
                                        user.copy(apiClient.getMe(token) as User, token, apiClient)
                                        getLibraryBooks = apiClient.getLibraryBooks(token)
                                        if (getLibraryBooks is List<*>) {
                                            getListLibraryBooks = (getLibraryBooks as List<*>).filterIsInstance<Book>()
                                            for (i in getListLibraryBooks!!) {
                                                mutableListLibraryBooks.add(i)
                                            }
                                        }
                                    } else {
                                        user.delete()
                                        token.delete()
                                        token.save(pref)
                                    }
                                } catch (e: IOException) {
                                    user.delete()
                                    token.delete()
                                    token.save(pref)
                                    scope.launch {
                                        withContext(Dispatchers.Main) {
                                            openDialogServer.value = true
                                        }
                                    }
                                }
                            } else {
                                try {
                                    user.copy(apiClient.getMe(token) as User, token, apiClient)
                                    getLibraryBooks = apiClient.getLibraryBooks(token)
                                    if (getLibraryBooks is List<*>) {
                                        getListLibraryBooks = (getLibraryBooks as List<*>).filterIsInstance<Book>()
                                        for (i in getListLibraryBooks!!) {
                                            mutableListLibraryBooks.add(i)
                                        }
                                    }
                                } catch (_: Throwable) {
                                    scope.launch {
                                        withContext(Dispatchers.Main) {
                                            openDialogServer.value = true
                                        }
                                    }
                                }
                            }
                        } else {
                            user.delete()
                            token.delete()
                            token.save(pref)
                        }
                    }
                }

                thOne.join()
                thTwo.join()
                thThree.join()
                thFour.join()

                if(openDialogServer.value) {
                    AlertDialog(
                        onDismissRequest = {
                            openDialogServer.value = false
                            activity?.finish()
                        },
                        title = { Text(text = "Ошибка") },
                        text = { Text(text = "Отсутствует соединение с сервером!") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    openDialogServer.value = false
                                    activity?.finish()
                                }
                            ) {
                                Text(text = "Выйти")
                            }
                        }
                    )
                }

                MainScreen(
                    pref,
                    mutableListBooksAddedOn,
                    token,
                    snackbarHostState,
                    apiClient,
                    user,
                    colorSnackBar,
                    mutableListLibraryBooks,
                    listGenres,
                    genreBooks,
                    context
                )
            }
        }
    }
}
