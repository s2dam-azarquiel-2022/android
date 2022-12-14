package net.azarquiel.foster.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.foster.R
import net.azarquiel.foster.databinding.ActivityProductListBinding
import net.azarquiel.foster.model.Favorites
import net.azarquiel.foster.model.ProductDetailedView
import net.azarquiel.foster.viemModel.ProductViewModel
import net.azarquiel.foster.view.adapter.ProductAdapter

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private var dataCache: List<ProductDetailedView>? = null

    private fun setup() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        productAdapter = ProductAdapter(
            this,
            binding.content.recyclerProductList,
            R.layout.product_row,
            ViewModelProvider(this)[ProductViewModel::class.java],
            intent.getSerializableExtra("categoryID") as Int
        )

        binding.fab.setOnClickListener { productAdapter.toggleShow() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}