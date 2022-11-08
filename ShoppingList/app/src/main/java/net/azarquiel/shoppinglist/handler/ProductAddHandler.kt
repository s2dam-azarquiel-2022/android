package net.azarquiel.shoppinglist.handler

import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.model.Product

class ProductAddHandler(
    private val cart: Cart,
    private val view: View,
): DialogInterface.OnClickListener {
    @Suppress("NOTHING_TO_INLINE")
    private inline fun Int.getText(): String {
        return view.findViewById<EditText>(this).text.toString()
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        cart.saveProduct(
            Product(
            0,
            (R.id.newProductName).getText(),
            (R.id.newProductQuantity).getText().toInt(),
            (R.id.newProductPrice).getText().let {
                if (it.isBlank()) 0F
                else it.toFloat()
            },
        ).also { it.id = it.hashCode() })
    }
}