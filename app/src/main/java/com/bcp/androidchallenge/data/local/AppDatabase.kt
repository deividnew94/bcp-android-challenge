package com.bcp.androidchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bcp.androidchallenge.data.model.ExchangeRateEntity

/**
 * Created by David Hernandez on 07 July 2020
 */
@Database(entities = [ExchangeRateEntity::class],version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao
}