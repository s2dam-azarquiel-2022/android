package net.azarquiel.dice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

    private fun Player.countPoints() {
        cup.dice.forEach {
            this.points += when (it.face) {
                "face0" -> 1
                "face1" -> 3
                "face2" -> 5
                "face3" -> 7
                "face4" -> 10
                "face5" -> 14
                else -> 0
            }
        }
    }

    private fun endGame() {
        startBtn.isEnabled = false
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
        cup.players[cup.currentPlayer].countPoints()
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