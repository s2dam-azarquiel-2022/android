package net.azarquiel.foster.model

import android.content.SharedPreferences
import androidx.core.content.edit

object Favorites {
    private var sharedPreferences: SharedPreferences? = null

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    fun getById(id: Int): Boolean =
        sharedPreferences!!.getBoolean("$id", false)

    fun setById(id: Int, favorite: Boolean) =
        sharedPreferences!!.edit(true) { putBoolean("$id", favorite) }
}