package net.azarquiel.shoppinglist

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.shoppinglist.adapter.CartAdapter
import net.azarquiel.shoppinglist.controller.Cart
import net.azarquiel.shoppinglist.databinding.ActivityMainBinding
import net.azarquiel.shoppinglist.handler.AddProducBtntHandler

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cart: Cart

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        cart = Cart(getSharedPreferences("products", Context.MODE_PRIVATE))
        cartAdapter = CartAdapter(this, binding.contentMain.cartAdapter, R.layout.product, cart)

        binding.addProductBtn.setOnClickListener(AddProducBtntHandler(this, cart))
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}