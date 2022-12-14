package net.azarquiel.foster.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.foster.databinding.ActivityProductDetailedBinding

class ProductDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailedBinding

    private fun setup() {
        binding = ActivityProductDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}