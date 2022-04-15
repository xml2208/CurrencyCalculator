package com.bignerdranch.android.currncycalculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import com.bignerdranch.android.currncycalculator.databinding.ActivityMainBinding
import com.bignerdranch.android.currncycalculator.model.CurrencyViewModel
import com.bignerdranch.android.currncycalculator.model.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

const val ACCESS_KEY = "df80c136dc0191112bdeb99b5ade88be84ad481a"

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels { ViewModelFactory() }

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var editText: EditText
    private lateinit var numericKeyboard: NumericKeyboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.hide()
        numericKeyboard = viewBinding.keyboard!!
        editText = viewBinding.editText!!
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        editText.setTextIsSelectable(true)
        val inputConnection = editText.onCreateInputConnection(EditorInfo())
        numericKeyboard.setUpInputConnection(inputConnection)

        viewBinding.convertBtn?.setOnClickListener {
            doConversion()
            observeUi()

        }

        viewBinding.swapBtn?.setOnClickListener {
            val apiKey = ACCESS_KEY
            val from = "${viewModel.resultCurrency.value}"
            val to = "${viewModel.originCurrency.value}"
            val amount = viewBinding.editText?.text.toString().toDouble()
            viewModel.getConvertedData(apiKey, from, to, amount)

            viewModel.onSwap()
            observeUi()
        }
    }

    private fun observeUi() {
        viewModel.originCurrency.observe(this) {
            viewBinding.currencyName.text = it
        }
        viewModel.resultCurrency.observe(this) {
            viewBinding.currencyName2.text = it
        }
        viewModel.resultAmount.observe(this) { newResult ->
            viewBinding.result.text = newResult
        }
        viewModel.originRate.observe(this) {
            viewBinding.currencyRate.text =
                "1 ${viewModel.originCurrency.value} - ${it} ${viewModel.resultCurrency.value}"
        }
        viewModel.resultRate.observe(this) {
            viewBinding.currencyRate2.text =
                "1 ${viewModel.resultCurrency.value} - ${it} ${viewModel.originCurrency.value}"
        }
    }

    private fun doConversion() {
        val apiKey = ACCESS_KEY
        val from = "${viewModel.originCurrency.value}"
        val to = "${viewModel.resultCurrency.value}"
        val amount = viewBinding.editText!!.text.toString().toDouble()
        viewModel.getConvertedData(apiKey, from, to, amount)
        observeUi()
    }

    private fun showDialog() {
        CurrenciesDialog().show(supportFragmentManager, "DialogFragment")
    }
}


