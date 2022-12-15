package net.azarquiel.towns.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.towns.R
import net.azarquiel.towns.databinding.ActivityMainBinding
import net.azarquiel.towns.model.DBFiles
import net.azarquiel.towns.view.adapter.CommunityAdapter
import net.azarquiel.towns.viewModel.CommunityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DBFiles.inject(this, "db.sqlite")

        CommunityAdapter(
            this,
            binding.content.comunityRecycler,
            R.layout.community_row,
            ViewModelProvider(this)[CommunityViewModel::class.java]
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}