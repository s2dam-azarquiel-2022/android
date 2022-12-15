package net.azarquiel.towns.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.towns.R
import net.azarquiel.towns.databinding.ActivityTownListBinding
import net.azarquiel.towns.model.Community
import net.azarquiel.towns.model.TownView
import net.azarquiel.towns.view.adapter.TownAdapter
import net.azarquiel.towns.viewModel.TownViewModel

class TownListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTownListBinding
    private lateinit var community: Community
    private lateinit var townAdapter: TownAdapter
    private var towns: List<TownView>? = null
    private var query: String? = null
    private var showingFavorites: Boolean = false

    private fun setup() {
        binding = ActivityTownListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        community = intent.getSerializableExtra("community") as Community

        title = community.name

        townAdapter = TownAdapter(this, binding.content.townListRecycler, R.layout.town_row)

        ViewModelProvider(this)[TownViewModel::class.java]
            .getByCommunityID(community.id)
            .observe(this) {
                towns = it
                applyQuery()
            }

        binding.fab.setOnClickListener {
            showingFavorites = !showingFavorites
            applyQuery()
        }
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

    private fun applyQuery() {
        townAdapter.setData(towns!!.filter {
            (query?.let { q -> it.province.contains(q) } ?: true) &&
                    (if (showingFavorites) it.favorite == 1 else true)
        })
    }

    inner class SearchHandler : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String): Boolean {
            query = q
            applyQuery()
            return false
        }

        override fun onQueryTextSubmit(p0: String?): Boolean {
            return false
        }
    }
}