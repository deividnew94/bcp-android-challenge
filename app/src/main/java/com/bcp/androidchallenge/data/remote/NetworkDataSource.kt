package com.bcp.androidchallenge.data.remote

import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.ExchangeRate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Created by David Hernandez on 03 July 2020
 */
@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(
    private val webService: WebService
) {
    suspend fun getExchangeRateByName(): Flow<Resource<List<ExchangeRate>>> =
        callbackFlow {
            offer(
                Resource.Success(
                    webService.getExchangeRateByAll()?.exchangeRateList ?: listOf()
                )
            )
            awaitClose { close() }
        }
}