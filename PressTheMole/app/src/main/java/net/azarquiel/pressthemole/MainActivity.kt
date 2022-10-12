package net.azarquiel.pressthemole

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var burrow: Burrow

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
        burrow = Burrow(
            mainLayout = findViewById(R.id.mainLayout),
            pointsView = findViewById(R.id.pointsView)
        )
        burrow.pointsView.text = getString(R.string.points, 0)
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
                launch(Dispatchers.Main) {
                    // There is a max quantity of moles to exist at once, so not to make
                    // your phone explode
                    if (burrow.moles < burrow.maxMoles) {
                        burrow.mainLayout.addView(Mole(this@MainActivity, burrow))
                    }
                }
            }
        }
    }
}