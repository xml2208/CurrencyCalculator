package com.bignerdranch.android.currncycalculator.network

data class ApiResponse(
        val amount: String,
        val base_currency_code: String,
        val base_currency_name: String,
        var rates: Map<String, Rates>,
        val status: String,
        val updated_date: String
)
