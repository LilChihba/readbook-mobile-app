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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.models.AuthUser
import com.example.readbook.ui.theme.AdditionalButton
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.PassBox
import com.example.readbook.ui.theme.TextBox
import com.example.readbook.ui.theme.TextForField
import com.example.readbook.ui.theme.TopNavigationBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileEditPage(
    authUser: AuthUser,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFName = remember { mutableStateOf(authUser.firstName) }
    val textMName = remember { mutableStateOf(authUser.middleName) }
    val textLName = remember { mutableStateOf(authUser.lastName) }
    val textOldPass = remember { mutableStateOf("") }
    val textNewPass = remember { mutableStateOf("") }
    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

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
                        if(Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        }
                        else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btm ->
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .size(180.dp)
                            )
                        }
                    }
                }
                AdditionalButton(
                    text = "Сменить фото",
                    modifier = Modifier.padding(top = 20.dp),
                    navigate = { launcher.launch("image/*") }
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
                        TextBox(text = textFName)
                        TextForField(text = "Фамилия")
                        TextBox(text = textLName)
                        TextForField(text = "Отчество")
                        TextBox(text = textMName)

                        AdditionalButton(
                            text = "Сохранить",
                            modifier = Modifier.padding(bottom = 20.dp),
                            navigate = { }
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
                            text = authUser.mail,
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

                        AdditionalButton(
                            text = "Изменить",
                            modifier = Modifier.padding(bottom = 20.dp),
                            navigate = { }
                        )
                    }
                }
            }
        }
    }
}