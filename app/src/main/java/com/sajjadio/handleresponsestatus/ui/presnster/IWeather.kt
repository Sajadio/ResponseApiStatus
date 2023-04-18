package com.sajjadio.handleresponsestatus.ui.presnster

import com.sajjadio.handleresponsestatus.data.model.ResponseWeather

interface IWeather {
    fun showLoading(isLoading: Boolean)
    fun showSuccessfulResponseStatus(weather: ResponseWeather)
    fun showFailureResponseStatus(errorMessage: String)
}