package com.bignerdranch.android.currncycalculator.ui.main.compose_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.currncycalculator.R

@Composable
fun CustomKeyboardView(
    modifier: Modifier = Modifier,
    onKeyboardButtonClick: (KeyboardKey) -> Unit,
) {
    Column(modifier.background(Color.White)) {
        Row(Modifier.fillMaxWidth()) {
            KeyboardKey(modifier = modifier.weight(1f), text = "1") { onKeyboardButtonClick(KeyboardKey.One) }
            KeyboardKey(modifier = modifier.weight(1f), text = "2") { onKeyboardButtonClick(KeyboardKey.Two) }
            KeyboardKey(modifier = modifier.weight(1f), text = "3") { onKeyboardButtonClick(KeyboardKey.Three) }
        }

        Row(Modifier.fillMaxWidth()) {
            KeyboardKey(modifier = modifier.weight(1f), text = "4") { onKeyboardButtonClick(KeyboardKey.Four) }
            KeyboardKey(modifier = modifier.weight(1f), text = "5") { onKeyboardButtonClick(KeyboardKey.Five) }
            KeyboardKey(modifier = modifier.weight(1f), text = "6") { onKeyboardButtonClick(KeyboardKey.Six) }
        }

        Row(Modifier.fillMaxWidth()) {
            KeyboardKey(modifier = modifier.weight(1f), text = "7") { onKeyboardButtonClick(KeyboardKey.Seven) }
            KeyboardKey(modifier = modifier.weight(1f), text = "8") { onKeyboardButtonClick(KeyboardKey.Eight) }
            KeyboardKey(modifier = modifier.weight(1f), text = "9") { onKeyboardButtonClick(KeyboardKey.Nine) }
        }

        Row(Modifier.fillMaxWidth()) {
            KeyboardKey(modifier = modifier.weight(1f), text = "00") { onKeyboardButtonClick(KeyboardKey.DoubleZero) }
            KeyboardKey(modifier = modifier.weight(1f), text = "0") { onKeyboardButtonClick(KeyboardKey.Zero) }
            IconButton(
                modifier = modifier
                    .weight(1f)
                    .border(0.5.dp, color = Color.LightGray, shape = RectangleShape),
                onClick = { onKeyboardButtonClick(KeyboardKey.Delete) },
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_backspace_24), "")
            }
        }
    }
}

sealed class KeyboardKey(val key: String) {
    object DoubleZero : KeyboardKey("00")
    object Zero : KeyboardKey("0")
    object One : KeyboardKey("1")
    object Two : KeyboardKey("2")
    object Three : KeyboardKey("3")
    object Four : KeyboardKey("4")
    object Five : KeyboardKey("5")
    object Six : KeyboardKey("6")
    object Seven : KeyboardKey("7")
    object Eight : KeyboardKey("8")
    object Nine : KeyboardKey("9")
    object Delete : KeyboardKey("delete")
}

@Composable
fun KeyboardKey(
    modifier: Modifier,
    text: String,
    onClick: (String) -> Unit,
) {
    Button(
        onClick = { onClick(text) },
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black,
        ),
        shape = RectangleShape,
        modifier = modifier.border(0.5.dp, color = Color.LightGray, shape = RectangleShape),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}