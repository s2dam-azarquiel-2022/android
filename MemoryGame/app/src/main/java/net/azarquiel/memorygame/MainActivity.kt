package net.azarquiel.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var pokemons: IntArray
    private lateinit var pokemonsLayout: TableLayout
    private var lastSelectedView: ImageView? = null
    private var completedPokemons: MutableList<Int> = mutableListOf()

    private inline fun iteratePokemonImages(f: (image: ImageView, n: Int) -> Unit) {
        for (j in 0 until pokemonsLayout.childCount) {
            val row: TableRow = pokemonsLayout.getChildAt(j) as TableRow
            for (i in 0 until row.childCount) {
                f(row.getChildAt(i) as ImageView, j * row.childCount + i)
            }
        }
    }

    private fun setupPokemons() {
        Random(System.currentTimeMillis()).let { rnGesus ->
            IntArray(10) { rnGesus.nextInt(1..809) }.let { randomPokes ->
                pokemons = (randomPokes + randomPokes).also { it.shuffle() }
            }
        }

        iteratePokemonImages { img, n ->
            Log.d("aru", "$n")
            img.setBackgroundResource(
                resources.getIdentifier(
                    "pokemon${pokemons[n]}",
                    "drawable",
                    packageName
                )
            )
            img.tag = "${pokemons[n]}"
        }
    }

    private fun ImageView.hideAndShow() {
        GlobalScope.launch {
            launch(Main) { this@hideAndShow.setTransparentFg() }
            SystemClock.sleep(1000)
            launch(Main) {
                if (this@hideAndShow.tagToInt() !in completedPokemons) {
                    this@hideAndShow.setImageResource(R.drawable.tapa)
                }
            }
        }
    }

    private fun ImageView.setTransparentFg() {
        GlobalScope.launch { launch(Main) {
            this@setTransparentFg.setImageResource(android.R.color.transparent)
        } }
    }

    private fun imageOnClick(img: ImageView) {
        if (lastSelectedView == null) {
            lastSelectedView = img
            img.hideAndShow()
        } else {
            if (lastSelectedView!!.tagToInt() == img.tagToInt()) {
                lastSelectedView!!.setTransparentFg()
                img.setTransparentFg()
                completedPokemons.add(img.tagToInt())
            } else {
                img.hideAndShow()
            }
            lastSelectedView = null
        }
    }

    private fun setupImageOnClicks() {
        iteratePokemonImages { img, _ -> img.setOnClickListener { imageOnClick(img) } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonsLayout = findViewById(R.id.pokemonsLayout)

        setupPokemons()
        setupImageOnClicks()
    }
}