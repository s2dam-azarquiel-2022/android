package net.azarquiel.tapitas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.tapitas.R
import net.azarquiel.tapitas.databinding.ActivityStablishmentBinding
import net.azarquiel.tapitas.model.StablishmentView
import net.azarquiel.tapitas.view.adapter.RecipeAdapter
import net.azarquiel.tapitas.viewModel.StablishmentViewModel

class StablishmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStablishmentBinding
    private var stablishmentID: Int = -1
    private lateinit var stablishmentViewModel: StablishmentViewModel

    private fun setup() {
        binding = ActivityStablishmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        stablishmentID = intent.getIntExtra("stablishmentID", -1)
        stablishmentViewModel = ViewModelProvider(this)[StablishmentViewModel::class.java]

        stablishmentViewModel.getById(stablishmentID).observe(this) { draw(it[0]) }

        RecipeAdapter(
            this,
            binding.content.stablishmentRecipesRecycler,
            R.layout.recipe_row,
            stablishmentViewModel,
            stablishmentID,
        )
    }

    private fun draw(stablishment: StablishmentView) {
        binding.content.stablishmentName.text = stablishment.name
        binding.content.stablishmentDirection.text = stablishment.direction

        Picasso.get().load("http://82.223.108.85/storage/${stablishment.imageURL}")
            .into(binding.content.stablishmentImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}