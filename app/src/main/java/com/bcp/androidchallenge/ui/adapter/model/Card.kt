package com.bcp.androidchallenge.ui.adapter.model

import com.bcp.androidchallenge.data.model.ExchangeRate

data class Card(
    val id: String,
    val title: String,
    val sub_title: String,
    val flag: Int,
    val exchangeRate: ExchangeRate
)