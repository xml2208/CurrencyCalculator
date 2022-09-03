package com.bignerdranch.android.currncycalculator.ui.main.simple_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bignerdranch.android.currncycalculator.databinding.DialogFragmentBinding
import com.bignerdranch.android.currncycalculator.model.Currencies
import com.bignerdranch.android.currncycalculator.model.Currency
import com.bignerdranch.android.currncycalculator.model.CurrencyViewModel
import com.bignerdranch.android.currncycalculator.model.currencyList

class CurrenciesDialog(private var isOrigin: Boolean) : DialogFragment() {

    private lateinit var viewBinding: DialogFragmentBinding
    private val viewModel: CurrencyViewModel by activityViewModels()

    private val currencies: Currencies by lazy { currencyList() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DialogFragmentBinding.inflate(layoutInflater)
        viewBinding.composeView.setContent {
            MaterialTheme {
                CurrencyList()
            }
        }
        return viewBinding.root
    }

    @Composable
    fun CurrencyList() {
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()) {
            LazyColumn(contentPadding = PaddingValues(10.dp)) {
                items(currencies) { currency ->
                    CurrencyListItem(
                        item = currency,
                        onItemClicked = {
                            viewModel.onListItemClick(isOrigin, it)
                            dismiss()
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
}






