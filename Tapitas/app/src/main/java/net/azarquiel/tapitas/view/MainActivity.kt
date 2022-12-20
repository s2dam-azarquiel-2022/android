package net.azarquiel.tapitas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.tapitas.R
import net.azarquiel.tapitas.databinding.ActivityMainBinding
import net.azarquiel.tapitas.model.DBFiles
import net.azarquiel.tapitas.model.TapaView
import net.azarquiel.tapitas.view.adapter.TapaAdapter
import net.azarquiel.tapitas.viewModel.TapaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tapaAdapter: TapaAdapter
    private var tapas: List<TapaView> = emptyList()
    private var query: String = ""
    private var showingFavorites: Boolean = false

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DBFiles.inject(this, "db.db")

        tapaAdapter = TapaAdapter(
            this,
            binding.content.recyclerTapitas,
            R.layout.tapa_row,
        )

        ViewModelProvider(this)[TapaViewModel::class.java]
            .getAll()
            .observe(this) {
                tapas = it
                applyQuery()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu.findItem(R.id.searchMenu).actionView as SearchView).let {
            it.queryHint = "Search ..."
            it.setOnQueryTextListener(SearchHandler())
        }
        return true
    }

    private fun applyQuery() {
        tapaAdapter.setData(tapas.filter {
            it.name.contains(query, true) && (if (showingFavorites) it.favorite == 1 else true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.favoriteMenu -> {
                showingFavorites = !showingFavorites
                (item.setIcon(
                    if (showingFavorites) android.R.drawable.star_big_on
                    else android.R.drawable.star_big_off
                ))
                applyQuery()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}