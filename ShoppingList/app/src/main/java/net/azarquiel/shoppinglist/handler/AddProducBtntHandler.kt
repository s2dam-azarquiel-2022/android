package net.azarquiel.shoppinglist.handler

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.model.Product

class AddProducBtntHandler(
    private val context: Context,
    private val cart: Cart
) : View.OnClickListener {
    override fun onClick(p0: View?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.new_product_alert, null)
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.addProductDialogTitle))
            .setView(dialogView)
            .setPositiveButton(
                context.getString(R.string.addProductDialogSave),
                ProductAddHandler(dialogView)
            )
            .setNegativeButton(
                context.getString(R.string.addProductDialogCancel)
            ) { _, _ -> /* do nothing */ }
            .show()
    }

    inner class ProductAddHandler(
        private val view: View,
    ): DialogInterface.OnClickListener {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.getText(): String {
            return view.findViewById<EditText>(this).text.toString()
        }

        override fun onClick(p0: DialogInterface?, p1: Int) {
            cart.saveProduct(Product(
                cart.getId(),
                (R.id.newProductName).getText(),
                (R.id.newProductQuantity).getText().toInt(),
                (R.id.newProductPrice).getText().let {
                    if (it.isBlank()) 0F
                    else it.toFloat()
                },
            ))
        }
    }
}