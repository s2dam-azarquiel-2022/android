package net.azarquiel.darksky

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import net.azarquiel.darksky.Utils.darken
import net.azarquiel.darksky.Utils.toColor
import net.azarquiel.darksky.adapter.DailyAdapter
import net.azarquiel.darksky.dao.DarkSky

class MainActivity : AppCompatActivity() {
    private lateinit var data: DarkSky.Result
    private lateinit var mainView: ConstraintLayout
    private lateinit var dailyAdapter: DailyAdapter
    private var uiMainColor: Int = 0

    // I don't know much yet about how to handle coroutines, but seems like this works
    // and does not make android yell at you for blocking the main thread.
    private fun setup() = runBlocking {
        data = DarkSky.getForecast(this@MainActivity)
        mainView = findViewById(R.id.mainView)
        dailyAdapter = DailyAdapter(this@MainActivity, R.layout.day)
    }

    private fun setUIColors() {
        uiMainColor = data.currently.temperature.toColor().darken()
        (mainView.background as GradientDrawable).colors = intArrayOf(
            (data.currently.temperature - 2.5F).toColor(),
            (data.currently.temperature + 2.5F).toColor()
        )
        window.statusBarColor = uiMainColor
        supportActionBar?.setBackgroundDrawable(ColorDrawable(uiMainColor))
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun Int.setText(stringId: Int, vararg: Any) {
        findViewById<TextView>(this).text = getString(stringId, vararg)
    }

    private fun setCurrentTime() {
        R.id.currentTemp.setText(R.string.temp, data.currently.temperature)
        R.id.currentSummary.setText(R.string.summary, data.currently.summary)
        R.id.currentPrecipProbability.setText(R.string.precipProbability, data.currently.getPrecipProbability())
        R.id.currentHumidity.setText(R.string.humidity, data.currently.humidity)
        R.id.currentWindSpeed.setText(R.string.windSpeed, data.currently.windSpeed)
        Picasso.get().load(data.currently.getIcon()).into(findViewById<ImageView>(R.id.currentIcon))
    }

    private fun setDailyTime() {
        findViewById<RecyclerView>(R.id.daily).let {
            it.adapter = dailyAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
        dailyAdapter.setDays(data.daily.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
        setUIColors()
        setCurrentTime()
        setDailyTime()
    }
}