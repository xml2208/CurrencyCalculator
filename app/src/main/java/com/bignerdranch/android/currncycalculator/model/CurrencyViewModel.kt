package com.bignerdranch.android.currncycalculator.model

import android.util.Log
import androidx.lifecycle.*
import com.bignerdranch.android.currncycalculator.R
import com.bignerdranch.android.currncycalculator.network.CurrencyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.String as String

class CurrencyViewModel : ViewModel() {

    private var _originCurrency = MutableStateFlow("usd")
    private val _resultCurrency = MutableStateFlow("uzs")
    private val _originRate = MutableStateFlow(BigDecimal.ONE)
    private val _resultRate = MutableStateFlow(BigDecimal.ONE)
    private val _result = MutableStateFlow("")
    private val _originImg = MutableStateFlow(R.drawable.us)
    private val _resultImg = MutableStateFlow(R.drawable.images)

    val originCurrency = _originCurrency.asStateFlow()
    var resultCurrency = _resultCurrency.asStateFlow()
    val originRate = _originRate.asStateFlow()
    val resultRate = _resultRate.asStateFlow()
    val resultAmount = _result.asStateFlow()
    val originImg = _originImg.asStateFlow()
    val resultImg = _resultImg.asStateFlow()

    fun onListItemClick(isOrigin:Boolean, item: Currency) {
       if (isOrigin) {
           _originCurrency.value = item.iso
           _originImg.value = item.icon
           calculate()
       }
        else {
            _resultCurrency.value = item.iso
           _resultImg.value = item.icon
           calculate()
       }
    }

    fun calculate() {
        viewModelScope.launch {
            try {
                val originRateResponse = CurrencyApi.retrofitService.convertCurrency(_originCurrency.value)
                val currencyRates = originRateResponse[_originCurrency.value] as? Map<String, Double>
                _originRate.value = currencyRates?.get(_resultCurrency.value)?.toString()?.toBigDecimal()

                val resultRateResponse = CurrencyApi.retrofitService.convertCurrency(_resultCurrency.value)
                val currencyRates2 = resultRateResponse[_resultCurrency.value] as? Map<String, Double>
                _resultRate.value = currencyRates2?.get(_originCurrency.value)?.toString()?.toBigDecimal()

            } catch (e: Exception) {
                Log.d("TAG", "$e")
            }
        }
    }

    fun onSwap() {
        val temp = _originCurrency.value
        _originCurrency.value = _resultCurrency.value
        _resultCurrency.value = temp

        val tempRate = _originRate.value
        _originRate.value = _resultRate.value
        _resultRate.value = tempRate

        val tepImg = _originImg.value
        _originImg.value = _resultImg.value
        _resultImg.value = tepImg
    }
}
