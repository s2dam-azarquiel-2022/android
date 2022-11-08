package net.azarquiel.shoppinglist.controller

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import net.azarquiel.shoppinglist.model.Product

class Cart(
    private val cartSharedPrefs: SharedPreferences
) {
    lateinit var products: MutableList<Product>

    init {
        getCartProductsFromSharedPrefs()
    }

    private fun getCartProductsFromSharedPrefs() {
        products = cartSharedPrefs.all.map { product ->
            Gson().fromJson(product.value.toString(), Product::class.java)
        }.toMutableList()
    }

    fun saveProduct(product: Product) {
        cartSharedPrefs.edit(true) { putString(product.id.toString(), Gson().toJson(product)) }
        products.add(product)
    }

    fun saveProduct(product: Product, pos: Int) {
        cartSharedPrefs.edit(true) { putString(product.id.toString(), Gson().toJson(product)) }
        products.add(pos, product)
    }

    fun removeProduct(pos: Int) {
        cartSharedPrefs.edit(true) { remove(products[pos].id.toString()) }
        products.removeAt(pos)
    }

    fun updateProduct(product: Product) {
        cartSharedPrefs.edit(true) { putString((product.id.toString()), Gson().toJson(product)) }
    }
}