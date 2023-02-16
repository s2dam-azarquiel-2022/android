package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.caravanas.databinding.ActivityParkingsBinding
import net.azarquiel.caravanas.model.Town

class ParkingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParkingsBinding
    private lateinit var town: Town

    private fun setup() {
        binding = ActivityParkingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        town = intent.extras?.get("town")!! as Town
        title = "Parkings (${town.name})"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}