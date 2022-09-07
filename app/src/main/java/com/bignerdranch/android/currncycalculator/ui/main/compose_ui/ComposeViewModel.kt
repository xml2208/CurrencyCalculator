package com.bignerdranch.android.currncycalculator.ui.main.compose_ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.currncycalculator.R
import com.bignerdranch.android.currncycalculator.model.Currency
import com.bignerdranch.android.currncycalculator.network.CurrencyApi
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ComposeViewModel: ViewModel() {
    var originCurrency by mutableStateOf("usd")
    var resultCurrency by mutableStateOf("uzs")
    var resultRate: BigDecimal? by mutableStateOf(BigDecimal.ONE)
    var originRate: BigDecimal? by mutableStateOf(BigDecimal.ONE)
    var resultFlag by mutableStateOf(R.drawable.icon_uzb)
    var originFlag by mutableStateOf(R.drawable.us)

    fun onListItemClick(isOrigin:Boolean, item: Currency) {
        if (isOrigin) {
            originCurrency = item.iso
            originFlag = item.icon
            calculate()
        }
        else {
            resultCurrency = item.iso
            resultFlag = item.icon
            calculate()
        }
        calculate()
    }

    fun calculate() {
        viewModelScope.launch {
            try {
                val originRateResponse = CurrencyApi.retrofitService.convertCurrency(originCurrency)
                val currencyRates = originRateResponse[originCurrency] as? Map<String, Double>
                originRate = currencyRates?.get(resultCurrency)?.toString()?.toBigDecimal()

                val resultRateResponse = CurrencyApi.retrofitService.convertCurrency(resultCurrency)
                val currencyRates2 = resultRateResponse[resultCurrency] as? Map<String, Double>
                resultRate = currencyRates2?.get(originCurrency)?.toString()?.toBigDecimal()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSwap() {
        val tempRate = originRate
        originRate = resultRate
        resultRate =  tempRate

        val tempCurrency = originCurrency
        originCurrency = resultCurrency
        resultCurrency = tempCurrency

        val tempFlag = originFlag
        originFlag = resultFlag
        resultFlag = tempFlag
    }
}