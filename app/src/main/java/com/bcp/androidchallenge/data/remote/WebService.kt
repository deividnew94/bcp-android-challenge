package com.bcp.androidchallenge.data.remote

import com.bcp.androidchallenge.data.model.ExchangeRateList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by David Hernandez on 04 July 2020
 */
interface WebService {
    @GET("exchangerates")
    suspend fun getExchangeRateByAll(): ExchangeRateList?
}