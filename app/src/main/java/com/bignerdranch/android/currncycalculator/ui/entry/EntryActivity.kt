package com.bignerdranch.android.currncycalculator.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.currncycalculator.R
import com.bignerdranch.android.currncycalculator.databinding.ActivityEntryBinding
import com.bignerdranch.android.currncycalculator.ui.main.compose_ui.ComposeActivity
import com.bignerdranch.android.currncycalculator.ui.main.simple_ui.MainActivity

class EntryActivity : AppCompatActivity() {
    private lateinit var vb: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.composeUiButton.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
        vb.simpleUiButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}