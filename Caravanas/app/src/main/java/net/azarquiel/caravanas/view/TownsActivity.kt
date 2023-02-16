package net.azarquiel.caravanas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.caravanas.databinding.ActivityTownsBinding
import net.azarquiel.caravanas.model.Province

class TownsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTownsBinding
    private lateinit var province: Province

    private fun setup() {
        binding = ActivityTownsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        province = intent.extras?.get("province")!! as Province
        title = "Municipios (${province.name})"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}