package com.bignerdranch.android.currncycalculator.ui.main.compose_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.currncycalculator.R
import com.bignerdranch.android.currncycalculator.databinding.ActivityComposeBinding
import java.text.DecimalFormat

class ComposeActivity : AppCompatActivity() {
    private lateinit var vb: ActivityComposeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityComposeBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.composeActivity.setContent {
            ScreenSetUp()
        }
    }
}

@Composable
fun ScreenSetUp(vm: ComposeViewModel = viewModel()) {
    MyApp(
        vm.originCurrency,
        vm.resultCurrency,
        vm.originRate.toString(),
        vm.resultRate.toString(),
        vm.originFlag,
        vm.resultFlag
    ) { vm.onSwap() }
    vm.calculate()
}

@Composable
fun MyApp(
    originCurrency: String,
    resultCurrency: String,
    originRateResult: String,
    resultRate: String,
    originFlag: Int,
    resultFlag: Int,
    onSwapClick: () -> Unit,
) {
    Column(Modifier.background(colorResource(id = R.color.white_))) {
        var inputText by rememberSaveable { mutableStateOf("0") }
        val formattedInput =
            remember(inputText) { mutableStateOf(formatAmount(inputText).replace(",", " ")) }
        val showDialog = rememberSaveable { mutableStateOf(false) }
        val isOrigin = rememberSaveable { mutableStateOf(false) }

        if (showDialog.value) {
            ShowDialog(setShowDialog = showDialog.value, isOrigin = isOrigin.value) {
                showDialog.value = false
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .weight(2f)

        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                FirstContainer(originCurrency, resultCurrency, resultRate, originFlag) {
                    showDialog.value = true
                    isOrigin.value = true
                }
                CustomTextField(inputText = formattedInput.value) {
                    calculateResult(
                        inputText,
                        originRateResult
                    )
                }
                BoxForSwap(onSwapClick)
                SecondContainer(originCurrency, resultCurrency, originRateResult, resultFlag) {
                    showDialog.value = true
                    isOrigin.value = false
                }
            }
            Text(
                text = formatAmount(calculateResult(inputText, originRateResult)).replace(",", " "),
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 30.dp, start = 30.dp),
            )
        }
        CustomKeyboardView(
            modifier = Modifier
                .weight(1f)
        ) {
            when (it) {
                KeyboardKey.Delete -> {
                    inputText = when {
                        inputText.isEmpty() || inputText.length == 1 -> "0"
                        else -> inputText.substring(0, inputText.length - 1)
                    }
                }
                else -> {
                    if (inputText.firstOrNull() == '0') {
                        inputText = it.key
                    } else {
                        inputText += it.key
                    }
                }
            }
        }
    }
}

@Composable
fun FirstContainer(
    originCurrency: String,
    resultCurrency: String,
    resultCurrencyRate: String,
    flag: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() })
    ) {
        Image(
            painter = painterResource(id = flag),
            contentDescription = null,
            Modifier.width(65.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp)
        ) {
            Text(text = originCurrency)
            Text(text = "1 $originCurrency - $resultCurrencyRate $resultCurrency")
        }
    }
}

@Composable
fun SecondContainer(
    originCurrency: String,
    resultCurrency: String,
    originCurrencyRate: String,
    flag: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick() })
    ) {
        Image(
            painter = painterResource(id = flag),
            contentDescription = null,
            modifier = Modifier.width(65.dp)
        )
        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp)
        ) {
            Text(text = resultCurrency)
            Text(text = "1 $resultCurrency - $originCurrencyRate $originCurrency")
        }
    }
}

@Composable
fun CustomTextField(
    inputText: String,
    onValueChange: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = inputText,
        onValueChange = { onValueChange() },
        modifier = Modifier
            .padding(top = 40.dp)
            .onFocusEvent { focusManager.clearFocus() },
        maxLines = 1,
        textStyle = TextStyle(fontSize = 20.sp),
    )
}

@Composable
private fun BoxForSwap(onSwapClick: () -> Unit) {
    Box {
        Divider(Modifier.align(Alignment.Center))
        Icon(
            painter = painterResource(id = R.drawable.ic_swap),
            contentDescription = null,

            Modifier
                .align(Alignment.CenterEnd)
                .border(BorderStroke(1.dp, Color.Black), shape = CircleShape)
                .background(Color.White)
                .padding(8.dp)
                .clickable(true) { onSwapClick() }
        )
    }
}

@Composable
fun ShowDialog(setShowDialog: Boolean, isOrigin: Boolean, onDismiss: () -> Unit) {
    if (setShowDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) { ComposeDialog(isOrigin = isOrigin, onDismiss) }
    }
}

fun calculateResult(inputString: String, originRate: String): String {
    return (inputString.toDouble() * originRate.toDouble()).toString()
}

val String.isValidAmount get(): Boolean = isNotBlank()
fun formatAmount(input: String): String =
    if (input.isValidAmount) {
        DecimalFormat("#,###,###").format(input.toDouble())
    } else {
        input
    }

@Preview(showSystemUi = true)
@Composable
fun AppPrev() {
    MyApp("UZS", "USD", "0.000091", "10980", R.drawable.us, R.drawable.icon_uzb) {}
}
