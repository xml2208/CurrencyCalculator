package com.bignerdranch.android.currncycalculator.ui.main.simple_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.FrameLayout
import android.widget.TextView
import com.bignerdranch.android.currncycalculator.R
import com.bignerdranch.android.currncycalculator.databinding.KeyboardBinding

class NumericKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var inputConnection: InputConnection

    init {
       val binding  = KeyboardBinding.inflate(LayoutInflater.from(context), this)

        binding.key1.setOnClickListener(::onClick)
        binding.key2.setOnClickListener(::onClick)
        binding.key3.setOnClickListener(::onClick)
        binding.key4.setOnClickListener(::onClick)
        binding.key5.setOnClickListener(::onClick)
        binding.key6.setOnClickListener(::onClick)
        binding.key7.setOnClickListener(::onClick)
        binding.key8.setOnClickListener(::onClick)
        binding.key9.setOnClickListener(::onClick)
        binding.key0.setOnClickListener(::onClick)
        binding.key.setOnClickListener(::onClick)
        binding.keyX.setOnClickListener(::onClick)
    }

    private fun onClick(v: View?) {
        when (v?.id) {
            R.id.key_x -> inputConnection.delete()
//            R.id.key_dot -> inputConnection.commitText(".", 1)
            else -> {
                val value = (v as? TextView)?.text
                inputConnection.commitText(value, 1)
            }
        }
    }

    private fun InputConnection.delete() {
        if (getSelectedText(0).isNullOrBlank()) {
            deleteSurroundingText(1, 0)
        } else {
            commitText("", 1)
        }
    }

    fun setUpInputConnection(ic: InputConnection) {
        this.inputConnection = ic
    }
}
