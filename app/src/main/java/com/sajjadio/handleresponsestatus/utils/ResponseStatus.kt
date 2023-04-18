package com.sajjadio.handleresponsestatus.utils

sealed class ResponseStatus {
    object Loading : ResponseStatus()
    class Success(val data:Any): ResponseStatus()
    class Error(val message: String) : ResponseStatus()
}