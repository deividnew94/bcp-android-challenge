package com.bcp.androidchallenge.core.repository

import androidx.lifecycle.LiveData
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.core.ResultType
import com.bcp.androidchallenge.core.model.ExchangesRateModel
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.data.model.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by David Hernandez on 16 July 2020
 */
interface ExchangeRateRepository {
    suspend fun getExchangeRateByName(cocktailName: String): Flow<Resource<List<ExchangeRate>>>
    suspend fun getCachedExchangeRates(cocktailName: String): Resource<List<ExchangeRate>>
    suspend fun saveExchangeRate(cocktail: ExchangeRateEntity)
    fun getFavoritesExchangeRate(): LiveData<List<ExchangeRate>>
}