package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.caravanas.databinding.ActivityPhotosBinding
import net.azarquiel.caravanas.model.Parking

class PhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotosBinding
    private lateinit var parking: Parking

    private fun setup() {
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        parking = intent.extras?.get("parking")!! as Parking
        title = parking.name

        binding.content.parkingName.text = parking.name
        binding.content.parkingDesc.text = parking.desc
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}