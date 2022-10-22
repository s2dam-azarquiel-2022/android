package net.azarquiel.darksky

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.*
import net.azarquiel.darksky.dao.DarkSky


class MainActivity : AppCompatActivity() {
    private lateinit var data: DarkSky.Result
    private lateinit var mainView: ConstraintLayout
    private var mainColor: Int = 0

    // I don't know much yet about how to handle coroutines, but seems like this works
    // and does not make android yell at you for blocking the main thread.
    private fun setup() = runBlocking {
        data = DarkSky.getForecast()
        mainView = findViewById(R.id.mainView)
        mainColor = colorFromTemp(data.currently.temperature + 2.5F)
        Log.d("aru", data.currently.temperature.toString())
    }

    private fun colorFromTemp(temp: Float): Int {
        // Took me a while to make this
        fun Int.between(min: Int, max: Int): Int =
            if (this > max) max
            else if (this < min) min
            else this
        return Color.rgb(
            ((temp + 30F) * 3.4F).toInt().between(0, 255),
            (100F - temp).toInt().between(0, 100),
            (255F - (temp * 3.4F)).toInt().between(0, 255)
        )
    }

    private fun setGradientBackground() {
        val bg: GradientDrawable = mainView.background as GradientDrawable
        bg.colors = intArrayOf(
            colorFromTemp(data.currently.temperature),
            colorFromTemp(data.currently.temperature+5)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
        setGradientBackground()
    }
}