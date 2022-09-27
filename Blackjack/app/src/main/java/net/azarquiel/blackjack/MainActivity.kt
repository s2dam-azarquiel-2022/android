package net.azarquiel.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    private val deck: Array<Card> = Array(40) { rank ->
        Card(rank % 10 + 1, Suit.values()[rank / 10])
    }.also { it.shuffle() }

    private lateinit var cardsView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardsView = findViewById(R.id.cardsView)

        cardsView.addView(ImageView(this).also {
            it.setImageResource(resources.getIdentifier(
                deck[0].let { card -> "${card.suit}${card.rank}"},
                "drawable",
                packageName
            ))
        })
    }
}