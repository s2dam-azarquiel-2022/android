package net.azarquiel.memorygame

import android.widget.ImageView

fun ImageView.tagToInt(): Int {
    return this.tag.toString().toInt()
}
