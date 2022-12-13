package net.azarquiel.alltricks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.alltricks.databinding.ActivityBikeDetailedBinding
import net.azarquiel.alltricks.model.BikeDetailedView
import net.azarquiel.alltricks.viewModel.BikeViewModel

class BikeDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBikeDetailedBinding
    private lateinit var bikeViewModel: BikeViewModel
    private lateinit var bike: BikeDetailedView

    private fun setup() {
        binding = ActivityBikeDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        bikeViewModel = ViewModelProvider(this)[BikeViewModel::class.java]
        bikeViewModel.getById(intent.getSerializableExtra("bikeID") as Int).observe(this) {
            bike = it[0]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}