package com.bcp.androidchallenge.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bcp.androidchallenge.application.ToastHelper
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.domain.ExchangeRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by David Hernandez on 03 July 2020
 */

class MainViewModel @ViewModelInject constructor(
    private val repository: ExchangeRateRepository,
    private val toastHelper: ToastHelper,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentExchangeRateName = savedStateHandle.getLiveData<String>("exchangeRateName", "margarita")

    fun setExchangeRate(exchangeRateName: String) {
        currentExchangeRateName.value = exchangeRateName
    }

    val fetchExchangeRateList = currentExchangeRateName.distinctUntilChanged().switchMap { exchangeRateName ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                repository.getExchangeRateByName(exchangeRateName).collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getFavoriteExchangeRate() =
        liveData<Resource<List<ExchangeRate>>>(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emitSource(repository.getFavoritesExchangeRate().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}