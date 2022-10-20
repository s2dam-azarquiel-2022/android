package net.azarquiel.dice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var cup: Cup
    private lateinit var startBtn: Button
    private var isWaitingForNext: Boolean = false

    private fun setupGame() {
        cup = Cup(
            diceHolder = findViewById(R.id.diceHolder),
            rnGesus = Random(System.currentTimeMillis())
        )
        startBtn = findViewById(R.id.playBtn)
        startBtn.setOnClickListener(this)
    }

    private fun setupNewGame() {
        cup.rnGesus = Random(System.currentTimeMillis())
        cup.dice.forEach {
            it.rnGesus = cup.rnGesus
            it.clear()
        }
        cup.currentPlayer = 0
        cup.players = genDefaultPlayers()
        startBtn.isEnabled = true
        startBtn.text = getString(R.string.playBtnRollTxt)
        isWaitingForNext = false
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClick(p0: View?) {
        if (isWaitingForNext) setupNextPlayer()
        else {
            cup.dice.forEach { it.roll() }
            startBtn.isEnabled = false
            GlobalScope.launch {
                delay(1425)
                launch(Dispatchers.Main) {
                    cup.dice.forEach { it.stop() }
                    startBtn.isEnabled = true
                    cup.players[cup.currentPlayer].rollsLeft--
                    if (cup.players[cup.currentPlayer].rollsLeft == 0) checkNextPlayer()
                }
            }
        }
    }

    private fun endGame() {
        startBtn.isEnabled = false
        cup.players.maxBy { p -> p.points }.let { winner ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.gameEndTitle, winner.name))
                .setMessage(cup.players.joinToString("\n") { p ->
                    getString(R.string.gameEndDescItem, p.name, p.points)
                })
                .setPositiveButton(R.string.newGameTxt) { _, _ -> setupNewGame() }
                .setNegativeButton(R.string.endGameTxt) { _, _ -> finish() }
                .show()
        }
    }

    private fun setupNextPlayer() {
        if (cup.players.size == cup.currentPlayer) endGame()
        else {
            isWaitingForNext = false
            startBtn.text = getString(R.string.playBtnRollTxt)
            cup.dice.forEach { it.clear() }
        }
    }

    private fun checkNextPlayer() {
        cup.players[cup.currentPlayer].points = cup.dice.sumOf { it.face.points }
        cup.currentPlayer++
        isWaitingForNext = true
        startBtn.text = getString(
            if (cup.players.size == cup.currentPlayer) R.string.playBtnEndTxt
            else R.string.playBtnNextTxt
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupGame()
    }
}