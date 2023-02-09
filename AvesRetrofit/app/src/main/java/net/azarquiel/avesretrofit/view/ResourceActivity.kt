package net.azarquiel.avesretrofit.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.avesretrofit.databinding.ActivityResourceBinding

class ResourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourceBinding

    private fun setup() {
        binding = ActivityResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}