package net.azarquiel.translator.controller.utils

import android.content.Context

object Utils {
    fun Context.toID(fName: String): Int {
        return this.resources.getIdentifier(fName, "drawable", this.packageName)
    }
}