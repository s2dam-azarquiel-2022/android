package net.azarquiel.blackjack

import android.content.Context
import android.widget.Toast

fun List<Player>?.toString(): String {
    return this?.joinToString(", ") { player -> player.name } ?: "No one"
}

fun Context.showS(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
