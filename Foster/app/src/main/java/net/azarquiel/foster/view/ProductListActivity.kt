package net.azarquiel.foster.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.foster.R
import net.azarquiel.foster.databinding.ActivityProductListBinding
import net.azarquiel.foster.viemModel.ProductViewModel
import net.azarquiel.foster.view.adapter.ProductAdapter

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    private fun setup() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        ProductAdapter(
            this,
            binding.content.recyclerProductList,
            R.layout.product_row,
            ViewModelProvider(this)[ProductViewModel::class.java],
            intent.getSerializableExtra("categoryID") as Int
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}