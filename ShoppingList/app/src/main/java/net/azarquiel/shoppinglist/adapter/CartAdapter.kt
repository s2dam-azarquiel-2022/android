package net.azarquiel.shoppinglist.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
        ItemTouchHelper(ProductSwipeLeftHandler()).attachToRecyclerView(thisView)
        ItemTouchHelper(ProductSwipeRightHandler()).attachToRecyclerView(thisView)

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

    abstract inner class ProductSwipeHandler(
        from: Int,
        direction: Int
    ) : ItemTouchHelper.SimpleCallback(from, direction) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        abstract override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    }

    inner class ProductSwipeLeftHandler : ProductSwipeHandler(
        ItemTouchHelper.LEFT,
        ItemTouchHelper.RIGHT
    ) {
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

    inner class ProductSwipeRightHandler : ProductSwipeHandler(
        ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT
    ) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun View.setText(id: Int, text: String) {
            this.findViewById<EditText>(id).setText(text)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val product = cart.products[pos]
            val dialogView = LayoutInflater.from(context).inflate(R.layout.new_product_alert, null)
            dialogView.setText(R.id.newProductName, product.name)
            dialogView.setText(R.id.newProductQuantity, product.quantity.toString())
            dialogView.setText(R.id.newProductPrice, product.price.toString())
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.addProductDialogTitle))
                .setView(dialogView)
                .setPositiveButton(
                    context.getString(R.string.addProductDialogSave),
                    ProductEditHandler(dialogView, product, pos)
                )
                .setNegativeButton(
                    context.getString(R.string.addProductDialogCancel)
                ) { _, _ -> this@CartAdapter.notifyItemChanged(pos) }
                .show()
        }

        inner class ProductEditHandler(
            private val view: View,
            private val product: Product,
            private val pos: Int
        ): DialogInterface.OnClickListener {
            @Suppress("NOTHING_TO_INLINE")
            private inline fun Int.getText(): String {
                return view.findViewById<EditText>(this).text.toString()
            }

            override fun onClick(p0: DialogInterface?, p1: Int) {
                cart.removeProduct(pos)
                this@CartAdapter.notifyItemRemoved(pos)
                cart.saveProduct(
                    Product(
                        0,
                        (R.id.newProductName).getText(),
                        (R.id.newProductQuantity).getText().toInt(),
                        (R.id.newProductPrice).getText().let {
                            if (it.isBlank()) 0F
                            else it.toFloat()
                        },
                    ).also { it.id = it.hashCode() },
                    pos
                )
                this@CartAdapter.notifyItemInserted(pos)

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
}