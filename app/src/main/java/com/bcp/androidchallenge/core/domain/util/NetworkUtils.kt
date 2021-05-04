package com.bcp.androidchallenge.core.domain.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception


suspend fun <T : Any> safeApiCall(call: suspend () -> ResultType<T>): ResultType<T> {
    return withContext(Dispatchers.IO) {
        try {
            call()
        } catch (e: Exception) {

            System.out.println("safeApiCall:Exception "+e)
            System.out.println("safeApi:e localMess "+e.localizedMessage)
            System.out.println("safeApi:e stackTrace "+e.stackTrace)

            for (i in 0..e.stackTrace.size-1) {

                System.out.println("safeApi:e stackTrace cN"+e.stackTrace[i].className)
                System.out.println("safeApi:e stackTrace fN"+e.stackTrace[i].fileName)
                System.out.println("safeApi:e stackTrace NM"+e.stackTrace[i].isNativeMethod)
                System.out.println("safeApi:e stackTrace lN"+e.stackTrace[i].lineNumber)
                System.out.println("safeApi:e stackTrace mN"+e.stackTrace[i].methodName)

            }

            System.out.println("safeApi:e cause "+e.cause)
            System.out.println("safeApi:e message "+e.message)
            // An exception was thrown when calling the API so we're converting this to an IOException
             if(e is DoNothing){
                 ResultType.Error(IOException(e.message))
             }else{
                 ResultType.Error(ErrorGeneric("APP00000",400))
             }//ErrorGeneric()

        }
    }
}


class DoNothing(text: String = "false", isHandled :Boolean = false): IOException(text)