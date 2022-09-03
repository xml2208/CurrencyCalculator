package com.bignerdranch.android.currncycalculator.model

typealias Currencies = List<Currency>

data class Currency(
    val iso: String,
    val icon: Int
)