package com.bcp.androidchallenge.application.injection

import com.bcp.androidchallenge.domain.DefaultExchangeRateRepository
import com.bcp.androidchallenge.domain.ExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: DefaultExchangeRateRepository): ExchangeRateRepository
}