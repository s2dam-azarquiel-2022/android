package net.azarquiel.translator.handler

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import net.azarquiel.translator.adapter.WordAdapter
import net.azarquiel.translator.controller.Dictionary
import net.azarquiel.translator.controller.LangSwitch

class SearchWordHandler(
    private val langSwitch: LangSwitch,
    private val dictionary: Dictionary,
    private val wordAdapter: WordAdapter,
) : SearchView.OnQueryTextListener {
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(query: String): Boolean {
        val a = dictionary.langWords[langSwitch.currentLangFromPos].filter {
            it!!.word.startsWith(query)
        }.map { it!!.id }
        wordAdapter.setData(a)
        wordAdapter.notifyDataSetChanged()
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
}