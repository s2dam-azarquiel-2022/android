package net.azarquiel.hanged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import kotlinx.coroutines.Dispatchers.Main
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var word: String
    private lateinit var positions: IntArray
    private var lettersGuessed: Int = 0
    private var livesLost: Int = 0
    private var startTime: Long = 0
    private lateinit var hangedImg: ImageView
    private lateinit var wordLetterImages: Array<ImageView>
    private lateinit var keyboard: LinearLayout

    private fun ImageView.setFgImg(name: String) {
        this.setImageResource(resources.getIdentifier(name, "drawable", packageName))
    }

    private fun genRandomWord() {
        Random(System.currentTimeMillis()).let { rnGesus ->
            word = words[rnGesus.nextInt(words.indices)]
            positions = IntArray(3) { rnGesus.nextInt(word.indices) }
        }

        for (i in word.indices) {
            wordLetterImages[i].setFgImg(
                if (i in positions) "guion"
                else "${word[i]}b",
            )
        }
    }

    private fun setupNewGame() {
        livesLost = 0
        lettersGuessed = 0
        startTime = System.currentTimeMillis()
        hangedImg.setFgImg("hanged_0")
        genRandomWord()
        clearKeyboard()
    }

    private inline fun iterateKeys(f: (image: ImageView, letter: Char) -> Unit) {
        var letter = 'a'
        for (j in keyboard.indices) {
            val row: LinearLayout = keyboard.getChildAt(j) as LinearLayout
            for (i in row.indices) {
                if (j != keyboard.indices.last || i != row.indices.first && i != row.indices.last) {
                    f(row.getChildAt(i) as ImageView, letter)
                    if (letter == 'v') letter = 'x'
                    else letter++
                }
            }
        }
    }

    private fun clearKeyboard() {
        iterateKeys { img, letter ->
            img.setFgImg("${letter}b")
            img.isEnabled = true
        }
    }

    private fun setupKeyboard() {
        iterateKeys { img, letter ->
            img.tag = letter
            img.setOnClickListener { v -> checkLetter(v) }
        }
    }

    private fun lifeLost() {
        livesLost++

        if (livesLost < 5) hangedImg.setFgImg("hanged_$livesLost")
        else GlobalScope.launch {
            hangedImg.setFgImg("hanged_dead")
            SystemClock.sleep(750)
            launch(Main) {
                endGame(true)
            }
        }
    }

    private fun endGame(lost: Boolean) {
        val timeTaken: Int = ((System.currentTimeMillis() - startTime) / 1000).toInt()
        val title: String =
            if (lost) resources.getString(R.string.lostTitle)
            else resources.getString(R.string.wonTitle)
        val msg: String = resources.getQuantityString(R.plurals.timeTaken, timeTaken, timeTaken) +
            if (lost) resources.getString(R.string.actualWord, word)
            else resources.getQuantityString(R.plurals.livesLost, livesLost, livesLost)

        iterateKeys { img, _ -> img.isEnabled = false }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Nueva partida") { _, _ -> setupNewGame() }
            .setNegativeButton("Cerrar") { _, _ -> finish() }
            .show()
    }

    private fun checkLetter(v: View?) {
        val letter: Char = v!!.tag as Char
        val img: ImageView = v as ImageView
        var correct = false
        for (pos in positions) {
            if (word[pos] == letter) {
                wordLetterImages[pos].setFgImg("${word[pos]}v")
                correct = true
                lettersGuessed++
            }
        }

        if (correct) img.setFgImg("${letter}v")
        else {
            lifeLost()
            img.setFgImg("${letter}r")
        }

        img.isEnabled = false

        if (lettersGuessed == positions.size) endGame(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hangedImg = findViewById(R.id.hangedImg)
        wordLetterImages = findViewById<LinearLayout>(R.id.wordLetterImages).let {
            Array(it.size) { i -> it.getChildAt(i) as ImageView }
        }
        keyboard = findViewById(R.id.keyboard)

        setupNewGame()
        setupKeyboard()
    }
}