package com.bignerdranch.android.currncycalculator.model

import com.bignerdranch.android.currncycalculator.R

fun currencyList() = mutableListOf(
        Currency("usd", R.drawable.us),
        Currency("uzs", R.drawable.icon_uzb),
        Currency("eur", R.drawable.europe),
        Currency("rub", R.drawable.russia),
        Currency("cad", R.drawable.canada),
        Currency("cny", R.drawable.ic_china)
    )