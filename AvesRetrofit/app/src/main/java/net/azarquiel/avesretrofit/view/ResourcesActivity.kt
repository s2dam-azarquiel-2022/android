package net.azarquiel.avesretrofit.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.avesretrofit.databinding.ActivityResourcesBinding

class ResourcesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourcesBinding

    private fun setup() {
        binding = ActivityResourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}