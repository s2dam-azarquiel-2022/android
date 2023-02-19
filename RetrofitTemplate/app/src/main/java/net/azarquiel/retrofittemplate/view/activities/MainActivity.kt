package net.azarquiel.retrofittemplate.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.retrofittemplate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun initVars() {
    }

    private fun paint() {
    }

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initVars()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        paint()
    }
}
