package net.azarquiel.tapitas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.tapitas.databinding.ActivityTapaDetailedBinding
import net.azarquiel.tapitas.model.TapaDetailedView
import net.azarquiel.tapitas.viewModel.TapaViewModel

class TapaDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTapaDetailedBinding
    private var tapaID: Int = -1
    private lateinit var tapaViewModel: TapaViewModel

    private fun setup() {
        binding = ActivityTapaDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        tapaID = intent.getIntExtra("tapaID", -1)
        tapaViewModel = ViewModelProvider(this)[TapaViewModel::class.java]

        tapaViewModel.getById(tapaID).observe(this) { tapas ->
            drawTapa(tapas[0])
        }

        binding.fab.setOnClickListener { tapaViewModel.toggleFav(tapaID) }
    }

    private fun drawTapa(tapa: TapaDetailedView) {
        binding.content.tapaName.text = tapa.name
        binding.content.tapaDescription.text = tapa.description
        binding.content.stablishmentName.text = tapa.stablishmentName
        binding.content.stablishmentDirection.text = tapa.direction

        Picasso.get().load("http://82.223.108.85/storage/${tapa.imageURL}")
            .into(binding.content.tapaImage)
        Picasso.get().load("http://82.223.108.85/storage/${tapa.stablishmentImageURL}")
            .into(binding.content.stablishmentImage)

        binding.fab.setImageResource(when (tapa.favorite) {
            1 -> android.R.drawable.star_on
            else -> android.R.drawable.star_off
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}