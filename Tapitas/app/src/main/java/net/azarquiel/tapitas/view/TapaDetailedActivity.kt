package net.azarquiel.tapitas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.tapitas.databinding.ActivityTapaDetailedBinding

class TapaDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTapaDetailedBinding

    private fun setup() {
        binding = ActivityTapaDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}