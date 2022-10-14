package net.azarquiel.pressthemole

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var burrow: Burrow
    private lateinit var upgradesShopOpener: ImageView
    private lateinit var upgradesShopCloser: ImageView
    private lateinit var upgradesShop: ScrollView

    private fun setupSizes() {
        // Get the screen's width and height
        Resources.getSystem().displayMetrics.let { metrics ->
            burrow.width = metrics.widthPixels
            burrow.height = metrics.heightPixels
        }

        // Sets the minimum and maximum sizes of a mole
        burrow.sizeRatio = burrow.width/Stats.maxSizeRatio .. burrow.width/Stats.minSizeRatio

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
        burrow.pointsView.text = getString(R.string.points, burrow.score)
    }

    private fun openUpgradesShop() {
        upgradesShop.visibility = View.VISIBLE
        upgradesShopOpener.visibility = View.GONE
    }

    private fun closeUpgradesShop() {
        upgradesShop.visibility = View.GONE
        upgradesShopOpener.visibility = View.VISIBLE
    }

    private fun usePoints(points: Int) {
        burrow.score -= points
        burrow.pointsView.text = getString(R.string.points, burrow.score)
    }

    private fun setupBoostsShop() {
        upgradesShop = findViewById(R.id.upgradesShop)
        upgradesShopOpener = findViewById(R.id.upgradesShopOpen)
        upgradesShopCloser = findViewById(R.id.upgradesShopClose)

        upgradesShopOpener.setOnClickListener { openUpgradesShop() }
        upgradesShopCloser.setOnClickListener { closeUpgradesShop() }

        findViewById<LinearLayout>(R.id.upgradeSlowerMoles).let {
            (it.getChildAt(1) as TextView).text = getString(R.string.pointPrice, Stats.slowerMolesPrice)
            (it.getChildAt(2) as Button).setOnClickListener { btn ->
                if (burrow.score >= Stats.slowerMolesPrice) {
                    burrow.delayDuration = Stats.delayDurationSlow
                    burrow.moleSkin = Stats.moleSkinSlow
                    burrow.shinyMoleSkin = Stats.shinyMoleSkinSlow
                    usePoints(Stats.slowerMolesPrice)
                    btn.isEnabled = false
                }
            }
        }

        findViewById<LinearLayout>(R.id.upgradeDoublePoints).let {
            (it.getChildAt(1) as TextView).text = getString(R.string.pointPrice, Stats.doublePointsPrice)
            (it.getChildAt(2) as Button).setOnClickListener { btn ->
                if (burrow.score >= Stats.doublePointsPrice) {
                    burrow.pointsMultiplier = Stats.pointsMultiplierDouble
                    usePoints(Stats.doublePointsPrice)
                    btn.isEnabled = false
                }
            }
        }

        findViewById<LinearLayout>(R.id.upgradeALuckierPerson).let {
            (it.getChildAt(1) as TextView).text = getString(R.string.pointPrice, Stats.aLuckierPersonPrice)
            (it.getChildAt(2) as Button).setOnClickListener { btn ->
                if (burrow.score >= Stats.aLuckierPersonPrice) {
                    burrow.maxLuckyNumber = Stats.maxLuckyNumberUpgraded
                    burrow.luckyNumber = burrow.rnGesus.nextInt(burrow.maxLuckyNumber)
                    burrow.shinyPointsMultiplier = Stats.shinyPointsMultiplierDouble
                    usePoints(Stats.aLuckierPersonPrice)
                    btn.isEnabled = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNewGame()
        setupBoostsShop()
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
                delay(Stats.newMoleWaitDuration)
                launch(Dispatchers.Main) {
                    // There is a max quantity of moles to exist at once, so not to make
                    // your phone explode
                    if (burrow.moles.size < Stats.maxMoles) {
                        burrow.mainLayout.addView(Mole(this@MainActivity, burrow))
                    }
                }
            }
        }
    }
}