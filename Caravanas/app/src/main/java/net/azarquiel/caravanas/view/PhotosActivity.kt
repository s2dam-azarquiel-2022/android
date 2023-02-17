package net.azarquiel.caravanas.view

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.caravanas.databinding.ActivityPhotosBinding
import net.azarquiel.caravanas.databinding.PhotoRowBinding
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Parking
import net.azarquiel.caravanas.model.Photo
import net.azarquiel.caravanas.viewModel.MainViewModel

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

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getPhotos(parking.id),
            this,
            binding.content.recyclerPhotos,
            PhotoRowBinding::inflate
        ) { binding, _, item: Photo ->
            Picasso.get().load(item.photo).into(binding.photo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}