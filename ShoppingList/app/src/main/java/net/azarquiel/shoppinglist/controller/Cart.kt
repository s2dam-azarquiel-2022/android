package net.azarquiel.shoppinglist.controller

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import net.azarquiel.shoppinglist.model.Product

class Cart(
    private val cartSharedPrefs: SharedPreferences,
    private val idCountSharedPrefs: SharedPreferences
) {
    lateinit var products: MutableList<Product>
    private var id: Int = 0

    init {
        getCartProductsFromSharedPrefs()
        getIdFromSharedPrefs()
    }

    private fun getCartProductsFromSharedPrefs() {
        products = cartSharedPrefs.all.map { product ->
            Gson().fromJson(product.value.toString(), Product::class.java)
        }.toMutableList()
    }

    private fun getIdFromSharedPrefs() {
        id = idCountSharedPrefs.getInt("c", 0)
    }

    fun getId(): Int {
        val result = id
        id++
        idCountSharedPrefs.edit(true) { putInt("c", id); }
        return result
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

    fun updateProduct(product: Product, pos: Int) {
        cartSharedPrefs.edit(true) { putString((product.id.toString()), Gson().toJson(product)) }
        products[pos] = product
    }
}