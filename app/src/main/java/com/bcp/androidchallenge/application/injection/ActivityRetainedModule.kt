package com.bcp.androidchallenge.application.injection

import com.bcp.androidchallenge.core.repository.ExchangeRateRepository
import com.bcp.androidchallenge.core.usecases.GetExchangeRateUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: GetExchangeRateUseCase): ExchangeRateRepository
}