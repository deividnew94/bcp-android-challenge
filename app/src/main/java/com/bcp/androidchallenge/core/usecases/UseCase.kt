package com.bcp.androidchallenge.core.usecases

import com.bcp.androidchallenge.core.ResultType


abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): ResultType<Type>

    suspend operator fun invoke(params: Params, onResult: (ResultType<Type>) -> Unit = {}) {
        val result = run(params)
        onResult(result)
    }

    class None
}


