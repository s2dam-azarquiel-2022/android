package net.azarquiel.translator.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import net.azarquiel.translator.R
import net.azarquiel.translator.controller.adapter.WordAdapter
import net.azarquiel.translator.controller.DataFiles
import net.azarquiel.translator.controller.Dictionary
import net.azarquiel.translator.databinding.ActivityMainBinding
import net.azarquiel.translator.controller.handler.SearchWordHandler
import net.azarquiel.translator.controller.utils.Utils.toID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var dictionary: Dictionary
    private lateinit var wordAdapter: WordAdapter

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DataFiles.inject(this, "en.xml")
        DataFiles.inject(this, "es.xml")

        dictionary = Dictionary(this, "en", "es")
        wordAdapter = WordAdapter(
            this,
            binding.contentMain.wordAdapter,
            R.layout.word_row,
            dictionary
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        searchView = (menu.findItem(R.id.searchWord).actionView) as SearchView
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(SearchWordHandler(dictionary, wordAdapter))
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.langSwitchFrom -> {
                dictionary.nextLangFrom()
                item.setIcon(this.toID("flag_${dictionary.currentLangFrom}"))
                wordAdapter.notifyDataSetChanged()
                true
            }
            R.id.langSwitchTo -> {
                dictionary.nextLangTo()
                item.setIcon(this.toID("flag_${dictionary.currentLangTo}"))
                wordAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}