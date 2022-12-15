package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.towns.databinding.ActivityTownWebBinding

class TownWebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownWebBinding

    private fun setup() {
        binding = ActivityTownWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}