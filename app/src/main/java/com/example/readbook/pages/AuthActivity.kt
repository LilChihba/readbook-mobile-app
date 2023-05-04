package com.example.readbook.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readbook.R
import com.example.readbook.ui.theme.Blue
import com.example.readbook.ui.theme.DarkGray
import com.example.readbook.ui.theme.Gray
import com.example.readbook.ui.theme.Milk
import com.example.readbook.ui.theme.backgroundSelectionColor

@Composable
fun AuthPage(
    navigateToRegPage: () -> Unit,
    navigateBack: () -> Unit,
    navigateBackToProfile: () -> Unit,
    navigateToForgotPassPage: () -> Unit
) {
    val textEmail = remember{ mutableStateOf("") }
    val textPassword = remember{ mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Milk)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { navigateBack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "back",
                    tint = Blue
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navigateBackToProfile() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "close",
                    tint = Blue
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 45.dp, top = 10.dp, end = 45.dp)
        ) {
            Row() {
                Text(
                    text = "Вход",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextForField(text = "Почта")
                TextBox(text = textEmail)

                TextForField(text = "Пароль")
                TextBox(text = textPassword)

                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Вход",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                AdditionalButton(text = "Забыл пароль", navigate = navigateToForgotPassPage)
                AdditionalButton(text = "Зарегистрироваться", navigate = navigateToRegPage)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(text: MutableState<String>) {
    var isFocus by remember { mutableStateOf(false) }

    BasicTextField(
        value = text.value,
        onValueChange = { newText -> text.value = newText },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = DarkGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 15.dp)
            .height(50.dp)
            .onFocusEvent {
                isFocus = it.isFocused
            }
            .border(
                brush = if (isFocus)
                    Brush.horizontalGradient(listOf(Blue, Blue))
                else
                    Brush.horizontalGradient(listOf(Color.LightGray, Color.LightGray)),
                width = 1.dp,
                shape = RoundedCornerShape(3.dp)
            ),
        cursorBrush = Brush.verticalGradient(listOf(Blue, Blue)),
        decorationBox = { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = text.value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = false,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember { MutableInteractionSource() },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    containerColor = if (isFocus) Color.White else Gray,
                    focusedBorderColor = Blue,
                    unfocusedBorderColor = Color.LightGray,
                    selectionColors = TextSelectionColors(
                        handleColor = Blue,
                        backgroundColor = backgroundSelectionColor
                    ),
                    cursorColor = Blue,
                ),
            )
        }
    )
}

@Composable
fun TextForField(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 14.sp
    )
}

@Composable
fun AdditionalButton(text: String, navigate: () -> Unit) {
    Button(
        onClick = { navigate() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = Blue),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .height(35.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            color = Blue,
            fontSize = 14.sp
        )
    }
}