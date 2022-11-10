package net.azarquiel.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.model.Product

class CartAdapter (
    private val context: Context,
    private val thisView: RecyclerView,
    private val itemLayout: Int,
    private val cart: Cart,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var data: MutableList<Product> = mutableListOf()
    private val productClickHandler = ProductClickHandler()

    init {
        ItemTouchHelper(ProductSwipeHandler(ItemTouchHelper.RIGHT)).attachToRecyclerView(thisView)

        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)

        setProducts(cart.products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(data: MutableList<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        layout: View,
    ) : RecyclerView.ViewHolder(layout) {

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(stringId: Int, vararg: Any) {
            itemView.findViewById<TextView>(this).text = context.getString(stringId, vararg)
        }

        fun bind(item: Product) {
            R.id.productName.setText(R.string.productName, item.name)
            R.id.productPrice.setText(R.string.productPrice, item.price)
            R.id.productQuantity.setText(R.string.productQuantity, item.quantity)

            if (item.bought) (itemView as CardView).setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.productBGColorBought)
            )

            itemView.tag = item
            itemView.setOnClickListener(productClickHandler)
        }
    }

    inner class ProductClickHandler: View.OnClickListener {
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

    inner class ProductSwipeHandler(
        direction: Int
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
            this@CartAdapter.notifyItemRemoved(pos)
            Snackbar.make(
                thisView,
                product.name,
                Snackbar.LENGTH_LONG
            ).setAction("Undo") {
                cart.saveProduct(product, pos)
                this@CartAdapter.notifyItemInserted(pos)
            }.show()
        }
    }
}