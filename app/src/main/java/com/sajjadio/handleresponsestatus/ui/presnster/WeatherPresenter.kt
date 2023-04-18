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
        view.checkResponseStatus(ResponseStatus.Loading)
    }

    private fun onSuccess(weather: ResponseWeather) {
        view.checkResponseStatus(ResponseStatus.Success(weather))
    }

    private fun onFailure(e: IOException) {
        view.checkResponseStatus(ResponseStatus.Error(e.message.toString()))
    }

}