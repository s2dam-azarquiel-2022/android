package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.towns.databinding.ActivityTownDetailedBinding

class TownDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownDetailedBinding

    private fun setup() {
        binding = ActivityTownDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}