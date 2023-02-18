package net.azarquiel.caravanas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        val clickHandler = View.OnClickListener {
            (it?.tag as Parking).let { parking ->
                Intent(this, PhotosActivity::class.java).let { intent ->
                    intent.putExtra("parking", parking)
                    this.startActivity(intent)
                }
            }
        }

        LiveAdapter(
            this,
            binding.content.recyclerParkings,
            ViewModelProvider(this)[MainViewModel::class.java].getParkings(town.lat, town.lon),
            ParkingRowBinding::inflate
        ) { binding, view, item: Parking ->
            binding.parkingName.text = item.name
            binding.parkingDesc.text = item.desc
            view.tag = item
            view.setOnClickListener(clickHandler)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}