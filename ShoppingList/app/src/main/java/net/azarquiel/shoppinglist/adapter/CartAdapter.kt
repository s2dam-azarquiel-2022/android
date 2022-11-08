package net.azarquiel.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.handler.ProductClickHandler
import net.azarquiel.shoppinglist.model.Product

class CartAdapter (
    private val context: Context,
    private val layout: Int,
    private val productClickHandler: ProductClickHandler
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var data: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
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

            itemView.setOnClickListener(productClickHandler)

            itemView.tag = item
        }
    }
}