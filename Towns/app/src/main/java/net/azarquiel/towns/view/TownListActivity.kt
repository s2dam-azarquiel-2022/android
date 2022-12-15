package net.azarquiel.towns.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.towns.R
import net.azarquiel.towns.databinding.ActivityTownListBinding
import net.azarquiel.towns.model.Community

class TownListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownListBinding
    private lateinit var community: Community

    private fun setup() {
        binding = ActivityTownListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        community = intent.getSerializableExtra("community") as Community

        title = community.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_town_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.searchMenu ->  {
                return  true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}