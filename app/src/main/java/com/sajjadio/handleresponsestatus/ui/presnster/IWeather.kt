package com.sajjadio.handleresponsestatus.ui.presnster

import com.sajjadio.handleresponsestatus.utils.ResponseStatus

interface IWeather {
    fun checkResponseStatus(status: ResponseStatus)
}