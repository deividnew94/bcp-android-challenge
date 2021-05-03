package com.bcp.androidchallenge.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by David Hernandez on 10 August 2020
 */

@ExperimentalCoroutinesApi
class LocalDataSource @Inject constructor(private val exchangeRateDao: ExchangeRateDao) {


    suspend fun saveExchangeRate(exchangeRate: ExchangeRateEntity) {
        exchangeRateDao.saveExchangeRate(exchangeRate)
    }

    suspend fun getCachedExchangeRates(exchangeRatelName: String): Resource<List<ExchangeRate>> {
        return Resource.Success(exchangeRateDao.getExchangeRates(exchangeRatelName).asExchangeRateList())
    }

    fun getFavoritesExchangeRates(): LiveData<List<ExchangeRate>> {
        return exchangeRateDao.getAllFavoriteExchangeRateWithChanges().map { it.asExchangeRateList() }
    }
}