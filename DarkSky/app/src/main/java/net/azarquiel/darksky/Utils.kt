package net.azarquiel.darksky

import android.graphics.Color

object Utils {
    fun Float.between(min: Int, max: Int): Int =
        if (this > max) max
        else if (this < min) min
        else this.toInt()

    fun Float.min(min: Int): Int =
        if (this < min) min
        else this.toInt()

    fun Int.darken(): Int {
        return Color.argb(
            80,
            (Color.red(this) * 0.8F).min(0),
            (Color.green(this) * 0.8F).min(0),
            (Color.blue(this) * 0.8F).min(0)
        )
    }

    fun colorFromTemp(temp: Float): Int {
        // Took me a while to make this
        return Color.rgb(
            ((temp + 30F) * 3.4F).between(0, 255),
            (100F - temp).between(0, 100),
            (255F - (temp * 3.4F)).between(0, 255)
        )
    }
}