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

    // I don't know much yet about how to handle coroutines, but seems like this works
    // and does not make android yell at you for blocking the main thread.
    private fun setup() = runBlocking {
        data = DarkSky.getForecast()
        mainView = findViewById(R.id.mainView)
        Log.d("aru", data.currently.temperature.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }
}