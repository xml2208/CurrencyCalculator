package com.bignerdranch.android.currncycalculator.model

import androidx.lifecycle.*
import com.bignerdranch.android.currncycalculator.network.CurrencyApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

const val ACCESS_KEY = "df80c136dc0191112bdeb99b5ade88be84ad481a"

class CurrencyViewModel : ViewModel() {

    private val _originCurrency = MutableLiveData("USD")
    private val _resultCurrency = MutableLiveData("UZS")
    private val _originRate = MutableLiveData("")
    private val _resultRate = MutableLiveData(" ")
    private val _result = MutableLiveData("0.0")

    val originCurrency: LiveData<String> = _originCurrency
    val resultCurrency: LiveData<String> = _resultCurrency
    val originRate: LiveData<String> = _originRate
    val resultRate: LiveData<String> = _resultRate
    val resultAmount: LiveData<String> get() = _result


    fun onSwap() {
        val temp = _originCurrency.value
        _originCurrency.value = _resultCurrency.value
        _resultCurrency.value = temp
    }

    fun getConvertedData(accessKey: String, from: String, to: String, amount: Double) {
        viewModelScope.launch {
            try {
                val originRateResponse = CurrencyApi.retrofitService.convertCurrency(ACCESS_KEY, "${_originCurrency.value}", "${_resultCurrency.value}", 1.0)
                _originRate.value = originRateResponse.rates.values.first().rate

                val resultRateResponse = CurrencyApi.retrofitService.convertCurrency(ACCESS_KEY, "${_resultCurrency.value}", "${_originCurrency.value}", 1.0)
                _resultRate.value = resultRateResponse.rates.values.first().rate

                val responseResult = CurrencyApi.retrofitService.convertCurrency(accessKey, from, to, amount)
                _result.value = responseResult.rates.values.first().rate_for_amount.toString()
            } catch (e: Exception) {

            }
        }
    }
}

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel() as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }

}



