package com.example.readbook.pages

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R
import com.example.readbook.models.ApiClient
import com.example.readbook.models.Token
import com.example.readbook.models.User
import com.example.readbook.ui.theme.AdditionalButton
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.regex.Pattern
import kotlin.concurrent.thread

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileEditPage(
    user: User,
    apiClient: ApiClient,
    token: Token,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    snackbarHostState: SnackbarHostState,
    colorSnackBar: MutableState<Color>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFName = remember { mutableStateOf(user.firstName) }
    val textMName = remember { mutableStateOf(user.secondName) }
    val textLName = remember { mutableStateOf(user.lastName) }
    val textOldPass = remember { mutableStateOf("") }
    val textNewPass = remember { mutableStateOf("") }
    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf(user.avatar) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Milk)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { keyboardController?.hide() }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            TopNavigationBar(
                navigateBack = navigateBack,
                navigateBackToProfile = navigateBackToProfile
            )
            Column(
                modifier = Modifier
                    .padding(start = 25.dp, top = 58.dp, end = 25.dp)
            ) {
                Text(
                    text = "Учетная запись",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    imageUri?.let {
                        val image: Bitmap?
                        val defaultImage = user.avatar

                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                            user.avatar = bitmap.value
                            image = bitmap.value
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                            user.avatar = bitmap.value
                            image = bitmap.value
                        }
                        thread {
                            try {
                                apiClient.changeAvatar(image, context = context, accessToken = token.accessToken)
                                colorSnackBar.value = Color.Green
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Аватарка успешно изменена!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } catch (e: IOException) {
                                bitmap.value = defaultImage
                                colorSnackBar.value = Color.Red
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Произошла ошибка при смене аватарки! Повторите попытку",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }.join()
                        imageUri = null
                    }
                    if(bitmap.value != null) {
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(200.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.default_avatar),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(200.dp)
                        )
                    }
                }
                AdditionalButton(
                    text = "Сменить фото",
                    modifier = Modifier.padding(top = 20.dp),
                    navigate = {
                        launcher.launch("image/*")
                    }
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Ваши данные",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 10.dp)
                        )
                        TextForField(text = "Имя")
                        TextBox(text = textFName, keyboardType = KeyboardType.Text)
                        TextForField(text = "Фамилия")
                        TextBox(text = textLName, keyboardType = KeyboardType.Text)
                        TextForField(text = "Отчество")
                        TextBox(text = textMName, keyboardType = KeyboardType.Text)

                        AdditionalButton(
                            text = "Сохранить",
                            modifier = Modifier.padding(bottom = 20.dp),
                            navigate = {
                                thread {
                                    try {
                                        apiClient.changeDataUser(textFName.value, textMName.value, textLName.value, user.username, token.accessToken)
                                        with(user) {
                                            firstName = textFName.value
                                            secondName = textMName.value
                                            lastName = textLName.value
                                        }
                                        colorSnackBar.value = Color.Green
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Вы успешно изменили ФИО!",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } catch (e: IOException) {
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Произошла ошибка при смене ФИО! Повторите попытку",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }.join()
                            }
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Электронная почта",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 10.dp)
                        )

                        Text(
                            text = user.email,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Изменить пароль",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 10.dp)
                        )
                        TextForField(text = "Старый пароль")
                        PassBox(textPass = textOldPass)
                        TextForField(text = "Новый пароль")
                        PassBox(textPass = textNewPass)

                        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"№;%:?*()_+-=@#\$^&<>,./{}\\[\\]])(?=\\S+\$).{8,}\$"

                        AdditionalButton(
                            text = "Изменить",
                            modifier = Modifier.padding(bottom = 20.dp),
                            navigate = {
                                thread {
                                    try {
                                        if(textNewPass.value != "" || textOldPass.value != "") {
                                            if(textNewPass.value != textOldPass.value) {
                                                if(Pattern.compile(passwordPattern).matcher(textNewPass.value).matches()) {
                                                    apiClient.changePasswordUser(textOldPass.value, textNewPass.value, token.accessToken)
                                                    colorSnackBar.value = Color.Green
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Вы успешно поменяли пароль!",
                                                            duration = SnackbarDuration.Short
                                                        )
                                                    }
                                                } else {
                                                    colorSnackBar.value = Color.Red
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Пароль не соответствует требованиям!",
                                                            duration = SnackbarDuration.Short
                                                        )
                                                    }
                                                }
                                            } else {
                                                colorSnackBar.value = Color.Red
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Пароли не должны совпадать",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                }
                                            }
                                        } else {
                                            colorSnackBar.value = Color.Red
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Оба поля должны быть заполнены!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    } catch (e: IOException) {
                                        colorSnackBar.value = Color.Red
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Произошла ошибка при смене пароля! Повторите попытку",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }.join()
                            }
                        )
                    }
                }
            }
        }
    }
}