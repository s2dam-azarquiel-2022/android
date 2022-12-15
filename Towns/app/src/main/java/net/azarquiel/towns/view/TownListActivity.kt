package net.azarquiel.towns.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
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
        (menu.findItem(R.id.searchMenu).actionView as SearchView).let {
            it.queryHint = "Search ..."
            it.setOnQueryTextListener(SearchHandler())
        }
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

    inner class SearchHandler : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(p0: String?): Boolean {
            return false
        }

        override fun onQueryTextSubmit(p0: String?): Boolean {
            return false
        }
    }
}