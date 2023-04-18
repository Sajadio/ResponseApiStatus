package com.sajjadio.handleresponsestatus.data.remote

import android.util.Log
import com.google.gson.Gson
import com.sajjadio.handleresponsestatus.data.model.ResponseWeather
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiServiceImpl : ApiService {

    private val client = OkHttpClient()

    override fun getWeatherResponse(
        onSuccess: (ResponseWeather) -> Unit,
        onFailure: (IOException) -> Unit
    ) {
        val request = buildWeatherRequest()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                handleWeatherResponse(response) {
                    onSuccess(it)
                }
            }
        })
    }

    private fun handleWeatherResponse(response: Response, onResponse: (ResponseWeather) -> Unit) {
        try {
            response.body?.string()?.let { jsonString ->
                val result = Gson().fromJson(jsonString, ResponseWeather::class.java)
                onResponse(result)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
        }
    }

    private fun buildWeatherRequest(): Request {
        val url = buildWeatherUrl()
        return buildRequest(url)
    }

    private fun buildWeatherUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("api.openweathermap.org")
            .addPathSegments("data/2.5/weather")
            .addQueryParameter("appid", "your api key")
            .addQueryParameter("lat", LATITUDE)
            .addQueryParameter("lon", LONGITUDE)
            .build()
    }

    private fun buildRequest(url: HttpUrl): Request {
        return Request.Builder()
            .url(url)
            .build()
    }


    private companion object {
        const val TAG = "sajjadio"
        const val LATITUDE = "30.5257657"
        const val LONGITUDE = "47.773797"
    }
}