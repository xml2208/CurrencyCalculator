package com.bignerdranch.android.currncycalculator.model

import com.google.gson.annotations.SerializedName

typealias Currencies = List<Currency>

data class Currency(
    val iso: String,
    val icon: Int
)