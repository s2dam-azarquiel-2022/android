package net.azarquiel.dice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    override fun onClick(p0: View?) {
        if (isWaitingForNext) setupNextPlayer()
        else {
            cup.roll()
            startBtn.isEnabled = false
            GlobalScope.launch {
                delay(1425)
                launch(Dispatchers.Main) {
                    cup.stop()
                    startBtn.isEnabled = true
                    cup.players[cup.currentPlayer].rollsLeft--
                    if (cup.players[cup.currentPlayer].rollsLeft == 0) checkNextPlayer()
                }
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
            cup.clear()
        }
    }

    private fun checkNextPlayer() {
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