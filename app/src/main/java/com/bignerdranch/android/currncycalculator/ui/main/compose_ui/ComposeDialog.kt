package com.bignerdranch.android.currncycalculator.ui.main.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.currncycalculator.model.Currencies
import com.bignerdranch.android.currncycalculator.model.Currency
import com.bignerdranch.android.currncycalculator.model.currencyList

private val currencies: Currencies by lazy {  currencyList() }
@Composable
fun ComposeDialog(isOrigin: Boolean, onDismiss: () -> Unit) {
    val vm: ComposeViewModel = viewModel()

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()) {
        LazyColumn(contentPadding = PaddingValues(10.dp)) {
            items(currencies) { currency ->
                CurrencyListItem(
                    item = currency,
                    onItemClicked = { currencyItem ->
                        vm.onListItemClick(isOrigin, currencyItem)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun CurrencyListItem(item: Currency, onItemClicked: (Currency) -> Unit) {
    Row(modifier = Modifier
        .clickable { onItemClicked(item) }
        .fillMaxWidth()) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = "",
            Modifier
                .width(65.dp)
                .height(65.dp)
        )
        Text(
            text = item.iso.uppercase(),
            fontSize = (25.sp),
            modifier = Modifier.padding(top = 15.dp, start = 10.dp)
        )
    }
}