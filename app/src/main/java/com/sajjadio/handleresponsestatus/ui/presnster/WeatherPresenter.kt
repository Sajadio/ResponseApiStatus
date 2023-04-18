package com.sajjadio.handleresponsestatus.ui.presnster

import com.sajjadio.handleresponsestatus.data.model.ResponseWeather
import com.sajjadio.handleresponsestatus.data.remote.ApiService
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
        view.showLoading(true)
    }

    private fun onSuccess(weather: ResponseWeather) {
        view.showLoading(false)
        view.showSuccessfulResponseStatus(weather)
    }

    private fun onFailure(e: IOException) {
        view.showLoading(true)
        view.showFailureResponseStatus(e.message.toString())
    }

}