package net.azarquiel.alltricks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.alltricks.databinding.ActivityBikeDetailedBinding
import net.azarquiel.alltricks.model.BikeDetailedView
import net.azarquiel.alltricks.viewModel.BikeViewModel

class BikeDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBikeDetailedBinding
    private lateinit var bikeViewModel: BikeViewModel

    private fun setup() {
        binding = ActivityBikeDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        bikeViewModel = ViewModelProvider(this)[BikeViewModel::class.java]

        bikeViewModel.getById(intent.getSerializableExtra("bikeID") as Int).observe(this) {
            drawBike(it[0])
        }
    }

    private fun drawBike(bike: BikeDetailedView) {
        Picasso.get().load(bike.photo).into(binding.contentMain.bikePhoto)
        binding.contentMain.brandName.text = bike.brand
        binding.contentMain.bikeName.text = bike.description
        binding.contentMain.bikePrice.text = bike.price
        binding.contentMain.bikeStar.setImageResource(when (bike.favorite) {
            1 -> android.R.drawable.star_on
            else -> android.R.drawable.star_off
        })
        binding.contentMain.bikeStar.setOnClickListener {
             bikeViewModel.toggleFavorite(bike.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}