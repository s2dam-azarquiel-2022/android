package net.azarquiel.darksky.dao

import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

object DarkSky {
    private const val url: String = "https://api.darksky.net/forecast"
    private const val appID: String = "21259f9de3537b4f719c53580fa39c3a"
    private const val coords: String = "39.8710026,-4.0251675"
    private const val exclude: String = "exclude=minutely,hourly,alerts,flags"
    private const val units: String = "units=ca"
    private const val lang: String = "lang=es"

    data class Result(
        var currently: Time,
        var daily: Daily,
    )

    data class Daily(
        var summary: String,
        private var icon: String,
        var data: List<Time>
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
    }

    data class Time(
        var summary: String,
        private var icon: String,
        var time: Long,
        var precipIntesity: Int,
        var precipProbability: Float,
        var temperature: Float,
        var humidity: Float,
        var pressure: Float,
        var windSpeed: Float,
        var cloudCover: Float,
        var visibility: Float,
    ) {
        fun getIcon(): String = "https://darksky.net/images/weather-icons/$icon.png"
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getForecast(): Result {
        var result: Result? = null
        GlobalScope.launch {
            val json = URL("$url/$appID/$coords?$lang&$units&$exclude").readText(Charsets.UTF_8)
            result = Gson().fromJson(json, Result::class.java)
        }.join()
        return result!!
    }
}