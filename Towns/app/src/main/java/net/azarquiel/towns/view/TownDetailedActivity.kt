package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.towns.R
import net.azarquiel.towns.databinding.ActivityTownDetailedBinding
import net.azarquiel.towns.model.TownView
import net.azarquiel.towns.view.adapter.TownAdapter
import net.azarquiel.towns.viewModel.TownViewModel

class TownDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownDetailedBinding
    private lateinit var townViewModel: TownViewModel
    private var townID: Int = -1

    private fun setup() {
        binding = ActivityTownDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        townViewModel = ViewModelProvider(this)[TownViewModel::class.java]
        townID = intent.getSerializableExtra("townID") as Int

        townViewModel.getById(townID).observe(this) {
            drawTown(it[0])
        }

        binding.content.moar.setOnClickListener {
        }

        binding.fab.setOnClickListener {
            townViewModel.toggleFav(townID)
        }
    }

    private fun drawTown(town: TownView) {
        Picasso.get().load(town.image).into(binding.content.townImage)
        binding.content.townName.text = town.name
        binding.content.townProvince.text = town.province
        binding.fab.setImageResource(when (town.favorite) {
            1 -> android.R.drawable.star_on
            else -> android.R.drawable.star_off
        })
        title = town.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}