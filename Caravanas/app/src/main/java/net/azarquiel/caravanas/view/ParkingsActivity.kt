package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.databinding.ActivityParkingsBinding
import net.azarquiel.caravanas.databinding.ParkingRowBinding
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Parking
import net.azarquiel.caravanas.model.Town
import net.azarquiel.caravanas.viewModel.MainViewModel

class ParkingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParkingsBinding
    private lateinit var town: Town

    private fun setup() {
        binding = ActivityParkingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        town = intent.extras?.get("town")!! as Town
        title = "Parkings (${town.name})"

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getParkings(town.lat, town.lon),
            this,
            binding.content.recyclerParkings,
            ParkingRowBinding::inflate
        ) { binding, _, item: Parking ->
            binding.parkingName.text = item.name
            binding.parkingDesc.text = item.desc
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}