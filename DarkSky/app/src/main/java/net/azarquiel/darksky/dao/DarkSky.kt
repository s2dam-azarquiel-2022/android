package net.azarquiel.darksky.dao

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.darksky.R
import java.net.URL

object DarkSky {
    private const val api: String = "https://api.darksky.net/forecast"
    private const val appID: String = "21259f9de3537b4f719c53580fa39c3a"
    private const val units: String = "units=ca"
    private const val exclude: String = "exclude=minutely,alerts,flags"

    private const val url: String = "$api/$appID"
    private const val coords: String = "39.8710026,-4.0251675"
    private const val params: String = "$units&$exclude"

    data class Result(
        var currently: TimeCurrent,
        var daily: Daily,
        var hourly: Hourly
    )

    data class Daily(
        var summary: String,
        private var icon: String,
        var data: List<TimeDaily>
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
    }

    data class Hourly(
        var summary: String,
        private var icon: String,
        var data: List<TimeHourly>
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
    }

    data class TimeCurrent(
        var summary: String,
        private var icon: String,
        var time: Long,
        private var precipProbability: Float,
        var temperature: Float,
        var humidity: Float,
        var windSpeed: Float,
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
        fun getPrecipProbability(): Float = precipProbability * 100
    }

    data class TimeDaily(
        var summary: String,
        private var icon: String,
        var time: Long,
        private var precipProbability: Float,
        var temperatureMax: Float,
        var temperatureMin: Float,
        var humidity: Float,
        var windSpeed: Float,
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
        fun getPrecipProbability(): Float = precipProbability * 100
    }

    data class TimeHourly(
        var summary: String,
        private var icon: String,
        var time: Long,
        private var precipProbability: Float,
        var temperature: Float,
        var humidity: Float,
        var windSpeed: Float,
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
        fun getPrecipProbability(): Float = precipProbability * 100
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getForecast(context: Context): Result {
        var result: Result? = null
        GlobalScope.launch {
            val lang = "lang=${context.getString(R.string.apiLang)}"
            val json = URL("$url/$coords?$lang&$params").readText(Charsets.UTF_8)
            result = Gson().fromJson(json, Result::class.java)
        }.join()
        return result!!
    }
}