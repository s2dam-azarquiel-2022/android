package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.caravanas.databinding.ActivityProvincesBinding
import net.azarquiel.caravanas.model.Community

class ProvincesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvincesBinding
    private lateinit var community: Community

    private fun setup() {
        binding = ActivityProvincesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        community = intent.extras?.get("community")!! as Community
        title = "Provincias (${community.name})"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}