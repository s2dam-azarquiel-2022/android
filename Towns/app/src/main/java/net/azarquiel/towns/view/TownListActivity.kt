package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.towns.databinding.ActivityTownListBinding

class TownListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownListBinding

    private fun setup() {
        binding = ActivityTownListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}