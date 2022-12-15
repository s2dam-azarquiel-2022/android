package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import net.azarquiel.towns.databinding.ActivityTownDetailedBinding
import net.azarquiel.towns.model.TownView

class TownDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownDetailedBinding
    private lateinit var town: TownView

    private fun setup() {
        binding = ActivityTownDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        town = intent.getSerializableExtra("town") as TownView

        Picasso.get().load(town.image).into(binding.content.townImage)
        binding.content.townName.text = town.name
        binding.content.townProvince.text = town.province

        binding.content.moar.setOnClickListener {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}