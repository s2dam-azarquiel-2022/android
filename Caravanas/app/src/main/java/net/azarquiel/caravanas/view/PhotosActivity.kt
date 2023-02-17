package net.azarquiel.caravanas.view

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.caravanas.R
import net.azarquiel.caravanas.databinding.ActivityPhotosBinding
import net.azarquiel.caravanas.databinding.PhotoRowBinding
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Parking
import net.azarquiel.caravanas.model.Photo
import net.azarquiel.caravanas.viewModel.MainViewModel
import kotlin.math.roundToInt

class PhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotosBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var parking: Parking

    private fun setup() {
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        parking = intent.extras?.get("parking")!! as Parking
        title = parking.name

        binding.content.parkingName.text = parking.name
        binding.content.parkingDesc.text = parking.desc
        viewModel.getAvgRate(parking.id).observe(this) {
            binding.content.parkingAvgRate.setDrawable(it?.let { "ic_${it.roundToInt()}" })
        }
        (1..5).forEach { i -> binding.content.rates.addView(
            ImageView(this, null, 0, R.style.rateImage).also { it.setDrawable("ic_${i}") }
        ) }

        LiveAdapter(
            viewModel.getPhotos(parking.id),
            this,
            binding.content.recyclerPhotos,
            PhotoRowBinding::inflate
        ) { binding, _, item: Photo ->
            Picasso.get().load(item.photo).into(binding.photo)
        }
    }

    private fun ImageView.setDrawable(name: String?) {
        if (name == null) this.setImageDrawable(null)
        else this.setImageResource(resources.getIdentifier(name, "drawable", packageName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}