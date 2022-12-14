package net.azarquiel.foster.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.foster.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    private fun setup() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}