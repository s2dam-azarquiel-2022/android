package net.azarquiel.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var pokemons: IntArray
    private lateinit var cardsLayout: TableLayout
    private var lastImg: ImageView? = null
    private var startTime: Long = 0
    private var correct: Int = 0

    private inline fun iteratePokemonImages(f: (image: ImageView, n: Int) -> Unit) {
        for (j in 0 until cardsLayout.childCount) {
            val row: TableRow = cardsLayout.getChildAt(j) as TableRow
            for (i in 0 until row.childCount) {
                f(row.getChildAt(i) as ImageView, j * row.childCount + i)
            }
        }
    }

    private fun setupPokemons() {
        Random(System.currentTimeMillis()).let { rnGesus ->
            IntArray(10) { rnGesus.nextInt(1..809) }.let { randomPokes ->
                pokemons = (randomPokes + randomPokes).also { it.shuffle(rnGesus) }
            }
        }

        iteratePokemonImages { img, n ->
            img.setBackgroundResource(
                resources.getIdentifier("pokemon${pokemons[n]}", "drawable", packageName)
            )
            img.tag = "${pokemons[n]}"
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun ImageView.showAndHide() {
        GlobalScope.launch {
            launch(Main) { this@showAndHide.show() }
            SystemClock.sleep(250)
            launch(Main) {
                if (this@showAndHide.isEnabled) this@showAndHide.hide()
            }
        }
    }

    private fun ImageView.show() {
            this@show.setImageResource(android.R.color.transparent)
    }

    private fun ImageView.hide() {
            this@hide.setImageResource(R.drawable.tapa)
    }

    private fun imageOnClick(img: ImageView) {
        if (lastImg == null) {
            lastImg = img
            img.show()
        } else {
            if (lastImg == img) {
                img.showAndHide()
            } else if (lastImg!!.tag == img.tag) {
                img.show()
                img.isEnabled = false
                lastImg!!.show()
                lastImg!!.isEnabled = false
                correct++
            } else {
                lastImg!!.showAndHide()
                img.showAndHide()
            }
            lastImg = null
        }

        if (correct == 10) { endGame() }
    }

    private fun endGame() {
        AlertDialog.Builder(this)
            .setTitle("Completed!")
            .setMessage("It took you ${(System.currentTimeMillis() - startTime)/1000} seconds.")
            .setPositiveButton("New game") { _, _ -> setupNewGame() }
            .setNegativeButton("End game") { _, _ -> finish() }
            .show()
    }

    private fun setupImageOnClicks() {
        iteratePokemonImages { img, _ -> img.setOnClickListener { imageOnClick(img) } }
    }

    private fun setupNewGame() {
        setupPokemons()
        iteratePokemonImages { i, _ ->
            i.isEnabled = true
            i.hide()
        }
        startTime = System.currentTimeMillis()
        correct = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsLayout = findViewById(R.id.pokemonsLayout)

        setupNewGame()
        setupImageOnClicks()
    }
}