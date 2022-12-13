package net.azarquiel.alltricks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.alltricks.databinding.ActivityBikeListBinding

class BikeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBikeListBinding

    private fun setup() {
        binding = ActivityBikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}