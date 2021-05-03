package com.bcp.androidchallenge.domain

import androidx.lifecycle.LiveData
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.local.LocalDataSource
import com.bcp.androidchallenge.data.model.*
import com.bcp.androidchallenge.data.remote.NetworkDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by David Hernandez on 10 August 2020
 */

@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class DefaultExchangeRateRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : ExchangeRateRepository {

    override suspend fun getExchangeRateByName(exchangeRateName: String): Flow<Resource<List<ExchangeRate>>> =
        callbackFlow {

            offer(getCachedExchangeRates(exchangeRateName))

            networkDataSource.getExchangeRateByName().collect {
                when (it) {
                    is Resource.Success -> {
                        for (exchangeRate in it.data) {
                            saveExchangeRate(exchangeRate.asExchangeRateEntity())
                        }
                        offer(getCachedExchangeRates(exchangeRateName))
                    }
                    is Resource.Failure -> {
                        offer(getCachedExchangeRates(exchangeRateName))
                    }
                }
            }
            awaitClose { cancel() }
        }

    override suspend fun saveExchangeRate(exchangeRate: ExchangeRateEntity) {
        localDataSource.saveExchangeRate(exchangeRate)
    }

    override fun getFavoritesExchangeRate(): LiveData<List<ExchangeRate>> {
        return localDataSource.getFavoritesExchangeRates()
    }

    override suspend fun getCachedExchangeRates(exchangeRateName: String): Resource<List<ExchangeRate>> {
        return localDataSource.getCachedExchangeRates(exchangeRateName)
    }
}