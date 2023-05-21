package com.example.readbook.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readbook.mail.Code
import com.example.readbook.mail.sendCodeInEmail
import com.example.readbook.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@Composable
fun ButtonApp(
    text: String,
    navigate: () -> Unit? = {},
    navController: NavHostController? = null,
    mail: String = "",
    code: String = "",
    password: String = "",
    repeatPassword: String = "",
    snackbarHostState: SnackbarHostState? = null,
    modifier: Modifier = Modifier,
    fontsize: TextUnit = 16.sp
) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            when(text){
                "Отправить письмо" -> {
                    if(mail != "") {
                        if(UserRepository().getUserByMail(mail = mail) != null) {
                            thread {
                                sendCodeInEmail(mail)
                            }
                            navController?.navigate("codePage/" + mail)
                        }
                        else
                            scope.launch {
                                snackbarHostState?.showSnackbar(
                                    message = "Аккаунта с данной почтой не существует. Зарегистрируйтесь!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                    }
                    else
                        scope.launch {
                            snackbarHostState?.showSnackbar(
                                message = "Это поле не может быть пустым!",
                                duration = SnackbarDuration.Short
                            )
                        }
                }
                "Проверить код" -> {
                    if(code != "")
                        if(code == Code.value)
                            navController?.navigate("changePassPage/" + mail)
                        else
                            scope.launch {
                                snackbarHostState?.showSnackbar(
                                    message = "Введён неверный код!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                    else
                        scope.launch {
                            snackbarHostState?.showSnackbar(
                                message = "Это поле не может быть пустым!",
                                duration = SnackbarDuration.Short
                            )
                        }
                }
                "Сменить пароль" -> {
                    if(password != "" && repeatPassword != "")
                        if(password == repeatPassword) {
                            UserRepository().updatePass(
                                UserRepository().getUserByMail(mail = mail),
                                password
                            )
                            navigate()
                        }
                        else
                            scope.launch {
                                snackbarHostState?.showSnackbar(
                                    message = "Пароли не совпадают, повторите попытку!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                    else
                        scope.launch {
                            snackbarHostState?.showSnackbar(
                                message = "Эти поля не могут быть пустыми!",
                                duration = SnackbarDuration.Short
                            )
                        }
                }
                else -> {
                    navigate()
                }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        shape = RoundedCornerShape(7.dp),
        modifier = modifier
            .padding(top = 10.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = fontsize
        )
    }
}