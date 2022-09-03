package com.bignerdranch.android.currncycalculator.ui.main.compose_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.bignerdranch.android.currncycalculator.databinding.ActivityComposeBinding

class ComposeActivity : AppCompatActivity() {
    private lateinit var vb: ActivityComposeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityComposeBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.composeActivity.setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        Text(text = "Helllooo")
    }

    @Composable
    fun CustomKeyboardView() {

    }
}