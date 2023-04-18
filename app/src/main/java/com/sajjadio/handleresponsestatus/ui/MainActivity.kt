package com.sajjadio.handleresponsestatus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.sajjadio.handleresponsestatus.data.model.ResponseWeather
import com.sajjadio.handleresponsestatus.data.remote.ApiService
import com.sajjadio.handleresponsestatus.data.remote.ApiServiceImpl
import com.sajjadio.handleresponsestatus.databinding.ActivityMainBinding
import com.sajjadio.handleresponsestatus.ui.presnster.IWeather
import com.sajjadio.handleresponsestatus.ui.presnster.WeatherPresenter
import com.sajjadio.handleresponsestatus.utils.ResponseStatus

class MainActivity : AppCompatActivity(), IWeather {

    private lateinit var binding: ActivityMainBinding
    private lateinit var api: ApiService
    private lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInjection()
        refreshData()
    }

    private fun refreshData() {
        binding.buttonRefesh.setOnClickListener {
            presenter.loadData()
        }
    }

    private fun setupInjection() {
        api = ApiServiceImpl()
        presenter = WeatherPresenter(this, api)
        presenter.loadData()
    }

    override fun handleResponseStatus(status: ResponseStatus) {
        runOnUiThread {
            when (status) {
                is ResponseStatus.Loading -> handleProgressBarVisibility(true)
                is ResponseStatus.Success -> {
                    handleProgressBarVisibility(false)
                    loadData(status.data as ResponseWeather)
                }

                is ResponseStatus.Error -> {
                    handleProgressBarVisibility(false)
                    makeToast(status.message)
                    Log.d("Sajjadio", "handleResponseStatus: ${status.message}")
                }
            }
        }
    }

    private fun handleProgressBarVisibility(visibility: Boolean) {
        binding.progress.isVisible = visibility
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadData(weather: ResponseWeather) {
        showData(weather)
    }

    private fun showData(weather: ResponseWeather) {
        runOnUiThread {
            with(binding) {
                textViewCity.text = weather.name
                textViewWeatherTemp.text = "${weather.main.temp.minus(273.15).toInt()}°"
                val icon = weather.weather.first().icon
                val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
                loadImage(iconUrl)
            }
        }
    }

    private fun loadImage(url: String) {
        with(binding.imageViewWeather) {
            Glide.with(this).load(url).into(this)
        }
    }
}