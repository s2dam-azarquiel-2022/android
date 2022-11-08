package net.azarquiel.shoppinglist.handler

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.model.Product

class ProductClickHandler(
    private val context: Context,
    private val cart: Cart
): View.OnClickListener {
    override fun onClick(v: View?) {
        (v?.tag as Product).let {
            it.bought = !it.bought
            cart.updateProduct(it)

            (v as CardView).setCardBackgroundColor(
                ContextCompat.getColor(context,
                if (it.bought) R.color.productBGColorBought
                else R.color.productBGColor
            ))
        }
    }
}
