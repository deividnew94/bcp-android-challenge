package com.bcp.androidchallenge.core.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateModel (
    val exchangeRateId: String = "",
    val currency: String = "",
    val purchasevalue: String = "",
    val salevalue: String = ""
)
