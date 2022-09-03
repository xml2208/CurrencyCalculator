package com.bignerdranch.android.currncycalculator.ui.main.simple_ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.currncycalculator.databinding.ActivityMainBinding
import com.bignerdranch.android.currncycalculator.model.CurrencyViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    val viewBinding get() = _binding!!
    private lateinit var editText: EditText
    private lateinit var numericKeyboard: NumericKeyboard
    private lateinit var inputAmount: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.hide()
        numericKeyboard = viewBinding.keyboard!!
        editText = viewBinding.editText!!
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        editText.setTextIsSelectable(true)
        val inputConnection = editText.onCreateInputConnection(EditorInfo())
        numericKeyboard.setUpInputConnection(inputConnection)

        observeUi()
        viewModel.calculate()

        viewBinding.firstConverter!!.setOnClickListener { showDialog(true) }
        viewBinding.secondConverter!!.setOnClickListener { showDialog(false) }

        viewBinding.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateResult()
            }

            override fun afterTextChanged(s: Editable) {
                editText.removeTextChangedListener(this)

                try {
                    var originalStr = s.toString().replace(" ", "")

                    val df = DecimalFormat("#,##0")
                    df.roundingMode = RoundingMode.FLOOR

                    val formattedString = df.format(originalStr.toDouble())
                    val againFormattedString = formattedString.replace(",", " ")

                    editText.setText(againFormattedString)
                    editText.setSelection(editText.text.length)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    editText.addTextChangedListener(this)
                }
            }
        })

        viewBinding.swapBtn?.setOnClickListener {
            calculateResultOnSwap()
            viewModel.onSwap()
        }
    }

    fun observeUi() {
        lifecycleScope.launchWhenResumed {
            viewModel.originCurrency.collect() { originCurrency ->
                viewBinding.currencyName.text = originCurrency
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.originImg.collect() {
                viewBinding.imageOrigin?.setImageResource(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.resultImg.collect() {
                viewBinding.imageResult.setImageResource(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.resultCurrency.collect() {
                viewBinding.currencyName2.text = it
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.originRate.collect() {
                viewBinding.currencyRate.text =
                    "1 ${viewModel.originCurrency.value} - ${it} ${viewModel.resultCurrency.value}"
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.resultRate.collect() {
                viewBinding.currencyRate2.text =
                    "1 ${viewModel.resultCurrency.value} - ${it} ${viewModel.originCurrency.value}"
            }
        }
    }

    private fun showDialog(isOrigin: Boolean) {
        CurrenciesDialog(isOrigin).show(supportFragmentManager, "qwe")
    }

    private fun calculateResult() {
        inputAmount = viewBinding.editText?.text.toString().replace(" ", "")
        val df = DecimalFormat("#,##0")
        df.roundingMode = RoundingMode.FLOOR

        lifecycleScope.launchWhenResumed {
            if (inputAmount.isEmpty()) {
                viewModel.resultAmount.collect() { viewBinding.result.text = "0" }
            } else {
                val result = "${
                    inputAmount.toDouble() * viewModel.originRate.value.toString().toDouble()
                }".toDouble()

                val formattedR = df.format(result).replace(",", " ")
                viewModel.resultAmount.collect() {
                    viewBinding.result.text = formattedR.toString()
                    viewModel.calculate()
                }
            }
        }
    }

    private fun calculateResultOnSwap() {
        inputAmount = viewBinding.editText?.text.toString().replace(" ", "")
        val df = DecimalFormat("#,##0.000")
        df.roundingMode = RoundingMode.FLOOR

        lifecycleScope.launchWhenResumed {
            if (inputAmount.isEmpty()) {
                viewModel.resultAmount.collect() { viewBinding.result.text = "0" }
            } else {
                val result = "${
                    (inputAmount.toDouble() * viewModel.resultRate.value.toString()
                        .toDouble()).toBigDecimal()
                }".toDouble()

//              val formattedR = String.format("%.2f", result)
                val formattedR = df.format(result).replace(',', ' ')
                viewModel.resultCurrency.collect() {
                    viewBinding.result.text = formattedR
                }
            }
        }
    }
}

//  val formattedStr = "%,.2f".format(originalStr.toDouble())

//class Person(val name: String)
//
//fun main() {
//    val mohina = Person("mohina")
//    val doston = Person("doston")
//
//    println(mohina == doston) //false
//}






