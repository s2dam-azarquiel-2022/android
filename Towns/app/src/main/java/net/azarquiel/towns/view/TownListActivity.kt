package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.towns.databinding.ActivityTownListBinding
import net.azarquiel.towns.model.Community

class TownListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownListBinding
    private lateinit var community: Community

    private fun setup() {
        binding = ActivityTownListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        community = intent.getSerializableExtra("community") as Community

        title = community.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}