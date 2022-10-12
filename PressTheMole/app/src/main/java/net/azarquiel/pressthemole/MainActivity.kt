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
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var pointsView: TextView
    private lateinit var burrow: Burrow
    private var points: Long = 0

    @OptIn(DelicateCoroutinesApi::class)
    private fun addPoints(x: Float, y: Float, w: Int, h: Int, shiny: Boolean) {
        // Add `p` to `points` and update `pointsView` to show them
        val pointsGained = (burrow.width / w).let { n ->
            if (shiny) n * burrow.luckyNumber
            else n / 4
        }
        points += pointsGained
        pointsView.text = getString(R.string.points, points)
        mainLayout.addView(TextView(this).also {
            it.text = getString(R.string.pointsGained, pointsGained)
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
        burrow.moles++
        mainLayout.addView(Mole(this, burrow, {
            addPoints(it.x, it.y, it.layoutParams.width, it.layoutParams.height, it.isShiny)
            burrow.moles--
            mainLayout.removeView(it)
        }, {
            removePoints(it.x, it.y, it.layoutParams.width, it.layoutParams.height)
        }))
    }

    private fun setupSizes() {
        // Get the screen's width and height
        Resources.getSystem().let { sys ->
            burrow.width = sys.displayMetrics.widthPixels
            burrow.height = sys.displayMetrics.heightPixels
        }

        // Sets the minimum and maximum sizes of a mole
        burrow.sizeRatio = burrow.width/12..burrow.width/6

        // Removes the max size of a mole from the size of the screen, so moles don't appear
        // on the end of the screen
        burrow.width -= burrow.sizeRatio.last
        burrow.height -= burrow.sizeRatio.last
    }

    private fun setupNewGame() {
        burrow = Burrow(Random(System.currentTimeMillis()).nextInt())
        burrow.moles = 0
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
                    if (burrow.moles < burrow.maxMoles) addMole()
                }
            }
        }
    }
}