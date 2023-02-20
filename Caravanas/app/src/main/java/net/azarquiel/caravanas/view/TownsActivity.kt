package net.azarquiel.caravanas.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.R
import net.azarquiel.caravanas.databinding.ActivityTownsBinding
import net.azarquiel.caravanas.databinding.TownRowBinding
import net.azarquiel.caravanas.model.FilteredLiveAdapter
import net.azarquiel.caravanas.model.Province
import net.azarquiel.caravanas.model.Town
import net.azarquiel.caravanas.viewModel.MainViewModel

class TownsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTownsBinding
    private lateinit var province: Province
    private lateinit var liveAdapter: FilteredLiveAdapter<TownRowBinding, Town>

    private fun setup() {
        binding = ActivityTownsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        province = intent.extras?.get("province")!! as Province
        title = "Municipios (${province.name})"

        val clickHandler = View.OnClickListener {
            (it?.tag as Town).let { town ->
                Intent(this, ParkingsActivity::class.java).let { intent ->
                    intent.putExtra("town", town)
                    this.startActivity(intent)
                }
            }
        }

        liveAdapter = FilteredLiveAdapter(
            this,
            binding.content.recyclerTowns,
            ViewModelProvider(this)[MainViewModel::class.java].getTowns(province.id),
            TownRowBinding::inflate,
            { data, query -> data.filter { it.name.startsWith(query, true) } }
        ) { binding, view, item: Town ->
            binding.townName.text = item.name
            view.tag = item
            view.setOnClickListener(clickHandler)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_towns_activity, menu)
        (menu.findItem(R.id.search).actionView as SearchView).let {
            it.queryHint = "Search ..."
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(q: String?): Boolean {
                    liveAdapter.query.value = q
                    return false
                }
                override fun onQueryTextSubmit(q: String?): Boolean = false
            })
        }
        return true
    }
}
