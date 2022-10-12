package net.azarquiel.pressthemole

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.Dispatchers.Main
import android.widget.ImageView
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
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private val maxMoles: Int = 20
    private lateinit var mainLayout: ConstraintLayout
    private var mainW: Int = 0
    private var mainH: Int = 0
    private lateinit var imgSizeR: IntRange
    private lateinit var rnGesus: Random
    private var luckyNumber: Int = Random.nextInt(100)
    private var moleCount: Int = 0
    private var points: Long = 0
    private lateinit var pointsView: TextView

    private fun ImageView.moveRandom() {
        // Move a ImageView to a random position on the screen
        this.x = rnGesus.nextInt(mainW).toFloat()
        this.y = rnGesus.nextInt(mainH).toFloat()
    }

    private fun addPoints(p: Int) {
        // Add `p` to `points` and update `pointsView` to show them
        points += p
        pointsView.text = getString(R.string.points, points)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addMole() {
        moleCount++
        rnGesus = Random(System.currentTimeMillis())
        val shiny = rnGesus.nextInt(100) == luckyNumber
        mainLayout.addView(ImageView(this).also {
            it.moveRandom()
            rnGesus.nextInt(imgSizeR).let {
                s -> it.layoutParams = ConstraintLayout.LayoutParams(s,s)
            }

            it.setBackgroundResource(
                if (shiny) R.drawable.special_animated
                else R.drawable.normal_animated
            )

            val animation: AnimationDrawable = it.background as AnimationDrawable
            animation.start()

            it.setOnClickListener { _ ->
                if (animation.current != animation.getFrame(0)) {
                    addPoints((mainW / it.layoutParams.width).let { n ->
                        if (shiny) n * luckyNumber
                        else n / 4
                    })
                    moleCount--
                    mainLayout.removeView(it)
                }
            }

            GlobalScope.launch {
                while (true) {
                    delay(rnGesus.nextInt(1..2).toLong() * 1600)
                    launch(Main) { it.visibility = View.INVISIBLE }
                    delay(500)
                    launch(Main) { it.visibility = View.VISIBLE; it.moveRandom() }
                }
            }
        })
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

    @Suppress("DEPRECATION")
    @OptIn(DelicateCoroutinesApi::class)
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

        addPoints(0) // Just to show 0 on the screen

        GlobalScope.launch {
            delay(1000)
            setupSizes()
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