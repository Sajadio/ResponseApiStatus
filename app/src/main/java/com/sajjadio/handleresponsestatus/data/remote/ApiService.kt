package com.sajjadio.handleresponsestatus.data.remote

import com.sajjadio.handleresponsestatus.data.model.ResponseWeather
import java.io.IOException

interface ApiService {
    fun getWeatherResponse(
        onSuccess: (ResponseWeather) -> Unit,
        onFailure: (IOException) -> Unit
    )
}