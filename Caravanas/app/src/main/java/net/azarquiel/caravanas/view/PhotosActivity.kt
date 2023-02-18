package net.azarquiel.caravanas.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanas.R
import net.azarquiel.caravanas.databinding.ActivityPhotosBinding
import net.azarquiel.caravanas.databinding.PhotoRowBinding
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Login
import net.azarquiel.caravanas.model.Parking
import net.azarquiel.caravanas.model.Photo
import net.azarquiel.caravanas.viewModel.MainViewModel
import kotlin.math.roundToInt

class PhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotosBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var parking: Parking
    private lateinit var avgRate: MutableLiveData<Float?>

    @OptIn(DelicateCoroutinesApi::class)
    private fun setup() {
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        parking = intent.extras?.get("parking")!! as Parking
        title = parking.name
        avgRate = viewModel.getAvgRate(parking.id)

        val rateClickHandler = View.OnClickListener {
            (it.tag as Int).let { rate ->
                GlobalScope.launch {
                    val res = viewModel.rate(parking.id, "$rate")
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@PhotosActivity,
                            if (res) "Rated successfully" else "There was an error",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.refreshAvgRate(avgRate, parking.id)
                    }
                }
            }
        }

        val notLoggedInClickHandler = View.OnClickListener {
            Toast.makeText(this, "You need to login first", Toast.LENGTH_LONG).show()
        }

        binding.content.parkingName.text = parking.name
        binding.content.parkingDesc.text = parking.desc
        avgRate.observe(this) {
            if (it == null || it < 1) binding.content.parkingAvgRate.visibility = View.GONE
            else {
                binding.content.parkingAvgRate.visibility = View.VISIBLE
                binding.content.parkingAvgRate.setDrawable( "ic_${it.roundToInt()}" )
            }
        }
        (1..5).forEach { i -> binding.content.rates.addView(
            ImageView(this, null, 0, R.style.rateImage).also {
                it.setDrawable("ic_${i}")
                it.tag = i
                if (Login.userID == null) {
                    it.alpha = 0.25F
                    it.setOnClickListener(notLoggedInClickHandler)
                } else it.setOnClickListener(rateClickHandler)
            }
        ) }

        LiveAdapter(
            this,
            binding.content.recyclerPhotos,
            viewModel.getPhotos(parking.id),
            PhotoRowBinding::inflate
        ) { binding, _, item: Photo ->
            Picasso.get().load(item.photo).into(binding.photo)
        }
    }

    private fun ImageView.setDrawable(name: String) {
        this.setImageResource(resources.getIdentifier(name, "drawable", packageName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}