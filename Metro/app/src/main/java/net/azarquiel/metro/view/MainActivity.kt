package net.azarquiel.metro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.metro.R
import net.azarquiel.metro.model.DBFiles
import net.azarquiel.metro.viewModel.LineViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var lineViewModel: LineViewModel

    private fun setup() {
        setContentView(R.layout.activity_main)
        DBFiles.inject(this, "Metro.db")
        lineViewModel = ViewModelProvider(this)[LineViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}