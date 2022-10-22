package net.azarquiel.darksky

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import net.azarquiel.darksky.Utils.colorFromTemp
import net.azarquiel.darksky.Utils.darken
import net.azarquiel.darksky.dao.DarkSky


class MainActivity : AppCompatActivity() {
    private lateinit var data: DarkSky.Result
    private lateinit var mainView: ConstraintLayout
    private var uiMainColor: Int = 0

    // I don't know much yet about how to handle coroutines, but seems like this works
    // and does not make android yell at you for blocking the main thread.
    private fun setup() = runBlocking {
        data = DarkSky.getForecast()
        mainView = findViewById(R.id.mainView)
        uiMainColor = colorFromTemp(data.currently.temperature).darken()
    }

    private fun setGradientBackground() {
        val bg: GradientDrawable = mainView.background as GradientDrawable
        bg.colors = intArrayOf(
            colorFromTemp(data.currently.temperature-2.5F),
            colorFromTemp(data.currently.temperature+2.5F)
        )
    }

    private fun setUIMainColor() {
        findViewById<ConstraintLayout>(R.id.current).background.setTint(uiMainColor)
        window.statusBarColor = uiMainColor
        supportActionBar?.setBackgroundDrawable(ColorDrawable(uiMainColor))
    }

    private fun setCurrentTime() {
        findViewById<TextView>(R.id.currentTemp).text = getString(R.string.temp, data.currently.temperature)
        findViewById<TextView>(R.id.currentSummary).text = getString(R.string.summary, data.currently.summary)
        findViewById<TextView>(R.id.currentPrecipProbability).text = getString(R.string.precipProbability, data.currently.precipProbability)
        findViewById<TextView>(R.id.currentHumidity).text = getString(R.string.humidity, data.currently.humidity)
        findViewById<TextView>(R.id.currentPressure).text = getString(R.string.pressure, data.currently.pressure)
        findViewById<TextView>(R.id.currentWindSpeed).text = getString(R.string.windSpeed, data.currently.windSpeed)
        findViewById<TextView>(R.id.currentVisibility).text = getString(R.string.visibility, data.currently.visibility)
        Picasso.get().load(data.currently.getIcon()).into(findViewById<ImageView>(R.id.currentIcon))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
        setGradientBackground()
        setUIMainColor()
        setCurrentTime()
    }
}