package net.azarquiel.alltricks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.alltricks.R
import net.azarquiel.alltricks.databinding.ActivityBikeListBinding
import net.azarquiel.alltricks.model.Brand
import net.azarquiel.alltricks.view.adapter.BikeAdapter
import net.azarquiel.alltricks.viewModel.BikeViewModel

class BikeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBikeListBinding

    private fun setup() {
        binding = ActivityBikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        BikeAdapter(
            this,
            binding.contentMain.bikeRecycler,
            R.layout.bike_row,
            ViewModelProvider(this)[BikeViewModel::class.java],
            intent.getSerializableExtra("brand") as Brand
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}