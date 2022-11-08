package net.azarquiel.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.shoppinglist.R
import net.azarquiel.shoppinglist.model.Product

class CartAdapter (
    private val context: Context,
    private val layout: Int,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var data: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false),
        context,
        ClickHandler(context)
    )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(data: MutableList<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ClickHandler(private val context: Context): View.OnClickListener {
        override fun onClick(v: View?) {
            (v?.tag as Product).let {
                it.bought = !it.bought
                Log.d("aru", "${it.bought}")

                (v as CardView).setCardBackgroundColor(ContextCompat.getColor(context,
                    if (it.bought) R.color.productBGColorBought
                    else R.color.productBGColor
                ))
            }
        }
    }

    class ViewHolder(
        layout: View,
        private val context: Context,
        private val clickHandler: ClickHandler
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

            itemView.setOnClickListener(clickHandler)

            itemView.tag = item
        }
    }
}