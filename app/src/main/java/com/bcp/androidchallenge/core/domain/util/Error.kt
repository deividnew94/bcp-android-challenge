package com.bcp.androidchallenge.core.domain.util

class ErrorUpdate(val errorCode: String = "APP00000", val httpCode: Int = 200) : Exception()
class ErrorGeneric(val errorCode: String = "APP00000", val httpCode: Int = 200) : Exception()
class ErrorTestGeneric(val errorCode: String = "", val httpCode: Int = 200) : Exception()
