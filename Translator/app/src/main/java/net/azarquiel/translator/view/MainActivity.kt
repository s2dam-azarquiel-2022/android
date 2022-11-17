package net.azarquiel.translator.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
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
import net.azarquiel.translator.controller.handler.TTSHandler
import net.azarquiel.translator.controller.utils.Utils.toID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var dictionary: Dictionary
    private lateinit var wordAdapter: WordAdapter
    private lateinit var tts: TextToSpeech

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val langs = resources.getStringArray(R.array.langs)
        langs.forEach { DataFiles.inject(this, "${it}.xml") }

        tts = TextToSpeech(this, TTSHandler())

        dictionary = Dictionary(
            this,
            langs,
            getString(R.string.defaultLangFrom),
            getString(R.string.defaultLangTo)
        )

        wordAdapter = WordAdapter(
            this,
            binding.contentMain.wordAdapter,
            R.layout.word_row,
            dictionary,
            tts,
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

    override fun onDestroy() {
        // Shutdown TTS
        tts.stop()
        tts.shutdown()

        super.onDestroy()
    }
}