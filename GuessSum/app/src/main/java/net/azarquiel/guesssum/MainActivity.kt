package net.azarquiel.guesssum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private var sumResult: Int = 0
    private var correctGuesses: Int = 0
    private var startTime: Long = 0
    private lateinit var addendsViews: List<TextView>
    private lateinit var resultView: TextView
    private lateinit var guessedView: TextView
    private lateinit var guessStatus: ImageView

    private fun genRandomNumber() {
        sumResult = Random(System.currentTimeMillis()).nextInt(2..18)
        resultView.text = getString(R.string.resultTxt, sumResult)
    }

    private fun incCorrectGuesses() {
        correctGuesses++
        guessedView.text = getString(R.string.guessesTxt, correctGuesses)
    }

    private fun ImageView.clear() {
        this.setImageDrawable(null)
    }

    private fun setupNewGame() {
        genRandomNumber()
        addendsViews.clear()
        correctGuesses = 0
        guessedView.text = getString(R.string.guessesTxt, 0)
        guessStatus.clear()
        startTime = System.currentTimeMillis()
    }

    private fun gameOver() {
        ((System.currentTimeMillis() - startTime) / 1000).let { timeTaken ->
            AlertDialog.Builder(this)
                .setTitle("Completed!")
                .setMessage("You completed the game in $timeTaken seconds.")
                .setPositiveButton("New game") { _, _ -> setupNewGame() }
                .setNegativeButton("End game") { _, _ -> finish() }
                .show()
        }
    }

    private fun checkSum() {
        if ((addendsViews.sumOf { it.toInt() }) == sumResult) {
            guessStatus.setImageResource(R.drawable.correct)
            incCorrectGuesses()
        } else { guessStatus.setImageResource(R.drawable.incorrect) }

        if (correctGuesses == 10) { gameOver() }
    }

    private fun addNumber(n: Int) {
        for ((i, textView) in addendsViews.withIndex()) {
            if (textView.text == "") {
                textView.text = getString(R.string.numberTxt, n)
                if (i == addendsViews.size-1) { checkSum() }
                else { return }
            }
        }
    }

    private fun TableLayout.setupNumberBtns() {
        for (i in 0 until this.childCount) {
            val row: TableRow = this.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val button: Button = row.getChildAt(j) as Button
                val number: Int = (i * this.childCount + j + 1)
                button.setOnClickListener { addNumber(number) }
                button.text = getString(R.string.numberTxt, number)
            }
        }
    }

    private fun nextNumber() {
        if (correctGuesses < 10) {
            addendsViews.clear()
            genRandomNumber()
            guessStatus.clear()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addendsViews = findViewById<LinearLayout>(R.id.addends).let {
            (0 until it.childCount).map { n -> (it.getChildAt(n) as TextView) }
        }
        resultView = findViewById(R.id.resultView)
        guessedView = findViewById(R.id.guessedView)
        guessStatus = findViewById(R.id.guessStatus)

        findViewById<TableLayout>(R.id.numberBtns).setupNumberBtns()
        findViewById<Button>(R.id.nextNumberBtn).setOnClickListener { nextNumber() }

        setupNewGame()
    }
}