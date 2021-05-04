package com.bcp.androidchallenge.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bcp.androidchallenge.application.ToastHelper
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.core.ResultType
import com.bcp.androidchallenge.core.domain.util.ErrorGeneric
import com.bcp.androidchallenge.core.domain.util.ErrorUpdate
import com.bcp.androidchallenge.core.model.ExchangeRateModel
import com.bcp.androidchallenge.core.model.ExchangesRateModel
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.core.repository.ExchangeRateRepository
import com.bcp.androidchallenge.core.usecases.GetExchangeRateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import com.bcp.androidchallenge.utils.Event

/**
 * Created by David Hernandez on 03 July 2020
 */

class MainViewModel @ViewModelInject constructor(
    private val repository: ExchangeRateRepository,
    private val toastHelper: ToastHelper,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getExchangeRateUseCase :GetExchangeRateUseCase
) : ViewModel() {

    private val currentExchangeRateName = savedStateHandle.getLiveData<String>("exchangeRateName", "margarita")

    private val _exchangeRateReport = MutableLiveData<Event<ExchangesRateModel>>()
    val exchangeRateReport:LiveData<ExchangesRateModel> = Transformations.map(_exchangeRateReport){ data ->
        data.getContentIfNotHandled()
    }
    fun setExchangeRate(exchangeRateName: String) {
        currentExchangeRateName.value = exchangeRateName
    }

    init {
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


    private fun handlerError(exception: Exception){

        when(exception){
            is ErrorGeneric -> {
                 Message.ErrorGetBudgetsReport
            }
            is ErrorUpdate ->{
                 Message.ErrorUpdateBudget
            }
            is IOException ->{
                if(exception.message.equals("false")){
                     Message.ErrorConnection
                }
            }
        }
    }
    sealed class Message{
        object ErrorGetBudgetsReport :Message()
        object ErrorConnection :Message()
        object ErrorUpdateBudget :Message()
    }
}