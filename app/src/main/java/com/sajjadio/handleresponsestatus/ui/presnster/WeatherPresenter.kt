package com.sajjadio.handleresponsestatus.ui.presnster

import com.sajjadio.handleresponsestatus.data.model.ResponseWeather
import com.sajjadio.handleresponsestatus.data.remote.ApiService
import com.sajjadio.handleresponsestatus.utils.ResponseStatus
import java.io.IOException

class WeatherPresenter(
    private val view: IWeather,
    private val api: ApiService
) {

    fun loadData() {
        api.getWeatherResponse(
            ::onSuccess,
            ::onFailure
        )
        view.handleResponseStatus(ResponseStatus.Loading)
    }

    private fun onSuccess(weather: ResponseWeather) {
        view.handleResponseStatus(ResponseStatus.Success(weather))
    }

    private fun onFailure(e: IOException) {
        view.handleResponseStatus(ResponseStatus.Error(e.message.toString()))
    }

}