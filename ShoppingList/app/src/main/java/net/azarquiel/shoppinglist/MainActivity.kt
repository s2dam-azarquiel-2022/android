package net.azarquiel.shoppinglist

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import net.azarquiel.shoppinglist.adapter.CartAdapter
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.databinding.ActivityMainBinding
import net.azarquiel.shoppinglist.handler.ProductClickHandler
import net.azarquiel.shoppinglist.handler.ProductSwipeHandler
import net.azarquiel.shoppinglist.model.Product


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cart: Cart
    private lateinit var productClickHandler: ProductClickHandler

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        cart = Cart(getSharedPreferences("products", Context.MODE_PRIVATE))

        productClickHandler = ProductClickHandler(this, cart)

        cartAdapter = CartAdapter(this, R.layout.product, productClickHandler)

        binding.contentMain.cartAdapter.adapter = cartAdapter
        binding.contentMain.cartAdapter.layoutManager = LinearLayoutManager(this)

        ItemTouchHelper(ProductSwipeHandler(
            ItemTouchHelper.RIGHT,
            cart,
            cartAdapter,
            binding.contentMain.cartAdapter)
        ).attachToRecyclerView(binding.contentMain.cartAdapter)

        cartAdapter.setProducts(cart.products)

        binding.fab.setOnClickListener { showNewProductDialog() }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun View.getText(id: Int): String {
        return this.findViewById<TextInputEditText>(id).text.toString()
    }

    private fun showNewProductDialog() {
        val view = layoutInflater.inflate(R.layout.alert, null)
        AlertDialog.Builder(this)
            .setTitle("Add product")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                cart.saveProduct(Product(
                    0,
                    view.getText(R.id.newProductName),
                    view.getText(R.id.newProductQuantity).toInt(),
                    view.getText(R.id.newProductPrice).toFloat(),
                ).also { it.id = it.hashCode() })
            }
            .setNegativeButton("Cancel") { _, _ -> /* do nothing */ }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}