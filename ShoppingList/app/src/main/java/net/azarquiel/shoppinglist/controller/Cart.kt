package net.azarquiel.shoppinglist.controller

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import net.azarquiel.shoppinglist.model.Product

class Cart(
    private val cartSharedPrefs: SharedPreferences
) {
    private lateinit var cart: List<Product>

    init {
        getCartProductsFromSharedPrefs()
    }

    private fun getCartProductsFromSharedPrefs() {
        cart = cartSharedPrefs.all.map { product ->
            Gson().fromJson(product.value.toString(), Product::class.java)
        }
    }

    fun saveProduct(product: Product) {
        cartSharedPrefs.edit(true) { putString(product.id.toString(), Gson().toJson(product)) }
    }
}