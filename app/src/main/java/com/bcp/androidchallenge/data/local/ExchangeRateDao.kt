package com.bcp.androidchallenge.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bcp.androidchallenge.core.ResultType
import com.bcp.androidchallenge.core.model.ExchangesRateModel
import com.bcp.androidchallenge.data.model.ExchangeRateEntity

/**
 * Created by David Hernandez on 07 July 2020
 */

@Dao
interface ExchangeRateDao {


    @Query("SELECT * FROM exchangeratesTable WHERE currency LIKE '%' || :exchangeRateName || '%'") // This Like operator is needed due that the API returns blank spaces in the name
    suspend fun getExchangeRates(exchangeRateName: String): List<ExchangeRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExchangeRate(exchangeRate: ExchangeRateEntity)

    @Query("SELECT * FROM exchangeratesTable")
    fun getAllFavoriteExchangeRateWithChanges(): LiveData<List<ExchangeRateEntity>>
}