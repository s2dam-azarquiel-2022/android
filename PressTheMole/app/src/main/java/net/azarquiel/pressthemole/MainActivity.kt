package net.azarquiel.pressthemole

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers.Main
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val maxMoles: Int = 10
    private lateinit var mainLayout: ConstraintLayout
    private var mainW: Int = 0
    private var mainH: Int = 0
    private lateinit var imgSizeR: IntRange
    private lateinit var rnGesus: Random
    private var luckyNumber: Int = 0
    private var moleCount: Int = 0
    private var points: Long = 0
    private lateinit var pointsView: TextView

    @OptIn(DelicateCoroutinesApi::class)
    private fun addPoints(x: Float, y: Float, w: Int, h: Int, shiny: Boolean) {
        // Add `p` to `points` and update `pointsView` to show them
        val pointsGained = (mainW / w).let { n ->
            if (shiny) n * luckyNumber
            else n / 4
        }
        points += pointsGained
        pointsView.text = getString(R.string.points, points)
        mainLayout.addView(TextView(this).also {
            it.text = "+$pointsGained"
            it.x = x + (w / 2)
            it.y = y + (h / 2)
            GlobalScope.launch {
                launch(Main) {
                    ObjectAnimator.ofFloat(it, "translationY", -100f).apply {
                        duration = 500
                        start()
                    }
                }
                delay(500)
                launch(Main) { mainLayout.removeView(it) }
            }
        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun removePoints(x: Float, y: Float, w: Int, h: Int) {
        // Remove `p` to `points` and update `pointsView` to show them
        points -= 1
        pointsView.text = getString(R.string.points, points)
        mainLayout.addView(TextView(this).also {
            it.text = "-1"
            it.x = x + (w / 2)
            it.y = y + (h / 2)
            it.setTextColor(Color.RED)
            GlobalScope.launch {
                launch(Main) {
                    ObjectAnimator.ofFloat(it, "translationY", -100f).apply {
                        duration = 500
                        start()
                    }
                }
                delay(500)
                launch(Main) { mainLayout.removeView(it) }
            }
        })
    }

    private fun addMole() {
        moleCount++
        rnGesus = Random(System.currentTimeMillis())
        mainLayout.addView(Mole(this, imgSizeR, mainW, mainH, luckyNumber, {
            addPoints(it.x, it.y, it.layoutParams.width, it.layoutParams.height, it.isShiny)
            moleCount--
            mainLayout.removeView(it)
        }, {
            removePoints(it.x, it.y, it.layoutParams.width, it.layoutParams.height)
        }))
    }

    private fun setupSizes() {
        // Get the screen's width and height
        Resources.getSystem().let { sys ->
            mainW = sys.displayMetrics.widthPixels
            mainH = sys.displayMetrics.heightPixels
        }

        // Sets the minimum and maximum sizes of a mole
        imgSizeR = mainW/12..mainW/6

        // Removes the max size of a mole from the size of the screen, so moles don't appear
        // on the end of the screen
        mainW -= imgSizeR.last
        mainH -= imgSizeR.last
    }

    private fun setupNewGame() {
        rnGesus = Random(System.currentTimeMillis())
        luckyNumber = rnGesus.nextInt(100)
        moleCount = 0
        points = 0
        pointsView.text = getString(R.string.points, 0)
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Snippet to remove the state bar at the top and make the app "fullscreen".
        // This seems to orly work on newer android versions, so to avoid it not working in older
        // ones I'm using the `?` operator to check for null
        ViewCompat.getWindowInsetsController(window.decorView)?.let {
            it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            it.hide(WindowInsetsCompat.Type.systemBars())
        }

        mainLayout = findViewById(R.id.mainLayout)
        pointsView = findViewById(R.id.pointsView)

        setupNewGame()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        // Seems like onResume works better than waiting 1 second in onCreate to use `setupSizes()`
        // The problem with `setupSizes()` is it got the size of the screen in portrait mode
        // since it did not give time for the activity to turn into landscape mode.
        super.onResume()

        setupSizes()

        GlobalScope.launch {
            while (true) {
                delay(1000)
                launch(Main) {
                    // There is a max quantity of moles to exist at once, so not to make
                    // your phone explode
                    if (moleCount < maxMoles) addMole()
                }
            }
        }
    }
}