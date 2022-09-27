package net.azarquiel.guesssum

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

fun TextView.toInt(): Int {
    return this.text.toString().toInt()
}

fun List<TextView>.clear() {
    for (textView in this) { textView.text = "" }
}

fun ImageView.clear() {
    this.setImageDrawable(null)
}
