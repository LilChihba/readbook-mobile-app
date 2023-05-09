package com.example.readbook.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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