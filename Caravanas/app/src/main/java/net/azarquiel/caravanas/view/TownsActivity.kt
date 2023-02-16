package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.databinding.ActivityTownsBinding
import net.azarquiel.caravanas.databinding.TownRowBinding
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Province
import net.azarquiel.caravanas.model.Town
import net.azarquiel.caravanas.model.Towns
import net.azarquiel.caravanas.viewModel.MainViewModel

class TownsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTownsBinding
    private lateinit var province: Province

    private fun setup() {
        binding = ActivityTownsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        province = intent.extras?.get("province")!! as Province
        title = "Municipios (${province.name})"

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getTowns(province.id),
            this,
            binding.content.recyclerTowns,
            TownRowBinding::inflate
        ) { binding, _, item: Town ->
            binding.townName.text = item.name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}