package net.azarquiel.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var deck: Array<Card>
    private var nextCard: Int = 0
    private lateinit var players: List<Player>
    private var playing: Int = -1
    private lateinit var cardsView: LinearLayout

    private fun genDeck(): Array<Card> {
        return Array(40)
            { rank -> Card(rank % 10 + 1, Suit.values()[rank / 10]) }
            .also { it.shuffle(Random(System.currentTimeMillis())) }
    }

    private fun Card.addToView() {
        cardsView.addView(ImageView(this@MainActivity).also {
            it.setImageResource(resources.getIdentifier(
                "${this.suit}${this.rank}",
                "drawable",
                packageName
            ))
        }, 0) // <- Insert at start
    }

    private fun addNextCardToView() {
        if (nextCard < 40) {
            deck[nextCard].let { card ->
                card.addToView()
                players[playing].points += card.rank
            }
            nextCard++
        } else { showS("You have already showed all the cards!") }
    }

    private fun addFirst2Cards() {
        for (i in 1..2) { addNextCardToView() }
    }

    private fun setupNextPlayer(wait: Boolean = false) {
        cardsView.removeAllViews()
        deck = genDeck()
        playing++
        nextCard = 0
        if (wait) {
            GlobalScope.launch {
                SystemClock.sleep(5000)
                MainScope().launch { addFirst2Cards() }
            }
        } else { addFirst2Cards() }
    }

    private fun setupNewGame() {
        playing = -1
        players = List(2) { i -> Player(name = "Player ${i+1}")}
        setupNextPlayer()
    }

    private fun calcPoints() {
        if (playing == players.size - 1) { endGame() }
        else { setupNextPlayer(true) }
    }

    private fun endGame() {
        val winners: List<Player>? = calcWinners()

        AlertDialog.Builder(this)
            .setTitle("${winners.toString()} won!")
            .setMessage(players.joinToString("\n") { p -> p.toString() } )
            .setPositiveButton("New game") { _, _ -> setupNewGame() }
            .setNegativeButton("End game") { _, _ -> finish() }
            .show()
    }

    private fun calcWinners(): List<Player>? {
        var winners: MutableList<Player>? = null
        for (player in players) {
            if (player.points <= 21) {
                if (winners == null) {
                    winners = mutableListOf(player)
                } else if ((winners.size == 0) or (winners[0].points == player.points)) {
                    winners.add(player)
                } else if (player.points > winners[0].points) {
                    winners = mutableListOf(player)
                }
            }
        }
        return winners
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsView = findViewById(R.id.cardsView)

        setupNewGame()
        findViewById<ImageView>(R.id.deck).setOnClickListener { addNextCardToView() }
        findViewById<Button>(R.id.stopBtn).setOnClickListener { calcPoints() }
    }
}