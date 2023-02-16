package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.databinding.ActivityProvincesBinding
import net.azarquiel.caravanas.databinding.ProvinceRowBinding
import net.azarquiel.caravanas.model.Community
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Province
import net.azarquiel.caravanas.viewModel.MainViewModel

class ProvincesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvincesBinding
    private lateinit var community: Community

    private fun setup() {
        binding = ActivityProvincesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        community = intent.extras?.get("community")!! as Community
        title = "Provincias (${community.name})"

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getProvinces(community.id),
            this,
            binding.content.recyclerProvinces,
            ProvinceRowBinding::inflate
        ) { binding, _, item: Province ->
            binding.provinceName.text = item.name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}