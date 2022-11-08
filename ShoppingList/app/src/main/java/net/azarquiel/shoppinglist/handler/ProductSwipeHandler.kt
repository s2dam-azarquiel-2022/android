package net.azarquiel.shoppinglist.handler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.azarquiel.shoppinglist.adapter.CartAdapter
import net.azarquiel.shoppinglist.controller.Cart

class ProductSwipeHandler(
    direction: Int,
    private val cart: Cart,
    private val cartAdapter: CartAdapter,
    private val recyclerView: RecyclerView,
) : ItemTouchHelper.SimpleCallback(0, direction) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        val product = cart.products[pos]
        cart.removeProduct(pos)
        cartAdapter.notifyItemRemoved(pos)
        Snackbar.make(
            recyclerView,
            product.name,
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            cart.saveProduct(product, pos)
            cartAdapter.notifyItemInserted(pos)
        }.show()
    }
}
