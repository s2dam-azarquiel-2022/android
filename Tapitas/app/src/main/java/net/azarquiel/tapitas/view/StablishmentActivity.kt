package net.azarquiel.tapitas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.tapitas.databinding.ActivityStablishmentBinding

class StablishmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStablishmentBinding

    private fun setup() {
        binding = ActivityStablishmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}