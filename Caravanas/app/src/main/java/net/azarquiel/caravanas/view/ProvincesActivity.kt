package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.caravanas.databinding.ActivityProvincesBinding

class ProvincesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvincesBinding

    private fun setup() {
        binding = ActivityProvincesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}