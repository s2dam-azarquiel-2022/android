package net.azarquiel.translator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import net.azarquiel.translator.adapter.WordAdapter
import net.azarquiel.translator.controller.DataFiles
import net.azarquiel.translator.controller.Dictionary
import net.azarquiel.translator.controller.LangSwitch
import net.azarquiel.translator.databinding.ActivityMainBinding
import net.azarquiel.translator.handler.SearchWordHandler
import net.azarquiel.translator.utils.Utils.toID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var langSwitch: LangSwitch
    private lateinit var dictionary: Dictionary
    private lateinit var wordAdapter: WordAdapter

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        langSwitch = LangSwitch("en", "es")
        dictionary = Dictionary(this, langSwitch)
        wordAdapter = WordAdapter(this, binding.contentMain.wordAdapter, R.layout.word_row, dictionary, langSwitch)

        DataFiles.inject(this, "en.xml")
        DataFiles.inject(this, "es.xml")
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
        searchView.setOnQueryTextListener(SearchWordHandler(langSwitch, dictionary, wordAdapter))
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.langSwitchFrom -> {
                langSwitch.nextLangFrom()
                item.setIcon(this.toID("flag_${langSwitch.currentLangFrom}"))
                wordAdapter.notifyDataSetChanged()
                true
            }
            R.id.langSwitchTo -> {
                langSwitch.nextLangTo()
                item.setIcon(this.toID("flag_${langSwitch.currentLangTo}"))
                wordAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}